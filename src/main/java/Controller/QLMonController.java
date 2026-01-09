package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Model.MonAn.MonAn;
import Model.MonAn.MonAnBo;

@WebServlet("/QLMonController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 5 * 1024 * 1024,
    maxRequestSize = 10 * 1024 * 1024
)
public class QLMonController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ================== GET ==================
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MonAnBo monBO = new MonAnBo();

        try {
            String action = request.getParameter("action");

            // LOAD DANH SÁCH
            if (action == null) {
                ArrayList<MonAn> ds = monBO.getAll();
                request.setAttribute("dsMon", ds);
                request.getRequestDispatcher("qlMon.jsp").forward(request, response);
                return;
            }

            // TÌM KIẾM
            if ("search".equals(action)) {
                String key = request.getParameter("key");
                ArrayList<MonAn> ds = monBO.timTheoTen(key);
                request.setAttribute("dsMon", ds);
                request.setAttribute("key", key);
                request.getRequestDispatcher("qlMon.jsp").forward(request, response);
                return;
            }

            // FORM THÊM
            if ("add".equals(action)) {
                request.getRequestDispatcher("themMon.jsp").forward(request, response);
                return;
            }

            // FORM SỬA
            if ("edit".equals(action)) {
                long maMon = Long.parseLong(request.getParameter("id"));
                MonAn m = monBO.getById(maMon);
                request.setAttribute("mon", m);
                request.getRequestDispatcher("suaMon.jsp").forward(request, response);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================== POST ==================
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MonAnBo monBO = new MonAnBo();

        try {
            request.setCharacterEncoding("UTF-8");
            String action = request.getParameter("action");

            long maMon = request.getParameter("maMon") == null ? 0
                    : Long.parseLong(request.getParameter("maMon"));

            String tenMon = request.getParameter("tenMon");
            long gia = Long.parseLong(request.getParameter("gia"));
            String moTa = request.getParameter("moTa");
            long maDM = Long.parseLong(request.getParameter("maDM"));
            int trangThai = Integer.parseInt(request.getParameter("trangThai"));
            int soLuong = Integer.parseInt(request.getParameter("soLuong"));

            // ====== XỬ LÝ FILE ẢNH ======
            String hinhAnh = null;
            Part filePart = request.getPart("anh");

            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName())
                        .getFileName().toString();

                String uploadPath = request.getServletContext()
                        .getRealPath("/images");

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                filePart.write(uploadPath + File.separator + fileName);
                hinhAnh = "images/" + fileName;
            } else {
                // trường hợp sửa nhưng không đổi ảnh
                hinhAnh = request.getParameter("hinhCu");
            }

            MonAn m = new MonAn(maMon, tenMon, gia, hinhAnh, moTa, maDM, trangThai, soLuong);

            boolean success = false;

            try {
                if ("insert".equals(action)) {
                    monBO.insert(m);
                    success = true;
                }

                if ("update".equals(action)) {
                    monBO.update(m);
                    success = true;
                }

                if ("delete".equals(action)) {
                    monBO.delete(maMon);
                    success = true;
                }

                if (success) {
                    request.getSession().setAttribute("msgSuccess", "Thao tác thành công!");
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("msgError", "❌ Có lỗi xảy ra, vui lòng thử lại!");
            }

            response.sendRedirect("QLMonController");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
