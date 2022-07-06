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


public class Login implements ActionListener {
	private MemberDAO dao;
	JFrame f;
	JButton bLogin, bNew;
	JLabel lid, lpw;
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
		layerpane.setSize(500, 700);

		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Login.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "����");
			System.exit(0);
		}

		myPanel panel = new myPanel();
		panel.setSize(500, 700);
		layerpane.add(panel);
		layerpane.setLayout(null);

		f.setBounds(700, 50, 510, 710);
		
		id = new JTextField();
		pf = new JPasswordField();
		id.setBounds(82, 78, 220, 25);
		pf.setBounds(82, 118, 220, 25);

		bLogin = new JButton("�α���");
		bNew = new JButton("ȸ������");
		bLogin.setBounds(28, 199, 122, 30);
		bNew.setBounds(180, 199, 122, 30);
		bLogin.addActionListener(this);
		bNew.addActionListener(this);
		
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(null);
		f.getContentPane().add(bLogin);
		f.getContentPane().add(bNew);
		f.getContentPane().add(id);
		f.getContentPane().add(pf);
		lid = new JLabel("ID : ", JLabel.RIGHT);
		lid.setBounds(42, 83, 23, 15);
		f.getContentPane().add(lid);
		lpw = new JLabel("PW : ", JLabel.RIGHT);
		lpw.setBounds(42, 123, 30, 15);
		f.getContentPane().add(lpw);
		f.getContentPane().add(layerpane);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("�α���")) {
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
					JOptionPane.showMessageDialog(null, "�α��� ����", "����", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		if (e.getActionCommand().equals("ȸ������")) {
			f.setVisible(false);
			new NewLogin();
		}

	}

	public static void main(String[] args) {
		new Login();
	}
}
