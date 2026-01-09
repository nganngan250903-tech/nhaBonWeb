package Model.GioHang;

import java.util.ArrayList;

public class GioHangBo {
    private ArrayList<GioHang> ds = new ArrayList<>();

    public ArrayList<GioHang> getDs() {
        return ds;
    }

    // ➕ thêm món (mặc định +1)
    public void them(long maMon, String tenMon, String hinhAnh, long gia) {
        them(maMon, tenMon, hinhAnh, gia, 1);
    }

    // ➕ thêm món có số lượng
    public void them(long maMon, String tenMon, String hinhAnh, long gia, int soLuong) {
        for (GioHang i : ds) {
            if (i.getMaMon() == maMon) {
                i.setSoLuong(i.getSoLuong() + soLuong);
                return;
            }
        }
        ds.add(new GioHang(maMon, tenMon, hinhAnh, gia, soLuong));
    }

    // ❌ xóa món
    public void xoa(long maMon) {
        ds.removeIf(i -> i.getMaMon() == maMon);
    }

    // 🔄 cập nhật số lượng
    public void capNhat(long maMon, int soLuong) {
        for (GioHang i : ds) {
            if (i.getMaMon() == maMon) {
                i.setSoLuong(soLuong);
                return;
            }
        }
    }

    // 📝 ghi chú
    public void capNhatGhiChu(long maMon, String ghiChu) {
        GioHang g = getById(maMon);
        if (g != null) {
            g.setGhiChu(ghiChu);
        }
    }

    // 🔍 tìm theo mã
    public GioHang getById(long maMon) {
        for (GioHang g : ds) {
            if (g.getMaMon() == maMon) {
                return g;
            }
        }
        return null;
    }

    // 🛒 tổng số lượng (icon)
    public int tongSoLuong() {
        int s = 0;
        for (GioHang g : ds) {
            s += g.getSoLuong();
        }
        return s;
    }

    // 💰 tổng tiền
    public long tongTien() {
        long sum = 0;
        for (GioHang i : ds) {
            sum += i.getThanhTien();
        }
        return sum;
    }

    // 🧹 xóa giỏ
    public void clear() {
        ds.clear();
    }
}
