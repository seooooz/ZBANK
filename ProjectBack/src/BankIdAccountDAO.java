

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankIdAccountDAO {

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
	String utype;
	int balance;
	int adbalance;

	/**
	 * 로드 연결을 위한 생성자
	 */
	public BankIdAccountDAO() {
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

	public boolean list(MemberVo v) {

		try {

			String sql = "select id, account, utype from usermember where id = '" + v.getId() + "'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				id = rs.getString("id");
				account = rs.getString("account");
				utype = rs.getString("utype");
			}

		} catch (SQLException e) {
			System.out.println(e + "=>  list fail");
		} finally {
			dbClose();
		}
		return false;
	}
	public boolean balan(MemberVo v) {

		try {

			String sql = "select balance from usermember where id = '" + v.getId() + "'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				balance = rs.getInt("balance");
			}

		} catch (SQLException e) {
			System.out.println(e + "=>  list fail");
		} finally {
			dbClose();
		}
		return false;
	}
	
	public int adbalan(Object obj) {
		int result = 0;
		try {

			String sql = "select balance from usermember where id = '" + obj + "'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				adbalance = rs.getInt("balance");
			}

		} catch (SQLException e) {
			System.out.println(e + "=>  list fail");
		} finally {
			dbClose();
		}
		return result;
	}
	
	public boolean userupdate() {

		try {
			String sql = "UPDATE usermember SET UTYPE  = '탈퇴신청' WHERE id = '" + MemberVo.user.getId() + "'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

		} catch (SQLException e) {
			System.out.println(e + "=>  list fail");
		} finally {
			dbClose();
		}
		return false;
	}
	
	public boolean adminupdate() {

		try {

			String sql = "UPDATE admin SET adTYPE  = '탈퇴신청' WHERE adid = '" + MemberVo.user.getId() + "'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

		} catch (SQLException e) {
			System.out.println(e + "=>  list fail");
		} finally {
			dbClose();
		}
		return false;
	}
}
