package Model.HoaDon;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonBO {

    ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO();

    public void taoCTHD(ChiTietHoaDon ct) throws Exception {
            dao.themCTHD(ct);
    }

    // Lấy chi tiết hóa đơn theo mã HD với thông tin món ăn
    public List<Object[]> getChiTietByMaHD(long maHD) throws Exception {
        return dao.getChiTietByMaHD(maHD);
    }

    // Cập nhật trạng thái món ăn
    public boolean capNhatTrangThai(long maCTHD, int trangThai) throws Exception {
        return dao.capNhatTrangThai(maCTHD, trangThai);
    }

    // Lấy chi tiết hóa đơn theo trạng thái
    public List<Object[]> getChiTietByTrangThai(int trangThai) throws Exception {
        return dao.getChiTietByTrangThai(trangThai);
    }
}
