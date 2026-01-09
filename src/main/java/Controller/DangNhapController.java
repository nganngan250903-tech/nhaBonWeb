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

	    // ===== HIỂN THỊ FORM ĐĂNG NHẬP =====
	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse res)
	            throws ServletException, IOException {

	        HttpSession session = req.getSession();

	        // Khởi tạo số lần sai
	        if (session.getAttribute("fail") == null) {
	            session.setAttribute("fail", 0);
	        }

	        req.getRequestDispatcher("DangNhap.jsp").forward(req, res);
	    }

	    // ===== XỬ LÝ ĐĂNG NHẬP =====
	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse res)
	            throws ServletException, IOException {

	        try {
	            HttpSession session = req.getSession();
	            int fail = (int) session.getAttribute("fail");

	            String un = req.getParameter("txtun");
	            String pass = req.getParameter("txtpass");
	            String loginType = req.getParameter("loginType"); // AD | NV

	            // ===== CAPTCHA =====
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

	            // ===== KIỂM TRA TÀI KHOẢN =====
	            String passMD5 = MaHoaMD5.md5(pass);
	            NhanVien nv = nvbo.dangNhap(un, passMD5);

	            if (nv == null) {
	                fail++;
	                session.setAttribute("fail", fail);

	                if (fail >= 3) {
	                    session.setAttribute("captcha", Modal.Captcha.taoCaptcha());
	                }

	                req.setAttribute("err", "Sai tài khoản hoặc mật khẩu");
	                req.getRequestDispatcher("DangNhap.jsp").forward(req, res);
	                return;
	            }

	            // ===== ĐĂNG NHẬP THÀNH CÔNG =====
	            session.setAttribute("fail", 0);
	            session.setAttribute("nhanvien", nv);

	            // ===== PHÂN QUYỀN =====
	            if ("1".equals(nv.getMaQuyen())) {
	                // ADMIN
	                session.setAttribute("admin", nv);
	                res.sendRedirect("AdminHomeController");
	            } else {
	                // NHÂN VIÊN
	                res.sendRedirect("TrangChuController");
	            }


	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
