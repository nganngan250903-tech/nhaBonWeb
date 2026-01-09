package Model.HoaDon;

public class ChiTietHoaDon {
    private long maCTHD;
    private long maHD;
    private long  maMon;
    private int soLuong;
    private long DonGia;
    private int trangThai;
    private long ChietKhau;
	public ChiTietHoaDon(long maCTHD, long maHD, long maMon, int soLuong, long donGia, int trangThai,
			long chietKhau) {
		super();
		this.maCTHD = maCTHD;
		this.maHD = maHD;
		this.maMon = maMon;
		this.soLuong = soLuong;
		DonGia = donGia;
		this.trangThai = trangThai;
		ChietKhau = chietKhau;
	}
	@Override
	public String toString() {
		return "ChiTietHoaDon [maCTHD=" + maCTHD + ", maHD=" + maHD + ", maMon=" + maMon + ", soLuong=" + soLuong
				+ ", DonGia=" + DonGia + ", trangThai=" + trangThai + ", ChietKhau=" + ChietKhau + "]";
	}
	public long getMaCTHD() {
		return maCTHD;
	}
	public void setMaCTHD(long maCTHD) {
		this.maCTHD = maCTHD;
	}
	public long getMaHD() {
		return maHD;
	}
	public void setMaHD(long maHD) {
		this.maHD = maHD;
	}
	public long getMaMon() {
		return maMon;
	}
	public void setMaMon(long maMon) {
		this.maMon = maMon;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public long getDonGia() {
		return DonGia;
	}
	public void setDonGia(long donGia) {
		DonGia = donGia;
	}
	public int getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}
	public long getChietKhau() {
		return ChietKhau;
	}
	public void setChietKhau(long chietKhau) {
		ChietKhau = chietKhau;
	}
    
    
}