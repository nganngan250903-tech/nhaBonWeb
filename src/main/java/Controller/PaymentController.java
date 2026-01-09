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

        // Kiểm tra đăng nhập khách hàng
        Model.KhachHang.KhachHang kh = (Model.KhachHang.KhachHang) session.getAttribute("khachhang");
        if (kh == null) {
            response.sendRedirect("DangNhapKhachController");
            return;
        }

        // Kiểm tra có đơn hàng đang ăn không
        Long maHDGoc = (Long) session.getAttribute("maHDGoc");
        Integer trangThaiDonHang = (Integer) session.getAttribute("trangThaiDonHang");

        if (maHDGoc == null || trangThaiDonHang != 3) {
            request.setAttribute("error", "Bạn chưa có đơn hàng nào đang phục vụ. Vui lòng đặt món trước!");
            RequestDispatcher rd = request.getRequestDispatcher("TrangChuController");
            rd.forward(request, response);
            return;
        }

        try {
            // Lấy thông tin đơn hàng từ database
            HoaDonBO hdBo = new HoaDonBO();
            List<Object[]> dsDonHang = hdBo.getDonHangByMaHD(maHDGoc);

            if (dsDonHang == null || dsDonHang.isEmpty()) {
                request.setAttribute("error", "Không tìm thấy thông tin đơn hàng!");
                RequestDispatcher rd = request.getRequestDispatcher("TheoDoiDonHangController");
                rd.forward(request, response);
                return;
            }

            Object[] donHang = dsDonHang.get(0);
            long tongTien = (Long) donHang[5];

            // Lưu thông tin thanh toán vào session
            session.setAttribute("tongTienThanhToan", tongTien);

            // Hiển thị trang thanh toán với thông tin khách hàng
            request.setAttribute("khachHang", kh);
            request.setAttribute("donHang", donHang);
            request.setAttribute("tongTien", tongTien);

            RequestDispatcher rd = request.getRequestDispatcher("ThanhToan.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải trang thanh toán!");
            RequestDispatcher rd = request.getRequestDispatcher("TheoDoiDonHangController");
            rd.forward(request, response);
        }
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

        // Lấy thông tin khách hàng từ session (đã đăng nhập)
        Model.KhachHang.KhachHang kh = (Model.KhachHang.KhachHang) session.getAttribute("khachhang");
        if (kh == null) {
            response.sendRedirect("DangNhapKhachController");
            return;
        }

        // Lấy thông tin thanh toán từ form
        String paymentMethod = request.getParameter("paymentMethod");

        // Validation
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng chọn phương thức thanh toán!");
            doGet(request, response);
            return;
        }

        // Lưu thông tin thanh toán vào session
        session.setAttribute("paymentMethod", paymentMethod);

        // Lấy thông tin đơn hàng
        long tongTien = (Long) session.getAttribute("tongTienThanhToan");
        Long maHDGoc = (Long) session.getAttribute("maHDGoc");

        // Lay ma ban tu session
        Long maBan = (Long) session.getAttribute("maBan");
        if (maBan == null) {
            maBan = 1L; // Fallback neu khong co trong session
        }

        // Chuyển hướng đến trang thanh toán phù hợp
        if ("qr".equals(paymentMethod)) {
            // Chuyển đến trang QR với parameters
            String redirectUrl = "QRPayment.jsp?maBan=" + maBan + "&tongTien=" + tongTien;
            if (maHDGoc != null) {
                redirectUrl += "&maHD=" + maHDGoc;
            }
            response.sendRedirect(redirectUrl);
        } else if ("cash".equals(paymentMethod)) {
            // Thanh toán bằng tiền mặt - chuyển trực tiếp đến xác nhận
            confirmCashPayment(request, response);
        } else {
            request.setAttribute("error", "Phương thức thanh toán không hợp lệ!");
            doGet(request, response);
        }
    }

    private void confirmCashPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            // Lấy thông tin khách hàng
            Model.KhachHang.KhachHang kh = (Model.KhachHang.KhachHang) session.getAttribute("khachhang");
            if (kh == null) {
                response.sendRedirect("DangNhapKhachController");
                return;
            }

            // Lấy thông tin đơn hàng
            Long maHDGoc = (Long) session.getAttribute("maHDGoc");
            long tongTien = (Long) session.getAttribute("tongTienThanhToan");

            if (maHDGoc == null) {
                request.setAttribute("error", "Không tìm thấy thông tin đơn hàng!");
                response.sendRedirect("TheoDoiDonHangController");
                return;
            }

            // Cập nhật trạng thái đơn hàng thành đã thanh toán (1)
            HoaDonBO hdBo = new HoaDonBO();
            boolean success = hdBo.capNhatTrangThaiThanhToan(maHDGoc, 1);

            if (!success) {
                request.setAttribute("error", "Không thể cập nhật trạng thái thanh toán!");
                response.sendRedirect("TheoDoiDonHangController");
                return;
            }

            // Xóa session đơn hàng sau khi thanh toán thành công
            session.removeAttribute("maHDGoc");
            session.removeAttribute("trangThaiDonHang");
            session.removeAttribute("tongTienThanhToan");
            session.removeAttribute("gio"); // Xóa giỏ hàng

            // Chuyển đến trang thành công
            request.setAttribute("maHD", maHDGoc);
            request.setAttribute("tongTien", tongTien);
            request.setAttribute("paymentMethod", "cash");

            RequestDispatcher rd = request.getRequestDispatcher("ThanhToanThanhCong.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra trong quá trình thanh toán!");
            response.sendRedirect("TheoDoiDonHangController");
        }
    }

    private void confirmPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            // Lấy thông tin khách hàng
            Model.KhachHang.KhachHang kh = (Model.KhachHang.KhachHang) session.getAttribute("khachhang");
            if (kh == null) {
                response.sendRedirect("DangNhapKhachController");
                return;
            }

            // Lấy thông tin đơn hàng
            Long maHDGoc = (Long) session.getAttribute("maHDGoc");
            long tongTien = (Long) session.getAttribute("tongTienThanhToan");

            if (maHDGoc == null) {
                request.setAttribute("error", "Không tìm thấy thông tin đơn hàng!");
                response.sendRedirect("TheoDoiDonHangController");
                return;
            }

            // Cập nhật trạng thái đơn hàng thành chờ xác nhận thanh toán (2)
            HoaDonBO hdBo = new HoaDonBO();
            boolean success = hdBo.capNhatTrangThaiThanhToan(maHDGoc, 2); // 2 = chờ xác nhận thanh toán

            if (!success) {
                request.setAttribute("error", "Không thể cập nhật trạng thái thanh toán!");
                response.sendRedirect("TheoDoiDonHangController");
                return;
            }

            // Lưu thông tin thanh toán vào session
            session.setAttribute("maHD", maHDGoc);
            session.setAttribute("thanhToanThanhCong", true);

            // Chuyển đến trang thành công
            request.setAttribute("maHD", maHDGoc);
            request.setAttribute("tongTien", tongTien);
            request.setAttribute("paymentMethod", "qr");

            RequestDispatcher rd = request.getRequestDispatcher("ThanhToanThanhCong.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra trong quá trình thanh toán. Vui lòng thử lại!");
            response.sendRedirect("TheoDoiDonHangController");
        }
    }
}
