

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "c##green";
	String password = "green1234";

	private Connection conn;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;


	public boolean list(UserVo uv) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);

			String sql = "insert all into signup(name, id, password, sdate) values (?,?,?, sysdate)"
					+ "into usermember(id, account, balance) values(?, useraccount.NEXTVAL,0)"
					+ "select * from dual";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uv.getName());
			pstmt.setString(2, uv.getId());
			pstmt.setString(3, uv.getPassword());
			pstmt.setString(4, uv.getId());

			pstmt.executeUpdate();
			
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql1 = "SELECT id FROM signup WHERE id='" + uv.getId() + "'";

			rs = stmt.executeQuery(sql1);
			rs.last();

			if (rs.getRow() == 0) {
//                System.out.println("0 row selected.....");
			} else {
				return true;
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return false;
	}
}