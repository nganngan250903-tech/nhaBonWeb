package Model.HoaDon;

import java.util.ArrayList;

public class ChiTietHoaDonBo {

    ChiTietHoaDonDao dao = new ChiTietHoaDonDao();

    public ArrayList<ChiTietHoaDon> getCTHD(String maHD) throws Exception {
        return dao.getCTHDTheoMaHD(maHD);
    }

    public void themMon(String maHD, String maMon, int soLuong, long donGia) throws Exception {
        dao.themMon(maHD, maMon, soLuong, donGia);
    }

    public void tangSoLuong(String maCTHD, String maHD) throws Exception {
        dao.tangSoLuong(maCTHD, maHD);
    }

    public void giamSoLuong(String maCTHD, String maHD) throws Exception {
        dao.giamSoLuong(maCTHD, maHD);
    }

    public void xoaMon(String maCTHD, String maHD) throws Exception {
        dao.xoaMon(maCTHD);
        new HoaDonDao().capNhatTongTien(maHD);
    }
    public void themMonCoGhiChu(
            String maHD,
            String maMon,
            int soLuong,
            String ghiChu
    ) throws Exception {
        dao.themMonCoGhiChu(maHD, maMon, soLuong, ghiChu);
        new HoaDonDao().capNhatTongTien(maHD);
    }

}
