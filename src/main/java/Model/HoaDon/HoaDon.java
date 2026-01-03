package Model.HoaDon;

import java.sql.Timestamp;

import java.util.Objects;

public class HoaDon {
	 private String maHD;
	    private String maBan;
	    private String maNV;
	    private Timestamp gioVao;
	    private Timestamp gioRa;
	    private long tongTien;
	    private int trangThai;
		public String getMaHD() {
			return maHD;
		}
		public void setMaHD(String maHD) {
			this.maHD = maHD;
		}
		public String getMaBan() {
			return maBan;
		}
		public void setMaBan(String maBan) {
			this.maBan = maBan;
		}
		public String getMaNV() {
			return maNV;
		}
		public void setMaNV(String maNV) {
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
		public int getTrangThai() {
			return trangThai;
		}
		public void setTrangThai(int trangThai) {
			this.trangThai = trangThai;
		}
		public HoaDon(String maHD, String maBan, String maNV, Timestamp gioVao, Timestamp gioRa, long tongTien,
				int trangThai) {
			super();
			this.maHD = maHD;
			this.maBan = maBan;
			this.maNV = maNV;
			this.gioVao = gioVao;
			this.gioRa = gioRa;
			this.tongTien = tongTien;
			this.trangThai = trangThai;
		}
		@Override
		public String toString() {
			return "HoaDon [maHD=" + maHD + ", maBan=" + maBan + ", maNV=" + maNV + ", gioVao=" + gioVao + ", gioRa="
					+ gioRa + ", tongTien=" + tongTien + ", trangThai=" + trangThai + "]";
		}
	    
}