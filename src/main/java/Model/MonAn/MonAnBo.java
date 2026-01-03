package Model.MonAn;

import java.util.ArrayList;

public class MonAnBo {
    MonAnDao dao = new MonAnDao();

    public ArrayList<MonAn> getAll() throws Exception {
        return dao.getAll();
    }

    public ArrayList<MonAn> timTheoTen(String key) throws Exception {
        return dao.timTheoTen(key);
    }
}
