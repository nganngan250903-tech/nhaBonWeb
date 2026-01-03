package Model.KetNoi;

import java.sql.Connection;
import java.sql.DriverManager;

public class KetNoi {

	public Connection cn;
	public void ketnoi() throws Exception{
	   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	   cn= DriverManager.getConnection("jdbc:sqlserver://DESKTOP-27LQTFG:1433;databaseName=QlNhaBon;user=sa; password=123");
	   System.out.println("Da ket noi");
	}
}
