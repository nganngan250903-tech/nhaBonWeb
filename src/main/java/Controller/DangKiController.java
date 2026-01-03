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

            String passMD5 = Modal.MaHoaMD5.md5(pass);

            NhanVien nv = new NhanVien(
                "NV" + System.currentTimeMillis(),
                ten,
                "NV",
                un,
                passMD5,
                true
            );

            nvbo.dangKy(nv);
            res.sendRedirect("dangnhapController");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
