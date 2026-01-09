package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.KhachHang.KhachHang;
import Model.KhachHang.KhachHangBO;

/**
 * Servlet implementation class DangNhapKhachController
 */
@WebServlet("/DangNhapKhachController")
public class DangNhapKhachController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DangNhapKhachController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Forward đến trang đăng nhập khách hàng
		request.getRequestDispatcher("DangNhapKhach.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("=== DangNhapKhachController doPost START ===");
			request.setCharacterEncoding("UTF-8");
            String sdt = request.getParameter("sdt");
            String tenKH = request.getParameter("tenKH");

            System.out.println("Received - SĐT: " + sdt + ", Tên: " + tenKH);

            // Validation cơ bản
            if (sdt == null || sdt.trim().isEmpty()) {
            	System.out.println("Validation failed: SĐT rỗng");
            	request.setAttribute("err", "Vui lòng nhập số điện thoại");
            	request.getRequestDispatcher("DangNhapKhach.jsp").forward(request, response);
            	return;
            }

            KhachHangBO bo = new KhachHangBO();

            // Kiểm tra khách hàng có tồn tại không
            KhachHang kh = bo.getBySDT(sdt.trim());

            if (kh != null) {
            	// Khách hàng đã tồn tại - đăng nhập thành công
            	System.out.println("Khách hàng đã tồn tại, đăng nhập thành công");
            	request.getSession().setAttribute("khachhang", kh);
            	System.out.println("Khách hàng đăng nhập: " + kh.getTenKH() + " - " + kh.getSdt());

            	// Kiểm tra có redirect URL không
            	String redirectUrl = (String) request.getSession().getAttribute("redirectAfterLogin");
            	if (redirectUrl != null) {
            		request.getSession().removeAttribute("redirectAfterLogin");
            		response.sendRedirect(redirectUrl);
            	} else {
            		response.sendRedirect("TrangChuController");
            	}
            } else {
            	// Khách hàng chưa tồn tại - cần đăng ký
            	System.out.println("Khách hàng chưa tồn tại");
            	if (tenKH == null || tenKH.trim().isEmpty()) {
            		// Chưa nhập tên - quay về form yêu cầu nhập tên
            		System.out.println("Chưa nhập tên, quay về form đăng ký");
            		request.setAttribute("sdt", sdt); // Giữ lại SĐT đã nhập
            		request.setAttribute("yeuCauTen", true); // Hiển thị field nhập tên
            		request.setAttribute("msg", "Số điện thoại chưa đăng ký. Vui lòng nhập họ tên để đăng ký.");
            		request.getRequestDispatcher("DangNhapKhach.jsp").forward(request, response);
            	} else {
            		// Có tên - tiến hành đăng ký
            		System.out.println("Có tên, tiến hành đăng ký");
            		kh = bo.dangKy(tenKH.trim(), sdt.trim());
            		request.getSession().setAttribute("khachhang", kh);
            		System.out.println("Khách hàng mới đăng ký: " + kh.getTenKH() + " - " + kh.getSdt());

            		// Kiểm tra có redirect URL không
            		String redirectUrl = (String) request.getSession().getAttribute("redirectAfterLogin");
            		if (redirectUrl != null) {
            			request.getSession().removeAttribute("redirectAfterLogin");
            			response.sendRedirect(redirectUrl);
            		} else {
            			response.sendRedirect("TrangChuController");
            		}
            	}
            }
            System.out.println("=== DangNhapKhachController doPost END ===");

        } catch (Exception e) {
            System.out.println("=== EXCEPTION in DangNhapKhachController ===");
            e.printStackTrace();
            request.setAttribute("err", "Có lỗi xảy ra. Vui lòng thử lại.");
            request.getRequestDispatcher("DangNhapKhach.jsp").forward(request, response);
        }
    }
}