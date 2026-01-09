package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.BanAn.BanAn;
import Model.BanAn.BanAnBO;

/**
 * Servlet implementation class QLBanController
 */
@WebServlet("/QLBanController")
public class QLBanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QLBanController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            BanAnBO bo = new BanAnBO();

            req.setAttribute("dsBan", bo.getAll());
            req.setAttribute("tongBan", bo.getTongBan());
            req.setAttribute("banDangDung", bo.getBanDangDung());

            req.getRequestDispatcher("qlBan.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            req.setCharacterEncoding("UTF-8");
            BanAnBO bo = new BanAnBO();
            String action = req.getParameter("action");

            if ("insert".equals(action)) {
                bo.themBan(req.getParameter("tenBan"));
            }

            if ("update".equals(action)) {
                long maBan = Long.parseLong(req.getParameter("maBan"));
                String tenBan = req.getParameter("tenBan");

                bo.suaBan(maBan, tenBan);
            }

            if ("delete".equals(action)) {
                bo.xoaBan(Long.parseLong(req.getParameter("maBan")));
            }

            req.getSession().setAttribute("msgSuccess", "✔️ Thao tác thành công!");
        } catch (Exception e) {
            req.getSession().setAttribute("msgError", "❌ Có lỗi xảy ra!");
        }

        resp.sendRedirect("QLBanController");
    }
}