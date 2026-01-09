package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.NhanVien.NhanVien;

/**
 * Filter để kiểm tra quyền admin trước khi truy cập các trang quản lý
 */
@WebFilter({
    "/QLMonController",
    "/QLDanhMucController",
    "/QLBanController",
    "/StatisticsController"
})
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo filter
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // ===== KIỂM TRA SESSION =====
        HttpSession session = req.getSession(false);
        if (session == null) {
            res.sendRedirect("DangNhapController");
            return;
        }

        // ===== KIỂM TRA NHÂN VIÊN =====
        NhanVien nv = (NhanVien) session.getAttribute("nhanvien");
        if (nv == null) {
            res.sendRedirect("DangNhapController");
            return;
        }

        // ===== KIỂM TRA QUYỀN ADMIN =====
        if (nv.getMaQuyen() != 1) {
            // Không phải admin, chuyển về trang chủ
            res.sendRedirect("TrangChuController");
            return;
        }

        // ===== CHO PHÉP TRUY CẬP =====
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Hủy filter
    }
}