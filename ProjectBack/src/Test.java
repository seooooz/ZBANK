import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Test extends JFrame {
	public static void main(String[] args) {

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "c##green";
		String password = "green1234";

		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt;
		ResultSet rs;

		String name;
		String id;
		String account;
		String myaccount;

		try {
//			String a = "333301028";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			
			String sql = "SELECT s.name, u.id, u.account FROM SIGNUP s, USERMEMBER u "
					+ "WHERE s.ID = u.ID  and ACCOUNT ='333301028'";
			
//			String sql = "SELECT id, account FROM USERMEMBER"
//					+ " WHERE ACCOUNT ='333301028'";

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
	}
}
