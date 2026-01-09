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

            // Lấy danh sách đơn hàng đang chờ xác nhận thanh toán (ThanhToan = 2)
            List<Object[]> dsChoXacNhan = hoaDonBO.getDonHangChoXacNhan();

            // Lấy danh sách đơn hàng đã thanh toán gần đây
            List<Object[]> dsDaThanhToan = hoaDonBO.getDonHangDaThanhToan();

            request.setAttribute("dsChoXacNhan", dsChoXacNhan);
            request.setAttribute("dsDaThanhToan", dsDaThanhToan);
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

        doGet(request, response);
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

        doGet(request, response);
    }
}
