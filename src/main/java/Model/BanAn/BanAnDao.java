package Model.BanAn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Model.KetNoi.KetNoi;

public class BanAnDao {

    KetNoi kn = new KetNoi();

    /* =================== CRUD =================== */

    public ArrayList<BanAn> getDanhSachBan() throws Exception {
        ArrayList<BanAn> ds = new ArrayList<>();
        kn.ketnoi();

        String sql = "SELECT * FROM BanAn ORDER BY MaBan";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ds.add(new BanAn(
                rs.getString("MaBan"),
                rs.getString("TenBan"),
                rs.getInt("TrangThai"),
                rs.getString("MucBan")
            ));
        }
        rs.close(); ps.close();
        return ds;
    }

    public void themBan(BanAn b) throws Exception {
        kn.ketnoi();
        String sql = "INSERT INTO BanAn VALUES(?,?,0,?)";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, b.getMaBan());
        ps.setString(2, b.getTenBan());
        ps.setString(3, b.getMucBan());
        ps.executeUpdate();
        ps.close();
    }

    public void suaBan(BanAn b) throws Exception {
        kn.ketnoi();
        String sql = """
            UPDATE BanAn
            SET TenBan=?, MucBan=?
            WHERE MaBan=?
        """;
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, b.getTenBan());
        ps.setString(2, b.getMucBan());
        ps.setString(3, b.getMaBan());
        ps.executeUpdate();
        ps.close();
    }

    public void xoaBan(String maBan) throws Exception {
        kn.ketnoi();
        String sql = "DELETE FROM BanAn WHERE MaBan=?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, maBan);
        ps.executeUpdate();
        ps.close();
    }

    public BanAn getBanTheoMa(String maBan) throws Exception {
        kn.ketnoi();
        String sql = "SELECT * FROM BanAn WHERE MaBan=?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, maBan);
        ResultSet rs = ps.executeQuery();

        BanAn b = null;
        if (rs.next()) {
            b = new BanAn(
                rs.getString("MaBan"),
                rs.getString("TenBan"),
                rs.getInt("TrangThai"),
                rs.getString("MucBan")
            );
        }
        rs.close(); ps.close();
        return b;
    }

    public void capNhatTrangThai(String maBan, int trangThai) throws Exception {
        kn.ketnoi();
        String sql = "UPDATE BanAn SET TrangThai=? WHERE MaBan=?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setInt(1, trangThai);
        ps.setString(2, maBan);
        ps.executeUpdate();
        ps.close();
    }
}
