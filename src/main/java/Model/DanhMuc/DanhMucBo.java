package Model.DanhMuc;

import java.util.ArrayList;

public class DanhMucBo {
    DanhMucDAO dao = new DanhMucDAO();

    public ArrayList<DanhMuc> getAll() throws Exception {
        return dao.getAll();
    }
}
