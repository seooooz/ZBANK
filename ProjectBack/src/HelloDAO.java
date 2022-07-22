

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloDAO {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "c##green";
	String password = "green1234";

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	String name;
	String id;
	String account;
	
	/**
	 * 로드 연결을 위한 생성자
	 */
	public HelloDAO() {
		try {
			// 로드
			Class.forName(driver);
			// 연결
			conn = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e) {
			System.out.println(e + "=> 로드 fail");
		} catch (SQLException e) {
			System.out.println(e + "=> 연결 fail");
		}
	}// 생성자

	/**
	 * DB닫기 기능 메소드
	 */
	public void dbClose() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			System.out.println(e + "=> dbClose fail");
		}
	}// dbClose() ---

	public boolean list(MemberVo nv) {

		try {
			String sql = "select name, id from signup where id = '" + nv.getId() + "'";
					
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				name = rs.getString("name");
				id = rs.getString("id");
			}

		} catch (SQLException e) {
			System.out.println(e + "=>  list fail");
		} finally {
			dbClose();
		}
		return false;
	}
	
	public boolean list1(MemberVo nv) {

		try {
			String sql = "select account from usermember where id = '" + nv.getId() + "'";
					
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				account = rs.getString("account");
			}

		} catch (SQLException e) {
			System.out.println(e + "=>  list fail");
		} finally {
			dbClose();
		}
		return false;
	}
	
	public boolean admin(MemberVo nv) {

	      try {
	         String sql = "UPDATE admin SET ADACCOUNT = '"+ account +"' WHERE adid = '"+ nv.getId() +"'";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.executeUpdate();
	         

	      } catch (SQLException e) {
				System.out.println(e + "=>  list fail");
			} finally {
				dbClose();
			}
	      return false;
	   }
	
}
