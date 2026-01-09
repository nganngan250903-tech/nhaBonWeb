package Model.HoaDon;

import java.util.ArrayList;

public class ChiTietHoaDonBO {

    ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO();

    public void taoCTHD(ChiTietHoaDon ct) throws Exception {
            dao.themCTHD(ct);   
    }
}
