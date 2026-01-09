package Model.BanAn;

public class BanAnBO {
	BanAnDAO dao = new BanAnDAO();

public int getTongBan() {
	return dao.countBan();
}
}