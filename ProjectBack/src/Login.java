import java.awt.EventQueue;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class Login implements ActionListener {
	private MemberDAO dao;
	JFrame f;
	JButton bLogin, bNew;
	JTextField id;
	JPasswordField pf; // ��й�ȣ �Ⱥ��̰�
	BufferedImage img = null;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public Login() {
		dao = new MemberDAO();
		f = new JFrame("�α���");
		f.getContentPane().setBackground(SystemColor.inactiveCaption);

		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(510, 740);

		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Login.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "����");
			System.exit(0);
		}

		myPanel panel = new myPanel();
		panel.setSize(510, 740);
		layerpane.add(panel);
		layerpane.setLayout(null);

		f.setBounds(700, 50, 510, 740);

		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(null);

		id = new JTextField();
		id.setBounds(162, 375, 205, 25);
		f.getContentPane().add(id);
		pf = new JPasswordField();
		pf.setBounds(162, 443, 205, 25);
		f.getContentPane().add(pf);
		bNew = new JButton("");
		bNew.setContentAreaFilled(false);
		bNew.setBorderPainted(false);
		bNew.setFocusPainted(false);
		bNew.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\new.png"));
		bNew.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\new_1.png"));
		bNew.setBounds(288, 548, 122, 57);
		f.getContentPane().add(bNew);

		bLogin = new JButton("");
		bLogin.setContentAreaFilled(false);
		bLogin.setBorderPainted(false);
		bLogin.setFocusPainted(false);
		bLogin.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\login.png"));
		bLogin.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\login_1.png"));
		bLogin.setBounds(83, 548, 122, 57);
		f.getContentPane().add(bLogin);
		bLogin.addActionListener(this);
		bNew.addActionListener(this);
		f.getContentPane().add(layerpane);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bLogin) {
			if (id.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "id�� �Է����ּ���", "", JOptionPane.WARNING_MESSAGE);
				id.grabFocus();
				return;
			} else if (pf.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է����ּ���", "", JOptionPane.WARNING_MESSAGE);
				pf.grabFocus();
				return;
			} else {

				MemberVo vo = new MemberVo(id.getText(), pf.getText());
				boolean b = dao.list(vo);

				if (b == true) {
					JOptionPane.showMessageDialog(null, "�α��� ����", "����", JOptionPane.PLAIN_MESSAGE);
					MemberVo.userinit(vo);
					f.setVisible(false);
					new Bank();

				} else {
					JOptionPane.showMessageDialog(null, "id�� ��й�ȣ�� Ȯ�����ּ���", "����", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		if (e.getSource()==bNew) {
			f.setVisible(false);
			new NewLogin();
		}

	}

	public static void main(String[] args) {
		new Login();
	}
}
