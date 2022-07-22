
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

	public UserDAO() {
		try {
			// �ε�
			Class.forName(driver);
			// ����
			conn = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e) {
			System.out.println(e + "=> �ε� fail");
		} catch (SQLException e) {
			System.out.println(e + "=> ���� fail");
		}
	}// ������

	/**
	 * DB�ݱ� ��� �޼ҵ�
	 */
	public void dbClose() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			System.out.println(e + "=> dbClose fail");
		}
	}// dbClose() ---

	public boolean list(MemberVo uv) {

		try {

			String sql = "insert all into signup(name, id, password, sdate) values (?,?,?, sysdate)"
					+ "into usermember(id, account, balance) values(?, useraccount.NEXTVAL,0)"
					+ "into admin(adname, adid) values (?, ?)" + "select * from dual";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uv.getName());
			pstmt.setString(2, uv.getId());
			pstmt.setString(3, uv.getPassword());
			pstmt.setString(4, uv.getId());
			pstmt.setString(5, uv.getName());
			pstmt.setString(6, uv.getId());

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

	/**
	 * �μ��� ���� ID�� �ش��ϴ� ���ڵ� �˻��Ͽ� �ߺ����� üũ�ϱ� ���ϰ��� true =��밡�� , false = �ߺ���
	 */
	public boolean getIdByCheck(String id) {
		boolean result = true;

		try {
			pstmt = conn.prepareStatement("SELECT * FROM signup WHERE id=?");
			pstmt.setString(1, id.trim());
			rs = pstmt.executeQuery(); // ����
			if (rs.next())
				result = false; // ���ڵ尡 �����ϸ� false

		} catch (SQLException e) {
			System.out.println(e + "=>  getIdByCheck fail");
		} finally {
			dbClose();
		}

		return result;

	}// getIdByCheck()
	

}