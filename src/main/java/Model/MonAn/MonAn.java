package Model.MonAn;

public class MonAn {
	private long maMon;
    private String tenMon;
    private long gia;
    private String hinhAnh;
    private String moTa;
    private long maDM;
    private int trangThaiMon; // 0: đang làm . 1: đã làm
    private  int SoLuong ;
	public long getMaMon() {
		return maMon;
	}
	public void setMaMon(long maMon) {
		this.maMon = maMon;
	}
	public String getTenMon() {
		return tenMon;
	}
	public void setTenMon(String tenMon) {
		this.tenMon = tenMon;
	}
	public long getGia() {
		return gia;
	}
	public void setGia(long gia) {
		this.gia = gia;
	}
	public String getHinhAnh() {
		return hinhAnh;
	}
	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public long getMaDM() {
		return maDM;
	}
	public void setMaDM(long maDM) {
		this.maDM = maDM;
	}
	public int getTrangThaiMon() {
		return trangThaiMon;
	}
	public void setTrangThaiMon(int trangThaiMon) {
		this.trangThaiMon = trangThaiMon;
	}
	public int getSoLuong() {
		return SoLuong;
	}
	public void setSoLuong(int soLuong) {
		SoLuong = soLuong;
	}
	public MonAn(long maMon, String tenMon, long gia, String hinhAnh, String moTa, long maDM, int trangThaiMon,
			int soLuong) {
		super();
		this.maMon = maMon;
		this.tenMon = tenMon;
		this.gia = gia;
		this.hinhAnh = hinhAnh;
		this.moTa = moTa;
		this.maDM = maDM;
		this.trangThaiMon = trangThaiMon;
		SoLuong = soLuong;
	}
	@Override
	public String toString() {
		return "MonAn [maMon=" + maMon + ", tenMon=" + tenMon + ", gia=" + gia + ", hinhAnh=" + hinhAnh + ", moTa="
				+ moTa + ", maDM=" + maDM + ", trangThaiMon=" + trangThaiMon + ", SoLuong=" + SoLuong + "]";
	}
    }