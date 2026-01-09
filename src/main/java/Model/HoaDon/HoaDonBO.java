package Model.HoaDon;

import java.sql.Timestamp;

public class HoaDonBO {

    HoaDonDAO dao = new HoaDonDAO();

    public long taoHoaDon(long maBan, long maNV, long maKH, long tongTien) throws Exception {

        HoaDon hd = new HoaDon();
        hd.setMaBan(maBan);
        hd.setMaNV(maNV);
        hd.setMaKH(maKH);
        hd.setGioVao(new Timestamp(System.currentTimeMillis()));
        hd.setTongTien(tongTien);
        hd.setThanhToan(0); // CHƯA THANH TOÁN

        return dao.themHoaDon(hd);
    }

    public int getTongHoaDon() {
		return dao.countHoaDon();
	}

	public long getTongDoanhThu() {
		return dao.getTongDoanhThu();
	}

	public long getDoanhThuHomNay() {
		return dao.getDoanhThuHomNay();
	}

	public long getDoanhThuTuanNay() {
		return dao.getDoanhThuTuanNay();
	}

	public long getDoanhThuThangNay() {
		return dao.getDoanhThuThangNay();
	}

	public int getSoHoaDonDaThanhToan() {
		return dao.getSoHoaDonDaThanhToan();
	}

	public int getSoHoaDonChuaThanhToan() {
		return dao.getSoHoaDonChuaThanhToan();
	}

	public List<long[]> getDoanhThu7NgayGanNhat() {
		return dao.getDoanhThu7NgayGanNhat();
	}

	public List<Object[]> getTopMonAnBanChay() {
		return dao.getTopMonAnBanChay();
	}
}
