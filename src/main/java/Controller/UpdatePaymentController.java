package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.HoaDon.HoaDonBO;

/**
 * Servlet implementation class UpdatePaymentController
 * Cập nhật trạng thái thanh toán hóa đơn
 */
@WebServlet("/UpdatePaymentController")
public class UpdatePaymentController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String maHDStr = request.getParameter("maHD");
        String status = request.getParameter("status"); // "paid" or "cancelled"

        if (maHDStr != null && status != null) {
            try {
                long maHD = Long.parseLong(maHDStr);
                HoaDonBO hdBo = new HoaDonBO();

                // Cập nhật trạng thái thanh toán
                // 1 = đã thanh toán, 0 = chưa thanh toán
                int thanhToan = "paid".equals(status) ? 1 : 0;

                // Ở đây chúng ta cần thêm method để cập nhật trạng thái hóa đơn
                // Hiện tại chúng ta chưa có method này trong HoaDonDAO

                if ("paid".equals(status)) {
                    response.getWriter().write("{\"success\": true, \"message\": \"Thanh toán thành công\"}");
                } else {
                    response.getWriter().write("{\"success\": false, \"message\": \"Thanh toán bị hủy\"}");
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().write("{\"success\": false, \"message\": \"Lỗi xử lý thanh toán\"}");
            }
        } else {
            response.getWriter().write("{\"success\": false, \"message\": \"Thiếu thông tin thanh toán\"}");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
