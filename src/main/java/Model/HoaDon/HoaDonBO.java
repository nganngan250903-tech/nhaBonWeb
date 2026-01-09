package Model.HoaDon;

import java.sql.Timestamp;
import java.util.List;

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

	// Lấy đơn hàng theo khách hàng (đơn hàng gần nhất chưa thanh toán)
	public List<Object[]> getDonHangByKhachHang(long maKH) throws Exception {
		return dao.getDonHangByKhachHang(maKH);
	}

	// Lấy tất cả đơn hàng đang xử lý (chưa thanh toán)
	public List<Object[]> getAllDonHangDangXuLy() throws Exception {
		return dao.getAllDonHangDangXuLy();
	}

	// Lấy tất cả hóa đơn với thông tin khách hàng
	public List<Object[]> getAllHoaDonWithKhachHang() throws Exception {
		return dao.getAllHoaDonWithKhachHang();
	}

	// Lấy hóa đơn theo ID với thông tin khách hàng
	public Object[] getHoaDonById(long maHD) throws Exception {
		return dao.getHoaDonById(maHD);
	}
}
