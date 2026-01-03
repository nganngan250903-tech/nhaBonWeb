package Model.HoaDon;

import Model.BanAn.BanAnDao;

public class HoaDonBo {

    private HoaDonDao dao = new HoaDonDao();

    /* ========== HÓA ĐƠN ========== */
    public long tinhTongTien(String maHD) throws Exception {
        long tong = dao.tinhTongTien(maHD);
        dao.capNhatTongTien(maHD);
        return tong;
    }
    public HoaDon moHoaDon(String maBan, String maNV) throws Exception {
        HoaDon hd = dao.getHoaDonDangMo(maBan);
        if (hd == null) {
            dao.taoHoaDonMoi(maBan, maNV);
            hd = dao.getHoaDonDangMo(maBan);
        }
        return hd;
    }

    public void dongHoaDon(String maHD, String maBan) throws Exception {
        long tongTien = dao.tinhTongTien(maHD);
        dao.dongHoaDon(maHD, tongTien);

        BanAnDao banDao = new BanAnDao();
        banDao.capNhatTrangThai(maBan, 0);
    }

    public void capNhatTongTien(String maHD) throws Exception {
        dao.capNhatTongTien(maHD);
    }

    public void chuyenBan(String maHD, String maBanMoi) throws Exception {
        dao.chuyenHoaDon(maHD, maBanMoi);
    }

    public void gopBan(String maHDPhu, String maHDChinh) throws Exception {
        dao.gopHoaDon(maHDPhu, maHDChinh);
        dao.capNhatTongTien(maHDChinh);
    }

    public void huyHoaDon(String maHD, String maBan) throws Exception {
        dao.huyHoaDon(maHD);

        BanAnDao banDao = new BanAnDao();
        banDao.capNhatTrangThai(maBan, 0);
    }
}
