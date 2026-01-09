package Model.HoaDon;

import java.sql.Timestamp;

public class HoaDonBO {

    HoaDonDAO hdDao = new HoaDonDAO();

    public long taoHoaDon(long maBan, long maNV, long maKH, long tongTien) throws Exception {

        HoaDon hd = new HoaDon();
        hd.setMaBan(maBan);
        hd.setMaNV(maNV);
        hd.setMaKH(maKH);
        hd.setGioVao(new Timestamp(System.currentTimeMillis()));
        hd.setTongTien(tongTien);
        hd.setThanhToan(0); // CHƯA THANH TOÁN

        return hdDao.themHoaDon(hd);
    }
    HoaDonDAO dao = new HoaDonDAO();
    public int getTongHoaDon() {
		return dao.countHoaDon();
	}
}
