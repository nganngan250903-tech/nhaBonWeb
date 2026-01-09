package Model.HoaDon;

import java.sql.*;
import Model.KetNoi.KetNoi;

public class ChiTietHoaDonDAO {

    public void themCTHD(ChiTietHoaDon ct) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();

        String sql =
          "INSERT INTO CTHD(maHD, maMon, soLuong, DonGia, trangThai, ChieuKhau) " +
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
}
