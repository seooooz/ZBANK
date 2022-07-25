import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class Check implements ActionListener {
	private JFrame f;
	private JTable table;
	private JLabel ltxid, ltxacc;
	private JButton bmain;

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "c##green";
	String password = "green1234";

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs;
	private BankIdAccountDAO dao = new BankIdAccountDAO();
	private BufferedImage img = null;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public Check() {
		dao = new BankIdAccountDAO();
		dao.list(MemberVo.user);
		//
		f = new JFrame("조회");

		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(494, 740);

		try {
			img = ImageIO.read(new File("images\\Check.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "실패");
			System.exit(0);
		}

		myPanel panel = new myPanel();
		panel.setSize(510, 740);
		layerpane.add(panel);

		layerpane.setLayout(null);

		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setFont(new Font("THE스피드", Font.PLAIN, 13));
		table.setRowHeight(30);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 248, 494, 413);
		f.getContentPane().setLayout(null);
		f.getContentPane().add(scrollPane);
		//
		ltxid = new JLabel(MemberVo.user.getId());
		ltxid.setBounds(367, 10, 101, 25);
		ltxid.setHorizontalAlignment(JLabel.RIGHT);
		f.getContentPane().add(ltxid);
		ltxid.setFont(new Font("THE스피드", Font.PLAIN, 15));
		ltxacc = new JLabel(dao.account);
		ltxacc.setBounds(367, 37, 101, 25);
		ltxacc.setHorizontalAlignment(JLabel.RIGHT);
		f.getContentPane().add(ltxacc);
		ltxacc.setFont(new Font("THE스피드", Font.PLAIN, 15));

		f.setBounds(700, 50, 510, 700);

		bmain = new JButton("");
		bmain.setContentAreaFilled(false);
		bmain.setBorderPainted(false);
		bmain.setFocusPainted(false);
		bmain.setIcon(new ImageIcon("images\\bt\\main.png"));
		bmain.setRolloverIcon(new ImageIcon("images\\bt\\main_1.png"));
		bmain.setBounds(23, 16, 57, 55);
		bmain.addActionListener(this);
		f.getContentPane().add(bmain);

		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(null);
		f.getContentPane().add(layerpane);
		f.setVisible(true);

		// 쿼리문

		try {
			dao.list(MemberVo.user);
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			System.out.println(dao.account);
			String sql = "SELECT id, tstype, cash, tsdate FROM USERMEMBER, TSHISTORY "
					+ "WHERE usermember.account= tshistory.receiver " + "AND sender = '" + dao.account + "'"
					+ "ORDER BY tsdate DESC ";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			String[] columnNames = { "", "", "", "" };

			ArrayList<ArrayList<String>> imsiData = new ArrayList<ArrayList<String>>();

			if (rs.next()) {
				do {
					ArrayList<String> temp = new ArrayList<String>();

					temp.add(rs.getString(1));
					temp.add(rs.getString(2));
					temp.add(rs.getString(3));
					temp.add(rs.getString(4));

					imsiData.add(temp);

				} while (rs.next());
			}
			rs.close();

			String[][] data = new String[imsiData.size()][4];

			for (int i = 0; i < imsiData.size(); i++) {
				ArrayList<String> temp = imsiData.get(i);
				for (int j = 0; j < temp.size(); j++) {
					data[i][j] = temp.get(j);
				}
			}

			DefaultTableModel model = new DefaultTableModel(data, columnNames) {
				public boolean isCellEditable(int i, int c) {
					return false;
				}
			};
			

			table.setModel(model);
			table.updateUI();

		}

		catch (Exception e) {
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

	public static void main(String[] args) {
		new Check();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bmain) {
			f.setVisible(false);
			try {
				new Bank();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}
