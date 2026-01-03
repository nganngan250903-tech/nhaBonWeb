package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.BanAn.BanAn;
import Model.BanAn.BanAnBo;
import Model.HoaDon.ChiTietHoaDon;
import Model.HoaDon.ChiTietHoaDonBo;
import Model.HoaDon.HoaDon;
import Model.HoaDon.HoaDonBo;
import Model.MonAn.MonAn;
import Model.MonAn.MonAnBo;

@WebServlet("/HomeController")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    BanAnBo banBo = new BanAnBo();
    HoaDonBo hoaDonBo = new HoaDonBo();
    ChiTietHoaDonBo cthdBo = new ChiTietHoaDonBo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	MonAnBo monBo = new MonAnBo();
    	String key = request.getParameter("key");

    	ArrayList<MonAn> dsMon = null;
    	if (key != null && !key.trim().isEmpty()) {
    	    try {
				dsMon = monBo.timTheoTen(key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} else {
    	    try {
				dsMon = monBo.getAll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	request.setAttribute("dsMon", dsMon);


        try {
            /* ===== LOAD DANH SÁCH BÀN ===== */
            ArrayList<BanAn> dsBan = banBo.getDanhSachBan();

            for (BanAn b : dsBan) {
                if (b.getTrangThai() == 1) {
                    HoaDon hd = hoaDonBo.moHoaDon(b.getMaBan(), "NV01");
                    if (hd != null) {
                        b.setTongTien(
                            hoaDonBo.tinhTongTien(hd.getMaHD())
                        );
                    }
                }
            }
            request.setAttribute("dsBan", dsBan);

            /* ===== CHỌN BÀN ===== */
            String maBan = request.getParameter("maBan");
            String maNV = "NV01";

            if (maBan != null) {

                BanAn banDangChon = banBo.getBanTheoMa(maBan);
                HoaDon hd = hoaDonBo.moHoaDon(maBan, maNV);

                ArrayList<ChiTietHoaDon> dsCTHD =
                        cthdBo.getCTHD(hd.getMaHD());

                long tongTien =
                        hoaDonBo.tinhTongTien(hd.getMaHD());

                request.setAttribute("banDangChon", banDangChon);
                request.setAttribute("hoaDon", hd);
                request.setAttribute("dsCTHD", dsCTHD);
                request.setAttribute("tongTien", tongTien);
            }

            RequestDispatcher rd = request.getRequestDispatcher("Home.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            String maBan = request.getParameter("maBan");
            String maHD = request.getParameter("maHD");
            if ("taoHoaDon".equals(action)) {
                String[] dsMon = request.getParameterValues("chonMon");
                HoaDon hd = hoaDonBo.moHoaDon(maBan, "NV01");

                if (dsMon != null) {
                    for (String maMon : dsMon) {
                        int soLuong = Integer.parseInt(
                            request.getParameter("soLuong_" + maMon)
                        );
                        String ghiChu = request.getParameter("ghiChu_" + maMon);

                        cthdBo.themMonCoGhiChu(
                            hd.getMaHD(),
                            maMon,
                            soLuong,
                            ghiChu
                        );
                    }
                }
            }

            if ("themMon".equals(action)) {
                cthdBo.themMon(
                    maHD,
                    request.getParameter("maMon"),
                    Integer.parseInt(request.getParameter("soLuong")),
                    Long.parseLong(request.getParameter("donGia"))
                );
            }

            if ("tang".equals(action)) {
                cthdBo.tangSoLuong(
                    request.getParameter("maCTHD"),
                    maHD
                );
            }

            if ("giam".equals(action)) {
                cthdBo.giamSoLuong(
                    request.getParameter("maCTHD"),
                    maHD
                );
            }

            if ("xoa".equals(action)) {
                cthdBo.xoaMon(
                    request.getParameter("maCTHD"),
                    maHD
                );
            }

            response.sendRedirect("HomeController?maBan=" + maBan);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
