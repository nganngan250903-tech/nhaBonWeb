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

	// Lấy đơn hàng theo khách hàng (đơn hàng gần nhất chưa thanh toán)
	public List<Object[]> getDonHangByKhachHang(long maKH) throws Exception {
		List<Object[]> result = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT TOP 1 MaHD, MaBan, MaNV, GioVao, GioRa, TongTien, ThanhToan, MaKH " +
						 "FROM HoaDon " +
						 "WHERE MaKH = ? AND ThanhToan = 0 " +
						 "ORDER BY GioVao DESC";

			ps = kn.cn.prepareStatement(sql);
			ps.setLong(1, maKH);

			rs = ps.executeQuery();
			if (rs.next()) {
				result.add(new Object[]{
					rs.getLong("MaHD"),
					rs.getLong("MaBan"),
					rs.getLong("MaNV"),
					rs.getTimestamp("GioVao"),
					rs.getTimestamp("GioRa"),
					rs.getLong("TongTien"),
					rs.getInt("ThanhToan"),
					rs.getLong("MaKH")
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			if (kn.cn != null) kn.cn.close();
		}
		return result;
	}

	// Lấy tất cả đơn hàng đang xử lý (chưa thanh toán) với thông tin khách hàng
	public List<Object[]> getAllDonHangDangXuLy() throws Exception {
		List<Object[]> result = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT hd.MaHD, hd.MaBan, hd.GioVao, hd.TongTien, kh.TenKH, kh.SDT " +
						 "FROM HoaDon hd " +
						 "JOIN KhachHang kh ON hd.MaKH = kh.MaKH " +
						 "WHERE hd.ThanhToan = 0 " +
						 "ORDER BY hd.GioVao DESC";

			ps = kn.cn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				result.add(new Object[]{
					rs.getLong("MaHD"),
					rs.getLong("MaBan"),
					rs.getTimestamp("GioVao"),
					rs.getLong("TongTien"),
					rs.getString("TenKH") != null ? rs.getString("TenKH") : "",
					rs.getString("SDT") != null ? rs.getString("SDT") : ""
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			if (kn.cn != null) kn.cn.close();
		}
		return result;
	}

	// Lấy tất cả hóa đơn với thông tin khách hàng
	public List<Object[]> getAllHoaDonWithKhachHang() throws Exception {
		List<Object[]> result = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT hd.MaHD, hd.MaBan, hd.GioVao, hd.GioRa, hd.TongTien, hd.ThanhToan, " +
						 "kh.TenKH, kh.SDT, nv.TenNV " +
						 "FROM HoaDon hd " +
						 "LEFT JOIN KhachHang kh ON hd.MaKH = kh.MaKH " +
						 "LEFT JOIN NhanVien nv ON hd.MaNV = nv.MaNV " +
						 "ORDER BY hd.GioVao DESC";

			ps = kn.cn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				result.add(new Object[]{
					rs.getLong("MaHD"),
					rs.getLong("MaBan"),
					rs.getTimestamp("GioVao"),
					rs.getTimestamp("GioRa"),
					rs.getLong("TongTien"),
					rs.getInt("ThanhToan"),
					rs.getString("TenKH") != null ? rs.getString("TenKH") : "N/A",
					rs.getString("SDT") != null ? rs.getString("SDT") : "N/A",
					rs.getString("TenNV") != null ? rs.getString("TenNV") : "N/A"
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			if (kn.cn != null) kn.cn.close();
		}
		return result;
	}

	// Lấy hóa đơn theo ID với thông tin khách hàng
	public Object[] getHoaDonById(long maHD) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT hd.MaHD, hd.MaBan, hd.GioVao, hd.GioRa, hd.TongTien, hd.ThanhToan, " +
						 "kh.TenKH, kh.SDT, nv.TenNV " +
						 "FROM HoaDon hd " +
						 "LEFT JOIN KhachHang kh ON hd.MaKH = kh.MaKH " +
						 "LEFT JOIN NhanVien nv ON hd.MaNV = nv.MaNV " +
						 "WHERE hd.MaHD = ?";

			ps = kn.cn.prepareStatement(sql);
			ps.setLong(1, maHD);
			rs = ps.executeQuery();

			if (rs.next()) {
				return new Object[]{
					rs.getLong("MaHD"),
					rs.getLong("MaBan"),
					rs.getTimestamp("GioVao"),
					rs.getTimestamp("GioRa"),
					rs.getLong("TongTien"),
					rs.getInt("ThanhToan"),
					rs.getString("TenKH") != null ? rs.getString("TenKH") : "N/A",
					rs.getString("SDT") != null ? rs.getString("SDT") : "N/A",
					rs.getString("TenNV") != null ? rs.getString("TenNV") : "N/A"
				};
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			if (kn.cn != null) kn.cn.close();
		}
		return null;
	}

	// Lấy đơn hàng theo mã hóa đơn
	public List<Object[]> getDonHangByMaHD(long maHD) throws Exception {
		List<Object[]> result = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT MaHD, MaBan, MaNV, GioVao, GioRa, TongTien, ThanhToan, MaKH " +
						 "FROM HoaDon " +
						 "WHERE MaHD = ?";

			ps = kn.cn.prepareStatement(sql);
			ps.setLong(1, maHD);

			rs = ps.executeQuery();
			if (rs.next()) {
				result.add(new Object[]{
					rs.getLong("MaHD"),
					rs.getLong("MaBan"),
					rs.getLong("MaNV"),
					rs.getTimestamp("GioVao"),
					rs.getTimestamp("GioRa"),
					rs.getLong("TongTien"),
					rs.getInt("ThanhToan"),
					rs.getLong("MaKH")
				});
			}
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			kn.cn.close();
		}
		return result;
	}

	// Lấy đơn hàng theo bàn (đơn hàng gần nhất)
	public List<Object[]> getDonHangByBan(long maBan) throws Exception {
		List<Object[]> result = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT TOP 5 MaHD, MaBan, MaNV, GioVao, GioRa, TongTien, ThanhToan, MaKH " +
						 "FROM HoaDon " +
						 "WHERE MaBan = ? " +
						 "ORDER BY GioVao DESC";

			ps = kn.cn.prepareStatement(sql);
			ps.setLong(1, maBan);

			rs = ps.executeQuery();
			while (rs.next()) {
				result.add(new Object[]{
					rs.getLong("MaHD"),
					rs.getLong("MaBan"),
					rs.getLong("MaNV"),
					rs.getTimestamp("GioVao"),
					rs.getTimestamp("GioRa"),
					rs.getLong("TongTien"),
					rs.getInt("ThanhToan"),
					rs.getLong("MaKH")
				});
			}
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			kn.cn.close();
		}
		return result;
	}

	// Cập nhật trạng thái thanh toán của hóa đơn
	public boolean capNhatTrangThaiThanhToan(long maHD, int trangThai) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;

		try {
			String sql = "UPDATE HoaDon SET ThanhToan = ? WHERE MaHD = ?";
			ps = kn.cn.prepareStatement(sql);
			ps.setInt(1, trangThai);
			ps.setLong(2, maHD);

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} finally {
			if (ps != null) ps.close();
			kn.cn.close();
		}
	}

	// Cập nhật tổng tiền của hóa đơn
	public boolean capNhatTongTien(long maHD, long tongTien) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;

		try {
			String sql = "UPDATE HoaDon SET TongTien = ? WHERE MaHD = ?";
			ps = kn.cn.prepareStatement(sql);
			ps.setLong(1, tongTien);
			ps.setLong(2, maHD);

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} finally {
			if (ps != null) ps.close();
			kn.cn.close();
		}
	}

	// Lấy danh sách đơn hàng đang chờ xác nhận thanh toán (ThanhToan = 2)
	public List<Object[]> getDonHangChoXacNhan() throws Exception {
		List<Object[]> result = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT MaHD, MaBan, MaNV, GioVao, GioRa, TongTien, ThanhToan, MaKH " +
						 "FROM HoaDon " +
						 "WHERE ThanhToan = 2 " +
						 "ORDER BY GioVao DESC";

			ps = kn.cn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				result.add(new Object[]{
					rs.getLong("MaHD"),
					rs.getLong("MaBan"),
					rs.getLong("MaNV"),
					rs.getTimestamp("GioVao"),
					rs.getTimestamp("GioRa"),
					rs.getLong("TongTien"),
					rs.getInt("ThanhToan"),
					rs.getLong("MaKH")
				});
			}
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			kn.cn.close();
		}
		return result;
	}

	// Lấy danh sách đơn hàng đã thanh toán gần đây (ThanhToan = 1)
	public List<Object[]> getDonHangDaThanhToan() throws Exception {
		List<Object[]> result = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT TOP 20 MaHD, MaBan, MaNV, GioVao, GioRa, TongTien, ThanhToan, MaKH " +
						 "FROM HoaDon " +
						 "WHERE ThanhToan = 1 " +
						 "ORDER BY GioVao DESC";

			ps = kn.cn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				result.add(new Object[]{
					rs.getLong("MaHD"),
					rs.getLong("MaBan"),
					rs.getLong("MaNV"),
					rs.getTimestamp("GioVao"),
					rs.getTimestamp("GioRa"),
					rs.getLong("TongTien"),
					rs.getInt("ThanhToan"),
					rs.getLong("MaKH")
				});
			}
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			kn.cn.close();
		}
		return result;
	}

	// Lấy đơn hàng đang ăn theo bàn (ThanhToan = 3)
	public List<Object[]> getDonHangDangAnByBan(long maBan) throws Exception {
		List<Object[]> result = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT TOP 1 MaHD, MaBan, MaNV, GioVao, GioRa, TongTien, ThanhToan, MaKH " +
						 "FROM HoaDon " +
						 "WHERE MaBan = ? AND ThanhToan = 3 " +
						 "ORDER BY GioVao DESC";

			ps = kn.cn.prepareStatement(sql);
			ps.setLong(1, maBan);

			rs = ps.executeQuery();
			if (rs.next()) {
				result.add(new Object[]{
					rs.getLong("MaHD"),
					rs.getLong("MaBan"),
					rs.getLong("MaNV"),
					rs.getTimestamp("GioVao"),
					rs.getTimestamp("GioRa"),
					rs.getLong("TongTien"),
					rs.getInt("ThanhToan"),
					rs.getLong("MaKH")
				});
			}
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			kn.cn.close();
		}
		return result;
	}

	// Lấy danh sách đơn hàng đang ăn (ThanhToan = 3)
	public List<Object[]> getDonHangDangAn() throws Exception {
		List<Object[]> result = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT MaHD, MaBan, MaNV, GioVao, GioRa, TongTien, ThanhToan, MaKH " +
						 "FROM HoaDon " +
						 "WHERE ThanhToan = 3 " +
						 "ORDER BY GioVao DESC";

			ps = kn.cn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				result.add(new Object[]{
					rs.getLong("MaHD"),
					rs.getLong("MaBan"),
					rs.getLong("MaNV"),
					rs.getTimestamp("GioVao"),
					rs.getTimestamp("GioRa"),
					rs.getLong("TongTien"),
					rs.getInt("ThanhToan"),
					rs.getLong("MaKH")
				});
			}
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			kn.cn.close();
		}
		return result;
	}
}
