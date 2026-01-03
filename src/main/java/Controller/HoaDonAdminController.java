package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.HoaDon.ChiTietHoaDonDao;
import Model.HoaDon.HoaDonDao;

/**
 * Servlet implementation class HoaDonAdminController
 */
@WebServlet("/HoaDonAdminController")

public class HoaDonAdminController extends HttpServlet {

    HoaDonDao hdDao = new HoaDonDao();
    ChiTietHoaDonDao ctDao = new ChiTietHoaDonDao();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String maHD = req.getParameter("maHD");

            if (maHD == null) {
                req.setAttribute("dsHD", hdDao.getLichSuHoaDon());
                req.getRequestDispatcher("/admin/hoadon/list.jsp").forward(req, resp);
            } else {
                req.setAttribute("cthd", ctDao.getCTHDTheoMaHD(maHD));
                req.getRequestDispatcher("/admin/hoadon/detail.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
