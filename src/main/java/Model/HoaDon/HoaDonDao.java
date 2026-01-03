package Model.HoaDon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import Model.KetNoi.KetNoi;

public class HoaDonDao {

    KetNoi kn = new KetNoi();

    /* ================= HÓA ĐƠN ĐANG MỞ ================= */

    // Lấy hóa đơn đang mở theo bàn
    public HoaDon getHoaDonDangMo(String maBan) throws Exception {
        kn.ketnoi();
        String sql = """
            SELECT * FROM HoaDon
            WHERE MaBan = ? AND TrangThai = 1
        """;

        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, maBan);
        ResultSet rs = ps.executeQuery();

        HoaDon hd = null;
        if (rs.next()) {
            hd = new HoaDon(
            		rs.getString("MaHD"),
                    rs.getString("MaBan"),
                    rs.getString("MaNV"),
                    rs.getTimestamp("GioVao"),
                    rs.getTimestamp("GioRa"),
                    rs.getLong("TongTien"),
                    rs.getInt("TrangThai")
                   
            );
        }
        rs.close(); ps.close();
        return hd;
    }

    // Lấy mã hóa đơn đang mở
    public String getMaHoaDonDangMo(String maBan) throws Exception {
        kn.ketnoi();
        String sql = "SELECT MaHD FROM HoaDon WHERE MaBan=? AND TrangThai=1";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, maBan);
        ResultSet rs = ps.executeQuery();

        String maHD = null;
        if (rs.next()) maHD = rs.getString("MaHD");
        rs.close(); ps.close();
        return maHD;
    }

    /* ================= TẠO / ĐÓNG HÓA ĐƠN ================= */

    // Tạo hóa đơn mới
    public String taoHoaDonMoi(String maBan, String maNV) throws Exception {
        kn.ketnoi();
        String sql = """
            INSERT INTO HoaDon(MaBan, MaNV, GioVao, TongTien, TrangThai)
            VALUES(?,?,GETDATE(),0,1)
        """;

        PreparedStatement ps = kn.cn.prepareStatement(
            sql, PreparedStatement.RETURN_GENERATED_KEYS);

        ps.setString(1, maBan);
        ps.setString(2, maNV);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        String maHD = null;
        if (rs.next()) maHD = rs.getString(1);

        rs.close(); ps.close();
        return maHD;
    }

    // Đóng hóa đơn (thanh toán)
    public void dongHoaDon(String maHD, long tongTien) throws Exception {
        kn.ketnoi();
        String sql = """
            UPDATE HoaDon
            SET GioRa = GETDATE(), TongTien=?, TrangThai=0
            WHERE MaHD=?
        """;

        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setLong(1, tongTien);
        ps.setString(2, maHD);
        ps.executeUpdate();
        ps.close();
    }

    /* ================= NGHIỆP VỤ BÀN ================= */

    // Chuyển bàn
    public void chuyenHoaDon(String maHD, String maBanMoi) throws Exception {
        kn.ketnoi();
        String sql = "UPDATE HoaDon SET MaBan=? WHERE MaHD=?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, maBanMoi);
        ps.setString(2, maHD);
        ps.executeUpdate();
        ps.close();
    }

    // Gộp hóa đơn
    public void gopHoaDon(String maHDPhu, String maHDChinh) throws Exception {
        kn.ketnoi();

        // Chuyển CTHD
        String sql1 = "UPDATE CTHD SET MaHD=? WHERE MaHD=?";
        PreparedStatement ps1 = kn.cn.prepareStatement(sql1);
        ps1.setString(1, maHDChinh);
        ps1.setString(2, maHDPhu);
        ps1.executeUpdate();
        ps1.close();

        // Đóng hóa đơn phụ
        String sql2 = "UPDATE HoaDon SET TrangThai=0 WHERE MaHD=?";
        PreparedStatement ps2 = kn.cn.prepareStatement(sql2);
        ps2.setString(1, maHDPhu);
        ps2.executeUpdate();
        ps2.close();
    }
    public void huyHoaDon(String maHD) throws Exception {
        kn.ketnoi();

        // Hủy CTHD
        String sql1 = "UPDATE CTHD SET TrangThai=0 WHERE MaHD=?";
        PreparedStatement ps1 = kn.cn.prepareStatement(sql1);
        ps1.setString(1, maHD);
        ps1.executeUpdate();
        ps1.close();

        // Đóng hóa đơn
        String sql2 = "UPDATE HoaDon SET TrangThai=0 WHERE MaHD=?";
        PreparedStatement ps2 = kn.cn.prepareStatement(sql2);
        ps2.setString(1, maHD);
        ps2.executeUpdate();
        ps2.close();
    }

    public boolean banDangSuDung(String maBan) throws Exception {
        kn.ketnoi();
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE MaBan=? AND TrangThai=1";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, maBan);
        ResultSet rs = ps.executeQuery();

        boolean kq = false;
        if (rs.next()) kq = rs.getInt(1) > 0;
        rs.close(); ps.close();
        return kq;
    }

    public void capNhatTongTien(String maHD) throws Exception {
        kn.ketnoi();
        String sql = """
            UPDATE HoaDon
            SET TongTien = (
                SELECT ISNULL(SUM(ThanhTien),0)
                FROM CTHD
                WHERE MaHD = ? AND TrangThai = 1
            )
            WHERE MaHD = ?
        """;
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, maHD);
        ps.setString(2, maHD);
        ps.executeUpdate();
        ps.close();
    }

    /* ================= THỐNG KÊ ================= */

    // Tính tổng tiền từ CTHD
    public long tinhTongTien(String maHD) throws Exception {
        kn.ketnoi();
        String sql = "SELECT SUM(ThanhTien) FROM CTHD WHERE MaHD=? AND TrangThai=1";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setString(1, maHD);
        ResultSet rs = ps.executeQuery();

        long tong = 0;
        if (rs.next()) tong = rs.getLong(1);
        rs.close(); ps.close();
        return tong;
    }
    

    // Lịch sử hóa đơn
    public ArrayList<HoaDon> getLichSuHoaDon() throws Exception {
        ArrayList<HoaDon> ds = new ArrayList<>();
        kn.ketnoi();

        String sql = "SELECT * FROM HoaDon ORDER BY GioVao DESC";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ds.add(new HoaDon(
                rs.getString("MaHD"),
                rs.getString("MaBan"),
                rs.getString("MaNV"),
                rs.getTimestamp("GioVao"),
                rs.getTimestamp("GioRa"),
                rs.getLong("TongTien"),
                rs.getInt("TrangThai")
            ));
        }
        rs.close(); ps.close();
        return ds;
    }
}
