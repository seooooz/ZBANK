import java.awt.Color;
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
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;


public class NewLogin implements ActionListener{
	UserDAO dao;
	private JFrame f;
	private JButton bJoin;
	JTextField name, id, pw;
	private JPanel panel;
	private boolean membershipProgress = false;
	BufferedImage img = null;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}
	
	public NewLogin() {
		dao = new UserDAO();
		f = new JFrame("회원가입");
		
		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(510, 740);

		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\NewLogin.png"));
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
		bJoin.setContentAreaFilled(false);
		bJoin.setBorderPainted(false);
		bJoin.setFocusPainted(false);
		bJoin.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\signup.png"));
		bJoin.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\signup_1.png"));
		bJoin.setBounds(184, 558, 126, 57);
		f.getContentPane().add(bJoin);
		bJoin.setForeground(SystemColor.textHighlight);
		bJoin.addActionListener(this);
		f.getContentPane().add(layerpane);
		f.setVisible(true);
		
	
	}

	public static void main(String[] args) {
		new NewLogin();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(name.getText().trim().length()==0 || name.getText().trim().equals("이름")) {
			JOptionPane.showMessageDialog(null, "이름을 입력해주세요", "", JOptionPane.WARNING_MESSAGE);
			name.grabFocus();
			return;
		}
		if(id.getText().trim().length()==0 || id.getText().trim().equals("아이디")) {
			JOptionPane.showMessageDialog(null, "아이디를 입력해주세요", "", JOptionPane.WARNING_MESSAGE);
			id.grabFocus();
			return;
		}
		if(pw.getText().trim().length()==0) {
			JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요", "", JOptionPane.WARNING_MESSAGE);
			pw.grabFocus();
			return;
		}
		membershipProgress = true;
		
		if(e.getSource()==bJoin) {
			UserVo nv = new UserVo(name.getText(), id.getText(), pw.getText());
			boolean b = dao.list(nv);
			
			if(b == true) {
				UserVo.userinit(nv);
				f.setVisible(false);
				new Hello();
			}else {
				JOptionPane.showMessageDialog(null, "새로운 id를 입력하세요", "실패", JOptionPane.WARNING_MESSAGE);
				new NewLogin();
				f.setVisible(false);
				
			}
			
		
		}
	}

}
