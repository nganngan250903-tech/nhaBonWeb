package Model.BanAn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import Model.KetNoi.KetNoi;

public class BanAnDAO {

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
