package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.HoaDon.ChiTietHoaDonBO;
import Model.HoaDon.HoaDonBO;
import Model.KhachHang.KhachHang;

/**
 * Controller cho trang theo dõi đơn hàng của khách hàng
 */
@WebServlet("/TheoDoiDonHangController")
public class TheoDoiDonHangController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        KhachHang kh = (KhachHang) session.getAttribute("khachhang");

        if (kh == null) {
            response.sendRedirect("DangNhapKhachController");
            return;
        }

        try {
            HoaDonBO hoaDonBO = new HoaDonBO();
            ChiTietHoaDonBO ctBO = new ChiTietHoaDonBO();

            Long maBan = (Long) session.getAttribute("maBan");
            if (maBan == null) maBan = 1L;

            // Lấy hóa đơn đang ăn (để hiển thị thông tin chung)
            List<Object[]> dsDonHang = hoaDonBO.getDonHangDangAnByBan(maBan);
            if (dsDonHang != null && !dsDonHang.isEmpty()) {
                request.setAttribute("donHang", dsDonHang.get(0));
            }

            // ✅ LẤY TOÀN BỘ CHI TIẾT THEO BÀN
            List<Object[]> dsChiTietTong = ctBO.getChiTietTongByBan(maBan);

            boolean hasProcessingItems = false;
            boolean hasCompletedItems = false;

            if (dsChiTietTong != null) {
                for (Object[] item : dsChiTietTong) {
                    int trangThai = (Integer) item[7];
                    if (trangThai == 0) hasProcessingItems = true;
                    else if (trangThai == 1) hasCompletedItems = true;
                }
            }

            request.setAttribute("dsChiTiet", dsChiTietTong);
            request.setAttribute("hasProcessingItems", hasProcessingItems);
            request.setAttribute("hasCompletedItems", hasCompletedItems);

            // Thông báo
            String success = (String) session.getAttribute("success");
            if (success != null) {
                request.setAttribute("success", success);
                session.removeAttribute("success");
            }

            request.setAttribute("khachHang", kh);
            request.setAttribute("activeMenu", "theodoi");
            request.getRequestDispatcher("TheoDoiDonHang.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải thông tin đơn hàng");
            request.getRequestDispatcher("TheoDoiDonHang.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}