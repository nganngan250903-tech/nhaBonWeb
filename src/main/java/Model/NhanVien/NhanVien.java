package Model.NhanVien;

import java.util.Objects;

public class NhanVien {
    private String MaNV;
    private String tenNV;
    private int MaQuyen;
    private String userName;
    private String pass;
    private boolean trangThaiNV;
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
	public int getMaQuyen() {
		return MaQuyen;
	}
	public void setMaQuyen(int maQuyen) {
		MaQuyen = maQuyen;
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
	public NhanVien(String maNV, String tenNV, int maQuyen, String userName, String pass, boolean trangThaiNV) {
		super();
		MaNV = maNV;
		this.tenNV = tenNV;
		MaQuyen = maQuyen;
		this.userName = userName;
		this.pass = pass;
		this.trangThaiNV = trangThaiNV;
	}
	@Override
	public String toString() {
		return "NhanVien [MaNV=" + MaNV + ", tenNV=" + tenNV + ", MaQuyen=" + MaQuyen + ", userName=" + userName
				+ ", pass=" + pass + ", trangThaiNV=" + trangThaiNV + "]";
	}
    
}