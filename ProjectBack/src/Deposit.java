import java.awt.Color;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class Deposit implements ActionListener {
	JFrame f;
	JButton bdeposit, bmain;
	JLabel ltxid, lacc;
	private JTextField textField;
	BankIdAccountDAO dao;
	TransDAO tsdao;
	BufferedImage img = null;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public Deposit() {

		// id¶û account ºÒ·¯¿À±â (MemberVo static ¸Þ¼Òµå »ç¿ë)
		dao = new BankIdAccountDAO();
		dao.list(MemberVo.user);
		ltxid = new JLabel(MemberVo.user.getId());
		ltxid.setHorizontalAlignment(JLabel.RIGHT);
		lacc = new JLabel(dao.account);
		lacc.setHorizontalAlignment(JLabel.RIGHT);

		tsdao = new TransDAO();

		f = new JFrame("ZBank");
		f.getContentPane().setBackground(SystemColor.inactiveCaption);
		
		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(500, 490);

		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\deposit.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "½ÇÆÐ");
			System.exit(0);
		}

		myPanel panel = new myPanel();
		panel.setSize(500, 490);
		layerpane.add(panel);
		layerpane.setLayout(null);

		f.setBounds(700, 50, 510, 490);

		ltxid.setFont(new Font("±¼¸²", Font.BOLD, 14));
		lacc.setFont(new Font("±¼¸²", Font.BOLD, 14));

		ltxid.setBounds(389, 22, 82, 25);
		lacc.setBounds(370, 49, 101, 25);

		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(null);
		f.getContentPane().add(ltxid);
		f.getContentPane().add(lacc);

		textField = new JTextField();
		textField.setBounds(153, 250, 166, 30);
		f.getContentPane().add(textField);
		
		bdeposit = new JButton("");
		bdeposit.setContentAreaFilled(false);
		bdeposit.setBorderPainted(false);
		bdeposit.setFocusPainted(false);
		bdeposit.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\success.png"));
		bdeposit.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\success_1.png"));
		bdeposit.setBounds(186, 324, 122, 62);
		f.getContentPane().add(bdeposit);
		
		bmain = new JButton("");
		bmain.setContentAreaFilled(false);
		bmain.setBorderPainted(false);
		bmain.setFocusPainted(false);
		bmain.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\main.png"));
		bmain.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\main_1.png"));
		bmain.setBounds(23, 16, 57, 55);
		bmain.addActionListener(this);
		f.getContentPane().add(bmain);
		
		bdeposit.addActionListener(this);
		f.getContentPane().add(layerpane);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bmain) {
			f.setVisible(false);
			new Bank();
		}
		if (e.getSource() == bdeposit) {
			TransVo v = new TransVo(Integer.valueOf(textField.getText()));
			tsdao.list1(v);
			tsdao.list5(v);
			f.setVisible(false);
			new Bank();
		}

	}

	public static void main(String[] args) {
		new Deposit();
	}
}