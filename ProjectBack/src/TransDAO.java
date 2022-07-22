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
	 * �ε� ������ ���� ������
	 */
	public TransDAO() {
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

			String sql = "INSERT INTO tshistory(tsnumber, sender, receiver, tstype, cash, tsdate) values(tsnum.NEXTVAL,?,?,'ä���',?,sysdate)";

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

			String sql = "INSERT all INTO tshistory(tsnumber, sender, receiver, tstype, cash, tsdate) values(tsnum.NEXTVAL,?,?,'���',?,sysdate)"
					+ "INTO tshistory(tsnumber, sender, receiver, tstype, cash, tsdate) values(tsnum.NEXTVAL+1,?,?,'�Ա�',?,sysdate)"
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

	// �α����� ��� -> �ٸ���� �۱� (�α����� ��� ���� ���̳ʽ�)
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

	// �ٸ���� �۱� -> �α����� ��� (�ٸ���� �۱� ���� �÷���)
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
					+ "' AND TSTYPE = '���' GROUP BY TSTYPE";

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
