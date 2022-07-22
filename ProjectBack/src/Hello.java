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


public class Hello implements ActionListener{
	private JFrame f;
	private JTextField tfId, tfPw;
	private JLabel lname, lid, tfcAcc;
	private JButton btgLogin;
	private HelloDAO dao;
	private BufferedImage img = null;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}
	
	public Hello() {	
		
		dao = new HelloDAO();
		dao.list(MemberVo.user);
		dao.list1(MemberVo.user);
		
		
		f = new JFrame("축하");
		tfId = new JTextField();
		tfPw = new JTextField();
		
		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(500, 490);

		try {
			img = ImageIO.read(new File("images\\Hello.png"));
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
		
		lname = new JLabel(dao.name);
		lname.setBounds(59, 221, 98, 21);
		lname.setHorizontalAlignment(JLabel.RIGHT);
		f.getContentPane().add(lname);
		lname.setFont(new Font("THE스피드", Font.BOLD, 17));
		
		lid =  new JLabel(MemberVo.user.getId());
		lid.setFont(new Font("THE스피드", Font.BOLD, 17));
		lid.setBounds(245, 258, 116, 21);
		f.getContentPane().add(lid);
		
		tfcAcc = new JLabel(dao.account);
		tfcAcc.setFont(new Font("THE스피드", Font.BOLD, 17));
		tfcAcc.setBounds(224, 298, 116, 21);
		f.getContentPane().add(tfcAcc);
		
		btgLogin = new JButton("");
		btgLogin.setContentAreaFilled(false);
		btgLogin.setBorderPainted(false);
		btgLogin.setFocusPainted(false);
		btgLogin.setIcon(new ImageIcon("images\\bt\\go.png"));
		btgLogin.setRolloverIcon(new ImageIcon("images\\bt\\go_1.png"));
		btgLogin.setBounds(167, 340, 159, 68);
		f.getContentPane().add(btgLogin);
		btgLogin.addActionListener(this);
		f.getContentPane().add(layerpane);
		f.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btgLogin) {
			dao.admin(MemberVo.user);
			f.setVisible(false);
			new Login();
		}
	}

	public static void main (String[] args) {
		 new Hello();
	}
}
	