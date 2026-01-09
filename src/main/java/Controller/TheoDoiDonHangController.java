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

                        // Lấy chi tiết đơn hàng với trạng thái từng món
                        List<Object[]> dsChiTiet = null;
                        try {
                            dsChiTiet = ctBO.getChiTietByMaHD(maHD);
                            System.out.println("Loaded " + (dsChiTiet != null ? dsChiTiet.size() : 0) + " chi tiet món ăn for MaHD: " + maHD);

                            // Phân tích trạng thái các món
                            boolean hasProcessingItems = false;
                            boolean hasCompletedItems = false;
                            if (dsChiTiet != null) {
                                for (Object[] item : dsChiTiet) {
                                    if (item.length >= 8) {
                                        int trangThai = (Integer) item[7]; // Index 7 là trạng thái món
                                        if (trangThai == 0) hasProcessingItems = true;
                                        else if (trangThai == 1) hasCompletedItems = true;
                                    }
                                }
                            }

                            request.setAttribute("hasProcessingItems", hasProcessingItems);
                            request.setAttribute("hasCompletedItems", hasCompletedItems);

                        } catch (Exception e) {
                            System.out.println("Error loading chi tiet món ăn for MaHD " + maHD + ": " + e.getMessage());
                            e.printStackTrace();
                            dsChiTiet = new ArrayList<>();
                        }

                        request.setAttribute("donHang", donHang);
                        request.setAttribute("dsChiTiet", dsChiTiet);
                        request.setAttribute("maHD", maHD);
                    }

                    // Xoa session sau khi da su dung
                    session.removeAttribute("maHDGoc");

                } else {
                // Lay ma ban tu session
                Long maBan = (Long) session.getAttribute("maBan");
                if (maBan == null) {
                    maBan = 1L; // Fallback neu khong co trong session
                }

                List<Object[]> dsDonHang = hoaDonBO.getDonHangDangAnByBan(maBan);

                if (dsDonHang != null && !dsDonHang.isEmpty()) {
                    // Lay don hang gan nhat (dau tien trong danh sach da sap xep)
                    Object[] donHang = dsDonHang.get(0);
                    long maHD = (Long) donHang[0];

                    // Lấy chi tiết đơn hàng với trạng thái từng món
                    List<Object[]> dsChiTiet = null;
                    try {
                        dsChiTiet = ctBO.getChiTietByMaHD(maHD);
                        System.out.println("Loaded " + (dsChiTiet != null ? dsChiTiet.size() : 0) + " chi tiet món ăn for MaHD: " + maHD);

                        // Phân tích trạng thái các món
                        boolean hasProcessingItems = false;
                        boolean hasCompletedItems = false;
                        if (dsChiTiet != null) {
                            for (Object[] item : dsChiTiet) {
                                if (item.length >= 8) {
                                    int trangThai = (Integer) item[7]; // Index 7 là trạng thái món
                                    if (trangThai == 0) hasProcessingItems = true;
                                    else if (trangThai == 1) hasCompletedItems = true;
                                }
                            }
                        }

                        request.setAttribute("hasProcessingItems", hasProcessingItems);
                        request.setAttribute("hasCompletedItems", hasCompletedItems);

                    } catch (Exception e) {
                        System.out.println("Error loading chi tiet món ăn for MaHD " + maHD + ": " + e.getMessage());
                        e.printStackTrace();
                        dsChiTiet = new ArrayList<>();
                    }

                    request.setAttribute("donHang", donHang);
                    request.setAttribute("dsChiTiet", dsChiTiet);
                    request.setAttribute("maHD", maHD);
                }
            }

            // Lay thong bao thanh cong tu session (neu co)
            String success = (String) session.getAttribute("success");
            if (success != null) {
                request.setAttribute("success", success);
                session.removeAttribute("success"); // Xoa sau khi da su dung
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