package Model.KhachHang;

public class KhachHangBO {
	KhachHangDAO dao = new KhachHangDAO();

    // Kiểm tra khách hàng có tồn tại theo SĐT không
    public KhachHang getBySDT(String sdt) throws Exception {
        return dao.getBySDT(sdt);
    }

    // Đăng ký khách hàng mới
    public KhachHang dangKy(String tenKH, String sdt) throws Exception {
        return dao.insert(tenKH, sdt);
    }

    // Đăng nhập hoặc đăng ký (logic cũ - giữ để tương thích)
    public KhachHang dangNhapHoacDangKy(String sdt, String tenKH)
            throws Exception {

        KhachHang kh = dao.getBySDT(sdt);

        if (kh != null) {
            return kh; // đã có
        }

        // chưa có → tạo mới
        return dao.insert(tenKH, sdt);
    }
}