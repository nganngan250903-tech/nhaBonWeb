package Controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Modal.MaHoaMD5;
import Model.NhanVien.NhanVien;
import Model.NhanVien.NhanVienBo;

/**
 * Servlet implementation class DangNhapController
 */
@WebServlet("/DangNhapController")
public class DangNhapController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    NhanVienBo nvbo = new NhanVienBo();

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("fail") == null)
            session.setAttribute("fail", 0);

        req.getRequestDispatcher("DangNhap.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            HttpSession session = req.getSession();
            int fail = (int) session.getAttribute("fail");

            String un = req.getParameter("txtun");
            String pass = req.getParameter("txtpass");

            if (fail >= 3) {
                req.setAttribute("showCaptcha", true);

                String capUser = req.getParameter("txtcaptcha");
                String capSession = (String) session.getAttribute("captcha");

                if (capUser == null || !capUser.equals(capSession)) {
                    session.setAttribute("captcha", Modal.Captcha.taoCaptcha());
                    req.setAttribute("err", "Sai captcha");
                    req.getRequestDispatcher("DangNhap.jsp").forward(req, res);
                    return;
                }
            }

            String passMD5 = Modal.MaHoaMD5.md5(pass);
            NhanVien nv = nvbo.dangNhap(un, passMD5);

            if (nv == null) {
                fail++;
                session.setAttribute("fail", fail);
                if (fail >= 3)
                    session.setAttribute("captcha", Modal.Captcha.taoCaptcha());

                req.setAttribute("err", "Sai tài khoản hoặc mật khẩu");
                doGet(req, res);
                return;
            }

            session.setAttribute("fail", 0);
            session.setAttribute("nhanvien", nv);

            if (nv.getMaQuyen().equalsIgnoreCase("AD"))
                res.sendRedirect("adminController");
            else
                res.sendRedirect("trangchuController");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
