package Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.GioHang.GioHang;
import Model.GioHang.GioHangBo;
import Model.HoaDon.HoaDonBO;

/**
 * Servlet implementation class PaymentController
 * Xử lý thanh toán cho khách hàng
 */
@WebServlet("/PaymentController")
public class PaymentController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        GioHangBo gioHang = (GioHangBo) session.getAttribute("gio");

        // Kiểm tra giỏ hàng
        if (gioHang == null || gioHang.getDs().isEmpty()) {
            request.setAttribute("error", "Giỏ hàng trống! Vui lòng thêm món ăn vào giỏ hàng.");
            RequestDispatcher rd = request.getRequestDispatcher("TrangChuController");
            rd.forward(request, response);
            return;
        }

        // Tính tổng tiền
        long tongTien = 0;
        List<GioHang> dsMon = gioHang.getDs();
        for (GioHang item : dsMon) {
            tongTien += item.getThanhTien();
        }

        // Lưu thông tin thanh toán vào session
        session.setAttribute("tongTienThanhToan", tongTien);
        session.setAttribute("soLuongMon", dsMon.size());

        // Hiển thị trang thanh toán
        request.setAttribute("gioHang", gioHang);
        request.setAttribute("tongTien", tongTien);

        RequestDispatcher rd = request.getRequestDispatcher("ThanhToan.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if ("processPayment".equals(action)) {
            processPayment(request, response);
        } else if ("confirmPayment".equals(action)) {
            confirmPayment(request, response);
        } else {
            doGet(request, response);
        }
    }

    private void processPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Lấy thông tin thanh toán từ form
        String paymentMethod = request.getParameter("paymentMethod");
        String customerName = request.getParameter("customerName");
        String customerPhone = request.getParameter("customerPhone");
        String customerEmail = request.getParameter("customerEmail");
        String deliveryAddress = request.getParameter("deliveryAddress");
        String notes = request.getParameter("notes");

        // Validation
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng chọn phương thức thanh toán!");
            doGet(request, response);
            return;
        }

        if (customerName == null || customerName.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập tên khách hàng!");
            doGet(request, response);
            return;
        }

        if (customerPhone == null || customerPhone.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập số điện thoại!");
            doGet(request, response);
            return;
        }

        // Lưu thông tin thanh toán vào session
        session.setAttribute("paymentMethod", paymentMethod);
        session.setAttribute("customerName", customerName);
        session.setAttribute("customerPhone", customerPhone);
        session.setAttribute("customerEmail", customerEmail);
        session.setAttribute("deliveryAddress", deliveryAddress);
        session.setAttribute("notes", notes);

        // Lưu thông tin và chuyển đến trang QR payment
        session.setAttribute("paymentMethod", paymentMethod);
        session.setAttribute("customerName", customerName);
        session.setAttribute("customerPhone", customerPhone);
        session.setAttribute("customerEmail", customerEmail);
        session.setAttribute("deliveryAddress", deliveryAddress);
        session.setAttribute("notes", notes);

        // Chuyển đến trang QR thanh toán với thông tin đơn hàng
        long tongTien = (Long) session.getAttribute("tongTienThanhToan");
        Long maHDGoc = (Long) session.getAttribute("maHDGoc");

        // Chuyển hướng đến trang QR với parameters
        String redirectUrl = "QRPayment.jsp?maBan=1&tongTien=" + tongTien;
        if (maHDGoc != null) {
            redirectUrl += "&maHD=" + maHDGoc;
        }

        response.sendRedirect(redirectUrl);
    }

    private void confirmPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            HoaDonBO hdBo = new HoaDonBO();

            // Lấy thông tin từ session
            GioHangBo gioHang = (GioHangBo) session.getAttribute("gio");
            String paymentMethod = (String) session.getAttribute("paymentMethod");
            String customerName = (String) session.getAttribute("customerName");
            String customerPhone = (String) session.getAttribute("customerPhone");

            if (gioHang == null || gioHang.getDs().isEmpty()) {
                request.setAttribute("error", "Giỏ hàng trống!");
                response.sendRedirect("TrangChuController");
                return;
            }

            // Tính tổng tiền
            long tongTien = 0;
            for (GioHang item : gioHang.getDs()) {
                tongTien += item.getThanhTien();
            }

            // Tạo hóa đơn với trạng thái thanh toán = 2 (đang chờ xác nhận thanh toán)
            // 0 = chưa thanh toán, 1 = đã thanh toán, 2 = chờ xác nhận thanh toán
            long maHD = hdBo.taoHoaDon(1, 1, 1, tongTien, 2); // maBan=1, maNV=1, maKH=1, thanhToan=2

            // Lưu thông tin thanh toán vào session để sử dụng sau
            session.setAttribute("maHD", maHD);
            session.setAttribute("thanhToanThanhCong", true);

            // Xóa giỏ hàng sau khi thanh toán thành công
            session.removeAttribute("gio");

            // Chuyển đến trang thành công
            request.setAttribute("maHD", maHD);
            request.setAttribute("tongTien", tongTien);
            request.setAttribute("paymentMethod", paymentMethod);

            RequestDispatcher rd = request.getRequestDispatcher("ThanhToanThanhCong.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra trong quá trình thanh toán. Vui lòng thử lại!");
            doGet(request, response);
        }
    }
}
