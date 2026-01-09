package Model.DanhMuc;

import java.sql.*;
import java.util.ArrayList;
import Model.KetNoi.KetNoi;

public class DanhMucDAO {

    // ================== LẤY TẤT CẢ DANH MỤC ==================
    public ArrayList<DanhMuc> getAll() throws Exception {
        ArrayList<DanhMuc> ds = new ArrayList<>();
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "SELECT * FROM DanhMuc";
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        ResultSet rs = cmd.executeQuery();

        while (rs.next()) {
            ds.add(new DanhMuc(
                    rs.getLong("MaDM"),
                    rs.getString("TenDM"),
                    rs.getString("MoTa")
            ));
        }

        rs.close();
        cmd.close();
        kn.cn.close();
        return ds;
    }

    // ================== LẤY DANH MỤC THEO ID ==================
    public DanhMuc getById(long maDM) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "SELECT * FROM DanhMuc WHERE MaDM=?";
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        cmd.setLong(1, maDM);
        ResultSet rs = cmd.executeQuery();

        DanhMuc dm = null;
        if (rs.next()) {
            dm = new DanhMuc(
                    rs.getLong("MaDM"),
                    rs.getString("TenDM"),
                    rs.getString("MoTa")
            );
        }

        rs.close();
        cmd.close();
        kn.cn.close();
        return dm;
    }

    // ================== THÊM DANH MỤC ==================
    public int insert(DanhMuc dm) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "INSERT INTO DanhMuc(TenDM, MoTa) VALUES (?, ?)";
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        cmd.setString(1, dm.getTenDM());
        cmd.setString(2, dm.getMoTa());

        int kq = cmd.executeUpdate();

        cmd.close();
        kn.cn.close();
        return kq;
    }

    // ================== CẬP NHẬT DANH MỤC ==================
    public int update(DanhMuc dm) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "UPDATE DanhMuc SET TenDM=?, MoTa=? WHERE MaDM=?";
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        cmd.setString(1, dm.getTenDM());
        cmd.setString(2, dm.getMoTa());
        cmd.setLong(3, dm.getMaDM());

        int kq = cmd.executeUpdate();

        cmd.close();
        kn.cn.close();
        return kq;
    }

    // ================== XÓA DANH MỤC ==================
    public int delete(long maDM) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "DELETE FROM DanhMuc WHERE MaDM=?";
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        cmd.setLong(1, maDM);

        int kq = cmd.executeUpdate();

        cmd.close();
        kn.cn.close();
        return kq;
    }
}
