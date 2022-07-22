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
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Login implements ActionListener {
	private MemberDAO dao = new MemberDAO();
	private JFrame f;
	private JButton bLogin, bNew;
	private JTextField id;
	private JPasswordField pf; // 비밀번호 안보이게
	private BufferedImage img = null;
	MemberVo vo;
	

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public Login() {
		   try {
	            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// LookAndFeel Windows 스타일 적용
	            SwingUtilities.updateComponentTreeUI(f);
	         } catch (Exception e) {

	         }
		
		f = new JFrame("로그인");
		f.getContentPane().setBackground(SystemColor.inactiveCaption);

		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(510, 740);

		try {
			img = ImageIO.read(new File("images\\Login.png"));
			
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
		bNew.setIcon(new ImageIcon("images\\bt\\new.png"));
		bNew.setRolloverIcon(new ImageIcon("images\\bt\\new_1.png"));
		bNew.setBounds(288, 548, 122, 57);
		f.getContentPane().add(bNew);
		bNew.addActionListener(this);

		bLogin = new JButton("");
		bLogin.setContentAreaFilled(false);
		bLogin.setBorderPainted(false);
		bLogin.setFocusPainted(false);
		bLogin.setIcon(new ImageIcon("images\\bt\\login.png"));
		bLogin.setRolloverIcon(
				new ImageIcon("images\\bt\\login_1.png"));
		bLogin.setBounds(83, 548, 122, 57);
		f.getContentPane().add(bLogin);
		bLogin.addActionListener(this);

		f.getContentPane().add(layerpane);
		f.setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bLogin) {
			if (id.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "id를 입력해주세요", "", JOptionPane.WARNING_MESSAGE);
				id.grabFocus();
				return;
			} else if (pf.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요", "", JOptionPane.WARNING_MESSAGE);
				pf.grabFocus();
				return;
			} else {
				System.out.printf("비밀번호 %10s\n",pf.getText());
				
				vo = new MemberVo(id.getText(), pf.getText());
				boolean b = dao.list(vo);

				if (b == true) {
					if (id.getText().equals("admin") && pf.getText().equals("admin")) {
						f.setVisible(false);
						new Admin();

					} else {

						JOptionPane.showMessageDialog(null, "로그인 성공", "성공", JOptionPane.PLAIN_MESSAGE);
						MemberVo.userinit(vo);
						f.setVisible(false);
						try {
							new Bank();
						} catch (URISyntaxException e1) {
							e1.printStackTrace();
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, "id와 비밀번호를 확인해주세요", "실패", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		if (e.getSource() == bNew) {
			f.setVisible(false);
			new NewLogin();
		}

	}

	public static void main(String[] args) {
		new Login();
	}
}
