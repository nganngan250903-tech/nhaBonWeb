package Model.HoaDon;

import java.util.Objects;

public class ChiTietHoaDon {
    private int maCTHD;
    private int maHD;
    private int maMon;
    private int soLuong;
    private double donGia;
    private double thanhTien;
    private int trangThai;
	public int getMaCTHD() {
		return maCTHD;
	}
	public void setMaCTHD(int maCTHD) {
		this.maCTHD = maCTHD;
	}
	public int getMaHD() {
		return maHD;
	}
	public void setMaHD(int maHD) {
		this.maHD = maHD;
	}
	public int getMaMon() {
		return maMon;
	}
	public void setMaMon(int maMon) {
		this.maMon = maMon;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	public double getThanhTien() {
		return thanhTien;
	}
	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}
	public int getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}
	@Override
	public String toString() {
		return "ChiTietHoaDon [maCTHD=" + maCTHD + ", maHD=" + maHD + ", maMon=" + maMon + ", soLuong=" + soLuong
				+ ", donGia=" + donGia + ", thanhTien=" + thanhTien + ", trangThai=" + trangThai + "]";
	}
	
	public ChiTietHoaDon(int maCTHD, int maHD, int maMon, int soLuong, double donGia, double thanhTien, int trangThai) {
		super();
		this.maCTHD = maCTHD;
		this.maHD = maHD;
		this.maMon = maMon;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.thanhTien = thanhTien;
		this.trangThai = trangThai;
	}

    
}
