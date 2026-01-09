package Model.HoaDon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.KetNoi.KetNoi;

public class ChiTietHoaDonDAO {

    public void themCTHD(ChiTietHoaDon ct) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql =
          "INSERT INTO CTHD(MaHD, MaMon, SoLuong, DonGia, TrangThai, ChietKhau) " +
          "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = kn.cn.prepareStatement(sql);

        ps.setLong(1, ct.getMaHD());
        ps.setLong(2, ct.getMaMon());
        ps.setInt(3, ct.getSoLuong());
        ps.setLong(4, ct.getDonGia());
        ps.setInt(5, ct.getTrangThai()); // 0 = đang làm
        ps.setLong(6, ct.getChietKhau());

        ps.executeUpdate();
        ps.close();
        kn.cn.close();
    }

    public List<Object[]> getChiTietTongByBan(long maBan) throws Exception {
	    List<Object[]> result = new ArrayList<>();
	    KetNoi kn = new KetNoi();
	    kn.ketnoi();

	    String sql =
	        "SELECT cthd.MaCTHD, cthd.MaHD, cthd.MaMon, m.TenMon, m.HinhAnh, " +
	        "cthd.SoLuong, cthd.DonGia, cthd.TrangThai " +
	        "FROM CTHD cthd " +
	        "JOIN HoaDon hd ON cthd.MaHD = hd.MaHD " +
	        "JOIN MonAn m ON cthd.MaMon = m.MaMon " +
	        "WHERE hd.MaBan = ? " +
	        "ORDER BY hd.GioVao ASC, cthd.MaCTHD ASC";

	    PreparedStatement ps = kn.cn.prepareStatement(sql);
	    ps.setLong(1, maBan);

	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
	        result.add(new Object[]{
	            rs.getLong("MaCTHD"),   // [0]
	            rs.getLong("MaHD"),     // [1]
	            rs.getLong("MaMon"),    // [2]
	            rs.getString("TenMon"), // [3]
	            rs.getString("HinhAnh"),    // [4]
	            rs.getInt("SoLuong"),   // [5]
	            rs.getLong("DonGia"),   // [6]
	            rs.getInt("TrangThai")  // [7]
	        });
	    }

	    rs.close();
	    ps.close();
	    kn.cn.close();
	    return result;
	}

    // Cập nhật trạng thái món ăn
    public boolean capNhatTrangThai(long maCTHD, int trangThai) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "UPDATE CTHD SET TrangThai = ? WHERE MaCTHD = ?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setInt(1, trangThai);
        ps.setLong(2, maCTHD);

        int result = ps.executeUpdate();

        ps.close();
        kn.cn.close();
        return result > 0;
    }

    // Lấy chi tiết hóa đơn theo trạng thái
    public List<Object[]> getChiTietByTrangThai(int trangThai) throws Exception {
        List<Object[]> result = new ArrayList<>();
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT ct.MaCTHD, ct.MaHD, ct.MaMon, m.TenMon, ct.SoLuong, ct.DonGia, ct.TrangThai, ct.ChietKhau, " +
                        "hd.GioVao, hd.ThanhToan " +
                        "FROM CTHD ct " +
                        "JOIN MonAn m ON ct.MaMon = m.MaMon " +
                        "JOIN HoaDon hd ON ct.MaHD = hd.MaHD " +
                        "WHERE ct.TrangThai = ? AND hd.ThanhToan = 3 " + // Chỉ lấy đơn hàng đang ăn
                        "ORDER BY hd.GioVao DESC, ct.MaCTHD";

            ps = kn.cn.prepareStatement(sql);
            ps.setInt(1, trangThai);
            rs = ps.executeQuery();

            while (rs.next()) {
                result.add(new Object[]{
                    rs.getLong("MaCTHD"),      // 0
                    rs.getLong("MaHD"),        // 1
                    rs.getLong("MaMon"),       // 2
                    rs.getString("TenMon"),    // 3
                    rs.getInt("SoLuong"),      // 4
                    rs.getLong("DonGia"),      // 5
                    rs.getInt("TrangThai"),    // 6
                    rs.getLong("ChietKhau"),   // 7
                    rs.getTimestamp("GioVao"), // 8
                    rs.getInt("ThanhToan")     // 9
                });
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            kn.cn.close();
        }
        return result;
    }
}
