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
import Model.NhanVien.NhanVien;

/**
 * Controller cho admin quản lý trạng thái món ăn
 */
@WebServlet("/QuanLyTrangThaiController")
public class QuanLyTrangThaiController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiem tra quyen admin
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("DangNhapController");
            return;
        }

        NhanVien nv = (NhanVien) session.getAttribute("nhanvien");
        if (nv == null || nv.getMaQuyen() != 1) {
            response.sendRedirect("TrangChuController");
            return;
        }

        try {
            HoaDonBO hoaDonBO = new HoaDonBO();
            ChiTietHoaDonBO ctBO = new ChiTietHoaDonBO();

            // Kiểm tra nếu có parameter maHD (xem chi tiết hóa đơn cụ thể)
            String maHDParam = request.getParameter("maHD");
            if (maHDParam != null && !maHDParam.trim().isEmpty()) {
                // Hiển thị chi tiết của một hóa đơn cụ thể
                long maHD = Long.parseLong(maHDParam);
                List<Object[]> chiTiet = ctBO.getChiTietByMaHD(maHD);
                Object[] hoaDonInfo = hoaDonBO.getHoaDonById(maHD);

                request.setAttribute("hoaDonInfo", hoaDonInfo);
                request.setAttribute("dsChiTiet", chiTiet);
                request.setAttribute("viewMode", "detail");
            } else {
                // Lấy tất cả đơn hàng đang xử lý (chưa thanh toán)
                List<Object[]> dsDonHang = hoaDonBO.getAllDonHangDangXuLy();

            // Tạo list mới với chi tiết món ăn
            List<Object[]> dsDonHangWithChiTiet = new ArrayList();

            for (Object[] donHang : dsDonHang) {
                long maHD = (Long) donHang[0];
                List<Object[]> chiTiet = null;

                try {
                    chiTiet = ctBO.getChiTietByMaHD(maHD);
                    System.out.println("Loaded " + (chiTiet != null ? chiTiet.size() : 0) + " chi tiet for MaHD: " + maHD);
                } catch (Exception e) {
                    System.out.println("Error loading chi tiet for MaHD " + maHD + ": " + e.getMessage());
                    e.printStackTrace();
                    chiTiet = new ArrayList<>(); // Empty list if error
                }

                // Tạo array mới với chi tiết
                Object[] donHangWithChiTiet = new Object[donHang.length + 1];
                System.arraycopy(donHang, 0, donHangWithChiTiet, 0, donHang.length);
                donHangWithChiTiet[donHang.length] = chiTiet;

                dsDonHangWithChiTiet.add(donHangWithChiTiet);
            }

                request.setAttribute("dsDonHang", dsDonHangWithChiTiet);
            }
            request.setAttribute("admin", nv);
            request.getRequestDispatcher("QuanLyTrangThai.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải danh sách đơn hàng");
            request.getRequestDispatcher("QuanLyTrangThai.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiem tra quyen admin
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("DangNhapController");
            return;
        }

        NhanVien nv = (NhanVien) session.getAttribute("nhanvien");
        if (nv == null || nv.getMaQuyen() != 1) {
            response.sendRedirect("TrangChuController");
            return;
        }

        try {
            String action = request.getParameter("action");

            if ("updateStatus".equals(action)) {
                long maCTHD = Long.parseLong(request.getParameter("maCTHD"));
                int trangThai = Integer.parseInt(request.getParameter("trangThai"));

                ChiTietHoaDonBO ctBO = new ChiTietHoaDonBO();
                boolean success = ctBO.capNhatTrangThai(maCTHD, trangThai);

                if (success) {
                    session.setAttribute("msgSuccess", "Cap nhat trang thai thanh cong!");
                } else {
                    session.setAttribute("msgError", "Cap nhat trang thai that bai!");
                }
            }

            // Chuyển hướng về trang chi tiết nếu có maHD
            String maHD = request.getParameter("maHD");
            if (maHD != null && !maHD.trim().isEmpty()) {
                response.sendRedirect("QuanLyTrangThaiController?maHD=" + maHD);
            } else {
                response.sendRedirect("QuanLyTrangThaiController");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msgError", "Co loi xay ra!");
            response.sendRedirect("QuanLyTrangThaiController");
        }
    }
}