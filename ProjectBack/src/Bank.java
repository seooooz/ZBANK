import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Bank implements ActionListener {
	private JFrame f;
	private JButton bCheck, bTrans, bdeposit, bmypage;
	private JLabel ltxid, lacc, textField;
	private ImageIcon icon;
	private BankIdAccountDAO dao;
	private BufferedImage img = null;
	private JButton bmap;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}
	
	private static void open(URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				/* TODO: error handling */ }
		} else {
			/* TODO: error handling */ }
	}

	public Bank() throws URISyntaxException{
		// 카카오맵 불러오기
		final URI uri = new URI("http://localhost:8000/1.html");
		class OpenUrlAction implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				open(uri);
			}
		}
		
		
		// id랑 account 불러오기 (MemberVo static 메소드 사용)
		dao = new BankIdAccountDAO();
		dao.list(MemberVo.user);
		dao.balan(MemberVo.user);

		f = new JFrame("ZBank");
		f.getContentPane().setBackground(SystemColor.inactiveCaption);

		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(500, 490);

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

		
		//로그인한 사람 id
		ltxid = new JLabel(MemberVo.user.getId());
		ltxid.setBounds(50, 40, 98, 25);
		ltxid.setFont(new Font("THE스피드", Font.BOLD, 16));
		ltxid.setHorizontalAlignment(JLabel.RIGHT);
		f.getContentPane().add(ltxid);

		//잔액 뜨는 칸
		textField = new JLabel(Integer.toString(dao.balance));
		textField.setHorizontalAlignment(JLabel.CENTER);
		textField.setFont(new Font("THE스피드", Font.BOLD, 18));
		textField.setBounds(232, 195, 98, 30);
		f.getContentPane().add(textField);
		
		//이체버튼
		bTrans = new JButton("");
		bTrans.setBounds(195, 254, 112, 46);
		bTrans.setContentAreaFilled(false);
		bTrans.setBorderPainted(false);
		bTrans.setFocusPainted(false);
		bTrans.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\3.png"));
		bTrans.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\3_1.png"));
		f.getContentPane().add(bTrans);
		bTrans.addActionListener(this);
		
		//버튼 ) 채우기
		bdeposit = new JButton("");
		bdeposit.setBounds(90, 370, 112, 46);
		bdeposit.setContentAreaFilled(false);
		bdeposit.setBorderPainted(false);
		bdeposit.setFocusPainted(false);
		bdeposit.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\deposit.png"));
		bdeposit.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\deposit_1.png"));
		bdeposit.addActionListener(this);
		f.getContentPane().add(bdeposit);
		
		// 로그인한 사람 계좌번호
		lacc = new JLabel(dao.account);
		lacc.setBounds(135, 133, 118, 25);
		f.getContentPane().add(lacc);
		lacc.setFont(new Font("굴림", Font.BOLD, 16));
		
		//버튼 ) 조회
		bCheck = new JButton("");
		bCheck.setBounds(301, 370, 112, 46);
		f.getContentPane().add(bCheck);
		bCheck.setContentAreaFilled(false);
		bCheck.setBorderPainted(false);
		bCheck.setFocusPainted(false);
		bCheck.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\check.png"));
		bCheck.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\check_1.png"));
		bCheck.addActionListener(this);
		
		//버튼) 마이페이지
		bmypage = new JButton("");
		bmypage.setContentAreaFilled(false);
		bmypage.setBorderPainted(false);
		bmypage.setFocusPainted(false);
		bmypage.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\main.png"));
		bmypage.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\mypage.png"));
		bmypage.setBounds(66, 113, 57, 55);
		bmypage.addActionListener(this);
		f.getContentPane().add(bmypage);
		
		//버튼) 카카오맵
		bmap = new JButton("");
		bmap.setContentAreaFilled(false);
		bmap.setBorderPainted(false);
		bmap.setFocusPainted(false);
		bmap.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\map.png"));
		bmap.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\map_1.png"));
		bmap.setToolTipText(uri.toString());
		bmap.addActionListener(new OpenUrlAction());
		bmap.setBounds(409, 10, 57, 55);
		f.getContentPane().add(bmap);
		
		
		f.getContentPane().add(layerpane);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()== bCheck) {
			f.setVisible(false);
			new Check();
		}
		if (e.getSource()== bTrans) {
			f.setVisible(false);
			new Trans();
		}
		if (e.getSource()==bdeposit) {
			f.setVisible(false);
			new Deposit();
		}
		if (e.getSource()==bmypage) {
			f.setVisible(false);
			new Mypage();
		}

	}

	public static void main(String[] args) throws URISyntaxException {
		new Bank();
	}
}