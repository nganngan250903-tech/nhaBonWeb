package Model.MonAn;

import java.util.ArrayList;

public class MonAnBo {
    MonAnDAO dao = new MonAnDAO();

    public ArrayList<MonAn> getAll() throws Exception {
        return dao.getAll();
    }

    public ArrayList<MonAn> timTheoTen(String key) throws Exception {
        return dao.timTheoTen(key);
    }

    public ArrayList<MonAn> getByDanhMuc(long maDM) throws Exception {
        return dao.getByDanhMuc(maDM);
    }
    public MonAn getById(long maMon) throws Exception {
        return dao.getById(maMon);
    }
    public int getTongMon() {
		return dao.countMon();
	}
    public void insert(MonAn m) throws Exception {
    	dao.insert(m);
    }

    public void update(MonAn m) throws Exception {
    	dao.update(m);
    }

    public void delete(long maMon) throws Exception {
    	dao.delete(maMon);
    }


}

