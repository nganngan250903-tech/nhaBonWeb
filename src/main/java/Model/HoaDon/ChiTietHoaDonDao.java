package Model.HoaDon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Model.KetNoi.KetNoi;

public class ChiTietHoaDonDao {

    KetNoi kn = new KetNoi();

    /* ================== THÊM MÓN ================== */
    public void themMon(String maHD, String maMon, int soLuong, long donGia) throws Exception {
        kn.ketnoi();

        // Kiểm tra món đã tồn tại trong hóa đơn chưa
        String checkSql = """
            SELECT MaCTHD, SoLuong 
            FROM CTHD 
            WHERE MaHD=? AND MaMon=? AND TrangThai=1
        """;
        PreparedStatement psCheck = kn.cn.prepareStatement(checkSql);
        psCheck.setString(1, maHD);
        psCheck.setString(2, maMon);
        ResultSet rs = psCheck.executeQuery();

        if (rs.next()) {
            // Đã có → tăng số lượng
            int soLuongCu = rs.getInt("SoLuong");
            int maCTHD = rs.getInt("MaCTHD");

            String update = """
                UPDATE CTHD 
                SET SoLuong=?, ThanhTien=? 
                WHERE MaCTHD=?
            """;
            PreparedStatement psUp = kn.cn.prepareStatement(update);
            psUp.setInt(1, soLuongCu + soLuong);
            psUp.setLong(2, (soLuongCu + soLuong) * donGia);
            psUp.setInt(3, maCTHD);
            psUp.executeUpdate();
            psUp.close();
        } else {
            // Chưa có → thêm mới
            String insert = """
                INSERT INTO CTHD(MaHD, MaMon, SoLuong, DonGia, ThanhTien, TrangThai)
                VALUES(?,?,?,?,?,1)
            """;
            PreparedStatement psIns = kn.cn.prepareStatement(insert);
            psIns.setString(1, maHD);
            psIns.setString(2, maMon);
            psIns.setInt(3, soLuong);
            psIns.setLong(4, donGia);
            psIns.setLong(5, soLuong * donGia);
            psIns.executeUpdate();
            psIns.close();
        }

        rs.close();
        psCheck.close();

        new HoaDonDao().capNhatTongTien(maHD);
    }

    /* ================== LẤY CTHD ================== */
    public ArrayList<ChiTietHoaDon> getCTHDTheoMaHD(String maHD) throws Exception {
        ArrayList<ChiTietHoaDon> ds = new ArrayList<>();
        kn.ketnoi();

        String sql = """
            SELECT * FROM CTHD
            WHERE MaHD=? AND TrangThai=1
        """;
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, maHD);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ds.add(new ChiTietHoaDon(
                rs.getInt("MaCTHD"),
                rs.getInt("MaHD"),
                rs.getInt("MaMon"),
                rs.getInt("SoLuong"),
                rs.getLong("DonGia"),
                rs.getLong("ThanhTien"),
                rs.getInt("TrangThai")
            ));
        }

        rs.close();
        ps.close();
        return ds;
    }

    /* ================== TĂNG SỐ LƯỢNG ================== */
    public void tangSoLuong(String maCTHD, String maHD) throws Exception {
        kn.ketnoi();

        String sql = """
            UPDATE CTHD
            SET SoLuong = SoLuong + 1,
                ThanhTien = (SoLuong + 1) * DonGia
            WHERE MaCTHD=? AND TrangThai=1
        """;
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, maCTHD);
        ps.executeUpdate();
        ps.close();

        new HoaDonDao().capNhatTongTien(maHD);
    }

    /* ================== GIẢM SỐ LƯỢNG ================== */
    public void giamSoLuong(String maCTHD, String maHD) throws Exception {
        kn.ketnoi();

        String check = "SELECT SoLuong FROM CTHD WHERE MaCTHD=?";
        PreparedStatement psCheck = kn.cn.prepareStatement(check);
        psCheck.setString(1, maCTHD);
        ResultSet rs = psCheck.executeQuery();

        if (rs.next()) {
            int sl = rs.getInt("SoLuong");

            if (sl <= 1) {
                xoaMon(maCTHD);
            } else {
                String update = """
                    UPDATE CTHD
                    SET SoLuong = SoLuong - 1,
                        ThanhTien = (SoLuong - 1) * DonGia
                    WHERE MaCTHD=?
                """;
                PreparedStatement psUp = kn.cn.prepareStatement(update);
                psUp.setString(1, maCTHD);
                psUp.executeUpdate();
                psUp.close();
            }
        }

        rs.close();
        psCheck.close();

        new HoaDonDao().capNhatTongTien(maHD);
    }

    /* ================== XÓA MÓN (XÓA MỀM) ================== */
    public void xoaMon(String maCTHD) throws Exception {
        kn.ketnoi();
        String sql = "UPDATE CTHD SET TrangThai=0 WHERE MaCTHD=?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, maCTHD);
        ps.executeUpdate();
        ps.close();
    }
    public void themMonCoGhiChu(
            String maHD,
            String maMon,
            int soLuong,
            String ghiChu
    ) throws Exception {

        kn.ketnoi();

        // 1. Kiểm tra món đã tồn tại chưa
        String checkSql =
            "SELECT MaCTHD FROM ChiTietHoaDon WHERE MaHD = ? AND MaMon = ?";
        PreparedStatement psCheck = kn.cn.prepareStatement(checkSql);
        psCheck.setString(1, maHD);
        psCheck.setString(2, maMon);
        ResultSet rs = psCheck.executeQuery();

        if (rs.next()) {
            // 2. Đã có → tăng số lượng + cập nhật ghi chú
            String updateSql =
                "UPDATE ChiTietHoaDon " +
                "SET SoLuong = SoLuong + ?, GhiChu = ? " +
                "WHERE MaCTHD = ?";
            PreparedStatement psUpdate = kn.cn.prepareStatement(updateSql);
            psUpdate.setInt(1, soLuong);
            psUpdate.setString(2, ghiChu);
            psUpdate.setString(3, rs.getString("MaCTHD"));
            psUpdate.executeUpdate();
            psUpdate.close();

        } else {
            // 3. Chưa có → thêm mới
            String insertSql =
                "INSERT INTO ChiTietHoaDon(MaHD, MaMon, SoLuong, DonGia, GhiChu) " +
                "SELECT ?, MaMon, ?, Gia, ? FROM MonAn WHERE MaMon = ?";
            PreparedStatement psInsert = kn.cn.prepareStatement(insertSql);
            psInsert.setString(1, maHD);
            psInsert.setInt(2, soLuong);
            psInsert.setString(3, ghiChu);
            psInsert.setString(4, maMon);
            psInsert.executeUpdate();
            psInsert.close();
        }

        psCheck.close();
    }

}
