package Model.BanAn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
