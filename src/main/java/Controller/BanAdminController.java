package Controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Model.BanAn.*;

@WebServlet("/admin/ban")
public class BanAdminController extends HttpServlet {

    BanAnBo banBo = new BanAnBo();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            if (action == null) {
                req.setAttribute("dsBan", banBo.getDanhSachBan());
                req.getRequestDispatcher("/admin/ban/list.jsp").forward(req, resp);
            }

            else if ("add".equals(action)) {
                req.getRequestDispatcher("/admin/ban/form.jsp").forward(req, resp);
            }

            else if ("edit".equals(action)) {
                String maBan = req.getParameter("maBan");
                req.setAttribute("ban", banBo.getBanTheoMa(maBan));
                req.getRequestDispatcher("/admin/ban/form.jsp").forward(req, resp);
            }

            else if ("delete".equals(action)) {
                banBo.xoaBan(req.getParameter("maBan"));
                resp.sendRedirect("ban");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String maBan = req.getParameter("maBan");
            String tenBan = req.getParameter("tenBan");
            String mucBan = req.getParameter("mucBan");

            BanAn b = new BanAn(maBan, tenBan, 0, mucBan);

            if (req.getParameter("edit") == null) {
                banBo.themBan(b);
            } else {
                banBo.suaBan(b);
            }

            resp.sendRedirect("ban");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
