package Model.DanhMuc;

import java.util.ArrayList;

public class DanhMucBo {
    DanhMucDAO dao = new DanhMucDAO();

    public ArrayList<DanhMuc> getAll() throws Exception {
        return dao.getAll();
    }
    public DanhMuc getById(long maDM) throws Exception {
        return dao.getById(maDM);
    }

    public void insert(DanhMuc dm) throws Exception {
        dao.insert(dm);
    }

    public void update(DanhMuc dm) throws Exception {
        dao.update(dm);
    }

    public void delete(long maDM) throws Exception {
        dao.delete(maDM);
    }
}
