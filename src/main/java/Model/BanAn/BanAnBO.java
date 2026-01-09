package Model.BanAn;

import java.util.List;

public class BanAnBO {
	BanAnDAO dao = new BanAnDAO();

	public int getTongBan() {
		return dao.countBan();
	}

	public List<Object[]> getThongKeSuDungBan() {
		return dao.getThongKeSuDungBan();
	}
