package Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.HoaDon.ChiTietHoaDonBO;

/**
 * Servlet implementation class AdminDishStatusController
 * Controller cho admin cập nhật trạng thái món ăn
 */
@WebServlet("/AdminDishStatusController")
public class AdminDishStatusController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ChiTietHoaDonBO ctBO = new ChiTietHoaDonBO();

            // Lấy tất cả chi tiết hóa đơn đang chế biến (trangThai = 0)
            List<Object[]> dsMonDangLam = ctBO.getChiTietByTrangThai(0);

            // Lấy tất cả chi tiết hóa đơn đã hoàn thành (trangThai = 1)
            List<Object[]> dsMonDaXong = ctBO.getChiTietByTrangThai(1);

            request.setAttribute("dsMonDangLam", dsMonDangLam);
            request.setAttribute("dsMonDaXong", dsMonDaXong);
            request.setAttribute("activeMenu", "dishstatus");

            RequestDispatcher rd = request.getRequestDispatcher("adminDishStatus.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải danh sách món ăn");
            request.getRequestDispatcher("adminDishStatus.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("updateStatus".equals(action)) {
            updateDishStatus(request, response);
        } else if ("markCompleted".equals(action)) {
            markDishCompleted(request, response);
        } else {
            doGet(request, response);
        }
    }

    private void updateDishStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String maCTHDStr = request.getParameter("maCTHD");
            String trangThaiStr = request.getParameter("trangThai");

            if (maCTHDStr != null && trangThaiStr != null) {
                long maCTHD = Long.parseLong(maCTHDStr);
                int trangThai = Integer.parseInt(trangThaiStr);

                ChiTietHoaDonBO ctBO = new ChiTietHoaDonBO();
                boolean success = ctBO.capNhatTrangThai(maCTHD, trangThai);

                if (success) {
                    request.setAttribute("success", "Đã cập nhật trạng thái món ăn thành công!");
                } else {
                    request.setAttribute("error", "Không thể cập nhật trạng thái món ăn");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi cập nhật trạng thái món ăn");
        }

        doGet(request, response);
    }

    private void markDishCompleted(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String maCTHDStr = request.getParameter("maCTHD");

            if (maCTHDStr != null) {
                long maCTHD = Long.parseLong(maCTHDStr);

                ChiTietHoaDonBO ctBO = new ChiTietHoaDonBO();
                boolean success = ctBO.capNhatTrangThai(maCTHD, 1); // 1 = đã hoàn thành

                if (success) {
                    request.setAttribute("success", "Đã đánh dấu món ăn hoàn thành!");
                } else {
                    request.setAttribute("error", "Không thể cập nhật trạng thái món ăn");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi cập nhật trạng thái món ăn");
        }

        doGet(request, response);
    }
}
