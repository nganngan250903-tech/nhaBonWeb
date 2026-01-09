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
          "INSERT INTO ChiTietHoaDon(MaHD, MaMon, SoLuong, DonGia, TrangThai, ChietKhau) " +
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

    // Lấy chi tiết hóa đơn theo mã HD với thông tin món ăn
    public List<Object[]> getChiTietByMaHD(long maHD) throws Exception {
        List<Object[]> result = new ArrayList<>();
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT ct.MaCTHD, ct.MaHD, ct.MaMon, ct.SoLuong, ct.DonGia, ct.TrangThai, ct.ChietKhau, " +
                         "m.TenMon, m.HinhAnh " +
                         "FROM ChiTietHoaDon ct " +
                         "JOIN MonAn m ON ct.MaMon = m.MaMon " +
                         "WHERE ct.MaHD = ? " +
                         "ORDER BY ct.TrangThai ASC, m.TenMon ASC";

            ps = kn.cn.prepareStatement(sql);
            ps.setLong(1, maHD);

            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Object[]{
                    rs.getLong("MaCTHD"),
                    rs.getLong("MaHD"),
                    rs.getLong("MaMon"),
                    rs.getString("TenMon") != null ? rs.getString("TenMon") : "",
                    rs.getString("HinhAnh") != null ? rs.getString("HinhAnh") : "",
                    rs.getInt("SoLuong"),
                    rs.getLong("DonGia"),
                    rs.getInt("TrangThai"),
                    rs.getLong("ChietKhau")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (kn.cn != null) kn.cn.close();
        }
        return result;
    }

    // Cập nhật trạng thái món ăn
    public boolean capNhatTrangThai(long maCTHD, int trangThai) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql = "UPDATE ChiTietHoaDon SET TrangThai = ? WHERE MaCTHD = ?";
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
                        "FROM ChiTietHoaDon ct " +
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
