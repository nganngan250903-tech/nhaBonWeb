package Model.BanAn;

public class BanAn {
    private long maBan;        // bigint
    private String tenBan;     // nvarchar(50)
    private int trangThai;     // int 0: Trống, 1: Đag dùng
    private String qrcode;     // nvarchar(50)
	public long getMaBan() {
		return maBan;
	}
	public void setMaBan(long maBan) {
		this.maBan = maBan;
	}
	public String getTenBan() {
		return tenBan;
	}
	public void setTenBan(String tenBan) {
		this.tenBan = tenBan;
	}
	public int getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	@Override
	public String toString() {
		return "BanAn [maBan=" + maBan + ", tenBan=" + tenBan + ", trangThai=" + trangThai + ", qrcode=" + qrcode + "]";
	}
	public BanAn(long maBan, String tenBan, int trangThai, String qrcode) {
		super();
		this.maBan = maBan;
		this.tenBan = tenBan;
		this.trangThai = trangThai;
		this.qrcode = qrcode;
	}
    
    
}