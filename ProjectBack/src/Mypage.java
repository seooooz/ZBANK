import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Mypage implements ActionListener {
	JFrame f;
	JButton bremove;
	JLabel ltxid, lacc, lbalance;
	ImageIcon icon;
	BankIdAccountDAO dao;
	BufferedImage img = null;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public Mypage() {
		// id랑 account 불러오기 (MemberVo static 메소드 사용)
		dao = new BankIdAccountDAO();
		dao.list(MemberVo.user);
		dao.balan(MemberVo.user);

		f = new JFrame("ZBank");
		f.getContentPane().setBackground(SystemColor.inactiveCaption);

		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(500, 441);

		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Bank.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "실패");
			System.exit(0);
		}

		myPanel panel = new myPanel();
		panel.setSize(500, 490);
		layerpane.add(panel);
		layerpane.setLayout(null);

		f.setBounds(700, 50, 510, 490);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(null);

		// 로그인한 사람 계좌번호
		lacc = new JLabel(dao.account);
		lacc.setBounds(231, 118, 118, 25);
		f.getContentPane().add(lacc);
		lacc.setFont(new Font("THE스피드", Font.BOLD, 16));

		// 로그인한 사람 id
		ltxid = new JLabel(MemberVo.user.getId());
		ltxid.setBounds(231, 85, 98, 25);
		f.getContentPane().add(ltxid);
		ltxid.setFont(new Font("THE스피드", Font.BOLD, 16));
		ltxid.setHorizontalAlignment(JLabel.RIGHT);

		// 잔액 뜨는 칸
		lbalance = new JLabel(Integer.toString(dao.balance));
		lbalance.setBounds(249, 165, 98, 30);
		f.getContentPane().add(lbalance);
		lbalance.setHorizontalAlignment(JLabel.CENTER);
		lbalance.setFont(new Font("THE스피드", Font.BOLD, 18));

		// 버튼 ) 채우기
		bremove = new JButton("");
		bremove.setBounds(194, 369, 112, 46);
		f.getContentPane().add(bremove);
		bremove.setContentAreaFilled(false);
		bremove.setBorderPainted(false);
		bremove.setFocusPainted(false);
		bremove.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\deposit.png"));
		bremove.setRolloverIcon(
				new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\deposit_1.png"));
		
		JLabel lutype = new JLabel(dao.utype);
		lutype.setFont(new Font("THE스피드", Font.BOLD, 16));
		lutype.setBounds(249, 245, 86, 35);
		f.getContentPane().add(lutype);
		bremove.addActionListener(this);
//		f.getContentPane().add(layerpane);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bremove) {
			f.setVisible(false);
			new Deposit();
		}

	}

	public static void main(String[] args) {
		new Mypage();
	}
}