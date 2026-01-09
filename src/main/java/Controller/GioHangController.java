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
import Model.MonAn.MonAn;
import Model.MonAn.MonAnBo;

/**
 * Servlet implementation class GioHangController
 */
@WebServlet("/GioHangController")
public class GioHangController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        GioHangBo gio = (GioHangBo) session.getAttribute("gio");

        if (gio == null) {
            gio = new GioHangBo();
            session.setAttribute("gio", gio);
        }

        String maMon = request.getParameter("maMon");
        String action = request.getParameter("action");

        try {
            // ===== KIỂM TRA ĐĂNG NHẬP KHÁCH HÀNG =====
            Model.KhachHang.KhachHang kh = (Model.KhachHang.KhachHang) session.getAttribute("khachhang");
            if (kh == null) {
                // Chưa đăng nhập - chuyển đến trang đăng nhập
                session.setAttribute("redirectAfterLogin", request.getRequestURI() +
                    (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
                response.sendRedirect("DangNhapKhachController");
                return;
            }

            // ➕ THÊM MÓN → quay lại MENU
            if (maMon != null && "add".equals(action)) {
                MonAnBo monBo = new MonAnBo();
                MonAn m = monBo.getById(Long.parseLong(maMon));

                gio.them(m.getMaMon(), m.getTenMon(),
                         m.getHinhAnh(), m.getGia());

                // 🔴 CỰC KỲ QUAN TRỌNG - Lưu lại vào session trước khi redirect
                session.setAttribute("gio", gio);
                response.sendRedirect("TrangChuController");
                return;
            }

            // ❌ XÓA MÓN
            if ("xoa".equals(action)) {
                if (kh == null) {
                    response.sendRedirect("DangNhapKhachController");
                    return;
                }
                gio.xoa(Long.parseLong(maMon));
            }

            // 🔄 CẬP NHẬT SỐ LƯỢNG
            if ("update".equals(action)) {
                if (kh == null) {
                    response.sendRedirect("DangNhapKhachController");
                    return;
                }
                int sl = Integer.parseInt(request.getParameter("soLuong"));
                gio.capNhat(Long.parseLong(maMon), sl);
            }

            // 📝 CẬP NHẬT GHI CHÚ
            if ("capnhat".equals(action)) {
                if (kh == null) {
                    response.sendRedirect("DangNhapKhachController");
                    return;
                }
                for (GioHang g : gio.getDs()) {
                    String note = request.getParameter("note_" + g.getMaMon());
                    if (note != null) {
                        gio.capNhatGhiChu(g.getMaMon(), note);
                    }
                }
            }
            session.setAttribute("gio", gio);
            // 👉 MẶC ĐỊNH: MỞ TRANG ĐƠN HÀNG
            request.getRequestDispatcher("DonHang.jsp")
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
