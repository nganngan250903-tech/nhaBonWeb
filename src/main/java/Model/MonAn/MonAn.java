package Model.MonAn;

public class MonAn {
	private String maMon;
    private String tenMon;
    private long gia;
    private String hinhAnh;
    private String moTa;
    private String maDM;
    private String trangThaiMon;
	public String getMaMon() {
		return maMon;
	}
	public void setMaMon(String maMon) {
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
	public String getMaDM() {
		return maDM;
	}
	public void setMaDM(String maDM) {
		this.maDM = maDM;
	}
	public String getTrangThaiMon() {
		return trangThaiMon;
	}
	public void setTrangThaiMon(String trangThaiMon) {
		this.trangThaiMon = trangThaiMon;
	}
	public MonAn() {}

    // constructor đầy đủ
    public MonAn(String maMon, String tenMon, long gia,
                 String hinhAnh, String moTa,
                 String maDM, String trangThaiMon) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
        this.maDM = maDM;
        this.trangThaiMon = trangThaiMon;
    }
	@Override
	public String toString() {
		return "MonAnBo [maMon=" + maMon + ", tenMon=" + tenMon + ", gia=" + gia + ", hinhAnh=" + hinhAnh + ", moTa="
				+ moTa + ", maDM=" + maDM + ", trangThaiMon=" + trangThaiMon + "]";
	}

}
