package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.HoaDon.HoaDonBO;
import Model.NhanVien.NhanVien;

/**
 * Controller cho admin quản lý danh sách hóa đơn
 */
@WebServlet("/HoaDonAdminController")
public class HoaDonAdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiem tra quyen admin
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("DangNhapController");
            return;
        }

        NhanVien nv = (NhanVien) session.getAttribute("nhanvien");
        if (nv == null || nv.getMaQuyen() != 1) {
            response.sendRedirect("TrangChuController");
            return;
        }

        try {
            HoaDonBO hoaDonBO = new HoaDonBO();

            // Lấy tất cả hóa đơn với thông tin khách hàng
            List<Object[]> dsHoaDon = hoaDonBO.getAllHoaDonWithKhachHang();

            request.setAttribute("dsHoaDon", dsHoaDon);
            request.setAttribute("admin", nv);
            request.getRequestDispatcher("adminHoaDon.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải danh sách hóa đơn");
            request.getRequestDispatcher("adminHoaDon.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}