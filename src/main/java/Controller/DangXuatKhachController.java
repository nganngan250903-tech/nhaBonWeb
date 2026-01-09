package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller cho đăng xuất khách hàng
 */
@WebServlet("/DangXuatKhachController")
public class DangXuatKhachController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            // Xóa thông tin khách hàng khỏi session
            session.removeAttribute("khachhang");
            session.removeAttribute("gio"); // Có thể xóa giỏ hàng cũng
            System.out.println("Khách hàng đã đăng xuất thành công");
        }

        // Chuyển hướng về trang chủ
        response.sendRedirect("TrangChuController");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}