package Model.BanAn;

import java.util.ArrayList;

import java.util.List;

public class BanAnBO {
	BanAnDAO dao = new BanAnDAO();

    public ArrayList<BanAn> getAll() throws Exception {
        return dao.getAll();
    }

    public int getTongBan() {
        try {
            return dao.countBan();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public BanAn getById(long maBan) throws Exception {
        for (BanAn b : dao.getAll()) {
            if (b.getMaBan() == maBan)
                return b;
        }
        return null;
    }

    public int getBanDangDung() {
        try {
            return dao.countBanDangDung();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void themBan(String tenBan) throws Exception {
        dao.insert(tenBan);
    }

    public void suaBan(long maBan, String tenBan) throws Exception {
        dao.update(maBan, tenBan);
    }

    public void xoaBan(long maBan) throws Exception {
        dao.delete(maBan);
    }

	

	public List<Object[]> getThongKeSuDungBan() {
		return dao.getThongKeSuDungBan();
	}
}