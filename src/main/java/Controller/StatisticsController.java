package Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.BanAn.BanAnBO;
import Model.HoaDon.HoaDonBO;
import Model.MonAn.MonAnBo;
import Model.NhanVien.NhanVienBo;

/**
 * Servlet implementation class StatisticsController
 * Controller for admin statistics page
 */
@WebServlet("/StatisticsController")
public class StatisticsController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Initialize business objects
		BanAnBO banBO = new BanAnBO();
		MonAnBo monBO = new MonAnBo();
		HoaDonBO hdBO = new HoaDonBO();
		NhanVienBo nvBO = new NhanVienBo();

		// Gather comprehensive statistics
		request.setAttribute("tongBan", banBO.getTongBan());
		request.setAttribute("tongMon", monBO.getTongMon());
		request.setAttribute("tongNhanVien", nvBO.getTongNhanVien());
		

		// Revenue statistics
		request.setAttribute("tongDoanhThu", hdBO.getTongDoanhThu());
		request.setAttribute("doanhThuHomNay", hdBO.getDoanhThuHomNay());
		request.setAttribute("doanhThuTuanNay", hdBO.getDoanhThuTuanNay());
		request.setAttribute("doanhThuThangNay", hdBO.getDoanhThuThangNay());

		// Order statistics
		request.setAttribute("soHoaDonDaThanhToan", hdBO.getSoHoaDonDaThanhToan());
		request.setAttribute("soHoaDonChuaThanhToan", hdBO.getSoHoaDonChuaThanhToan());

		// Detailed statistics for charts
		request.setAttribute("doanhThu7Ngay", hdBO.getDoanhThu7NgayGanNhat());
		request.setAttribute("topMonAn", hdBO.getTopMonAnBanChay());
		request.setAttribute("thongKeNhanVien", nvBO.getThongKeNhanVien());
		request.setAttribute("thongKeBan", banBO.getThongKeSuDungBan());

		RequestDispatcher rd = request.getRequestDispatcher("adminStatistics.jsp");
		rd.forward(request, response);
	}
}
