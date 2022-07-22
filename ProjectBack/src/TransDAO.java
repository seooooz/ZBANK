import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "c##green";
	String password = "green1234";

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt;
	private ResultSet rs;

	String name;
	String id;
	String account;
	String myaccount;
	String tstype;
	String tsdate;
	int balance;
	BankIdAccountDAO dao = new BankIdAccountDAO();

	/**
	 * 로드 연결을 위한 생성자
	 */
	public TransDAO() {
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
			if (stmt != null)
				stmt.close();
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			System.out.println(e + "=> dbClose fail");
		}
	}// dbClose() ---

	public boolean list(TransVo tv) {

		try {
			String sql = "SELECT account FROM usermember WHERE id='" + tv.getId() + "'";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				account = rs.getString("account");
				return true;
			}

		} catch (SQLException e) {
			System.out.println(e + "=>  list fail");
		} finally {
			dbClose();
		}
		return false;
	}

	public boolean list1(TransVo tv) {

		try {

			dao.list(MemberVo.user);

			String sql = "INSERT INTO tshistory(tsnumber, sender, receiver, tstype, cash, tsdate) values(tsnum.NEXTVAL,?,?,'채우기',?,sysdate)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dao.account);
			pstmt.setString(2, dao.account);
			pstmt.setInt(3, tv.getCash());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=>  list1 fail");
		} finally {
			dbClose();
		}
		return false;
	}

	// tshistory insert
	public boolean list2(TransVo tv) {

		try {

			dao.list(MemberVo.user);

			String sql = "INSERT all INTO tshistory(tsnumber, sender, receiver, tstype, cash, tsdate) values(tsnum.NEXTVAL,?,?,'출금',?,sysdate)"
					+ "INTO tshistory(tsnumber, sender, receiver, tstype, cash, tsdate) values(tsnum.NEXTVAL+1,?,?,'입금',?,sysdate)"
					+ "select * from dual";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dao.account);
			pstmt.setString(2, account);
			pstmt.setInt(3, tv.getCash());

			pstmt.setString(4, account);
			pstmt.setString(5, dao.account);
			pstmt.setInt(6, tv.getCash());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=>  list2 fail");
		} finally {
			dbClose();
		}
		return false;
	}

	// 로그인한 사람 -> 다른사람 송금 (로그인한 사람 계좌 마이너스)
	public boolean list3(TransVo tv) {

		try {

			dao.list(MemberVo.user);

			String sql = "update usermember set balance = balance - '" + tv.getCash() + "'where account = '"
					+ dao.account + "'";

			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=>  list3 fail");
		} finally {
			dbClose();
		}
		return false;
	}

	// 다른사람 송금 -> 로그인한 사람 (다른사람 송금 계좌 플러스)
	public boolean list4(TransVo tv) {

		try {

			dao.list(MemberVo.user);

			String sql = "update usermember set balance = balance + '" + tv.getCash() + "'where account = '" + account
					+ "'";

			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=>  list4 fail");
		} finally {
			dbClose();
		}
		return false;
	}

	public boolean list5(TransVo tv) {

		try {

			dao.list(MemberVo.user);

			String sql = "update usermember set balance = balance + '" + tv.getCash() + "'where account = '"
					+ dao.account + "'";

			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

//		

		} catch (SQLException e) {
			System.out.println(e + "=>  list5 fail");
		} finally {
			dbClose();
		}
		return false;
	}

	public boolean succ(TransVo tv) {

		try {

			String sql = "SELECT s.name, u.id, u.account FROM SIGNUP s, USERMEMBER u "
					+ "WHERE s.ID = u.ID  and u.id ='" + tv.getId() + "'";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				name = rs.getString("name");
				id = rs.getString("id");
				account = rs.getString("account");
				System.out.println(name);
				System.out.println(id);
				System.out.println(account);
			}

//		

		} catch (SQLException e) {
			System.out.println(e + "=>  succ fail");
		} finally {
			dbClose();
		}
		return false;
	}

	public boolean succdate() {

		try {

			String sql = "SELECT TSTYPE , MAX(TSDATE) FROM TSHISTORY " + "WHERE RECEIVER  = '" + account
					+ "' AND TSTYPE = '출금' GROUP BY TSTYPE";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tstype = rs.getString("tstype");
				tsdate = rs.getString("MAX(TSDATE)");
				System.out.println(tstype);
				System.out.println(tsdate);
			}

//		

		} catch (SQLException e) {
			System.out.println(e + "=>  succdate fail");
		} finally {
			dbClose();
		}
		return false;
	}

	public boolean tranmybal() {

		try {

			dao.list(MemberVo.user);

			String sql = "SELECT balance FROM USERMEMBER WHERE ACCOUNT = '" + dao.account + "'";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				balance = rs.getInt("balance");
				System.out.println(tsdate);
			}

//		
		} catch (SQLException e) {
			System.out.println(e + "=>  tranmybal fail");
		} finally {
			dbClose();
		}
		return false;
	}

	public boolean tsnumcount(MemberVo v) {

		try {

			dao.list(MemberVo.user);
			String sql = "UPDATE admin SET adtsnumber = (SELECT COUNT(tsnumber) FROM TSHISTORY WHERE SENDER ='"
					+ dao.account + "') WHERE adid ='" + dao.id + "'";

			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=>  tsnumcount fail");
		} finally {
			dbClose();
		}
		return false;
	}
}
