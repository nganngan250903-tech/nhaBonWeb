package Model.MonAn;

import java.sql.*;
import java.util.ArrayList;
import Model.KetNoi.KetNoi;

public class MonAnDAO {

    public ArrayList<MonAn> getAll() throws Exception {
        ArrayList<MonAn> ds = new ArrayList<>();
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "select * from MonAn where TrangThaiMon = 1";
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        ResultSet rs = cmd.executeQuery();

        while (rs.next()) {
            ds.add(new MonAn(
                rs.getLong("MaMon"),
                rs.getString("TenMon"),
                rs.getLong("Gia"),
                rs.getString("HinhAnh"),
                rs.getString("MoTa"),
                rs.getLong("MaDM"),
                rs.getInt("TrangThaiMon"),
                rs.getInt("SoLuong")
            ));
        }
        kn.cn.close();
        return ds;
    }

    // 🔍 tìm theo tên
    public ArrayList<MonAn> timTheoTen(String key) throws Exception {
        ArrayList<MonAn> ds = new ArrayList<>();
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "SELECT * FROM MonAn WHERE TrangThaiMon = 1 AND TenMon LIKE ?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, "%" + key + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ds.add(new MonAn(
                rs.getLong("MaMon"),
                rs.getString("TenMon"),
                rs.getLong("Gia"),
                rs.getString("HinhAnh"),
                rs.getString("MoTa"),
                rs.getLong("MaDM"),
                rs.getInt("TrangThaiMon"),
                rs.getInt("SoLuong")
            ));
        }
        kn.cn.close();
        return ds;
    }
    public MonAn getById(long maMon) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "SELECT * FROM MonAn WHERE MaMon = ?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setLong(1, maMon);

        ResultSet rs = ps.executeQuery();
        MonAn m = null;

        if (rs.next()) {
            m = new MonAn(
                rs.getLong("MaMon"),
                rs.getString("TenMon"),
                rs.getLong("Gia"),
                rs.getString("HinhAnh"),
                rs.getString("MoTa"),
                rs.getLong("MaDM"),
                rs.getInt("TrangThaiMon"),
                rs.getInt("SoLuong")
            );
        }

        kn.cn.close();
        return m;
    }

    public int countMon() {
		try {
			KetNoi kn = new KetNoi();
			kn.ketnoi();

			String sql = "SELECT COUNT(*) FROM MonAn";
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

    // 📂 lọc theo danh mục
    public ArrayList<MonAn> getByDanhMuc(long maDM) throws Exception {
        ArrayList<MonAn> ds = new ArrayList<>();
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "select * from MonAn where MaDM = ?";
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        cmd.setLong(1, maDM);
        ResultSet rs = cmd.executeQuery();

        while (rs.next()) {
            ds.add(new MonAn(
                rs.getLong("MaMon"),
                rs.getString("TenMon"),
                rs.getLong("Gia"),
                rs.getString("HinhAnh"),
                rs.getString("MoTa"),
                rs.getLong("MaDM"),
                rs.getInt("TrangThaiMon"),
                rs.getInt("SoLuong")
            ));
        }
        kn.cn.close();
        return ds;
    }

//➕ THÊM MÓN
public void insert(MonAn m) throws Exception {
	KetNoi kn = new KetNoi();
	kn.ketnoi();

	String sql = """
		INSERT INTO MonAn
		(TenMon, Gia, HinhAnh, MoTa, MaDM, TrangThaiMon, SoLuong)
		VALUES (?, ?, ?, ?, ?, ?, ?)
	""";

	PreparedStatement ps = kn.cn.prepareStatement(sql);
	ps.setString(1, m.getTenMon());
	ps.setLong(2, m.getGia());
	ps.setString(3, m.getHinhAnh());
	ps.setString(4, m.getMoTa());
	ps.setLong(5, m.getMaDM());
	ps.setInt(6, m.getTrangThaiMon());
	ps.setInt(7, m.getSoLuong());

	ps.executeUpdate();
	kn.cn.close();
}

//✏️ SỬA MÓN
public void update(MonAn m) throws Exception {
	KetNoi kn = new KetNoi();
	kn.ketnoi();

	String sql = """
		UPDATE MonAn SET
			TenMon = ?,
			Gia = ?,
			HinhAnh = ?,
			MoTa = ?,
			MaDM = ?,
			TrangThaiMon = ?,
			SoLuong = ?
		WHERE MaMon = ?
	""";

	PreparedStatement ps = kn.cn.prepareStatement(sql);
	ps.setString(1, m.getTenMon());
	ps.setLong(2, m.getGia());
	ps.setString(3, m.getHinhAnh());
	ps.setString(4, m.getMoTa());
	ps.setLong(5, m.getMaDM());
	ps.setInt(6, m.getTrangThaiMon());
	ps.setInt(7, m.getSoLuong());
	ps.setLong(8, m.getMaMon());

	ps.executeUpdate();
	kn.cn.close();
}

//❌ XÓA MÓN
public void delete(long maMon) throws Exception {
	KetNoi kn = new KetNoi();
	kn.ketnoi();

	String sql = "DELETE FROM MonAn WHERE MaMon = ?";
	PreparedStatement ps = kn.cn.prepareStatement(sql);
	ps.setLong(1, maMon);

	ps.executeUpdate();
	kn.cn.close();
}
}

