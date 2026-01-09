package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.BanAn.BanAnBO;
import Model.BanAn.BanAnDAO;
import Model.HoaDon.HoaDonBO;
import Model.HoaDon.HoaDonDAO;
import Model.MonAn.MonAnBo;
import Model.MonAn.MonAnDAO;

/**
 * Servlet implementation class AdminHomeController
 */
@WebServlet("/AdminHomeController")
public class AdminHomeController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BanAnBO banBO = new BanAnBO();
		MonAnBo monBO = new MonAnBo();
		HoaDonBO hdBO = new HoaDonBO();

		request.setAttribute("tongBan", banBO.getTongBan());
		request.setAttribute("tongMon", monBO.getTongMon());
		request.setAttribute("tongHD", hdBO.getTongHoaDon());

		RequestDispatcher rd = request.getRequestDispatcher("adminHome.jsp");
		rd.forward(request, response);
	}
}
