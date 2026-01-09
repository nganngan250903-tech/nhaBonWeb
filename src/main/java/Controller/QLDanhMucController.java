package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.DanhMuc.DanhMuc;
import Model.DanhMuc.DanhMucBo;

/**
 * Servlet implementation class QLDanhMucController
 */
@WebServlet("/QLDanhMucController")
public class QLDanhMucController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QLDanhMucController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DanhMucBo bo = new DanhMucBo();

        try {
            String action = request.getParameter("action");

            if (action == null) {
                ArrayList<DanhMuc> ds = bo.getAll();
                request.setAttribute("dsDM", ds);
                request.getRequestDispatcher("qlDanhMuc.jsp").forward(request, response);
                return;
            }

            if ("add".equals(action)) {
                request.getRequestDispatcher("themDanhMuc.jsp").forward(request, response);
                return;
            }

            if ("edit".equals(action)) {
                long maDM = Long.parseLong(request.getParameter("id"));
                request.setAttribute("dm", bo.getById(maDM));
                request.getRequestDispatcher("suaDanhMuc.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DanhMucBo bo = new DanhMucBo();
        request.setCharacterEncoding("UTF-8");

        try {
            String action = request.getParameter("action");

            if ("insert".equals(action)) {
                bo.insert(new DanhMuc(0,
                        request.getParameter("tenDM"),
                        request.getParameter("moTa")));
            }

            if ("update".equals(action)) {
                bo.update(new DanhMuc(
                        Long.parseLong(request.getParameter("maDM")),
                        request.getParameter("tenDM"),
                        request.getParameter("moTa")));
            }

            if ("delete".equals(action)) {
                bo.delete(Long.parseLong(request.getParameter("maDM")));
            }

            request.getSession().setAttribute("msgSuccess", "✔️ Thao tác thành công!");
            response.sendRedirect("QLDanhMucController");

        } catch (Exception e) {
            request.getSession().setAttribute("msgError", "❌ Có lỗi xảy ra!");
            response.sendRedirect("QLDanhMucController");
        }
    }
}