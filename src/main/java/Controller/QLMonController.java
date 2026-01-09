package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.MonAn.MonAn;
import Model.MonAn.MonAnBo;

/**
 * Servlet implementation class QLMonController
 */
@WebServlet("/QLMonController")
public class QLMonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	MonAnBo monBO = new MonAnBo(); 
	try {
		String action = request.getParameter("action");

		// ===== LOAD DANH SÁCH =====
		if (action == null) {
			ArrayList<MonAn> ds = monBO.getAll();
			request.setAttribute("dsMon", ds);
			request.getRequestDispatcher("qlMon.jsp").forward(request, response);
			return;
		}

		// ===== TÌM KIẾM =====
		if ("search".equals(action)) {
			String key = request.getParameter("key");
			ArrayList<MonAn> ds = monBO.timTheoTen(key);
			request.setAttribute("dsMon", ds);
			request.setAttribute("key", key);
			request.getRequestDispatcher("qlMon.jsp").forward(request, response);
			return;
		}

		// ===== FORM THÊM =====
		if ("add".equals(action)) {
			request.getRequestDispatcher("themMon.jsp").forward(request, response);
			return;
		}

		// ===== FORM SỬA =====
		if ("edit".equals(action)) {
			long maMon = Long.parseLong(request.getParameter("id"));
			MonAn m = monBO.getById(maMon);
			request.setAttribute("mon", m);
			request.getRequestDispatcher("suaMon.jsp").forward(request, response);
			return;
		}

	} catch (Exception e) {
		e.printStackTrace();
	}
}

protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	MonAnBo monBO = new MonAnBo(); 
	try {
		String action = request.getParameter("action");

		long maMon = Long.parseLong(request.getParameter("maMon"));
		String tenMon = request.getParameter("tenMon");
		long gia = Long.parseLong(request.getParameter("gia"));
		String hinhAnh = request.getParameter("hinhAnh");
		String moTa = request.getParameter("moTa");
		long maDM = Long.parseLong(request.getParameter("maDM"));
		int trangThai = Integer.parseInt(request.getParameter("trangThai"));
		int soLuong = Integer.parseInt(request.getParameter("soLuong"));

		MonAn m = new MonAn(maMon, tenMon, gia, hinhAnh, moTa, maDM, trangThai, soLuong);
		if ("insert".equals(action)) {
			monBO.insert(m);
		}

		if ("update".equals(action)) {
			monBO.update(m);
		}

		if ("delete".equals(action)) {
			monBO.delete(maMon);
		}

		response.sendRedirect("QLMonController");

	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
