package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.HoaDon.HoaDonBO;
import Model.KhachHang.KhachHang;

/**
 * Controller cho trang lịch sử đơn hàng của khách hàng
 */
@WebServlet("/LichSuController")
public class LichSuController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        KhachHang kh = (KhachHang) session.getAttribute("khachhang");

        // Kiểm tra khách hàng đã đăng nhập chưa
        if (kh == null) {
            response.sendRedirect("DangNhapKhachController");
            return;
        }

        try {
            HoaDonBO hdBo = new HoaDonBO();

            // Có thể thêm logic lấy lịch sử đơn hàng của khách hàng
            // Hiện tại chỉ forward đến trang
            request.setAttribute("khachHang", kh);
            request.setAttribute("activeMenu", "lichsu");

            request.getRequestDispatcher("LichSu.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải lịch sử đơn hàng");
            request.getRequestDispatcher("LichSu.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}