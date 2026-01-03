package Model.BanAn;

import java.util.ArrayList;

import Model.HoaDon.HoaDonDao;

public class BanAnBo {

    BanAnDao banDao = new BanAnDao();
    HoaDonDao hdDao = new HoaDonDao();

    /* ========== CRUD ========== */

    public ArrayList<BanAn> getDanhSachBan() throws Exception {
        return banDao.getDanhSachBan();
    }

    public void themBan(BanAn b) throws Exception {
        banDao.themBan(b);
    }

    public void suaBan(BanAn b) throws Exception {
        banDao.suaBan(b);
    }

    public void xoaBan(String maBan) throws Exception {
        banDao.xoaBan(maBan);
    }

    /* ========== NGHIỆP VỤ QUÁN ========== */

    // Chuyển bàn (bàn A -> bàn B)
    public void chuyenBan(String maBanCu, String maBanMoi) throws Exception {
        String maHD = hdDao.getMaHoaDonDangMo(maBanCu);
        if (maHD != null) {
            hdDao.chuyenHoaDon(maHD, maBanMoi);
            banDao.capNhatTrangThai(maBanCu, 0);
            banDao.capNhatTrangThai(maBanMoi, 1);
        }
    }
    public BanAn getBanTheoMa(String maBan) throws Exception {
        return banDao.getBanTheoMa(maBan);
    }

    // Gộp bàn (A + B -> A)
    public void gopBan(String banChinh, String banPhu) throws Exception {
        String hdChinh = hdDao.getMaHoaDonDangMo(banChinh);
        String hdPhu = hdDao.getMaHoaDonDangMo(banPhu);

        if (hdChinh != null && hdPhu != null) {
            hdDao.gopHoaDon(hdPhu, hdChinh);
            banDao.capNhatTrangThai(banPhu, 0);
        }
    }

    // Tách bàn (1 bàn -> tạo bàn mới + hóa đơn mới)
    
    
}
