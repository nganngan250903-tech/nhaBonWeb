package Model.HoaDon;

import java.sql.*;
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
}
