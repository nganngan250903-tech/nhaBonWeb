package Model.MonAn;

import java.sql.*;
import java.util.ArrayList;
import Model.KetNoi.KetNoi;

public class MonAnDao {
	 KetNoi kn = new KetNoi();
    public ArrayList<MonAn> getAll() throws Exception {
        ArrayList<MonAn> ds = new ArrayList<>();
        kn.ketnoi();
        String sql = "SELECT * FROM MonAn WHERE TrangThaiMon = N'Bán'";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            MonAn m = new MonAn();
            m.setMaMon(rs.getString("MaMon"));
            m.setTenMon(rs.getString("TenMon"));
            m.setGia(rs.getLong("Gia"));
            m.setMoTa(rs.getString("MoTa"));
            ds.add(m);
        }
        ps.close();
        return ds;
    }

    public ArrayList<MonAn> timTheoTen(String key) throws Exception {
        ArrayList<MonAn> ds = new ArrayList<>();
        kn.ketnoi();

        String sql = "SELECT * FROM MonAn WHERE TenMon LIKE ?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, "%" + key + "%");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            MonAn m = new MonAn();
            m.setMaMon(rs.getString("MaMon"));
            m.setTenMon(rs.getString("TenMon"));
            m.setGia(rs.getLong("Gia"));
            ds.add(m);
        }
        ps.close();
        return ds;
    }
}
