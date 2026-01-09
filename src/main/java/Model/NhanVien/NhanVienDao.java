package Model.NhanVien;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import Model.KetNoi.KetNoi;



public class NhanVienDao {

    public NhanVien dangNhap(String un, String passMD5) throws Exception {
        Model.KetNoi.KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = """
            SELECT * FROM NhanVien
            WHERE UserName = ? AND Pass = ? AND TrangThaiNV = 1
        """;

        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, un);
        ps.setString(2, passMD5);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new NhanVien(
                rs.getString("MaNV"),
                rs.getString("TenNV"),
                rs.getInt("MaQuyen"),
                rs.getString("UserName"),
                rs.getString("Pass"),
                rs.getBoolean("TrangThaiNV")
            );
        }
        return null;
    }

    public boolean dangKy(NhanVien nv) throws Exception {
        Model.KetNoi.KetNoi kn = new Model.KetNoi.KetNoi();
        kn.ketnoi();

        String sql = """
        	    INSERT INTO NhanVien
(TenNV, MaQuyen, UserName, Pass, TrangThaiNV)
VALUES (?, ?, ?, ?, ?)
        	""";

        	PreparedStatement ps = kn.cn.prepareStatement(sql);
        	
        	ps.setString(1, nv.getTenNV());
        	ps.setInt(2, nv.getMaQuyen()); // ✅ QUAN TRỌNG
        	ps.setString(3, nv.getUserName());
        	ps.setString(4, nv.getPass());
        	ps.setBoolean(5, nv.isTrangThaiNV());

        	return ps.executeUpdate() > 0;
    }

    // Get total number of employees
    public int getTongNhanVien() {
        try {
            KetNoi kn = new KetNoi();
            kn.ketnoi();

            String sql = "SELECT COUNT(*) FROM NhanVien WHERE TrangThaiNV = 1";
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

    // Get employee performance statistics
    public List<Object[]> getThongKeNhanVien() {
        List<Object[]> result = new ArrayList<>();
        try {
            KetNoi kn = new KetNoi();
            kn.ketnoi();

            String sql = "SELECT nv.TenNV, COUNT(hd.MaHD) as soHoaDon, COALESCE(SUM(hd.TongTien), 0) as tongDoanhThu " +
                        "FROM NhanVien nv " +
                        "LEFT JOIN HoaDon hd ON nv.MaNV = hd.MaNV AND hd.ThanhToan = 1 " +
                        "WHERE nv.TrangThaiNV = 1 " +
                        "GROUP BY nv.MaNV, nv.TenNV " +
                        "ORDER BY tongDoanhThu DESC";

            PreparedStatement ps = kn.cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(new Object[]{
                    rs.getString("TenNV") != null ? rs.getString("TenNV") : "N/A",
                    rs.getInt("soHoaDon"),
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
