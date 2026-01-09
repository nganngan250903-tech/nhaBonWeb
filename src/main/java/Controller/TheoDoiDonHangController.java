package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.HoaDon.ChiTietHoaDonBO;
import Model.HoaDon.HoaDonBO;
import Model.KhachHang.KhachHang;

/**
 * Controller cho trang theo dõi đơn hàng của khách hàng
 */
@WebServlet("/TheoDoiDonHangController")
public class TheoDoiDonHangController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Kiểm tra đăng nhập khách hàng
        KhachHang kh = (KhachHang) session.getAttribute("khachhang");
        if (kh == null) {
            response.sendRedirect("DangNhapKhachController");
            return;
        }

        try {
            HoaDonBO hoaDonBO = new HoaDonBO();
            ChiTietHoaDonBO ctBO = new ChiTietHoaDonBO();

            // Lay thong tin don hang theo ban
            // Uu tien lay tu session neu co ma hoa don vua tao
            Long maHDGoc = (Long) session.getAttribute("maHDGoc");

            if (maHDGoc != null) {
                // Lay thong tin don hang vua tao
                List<Object[]> dsDonHang = hoaDonBO.getDonHangByMaHD(maHDGoc);

                if (dsDonHang != null && !dsDonHang.isEmpty()) {
                    Object[] donHang = dsDonHang.get(0);
                    long maHD = (Long) donHang[0];

                    // Lấy chi tiết đơn hàng với trạng thái
                    List<Object[]> dsChiTiet = null;
                    try {
                        dsChiTiet = ctBO.getChiTietByMaHD(maHD);
                        System.out.println("Loaded " + (dsChiTiet != null ? dsChiTiet.size() : 0) + " chi tiet for MaHD: " + maHD);
                    } catch (Exception e) {
                        System.out.println("Error loading chi tiet for MaHD " + maHD + ": " + e.getMessage());
                        e.printStackTrace();
                        dsChiTiet = new ArrayList<>(); // Empty list if error
                    }

                    request.setAttribute("donHang", donHang);
                    request.setAttribute("dsChiTiet", dsChiTiet);
                    request.setAttribute("maHD", maHD);
                }

                // Xoa session sau khi da su dung
                session.removeAttribute("maHDGoc");

            } else {
                // Lay don hang gan nhat theo ban (mac dinh ban 1)
                // Trong thuc te, can lay thong tin ban tu session hoac database
                long maBan = 1; // Default ban, co the lay tu session

                List<Object[]> dsDonHang = hoaDonBO.getDonHangByBan(maBan);

                if (dsDonHang != null && !dsDonHang.isEmpty()) {
                    // Lay don hang gan nhat (dau tien trong danh sach da sap xep)
                    Object[] donHang = dsDonHang.get(0);
                    long maHD = (Long) donHang[0];

                    // Lấy chi tiết đơn hàng với trạng thái
                    List<Object[]> dsChiTiet = null;
                    try {
                        dsChiTiet = ctBO.getChiTietByMaHD(maHD);
                        System.out.println("Loaded " + (dsChiTiet != null ? dsChiTiet.size() : 0) + " chi tiet for MaHD: " + maHD);
                    } catch (Exception e) {
                        System.out.println("Error loading chi tiet for MaHD " + maHD + ": " + e.getMessage());
                        e.printStackTrace();
                        dsChiTiet = new ArrayList<>(); // Empty list if error
                    }

                    request.setAttribute("donHang", donHang);
                    request.setAttribute("dsChiTiet", dsChiTiet);
                    request.setAttribute("maHD", maHD);
                }
            }

            request.setAttribute("khachHang", kh);
            request.setAttribute("activeMenu", "theodoi");
            request.getRequestDispatcher("TheoDoiDonHang.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải thông tin đơn hàng");
            request.getRequestDispatcher("TheoDoiDonHang.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}