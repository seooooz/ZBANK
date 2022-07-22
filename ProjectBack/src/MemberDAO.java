

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// db�� ����� id == pw ã���ִ� sql
public class MemberDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "c##green";
	String password = "green1234";

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	/**
	 * �ε� ������ ���� ������
	 */
	public MemberDAO() {
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


	public boolean list(MemberVo p) {

		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			String query = "SELECT*FROM signup WHERE id='" + p.getId() + "' AND password ='"+ p.getPassword() + "'";

			rs = stmt.executeQuery(query);
			// ������ �̵�
			rs.last();

			if (rs.getRow() == 0) {
                System.out.println("0 row selected.....");
			} else {
				return true;
			}
						

		} catch (SQLException e) {
			System.out.println(e + "=>  list fail");
		} finally {
			dbClose();
		}

		return false;
	}

	public boolean passselect(String pw) {
		boolean result = true;

		try {

			String sql = "SELECT*FROM signup WHERE id='" + MemberVo.user.getId() + "' AND password ='"+ pw + "'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				result = false;
			

		} catch (SQLException e) {
			System.out.println(e + "=>  list fail");
		} finally {
			dbClose();
		}
		return result;
	}

	public boolean passupdate(String pass) {

		try {

			String sql = "UPDATE signup SET password  = ? WHERE id = '" + MemberVo.user.getId() + "'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pass);
			
			rs = pstmt.executeQuery();

		} catch (SQLException e) {
			System.out.println(e + "=>  list fail");
		} finally {
			dbClose();
		}
		return true;
	}

	
}
