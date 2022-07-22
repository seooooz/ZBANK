import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Mypage implements ActionListener {
	private JFrame f;
	private JButton bremove, bmain, bchange;
	private JLabel ltxid, lacc, lbalance, lutype;
	private ImageIcon icon;
	private BankIdAccountDAO dao;
	private BufferedImage img = null;

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
		layerpane.setSize(500, 460);

		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Mypage.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "실패");
			System.exit(0);
		}

		myPanel panel = new myPanel();
		panel.setSize(500, 460);
		layerpane.add(panel);
		layerpane.setLayout(null);

		f.setBounds(700, 50, 510, 460);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(null);

		// 로그인한 사람 계좌번호
		lacc = new JLabel(dao.account);
		lacc.setBounds(210, 254, 120, 35);
		lacc.setHorizontalAlignment(JLabel.CENTER);
		f.getContentPane().add(lacc);
		lacc.setFont(new Font("THE스피드", Font.BOLD, 16));

		// 로그인한 사람 id
		ltxid = new JLabel(MemberVo.user.getId());
		ltxid.setBounds(198, 130, 98, 35);
		f.getContentPane().add(ltxid);
		ltxid.setFont(new Font("THE스피드", Font.BOLD, 16));
		ltxid.setHorizontalAlignment(JLabel.CENTER);

		// 잔액 뜨는 칸
		lbalance = new JLabel(Integer.toString(dao.balance));
		lbalance.setBounds(221, 299, 98, 30);
		f.getContentPane().add(lbalance);
		lbalance.setHorizontalAlignment(JLabel.CENTER);
		lbalance.setFont(new Font("THE스피드", Font.BOLD, 18));

		// 버튼 ) 채우기
		bremove = new JButton("");
		bremove.setBounds(167, 345, 159, 46);
		f.getContentPane().add(bremove);
		bremove.setContentAreaFilled(false);
		bremove.setBorderPainted(false);
		bremove.setFocusPainted(false);
		bremove.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\remove.png"));
		bremove.setRolloverIcon(
				new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\remove_1.png"));

		lutype = new JLabel(dao.utype);
		lutype.setFont(new Font("THE스피드", Font.BOLD, 16));
		lutype.setBounds(204, 165, 86, 35);
		lutype.setHorizontalAlignment(JLabel.CENTER);
		f.getContentPane().add(lutype);
		bremove.addActionListener(this);

		bmain = new JButton("");
		bmain.setContentAreaFilled(false);
		bmain.setBorderPainted(false);
		bmain.setFocusPainted(false);
		bmain.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\main.png"));
		bmain.setRolloverIcon(
				new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\main_1.png"));
		bmain.setBounds(25, 25, 57, 55);
		bmain.addActionListener(this);
		f.getContentPane().add(bmain);

		bchange = new JButton("");
		bchange.setContentAreaFilled(false);
		bchange.setBorderPainted(false);
		bchange.setFocusPainted(false);
		bchange.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\change.png"));
		bchange.setRolloverIcon(
				new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\change_1.png"));
		bchange.setBounds(360, 25, 134, 38);
		bchange.addActionListener(this);
		f.getContentPane().add(bchange);

		f.getContentPane().add(layerpane);
		f.setVisible(true);
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

		if (e.getSource() == bchange) {
			new PasswordUpdate();
			f.setVisible(false);
		}

		if (e.getSource() == bremove) {
			int result = JOptionPane.showConfirmDialog(null, "탈퇴 신청을 하시겠습니까?", " ", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				dao.userupdate();
				dao.adminupdate();
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

	public static void main(String[] args) {
		new Mypage();
	}
}