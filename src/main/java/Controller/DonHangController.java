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
import Model.HoaDon.HoaDonBO;
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
            // Kiem tra action
            String action = request.getParameter("action");
            if (!"datmon".equals(action)) {
                response.sendRedirect("TrangChuController");
                return;
            }

            // Lay danh sach mon duoc chon
            String[] selectedItems = request.getParameterValues("chonMon");

            if (selectedItems == null || selectedItems.length == 0) {
                request.setAttribute("error", "Vui long chon it nhat mot mon!");
                doGet(request, response);
                return;
            }

            // Lay ma ban tu session
            Long maBan = (Long) session.getAttribute("maBan");
            if (maBan == null) {
                maBan = 1L; // Fallback neu khong co trong session
            }

            // Tinh tam tong tien ban dau tu gio hang
            long tongTienTemp = gio.tongTien();

            // Tao hoa don moi voi trang thai "dang an" (3)
            HoaDonDAO hdDao = new HoaDonDAO();
            HoaDon hd = new HoaDon();
            hd.setMaBan(maBan);
            hd.setMaNV(1L);
            hd.setMaKH(kh.getMaKH());
            hd.setGioVao(new java.sql.Timestamp(System.currentTimeMillis()));
            hd.setTongTien(tongTienTemp); // Tam thoi, se cap nhat sau
            hd.setThanhToan(3); // 3 = dang an
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

                // Cap nhat so luong tu form (neu co)
                String soLuongParam = request.getParameter("soLuong_" + maMon);
                int soLuong = 1; // Default
                if (soLuongParam != null && !soLuongParam.trim().isEmpty()) {
                    try {
                        soLuong = Integer.parseInt(soLuongParam);
                        if (soLuong < 1) soLuong = 1;
                    } catch (NumberFormatException e) {
                        soLuong = 1;
                    }
                }

                // Tim mon trong gio hang
                Model.GioHang.GioHang item = gio.getDs().stream()
                    .filter(g -> g.getMaMon() == maMon)
                    .findFirst().orElse(null);

                if (item != null) {
                    // Su dung so luong tu form hoac tu gio hang
                    int finalSoLuong = (soLuongParam != null) ? soLuong : item.getSoLuong();

                    ChiTietHoaDon ct = new ChiTietHoaDon(
                        0, // maCTHD se tu tang
                        maHD,
                        item.getMaMon(),
                        finalSoLuong,
                        item.getGia(),
                        0, // TrangThai = 0 (Dang lam)
                        0  // ChietKhau = 0
                    );
                    ctDao.themCTHD(ct);
                }
            }

            // Luu ma hoa don vao session de theo doi
            session.setAttribute("maHDGoc", maHD);
            session.setAttribute("trangThaiDonHang", 3); // dang an

            // Tinh lai tong tien dua tren so luong da cap nhat
            long tongTienCapNhat = 0;
            for (String maMonStr : selectedItems) {
                long maMon = Long.parseLong(maMonStr);
                String soLuongParam = request.getParameter("soLuong_" + maMon);
                Model.GioHang.GioHang item = gio.getDs().stream()
                    .filter(g -> g.getMaMon() == maMon)
                    .findFirst().orElse(null);

                if (item != null) {
                    int soLuong = (soLuongParam != null && !soLuongParam.trim().isEmpty()) ?
                        Integer.parseInt(soLuongParam) : item.getSoLuong();
                    tongTienCapNhat += (long) soLuong * item.getGia();
                }
            }
            HoaDonBO hdBo = new HoaDonBO();
            // Cap nhat tong tien trong HoaDon
            hdBo.capNhatTongTien(maHD, tongTienCapNhat);

            // Cap nhat tong tien trong session
            session.setAttribute("tongTienThanhToan", tongTienCapNhat);

            // Xóa giỏ hàng sau khi đặt món thành công
            session.removeAttribute("gio");

            // Luu thong bao thanh cong
            session.setAttribute("success", "Đặt món thành công! Đơn hàng #" + maHD + " đang được chế biến.");

            // Chuyển đến trang theo dõi đơn hàng
            response.sendRedirect("TheoDoiDonHangController");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi đặt hàng!");
            doGet(request, response);
        }
    }
}
