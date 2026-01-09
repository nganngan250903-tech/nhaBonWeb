package Controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.GioHang.GioHangBo;
import Model.HoaDon.HoaDon;
import Model.HoaDon.HoaDonDAO;

/**
 * Servlet implementation class DonHangController
 */
@WebServlet("/DonHangController")
public class DonHangController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        GioHangBo gio = (GioHangBo) session.getAttribute("gio");

        if (gio == null || gio.getDs().isEmpty()) {
            response.sendRedirect("TrangChuController");
            return;
        }

        request.getRequestDispatcher("DonHang.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            GioHangBo gio = (GioHangBo) session.getAttribute("gio");

            HoaDon hd = new HoaDon();
            hd.setMaBan(1);
            hd.setMaNV(1);
            hd.setMaKH(1);
            hd.setGioVao(new Timestamp(System.currentTimeMillis()));
            hd.setTongTien(gio.tongTien());
            hd.setThanhToan(0);

            HoaDonDAO dao = new HoaDonDAO();
            dao.themHoaDon(hd);

            session.removeAttribute("gio"); // clear giỏ

            response.sendRedirect("TrangChuController");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
