package Model.DanhMuc;

public class DanhMuc {
	private long MaDM;
	private String TenDM;
	private String MoTa;
	public long getMaDM() {
		return MaDM;
	}
	public void setMaDM(long maDM) {
		MaDM = maDM;
	}
	public String getTenDM() {
		return TenDM;
	}
	public void setTenDM(String tenDM) {
		TenDM = tenDM;
	}
	public String getMoTa() {
		return MoTa;
	}
	public void setMoTa(String moTa) {
		MoTa = moTa;
	}
	@Override
	public String toString() {
		return "DanhMuc [MaDM=" + MaDM + ", TenDM=" + TenDM + ", MoTa=" + MoTa + "]";
	}
	public DanhMuc(long maDM, String tenDM, String moTa) {
		super();
		MaDM = maDM;
		TenDM = tenDM;
		MoTa = moTa;
	}
	
}
