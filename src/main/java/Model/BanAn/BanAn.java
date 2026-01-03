package Model.BanAn;

public class BanAn {
	    private String maBan;
	    private String tenBan;
	    private int trangThai; // 0: trống | 1: đang dùng
	    private String mucBan;
	    private long tongTien;
		public String getMaBan() {
			return maBan;
		}
		public void setMaBan(String maBan) {
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
		public String getMucBan() {
			return mucBan;
		}
		public void setMucBan(String mucBan) {
			this.mucBan = mucBan;
		}
		public BanAn(String maBan, String tenBan, int trangThai, String mucBan) {
			super();
			this.maBan = maBan;
			this.tenBan = tenBan;
			this.trangThai = trangThai;
			this.mucBan = mucBan;
		}
		@Override
		public String toString() {
			return "BanAn [maBan=" + maBan + ", tenBan=" + tenBan + ", trangThai=" + trangThai + ", mucBan=" + mucBan
					+ "]";
		}
		public String getTrangThaiText() {
	        return trangThai == 1 ? "Đang dùng" : "Trống";
	    }
		
		 public long getTongTien() {
		        return tongTien;
		    }

		    public void setTongTien(long tongTien) {
		        this.tongTien = tongTien;
		    }
	    }
