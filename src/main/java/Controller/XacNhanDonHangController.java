package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.GioHang.GioHang;
import Model.GioHang.GioHangBo;
import Model.HoaDon.ChiTietHoaDon;
import Model.HoaDon.ChiTietHoaDonBO;
import Model.HoaDon.HoaDonBO;

/**
 * Servlet implementation class XacNhanDonHangController
 */
@WebServlet("/XacNhanDonHangController")
public class XacNhanDonHangController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		HttpSession session = request.getSession();
		HoaDonBO hdBo = new HoaDonBO();

        try {
        	GioHangBo gh = (GioHangBo)session.getAttribute("gio");
        	
        	// ví dụ demo (bạn có thể lấy từ session đăng nhập)
        	long maBan = 1;
        	long maNV  = 1;
        	long maKH  = 1;
        	long tongTien = 0;
        	for (GioHang item : gh.getDs())
        		tongTien+= item.getThanhTien();
        	// 1. TẠO HÓA ĐƠN
			long maHD = hdBo.taoHoaDon(
        	        maBan,
        	        maNV,
        	        maKH,
        	        tongTien
        	);

        	// 2. TẠO CHI TIẾT HÓA ĐƠN TỪ GIỎ HÀNG
        	ChiTietHoaDonBO ctBo = new ChiTietHoaDonBO();
        	for (GioHang g : gh.getDs()) {
        	    ChiTietHoaDon ct = new ChiTietHoaDon(
        	            0,
        	            maHD,
        	            g.getMaMon(),
        	            g.getSoLuong(),
        	            g.getGia(),
        	            0,                    // 0 = đang làm
        	            0
        	    );
        	    ctBo.taoCTHD(ct);
        	}
        	
        	// 3. XÓA GIỎ HÀNG SAU KHI TẠO ĐƠN THÀNH CÔNG
        	session.removeAttribute("gio");

            // Chuyển đến trang thanh toán thay vì về trang chủ
            response.sendRedirect("PaymentController");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    doGet(request, response);
	}
}