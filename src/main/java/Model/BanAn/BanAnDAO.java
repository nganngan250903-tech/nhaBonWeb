package Model.BanAn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import Model.KetNoi.KetNoi;

public class BanAnDAO {

	// ===== LẤY TẤT CẢ BÀN =====
	public ArrayList<BanAn> getAll() throws Exception {
		ArrayList<BanAn> ds = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();

		String sql = "SELECT * FROM BanAn";
		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			ds.add(new BanAn(
				rs.getLong("MaBan"),
				rs.getString("TenBan"),
				rs.getInt("TrangThai"),
				rs.getString("QRCode")
			));
		}

		rs.close();
		ps.close();
		kn.cn.close();
		return ds;
	}

	// ===== ĐẾM TỔNG BÀN =====
	public int countBan() {
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT COUNT(*) FROM BanAn";
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

	// ===== ĐẾM BÀN ĐANG DÙNG =====
	public int countBanDangDung() {
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT COUNT(*) FROM BanAn WHERE TrangThai=1";
			PreparedStatement ps = kn.cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int result = rs.getInt(1);
				rs.close();
				ps.close();
				kn.cn.close();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// ===== THÊM BÀN =====
	public int insert(String tenBan) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();

		String sql = "INSERT INTO BanAn(TenBan, TrangThai) VALUES (?,0)";
		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ps.setString(1, tenBan);

		int kq = ps.executeUpdate();

		ps.close();
		kn.cn.close();
		return kq;
	}

	// ===== SỬA BÀN =====
	public int update(long maBan, String tenBan) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();

		String sql = "UPDATE BanAn SET TenBan=? WHERE MaBan=?";
		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ps.setString(1, tenBan);
		ps.setLong(2, maBan);

		int kq = ps.executeUpdate();

		ps.close();
		kn.cn.close();
		return kq;
	}

	// ===== XÓA BÀN =====
	public int delete(long maBan) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();

		// Kiểm tra xem bàn có đang được sử dụng không (có hóa đơn chưa thanh toán)
		String checkSql = "SELECT COUNT(*) FROM HoaDon WHERE MaBan=? AND ThanhToan=0";
		PreparedStatement checkPs = kn.cn.prepareStatement(checkSql);
		checkPs.setLong(1, maBan);
		ResultSet checkRs = checkPs.executeQuery();

		if (checkRs.next() && checkRs.getInt(1) > 0) {
			checkRs.close();
			checkPs.close();
			kn.cn.close();
			throw new Exception("Không thể xóa bàn đang có hóa đơn chưa thanh toán!");
		}

		checkRs.close();
		checkPs.close();

		String sql = "DELETE FROM BanAn WHERE MaBan=?";
		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ps.setLong(1, maBan);

		int kq = ps.executeUpdate();

		ps.close();
		kn.cn.close();
		return kq;
	}

	// Get table usage statistics
	public List<Object[]> getThongKeSuDungBan() {
		List<Object[]> result = new ArrayList<>();
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT ba.TenBan, COUNT(hd.MaHD) as soLanSuDung, COALESCE(SUM(hd.TongTien), 0) as tongDoanhThu " +
						"FROM BanAn ba " +
						"LEFT JOIN HoaDon hd ON ba.MaBan = hd.MaBan AND hd.ThanhToan = 1 " +
						"GROUP BY ba.MaBan, ba.TenBan " +
						"ORDER BY soLanSuDung DESC";

			PreparedStatement ps = kn.cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				result.add(new Object[]{
					rs.getString("TenBan") != null ? rs.getString("TenBan") : "N/A",
					rs.getInt("soLanSuDung"),
					rs.getLong("tongDoanhThu")
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
