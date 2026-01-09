package Model.GioHang;

public class GioHang {
    private long maMon;
    private String tenMon;
    private String hinhAnh;
    private long gia;
    private int soLuong;
    private String ghiChu;

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }


    public GioHang(long maMon, String tenMon, String hinhAnh,
            long gia, int soLuong) {
	 this.maMon = maMon;
	 this.tenMon = tenMon;
	 this.hinhAnh = hinhAnh;
	 this.gia = gia;
	 this.soLuong = soLuong;
	 this.ghiChu = "";
	}


    public long getThanhTien() {
        return gia * soLuong;
    }

    // getter & setter
    public long getMaMon() { return maMon; }
    public String getTenMon() { return tenMon; }
    public String getHinhAnh() { return hinhAnh; }
    public long getGia() { return gia; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}
