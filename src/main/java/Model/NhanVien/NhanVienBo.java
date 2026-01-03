package Model.NhanVien;


public class NhanVienBo {
    NhanVienDao dao = new NhanVienDao();

    public NhanVien dangNhap(String un, String passMD5) throws Exception {
        return dao.dangNhap(un, passMD5);
    }

    public boolean dangKy(NhanVien nv) throws Exception {
        return dao.dangKy(nv);
    }
}
