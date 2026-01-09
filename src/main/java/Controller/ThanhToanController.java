package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.GioHang.GioHangBo;
import Model.KhachHang.KhachHang;

/**
 * Controller cho trang thanh toán
 */
@WebServlet("/ThanhToanController")
public class ThanhToanController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        GioHangBo gio = (GioHangBo) session.getAttribute("gio");
        KhachHang kh = (KhachHang) session.getAttribute("khachhang");

        // Kiểm tra giỏ hàng và khách hàng
        if (gio == null || gio.getDs().isEmpty()) {
            response.sendRedirect("TrangChuController");
            return;
        }

        if (kh == null) {
            response.sendRedirect("DangNhapKhachController");
            return;
        }

        // Tính tổng tiền
        long tongTien = gio.tongTien();

        request.setAttribute("khachHang", kh);
        request.setAttribute("gioHang", gio);
        request.setAttribute("tongTien", tongTien);
        request.setAttribute("activeMenu", "thanhtoan");

        request.getRequestDispatcher("ThanhToan.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        GioHangBo gio = (GioHangBo) session.getAttribute("gio");
        KhachHang kh = (KhachHang) session.getAttribute("khachhang");

        // Kiểm tra lại điều kiện (double check)
        if (gio == null || gio.getDs().isEmpty() || kh == null) {
            response.sendRedirect("TrangChuController");
            return;
        }

        // Lưu thông tin thanh toán vào session để PaymentController sử dụng
        session.setAttribute("thanhToanInfo", "Đã xác nhận thanh toán");

        // Chuyển đến trang thanh toán
        response.sendRedirect("PaymentController");
    }
}