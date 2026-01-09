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
import Model.HoaDon.ChiTietHoaDonDAO;
import Model.HoaDon.HoaDon;
import Model.HoaDon.HoaDonDAO;

/**
 * Servlet implementation class DonHangController
 */
@WebServlet("/DonHangController")
public class DonHangController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Kiểm tra đăng nhập khách hàng
        Model.KhachHang.KhachHang kh = (Model.KhachHang.KhachHang) session.getAttribute("khachhang");
        if (kh == null) {
            response.sendRedirect("DangNhapKhachController");
            return;
        }

        GioHangBo gio = (GioHangBo) session.getAttribute("gio");

        if (gio == null || gio.getDs().isEmpty()) {
            response.sendRedirect("TrangChuController");
            return;
        }

        request.setAttribute("khachHang", kh);
        request.setAttribute("activeMenu", "donhang");
        request.getRequestDispatcher("DonHang.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Kiểm tra đăng nhập khách hàng
        Model.KhachHang.KhachHang kh = (Model.KhachHang.KhachHang) session.getAttribute("khachhang");
        if (kh == null) {
            response.sendRedirect("DangNhapKhachController");
            return;
        }

        GioHangBo gio = (GioHangBo) session.getAttribute("gio");

        if (gio == null || gio.getDs().isEmpty()) {
            response.sendRedirect("TrangChuController");
            return;
        }

        try {
            // Lay danh sach mon duoc chon
            String[] selectedItems = request.getParameterValues("chonMon");

            if (selectedItems == null || selectedItems.length == 0) {
                request.setAttribute("error", "Vui long chon it nhat mot mon!");
                doGet(request, response);
                return;
            }

            // Tao hoa don moi
            HoaDonDAO hdDao = new HoaDonDAO();
            HoaDon hd = new HoaDon();
            hd.setMaBan(1L);
            hd.setMaNV(1L);
            hd.setMaKH(kh.getMaKH());
            hd.setGioVao(new java.sql.Timestamp(System.currentTimeMillis()));
            hd.setTongTien(gio.tongTien());
            hd.setThanhToan(0);
            long maHD = hdDao.themHoaDon(hd);

            if (maHD <= 0) {
                request.setAttribute("error", "Khong the tao hoa don!");
                doGet(request, response);
                return;
            }

            // Tao chi tiet hoa don cho cac mon duoc chon
            ChiTietHoaDonDAO ctDao = new ChiTietHoaDonDAO();

            for (String maMonStr : selectedItems) {
                long maMon = Long.parseLong(maMonStr);
                // Tim mon trong gio hang
                Model.GioHang.GioHang item = gio.getDs().stream()
                    .filter(g -> g.getMaMon() == maMon)
                    .findFirst().orElse(null);

                if (item != null) {
                    ChiTietHoaDon ct = new ChiTietHoaDon(
                        0, // maCTHD se tu tang
                        maHD,
                        item.getMaMon(),
                        item.getSoLuong(),
                        item.getGia(),
                        0, // TrangThai = 0 (Dang lam)
                        0  // ChietKhau = 0
                    );
                    ctDao.themCTHD(ct);
                }
            }

            // Xoa gio hang sau khi dat hang thanh cong
            session.removeAttribute("gio");

            // Chuyển đến trang theo dõi đơn hàng
            response.sendRedirect("TheoDoiDonHangController");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi đặt hàng!");
            doGet(request, response);
        }
    }
}
