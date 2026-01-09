package Model.HoaDon;

import java.sql.Timestamp;

import java.util.Objects;

public class HoaDon {
	 private long maHD;
	    private long maBan;
	    private long maNV;
	    private Timestamp gioVao;
	    private Timestamp gioRa;
	    private long tongTien;
	    private int ThanhToan;
	    private long MaKH;
	    public HoaDon() {}
		public long getMaHD() {
			return maHD;
		}
		public void setMaHD(long maHD) {
			this.maHD = maHD;
		}
		public long getMaBan() {
			return maBan;
		}
		public void setMaBan(long maBan) {
			this.maBan = maBan;
		}
		public long getMaNV() {
			return maNV;
		}
		public void setMaNV(long maNV) {
			this.maNV = maNV;
		}
		public Timestamp getGioVao() {
			return gioVao;
		}
		public void setGioVao(Timestamp gioVao) {
			this.gioVao = gioVao;
		}
		public Timestamp getGioRa() {
			return gioRa;
		}
		public void setGioRa(Timestamp gioRa) {
			this.gioRa = gioRa;
		}
		public long getTongTien() {
			return tongTien;
		}
		public void setTongTien(long tongTien) {
			this.tongTien = tongTien;
		}
		public int getThanhToan() {
			return ThanhToan;
		}
		public void setThanhToan(int thanhToan) {
			ThanhToan = thanhToan;
		}
		public long getMaKH() {
			return MaKH;
		}
		public void setMaKH(long maKH) {
			MaKH = maKH;
		}
		@Override
		public String toString() {
			return "HoaDon [maHD=" + maHD + ", maBan=" + maBan + ", maNV=" + maNV + ", gioVao=" + gioVao + ", gioRa="
					+ gioRa + ", tongTien=" + tongTien + ", ThanhToan=" + ThanhToan + ", MaKH=" + MaKH + "]";
		}
		public HoaDon(long maHD, long maBan, long maNV, Timestamp gioVao, Timestamp gioRa, long tongTien,
				int thanhToan, long maKH) {
			super();
			this.maHD = maHD;
			this.maBan = maBan;
			this.maNV = maNV;
			this.gioVao = gioVao;
			this.gioRa = gioRa;
			this.tongTien = tongTien;
			ThanhToan = thanhToan;
			MaKH = maKH;
		}
	    
}