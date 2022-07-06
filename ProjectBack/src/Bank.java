import java.awt.Color;
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

public class Bank implements ActionListener {
	JFrame f;
	JButton bCheck, bTrans, bdeposit;
	JLabel ltxid, lacc, textField;
	ImageIcon icon;
	BankIdAccountDAO dao;
	BufferedImage img = null;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public Bank() {
		// id�� account �ҷ����� (MemberVo static �޼ҵ� ���)
		dao = new BankIdAccountDAO();
		dao.list(MemberVo.user);
		dao.balan(MemberVo.user);

		f = new JFrame("ZBank");
		f.getContentPane().setBackground(SystemColor.inactiveCaption);

		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(500, 431);

		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Bank.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "����");
			System.exit(0);
		}

		myPanel panel = new myPanel();
		panel.setSize(500, 450);
		layerpane.add(panel);
		layerpane.setLayout(null);

		f.setBounds(700, 50, 510, 470);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(null);

		
		//�α����� ��� id
		ltxid = new JLabel(MemberVo.user.getId());
		ltxid.setBounds(85, 40, 82, 25);
		ltxid.setFont(new Font("����", Font.BOLD, 14));
		f.getContentPane().add(ltxid);

		//�ܾ� �ߴ� ĭ
		textField = new JLabel(Integer.toString(dao.balance));
		textField.setFont(new Font("����", Font.BOLD, 16));
		textField.setBounds(254, 195, 76, 30);
		f.getContentPane().add(textField);
		
		//��ü��ư
		bTrans = new JButton("");
		bTrans.setBounds(195, 254, 112, 46);
		bTrans.setContentAreaFilled(false);
		bTrans.setBorderPainted(false);
		bTrans.setFocusPainted(false);
		bTrans.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\3.png"));
		bTrans.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\3_1.png"));
		f.getContentPane().add(bTrans);
		bTrans.addActionListener(this);
		
		//��ư ) ä���
		bdeposit = new JButton("");
		bdeposit.setBounds(90, 370, 112, 46);
		bdeposit.setContentAreaFilled(false);
		bdeposit.setBorderPainted(false);
		bdeposit.setFocusPainted(false);
		bdeposit.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\deposit.png"));
		bdeposit.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\deposit_1.png"));
		bdeposit.addActionListener(this);
		f.getContentPane().add(bdeposit);
		
		// �α����� ��� ���¹�ȣ
		lacc = new JLabel(dao.account);
		lacc.setBounds(135, 133, 118, 25);
		f.getContentPane().add(lacc);
		lacc.setFont(new Font("����", Font.BOLD, 16));
		
		//��ư ) ��ȸ
		bCheck = new JButton("");
		bCheck.setBounds(301, 370, 112, 46);
		f.getContentPane().add(bCheck);
		bCheck.setContentAreaFilled(false);
		bCheck.setBorderPainted(false);
		bCheck.setFocusPainted(false);
		bCheck.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\check.png"));
		bCheck.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\check_1.png"));
		bCheck.addActionListener(this);
		
		
		f.getContentPane().add(layerpane);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()== bCheck) {
			new Check();
			f.setVisible(false);
		}
		if (e.getSource()== bTrans) {
			f.setVisible(false);
			new Trans();
		}
		if (e.getSource()==bdeposit) {
			f.setVisible(false);
			new Deposit();
		}

	}

	public static void main(String[] args) {
		new Bank();
	}
}