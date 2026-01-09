package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.DanhMuc.DanhMucBo;
import Model.MonAn.MonAnBo;

/**
 * Servlet implementation class TrangChuController
 */
@WebServlet("/TrangChuController")
public class TrangChuController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

	    try {
	        MonAnBo monBo = new MonAnBo();
	        DanhMucBo dmBo = new DanhMucBo();

	        // ✅ ĐÚNG TÊN PARAM
	        String key = request.getParameter("txtTim");
	        String maDM = request.getParameter("maDM");

	        // load danh mục luôn
	        request.setAttribute("dsDanhMuc", dmBo.getAll());

	        // ===== LOGIC CHUẨN =====
	        if (key != null && !key.trim().isEmpty()) {
	            request.setAttribute("dsMon", monBo.timTheoTen(key.trim()));
	        } 
	        else if (maDM != null && !maDM.trim().isEmpty()) {
	            request.setAttribute("dsMon",
	                    monBo.getByDanhMuc(Long.parseLong(maDM)));
	        } 
	        else {
	            request.setAttribute("dsMon", monBo.getAll());
	        }

	        request.getRequestDispatcher("TrangChu.jsp")
	               .forward(request, response);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    doGet(request, response);
	}
}
