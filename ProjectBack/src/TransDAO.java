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

	Connection conn = null;
	PreparedStatement pstmt = null;
	private Statement stmt;
	private ResultSet rs;
	
	String account;
	String myaccount;
	BankIdAccountDAO dao = new BankIdAccountDAO();
	

	public boolean list(TransVo tv) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);

			String sql = "SELECT account FROM usermember WHERE id='" + tv.getId() + "'";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				account = rs.getString("account");
				System.out.println(account);
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
	
	public boolean list1(TransVo tv) {

			try {
				
				dao.list(MemberVo.user);
				
				Class.forName(driver);
				conn = DriverManager.getConnection(url, user, password);

				String sql = "INSERT INTO tshistory(tsnumber, sender, receiver, tstype, cash, tsdate) values(tsnum.NEXTVAL,?,?,'채우기',?,sysdate)";
						

				pstmt = conn.prepareStatement(sql);
				
//				System.out.println(dao.account);
				
				pstmt.setString(1, dao.account);
				pstmt.setString(2, dao.account);
				pstmt.setInt(3, tv.getCash());
				
				pstmt.executeUpdate();
				
//				if(rs.next()) {
//					myaccount = rs.getString("account");
//				}
//				

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
	
	//tshistory insert
	public boolean list2(TransVo tv) {

		try {
			
			dao.list(MemberVo.user);
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);

			String sql = "INSERT all INTO tshistory(tsnumber, sender, receiver, tstype, cash, tsdate) values(tsnum.NEXTVAL,?,?,'출금',?,sysdate)"
					+ "INTO tshistory(tsnumber, sender, receiver, tstype, cash, tsdate) values(tsnum.NEXTVAL+1,?,?,'입금',?,sysdate)"
					+ "select * from dual";

			pstmt = conn.prepareStatement(sql);
			
//			System.out.println(dao.account);
			
			pstmt.setString(1, dao.account);
			pstmt.setString(2, account);
			pstmt.setInt(3, tv.getCash());
			
			pstmt.setString(4, account);
			pstmt.setString(5, dao.account);
			pstmt.setInt(6, tv.getCash());
			
			pstmt.executeUpdate();
			
//			if(rs.next()) {
//				myaccount = rs.getString("account");
//			}
//			

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
	//로그인한 사람 -> 다른사람 송금 (로그인한 사람 계좌 마이너스)
	public boolean list3(TransVo tv) {

		try {
			
			dao.list(MemberVo.user);
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);

			String sql = "update usermember set balance = balance - '" + tv.getCash() +"'where account = '"+ dao.account +"'";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.executeUpdate();
			
//			if(rs.next()) {
//				myaccount = rs.getString("account");
//			}
//			

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
	//다른사람 송금 -> 로그인한 사람 (다른사람 송금 계좌 마이너스)
		public boolean list4(TransVo tv) {

			try {
				
				dao.list(MemberVo.user);
				
				Class.forName(driver);
				conn = DriverManager.getConnection(url, user, password);

				String sql = "update usermember set balance = balance + '" + tv.getCash() +"'where account = '" + account +"'";

				pstmt = conn.prepareStatement(sql);
				
				pstmt.executeUpdate();
				
//				if(rs.next()) {
//					myaccount = rs.getString("account");
//				}
//				

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
		
		public boolean list5(TransVo tv) {

			try {
				
				dao.list(MemberVo.user);
				
				Class.forName(driver);
				conn = DriverManager.getConnection(url, user, password);

				String sql = "update usermember set balance = balance + '" + tv.getCash() +"'where account = '" + dao.account +"'";

				pstmt = conn.prepareStatement(sql);
				
				pstmt.executeUpdate();
				
//		

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

