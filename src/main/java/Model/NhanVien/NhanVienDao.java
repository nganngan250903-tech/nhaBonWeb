package Model.NhanVien;



import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
                rs.getString("MaQuyen"),
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
            VALUES (?, ?, 'NV', ?, ?, 1)
        """;

        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, nv.getMaNV());
        ps.setString(2, nv.getTenNV());
        ps.setString(3, nv.getUserName());
        ps.setString(4, nv.getPass());

        return ps.executeUpdate() > 0;
    }
}
