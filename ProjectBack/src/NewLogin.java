import java.awt.Color;
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
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class NewLogin implements ActionListener {
	private JFrame f;
	private JButton bJoin, bsameid;
	private JTextField name, id, pw;
	private BufferedImage img = null;

	int result;
	MemberVo nv;

	private UserDAO dao = new UserDAO();

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public NewLogin() {
		f = new JFrame("회원가입");

		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(510, 740);

		try {
			img = ImageIO.read(new File("images\\NewLogin.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "실패");
			System.exit(0);
		}

		myPanel panel = new myPanel();
		panel.setSize(510, 740);
		layerpane.add(panel);
		layerpane.setLayout(null);

		f.setBounds(700, 50, 510, 740);

		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBackground(Color.white);
		f.getContentPane().setLayout(null);
		name = new JTextField();
		name.setBounds(173, 288, 208, 25);
		f.getContentPane().add(name);
		id = new JTextField();
		id.setBounds(173, 372, 208, 25);
		f.getContentPane().add(id);
		pw = new JTextField();
		pw.setBounds(173, 460, 208, 25);
		f.getContentPane().add(pw);

		bJoin = new JButton("");
		bJoin.setEnabled(false);
		bJoin.setContentAreaFilled(false);
		bJoin.setBorderPainted(false);
		bJoin.setFocusPainted(false);
		bJoin.setIcon(new ImageIcon("images\\bt\\signup.png"));
		bJoin.setRolloverIcon(
				new ImageIcon("images\\bt\\signup_1.png"));
		bJoin.setBounds(184, 558, 126, 57);
		f.getContentPane().add(bJoin);
		bJoin.setForeground(SystemColor.textHighlight);
		bJoin.addActionListener(this);

		bsameid = new JButton("\uC911\uBCF5\uD655\uC778");
		bsameid.setBounds(393, 373, 89, 23);
		bsameid.addActionListener(this);
		f.getContentPane().add(bsameid);
		f.getContentPane().add(layerpane);
		f.setVisible(true);

	}

	public static void main(String[] args) {
		new NewLogin();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == bsameid) {

			if (dao.getIdByCheck(id.getText())) {// 중복아니다.(사용가능)
				JOptionPane.showMessageDialog(null, "사용가능한 아이디입니다.", "", JOptionPane.WARNING_MESSAGE);
				bJoin.setEnabled(true);
				if (name.getText().trim().length() == 0 || name.getText().trim().equals("이름")) {
					JOptionPane.showMessageDialog(null, "이름을 입력해주세요", "", JOptionPane.WARNING_MESSAGE);
					name.grabFocus();
					return;
				} else if (id.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요", "", JOptionPane.WARNING_MESSAGE);
					id.requestFocus();// 포커스이동
				}

			} else { // 중복이다.
				JOptionPane.showMessageDialog(null, "새로운 id를 입력하세요", "실패", JOptionPane.WARNING_MESSAGE);
				id.setText("");// text박스지우기
				id.requestFocus();// 커서놓기
			}

		}
		if (e.getSource() == bJoin) {
			if (pw.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요", "", JOptionPane.WARNING_MESSAGE);
				pw.grabFocus();
				return;
			}
			nv = new MemberVo(name.getText(), id.getText(), pw.getText());
			boolean b = dao.list(nv);

			if (b == true) {
				MemberVo.userinit(nv);
				f.setVisible(false);
				new Hello();
			}
		}

	}
}
