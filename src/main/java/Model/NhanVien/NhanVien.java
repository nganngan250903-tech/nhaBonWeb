package Model.NhanVien;

import java.util.Objects;

public class NhanVien {
    private String MaNV;
    private String tenNV;
    private String maQuyen;
    private String userName;
    private String pass;
    private boolean trangThaiNV;
	@Override
	public String toString() {
		return "NhanVien [MaNV=" + MaNV + ", tenNV=" + tenNV + ", maQuyen=" + maQuyen + ", userName=" + userName
				+ ", pass=" + pass + ", trangThaiNV=" + trangThaiNV + "]";
	}
	public String getMaNV() {
		return MaNV;
	}
	public void setMaNV(String maNV) {
		MaNV = maNV;
	}
	public String getTenNV() {
		return tenNV;
	}
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	public String getMaQuyen() {
		return maQuyen;
	}
	public void setMaQuyen(String maQuyen) {
		this.maQuyen = maQuyen;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public boolean isTrangThaiNV() {
		return trangThaiNV;
	}
	public void setTrangThaiNV(boolean trangThaiNV) {
		this.trangThaiNV = trangThaiNV;
	}
	public NhanVien(String maNV, String tenNV, String maQuyen, String userName, String pass, boolean trangThaiNV) {
		super();
		MaNV = maNV;
		this.tenNV = tenNV;
		this.maQuyen = maQuyen;
		this.userName = userName;
		this.pass = pass;
		this.trangThaiNV = trangThaiNV;
	}
}