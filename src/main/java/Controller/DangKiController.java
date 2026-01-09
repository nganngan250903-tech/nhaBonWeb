package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.NhanVien.NhanVien;
import Model.NhanVien.NhanVienBo;

/**
 * Servlet implementation class DangKiController
 */
@WebServlet("/dangkiController")
public class DangKiController extends HttpServlet {

    NhanVienBo nvbo = new NhanVienBo();

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("DangKy.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            String un = req.getParameter("txtun");
            String ten = req.getParameter("hoten");
            String pass = req.getParameter("pass");
            String confirmPass = req.getParameter("confirmPass");

            // ===== VALIDATION =====
            if (un == null || un.trim().isEmpty()) {
                req.setAttribute("err", "Vui lòng nhập tên đăng nhập");
                req.getRequestDispatcher("DangKy.jsp").forward(req, res);
                return;
            }

            if (ten == null || ten.trim().isEmpty()) {
                req.setAttribute("err", "Vui lòng nhập họ tên");
                req.getRequestDispatcher("DangKy.jsp").forward(req, res);
                return;
            }

            if (pass == null || pass.trim().isEmpty()) {
                req.setAttribute("err", "Vui lòng nhập mật khẩu");
                req.getRequestDispatcher("DangKy.jsp").forward(req, res);
                return;
            }

            if (confirmPass == null || !pass.equals(confirmPass)) {
                req.setAttribute("err", "Mật khẩu xác nhận không khớp");
                req.getRequestDispatcher("DangKy.jsp").forward(req, res);
                return;
            }

            if (pass.length() < 6) {
                req.setAttribute("err", "Mật khẩu phải có ít nhất 6 ký tự");
                req.getRequestDispatcher("DangKy.jsp").forward(req, res);
                return;
            }

            int maQuyen = 2; // Mặc định là nhân viên
            String passMD5 = Modal.MaHoaMD5.md5(pass);

           

NhanVien nv = new NhanVien(
    null,
    ten,
    maQuyen,
    un,
    passMD5,
    true
);


            // ===== ĐĂNG KÝ =====
            boolean success = nvbo.dangKy(nv);
            if (success) {
                req.setAttribute("msg", "Đăng ký thành công! Vui lòng đăng nhập.");
                req.getRequestDispatcher("DangNhapController").forward(req, res);
            } else {
                req.setAttribute("err", "Đăng ký thất bại. Tên đăng nhập có thể đã tồn tại.");
                req.getRequestDispatcher("DangKy.jsp").forward(req, res);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("err", "Có lỗi xảy ra. Vui lòng thử lại.");
            req.getRequestDispatcher("DangKy.jsp").forward(req, res);
        }
    }
}
