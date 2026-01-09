package Model.HoaDon;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import Model.KetNoi.KetNoi;

public class HoaDonDAO {

    public long themHoaDon(HoaDon hd) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql =
          "INSERT INTO HoaDon(maBan, maNV, gioVao, tongTien, ThanhToan, MaKH) " +
          "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = kn.cn.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS);

        ps.setLong(1, hd.getMaBan());
        ps.setLong(2, hd.getMaNV());
        ps.setTimestamp(3, hd.getGioVao());
        ps.setLong(4, hd.getTongTien());
        ps.setInt(5, hd.getThanhToan()); // 0 = chưa thanh toán
        ps.setLong(6, hd.getMaKH());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        long maHD = -1;
        if (rs.next()) maHD = rs.getLong(1);

        kn.cn.close();
        return maHD;
    }
    public int countHoaDon() {
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT COUNT(*) FROM HoaDon";
			PreparedStatement ps = kn.cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Get total revenue from paid invoices
	public long getTongDoanhThu() {
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT COALESCE(SUM(TongTien), 0) FROM HoaDon WHERE ThanhToan = 1";
			PreparedStatement ps = kn.cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong(1);
			}
			rs.close();
			ps.close();
			kn.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Get revenue for today
	public long getDoanhThuHomNay() {
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT COALESCE(SUM(TongTien), 0) FROM HoaDon WHERE ThanhToan = 1 AND DATE(GioVao) = CURDATE()";
			PreparedStatement ps = kn.cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong(1);
			}
			rs.close();
			ps.close();
			kn.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Get revenue for this week
	public long getDoanhThuTuanNay() {
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT COALESCE(SUM(TongTien), 0) FROM HoaDon WHERE ThanhToan = 1 AND YEARWEEK(GioVao) = YEARWEEK(CURDATE())";
			PreparedStatement ps = kn.cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong(1);
			}
			rs.close();
			ps.close();
			kn.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Get revenue for this month
	public long getDoanhThuThangNay() {
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT COALESCE(SUM(TongTien), 0) FROM HoaDon WHERE ThanhToan = 1 AND MONTH(GioVao) = MONTH(CURDATE()) AND YEAR(GioVao) = YEAR(CURDATE())";
			PreparedStatement ps = kn.cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong(1);
			}
			rs.close();
			ps.close();
			kn.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Get count of paid invoices
	public int getSoHoaDonDaThanhToan() {
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT COUNT(*) FROM HoaDon WHERE ThanhToan = 1";
			PreparedStatement ps = kn.cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
			rs.close();
			ps.close();
			kn.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Get count of unpaid invoices
	public int getSoHoaDonChuaThanhToan() {
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT COUNT(*) FROM HoaDon WHERE ThanhToan = 0";
			PreparedStatement ps = kn.cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
			rs.close();
			ps.close();
			kn.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Get daily revenue for the last 7 days
	public List<long[]> getDoanhThu7NgayGanNhat() {
		List<long[]> result = new ArrayList<>();
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT DATE(GioVao) as ngay, COALESCE(SUM(TongTien), 0) as doanhthu " +
						"FROM HoaDon " +
						"WHERE ThanhToan = 1 AND DATE(GioVao) >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) " +
						"GROUP BY DATE(GioVao) " +
						"ORDER BY DATE(GioVao)";

			PreparedStatement ps = kn.cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				result.add(new long[]{rs.getDate("ngay").getTime(), rs.getLong("doanhthu")});
			}
			rs.close();
			ps.close();
			kn.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// Get top 5 best-selling dishes
	public List<Object[]> getTopMonAnBanChay() {
		List<Object[]> result = new ArrayList<>();
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT m.TenMon, SUM(ct.SoLuong) as tongSoLuong " +
						"FROM ChiTietHoaDon ct " +
						"JOIN MonAn m ON ct.MaMon = m.MaMon " +
						"JOIN HoaDon hd ON ct.MaHD = hd.MaHD " +
						"WHERE hd.ThanhToan = 1 " +
						"GROUP BY m.TenMon, ct.MaMon " +
						"ORDER BY tongSoLuong DESC " +
						"LIMIT 5";

			PreparedStatement ps = kn.cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				result.add(new Object[]{
					rs.getString("TenMon") != null ? rs.getString("TenMon") : "N/A",
					rs.getInt("tongSoLuong")
				});
			}
			rs.close();
			ps.close();
			kn.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
