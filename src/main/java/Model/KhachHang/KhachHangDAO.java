package Model.KhachHang;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.KetNoi.KetNoi;

public class KhachHangDAO {

	

	    // 🔎 Tìm theo SĐT
	    public KhachHang getBySDT(String sdt) throws Exception {
	        System.out.println("Tìm KhachHang với SĐT: " + sdt);

	        KetNoi kn = new KetNoi();
	        kn.ketnoi();

	        String sql = "SELECT MaKH, TenKH, SĐT FROM KhachHang WHERE SĐT = ?";
	        PreparedStatement ps = kn.cn.prepareStatement(sql);
	        ps.setString(1, sdt);

	        ResultSet rs = ps.executeQuery();

	        KhachHang kh = null;
	        if (rs.next()) {
	            kh = new KhachHang(
	                rs.getLong("MaKH"),
	                rs.getString("TenKH"),
	                rs.getString("SĐT")
	            );
	            System.out.println("Tìm thấy KhachHang: " + kh.getTenKH() + " - MaKH: " + kh.getMaKH());
	        } else {
	            System.out.println("Không tìm thấy KhachHang với SĐT: " + sdt);
	        }

	        rs.close();
	        ps.close();
	        kn.cn.close();
	        return kh;
	    }

	    // ➕ Thêm khách mới
	    public KhachHang insert(String tenKH, String sdt) throws Exception {
	        KetNoi kn = new KetNoi();
	        kn.ketnoi();

	        String sql = "INSERT INTO KhachHang(TenKH, SĐT) VALUES (?, ?)";
	        PreparedStatement ps = kn.cn.prepareStatement(
	            sql, PreparedStatement.RETURN_GENERATED_KEYS
	        );
	        ps.setString(1, tenKH);
	        ps.setString(2, sdt);

	        int affectedRows = ps.executeUpdate();
	        System.out.println("Insert KhachHang affected rows: " + affectedRows);

	        long maKH = -1;
	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	            maKH = rs.getLong(1);
	            System.out.println("Generated MaKH: " + maKH);
	        } else {
	            System.out.println("No generated keys for KhachHang");
	        }
	        rs.close();

	        ps.close();
	        kn.cn.close();

	        if (maKH == -1) {
	            throw new Exception("Không thể tạo khách hàng mới - không lấy được MaKH");
	        }

	        return new KhachHang(maKH, tenKH, sdt);
	    }
	}
