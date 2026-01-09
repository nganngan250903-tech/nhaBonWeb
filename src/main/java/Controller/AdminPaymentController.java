package Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.HoaDon.HoaDonBO;

/**
 * Servlet implementation class AdminPaymentController
 * Controller cho admin quản lý xác nhận thanh toán
 */
@WebServlet("/AdminPaymentController")
public class AdminPaymentController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HoaDonBO hoaDonBO = new HoaDonBO();

            // Kiểm tra nếu có parameter maBan để hiển thị chi tiết bàn
            String maBanParam = request.getParameter("maBan");

            if (maBanParam != null && !maBanParam.trim().isEmpty()) {
                // Hiển thị chi tiết đơn hàng của bàn cụ thể
                long maBan = Long.parseLong(maBanParam);

                List<Object[]> dsDonHangBan = hoaDonBO.getDonHangByBan(maBan);
                List<Object[]> dsChoXacNhanBan = hoaDonBO.getDonHangChoXacNhanByBan(maBan);
                List<Object[]> dsDangAnBan = hoaDonBO.getDonHangDangAnByBan(maBan);
                List<Object[]> dsDaThanhToanBan = hoaDonBO.getDonHangDaThanhToanByBan(maBan);

                request.setAttribute("currentMaBan", maBan);
                request.setAttribute("dsDonHangBan", dsDonHangBan);
                request.setAttribute("dsChoXacNhanBan", dsChoXacNhanBan);
                request.setAttribute("dsDangAnBan", dsDangAnBan);
                request.setAttribute("dsDaThanhToanBan", dsDaThanhToanBan);
                request.setAttribute("viewMode", "tableDetail");

            } else {
                // Hiển thị danh sách tổng quan và danh sách bàn
                List<Object[]> dsChoXacNhan = hoaDonBO.getDonHangChoXacNhan();
                List<Object[]> dsDangAn = hoaDonBO.getDonHangDangAn();
                List<Object[]> dsDaThanhToan = hoaDonBO.getDonHangDaThanhToan();
                List<Object[]> dsBanCoDonHang = hoaDonBO.getDanhSachBanCoDonHang();

                request.setAttribute("dsChoXacNhan", dsChoXacNhan);
                request.setAttribute("dsDaThanhToan", dsDaThanhToan);
                request.setAttribute("dsDangAn", dsDangAn);
                request.setAttribute("dsBanCoDonHang", dsBanCoDonHang);
                request.setAttribute("viewMode", "overview");
            }

            request.setAttribute("activeMenu", "payment");

            RequestDispatcher rd = request.getRequestDispatcher("adminPayment.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải danh sách thanh toán");
            request.getRequestDispatcher("adminPayment.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("confirmPayment".equals(action)) {
            confirmPayment(request, response);
        } else if ("rejectPayment".equals(action)) {
            rejectPayment(request, response);
        } else if ("confirmPaymentByTable".equals(action)) {
            confirmPaymentByTable(request, response);
        } else {
            doGet(request, response);
        }
    }

    private void confirmPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String maHDStr = request.getParameter("maHD");
            if (maHDStr != null) {
                long maHD = Long.parseLong(maHDStr);
                HoaDonBO hoaDonBO = new HoaDonBO();

                // Cập nhật trạng thái thành đã thanh toán (1)
                boolean success = hoaDonBO.capNhatTrangThaiThanhToan(maHD, 1);

                if (success) {
                    request.setAttribute("success", "Đã xác nhận thanh toán cho đơn hàng #" + maHD);
                } else {
                    request.setAttribute("error", "Không thể cập nhật trạng thái thanh toán");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi xác nhận thanh toán");
        }

        // Redirect về trang với filter hiện tại
        String maBan = request.getParameter("maBan");
        if (maBan != null && !maBan.trim().isEmpty()) {
            response.sendRedirect("AdminPaymentController?maBan=" + maBan);
        } else {
            response.sendRedirect("AdminPaymentController");
        }
    }

    private void rejectPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String maHDStr = request.getParameter("maHD");
            if (maHDStr != null) {
                long maHD = Long.parseLong(maHDStr);
                HoaDonBO hoaDonBO = new HoaDonBO();

                // Cập nhật trạng thái thành chưa thanh toán (0)
                boolean success = hoaDonBO.capNhatTrangThaiThanhToan(maHD, 0);

                if (success) {
                    request.setAttribute("success", "Đã từ chối thanh toán cho đơn hàng #" + maHD);
                } else {
                    request.setAttribute("error", "Không thể cập nhật trạng thái thanh toán");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi từ chối thanh toán");
        }

        // Redirect về trang với filter hiện tại
        String maBan = request.getParameter("maBan");
        if (maBan != null && !maBan.trim().isEmpty()) {
            response.sendRedirect("AdminPaymentController?maBan=" + maBan);
        } else {
            response.sendRedirect("AdminPaymentController");
        }
    }

    private void confirmPaymentByTable(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String maBanStr = request.getParameter("maBan");
            if (maBanStr != null) {
                long maBan = Long.parseLong(maBanStr);
                HoaDonBO hoaDonBO = new HoaDonBO();

                // Cập nhật trạng thái thành đã thanh toán (1) cho tất cả hóa đơn của bàn
                int rowsAffected = hoaDonBO.capNhatTrangThaiThanhToanByBan(maBan, 1);

                if (rowsAffected > 0) {
                    request.setAttribute("success", "Đã xác nhận thanh toán cho bàn #" + maBan + " (" + rowsAffected + " hóa đơn)");
                } else {
                    request.setAttribute("error", "Không có hóa đơn nào cần cập nhật trạng thái thanh toán cho bàn #" + maBan);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi xác nhận thanh toán cho bàn");
        }

        // Redirect về trang tổng quan sau khi xác nhận thanh toán cho bàn
        response.sendRedirect("AdminPaymentController");
    }
}
