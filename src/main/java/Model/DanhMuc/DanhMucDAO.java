package Model.DanhMuc;

import java.sql.*;
import java.util.ArrayList;
import Model.KetNoi.KetNoi;

public class DanhMucDAO {

    public ArrayList<DanhMuc> getAll() throws Exception {
        ArrayList<DanhMuc> ds = new ArrayList<>();
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "select * from DanhMuc";
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        ResultSet rs = cmd.executeQuery();

        while (rs.next()) {
            ds.add(new DanhMuc(
                rs.getLong("MaDM"),
                rs.getString("TenDM"),
                rs.getString("MoTa")
            ));
        }
        kn.cn.close();
        return ds;
    }
}
