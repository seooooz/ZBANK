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
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class PasswordUpdate implements ActionListener {
	private JFrame f;
	private JButton bpwchange;
	private BufferedImage img = null;
	private JTextField tfnowpass;
	private JPasswordField tfnewpass, tfnewpasscheck;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public PasswordUpdate() {
		f = new JFrame("pw변경");
		f.getContentPane().setBackground(SystemColor.inactiveCaption);

		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(500, 460);

		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Change.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "실패");
			System.exit(0);
		}
		
		

		myPanel panel = new myPanel();
		panel.setSize(500, 460);
		layerpane.add(panel);
		layerpane.setLayout(null);

		f.setBounds(700, 50, 456, 440);
		f.setLocationRelativeTo(null);
		f.getContentPane().setLayout(null);

		// 버튼 ) 채우기
		bpwchange = new JButton("");
		bpwchange.setBounds(143, 345, 153, 46);
		f.getContentPane().add(bpwchange);
		bpwchange.setContentAreaFilled(false);
		bpwchange.setBorderPainted(false);
		bpwchange.setFocusPainted(false);
		bpwchange.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\pwchange.png"));
		bpwchange.setRolloverIcon(
				new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\pwchange_1.png"));
		
		JLabel lmyid = new JLabel(MemberVo.user.getId());
		lmyid.setFont(new Font("THE스피드", Font.BOLD, 16));
		lmyid.setBounds(241, 165, 124, 26);
		f.getContentPane().add(lmyid);
		
		tfnowpass = new JTextField();
		tfnowpass.setFont(new Font("THE스피드", Font.BOLD, 16));
		tfnowpass.setBounds(241, 209, 124, 21);
		f.getContentPane().add(tfnowpass);
		tfnowpass.setColumns(10);
		
		tfnewpass = new JPasswordField();
		tfnewpass.setFont(new Font("THE스피드", Font.BOLD, 16));
		tfnewpass.setColumns(10);
		tfnewpass.setBounds(241, 248, 124, 21);
		f.getContentPane().add(tfnewpass);
		
		tfnewpasscheck = new JPasswordField();
		tfnewpasscheck.setFont(new Font("THE스피드", Font.BOLD, 16));
		tfnewpasscheck.setColumns(10);
		tfnewpasscheck.setBounds(241, 288, 124, 21);
		f.getContentPane().add(tfnewpasscheck);
		bpwchange.addActionListener(this);
		
		
		
		f.getContentPane().add(layerpane);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bpwchange) {
			MemberDAO md = new MemberDAO();
			
			boolean b = md.passselect(tfnowpass.getText());
			
			//true = 중복값 없음,,,,, false = 중복값 있음
			if(b == false) {
				if(tfnowpass.getText().equals(tfnewpass.getText())) {
					JOptionPane.showMessageDialog(null, "현재 비밀번호와 같습니다.", "실패", JOptionPane.WARNING_MESSAGE);
					System.out.printf("현 비밀번호 %8s\n",tfnowpass.getText());
					System.out.printf("새 비밀번호 %8s\n",tfnewpass.getText());
				}else if(tfnewpass.getText().equals(tfnewpasscheck.getText())) {
					boolean ok = md.passupdate(tfnewpass.getText());
					
					if(ok == true) {
						JOptionPane.showMessageDialog(null, "정상적으로 바뀌었습니다.", "성공", JOptionPane.PLAIN_MESSAGE);
						System.out.println("------------------------");
						System.out.println("비밀번호 바꾸기 성공");
						System.out.printf("새 비밀번호	%9s\n",tfnewpass.getText());
						
						f.setVisible(false);
						new Login();
					}else {
						System.out.println("xxx비밀번호 바꾸기 실패xxx");
					}
				}else {
					JOptionPane.showMessageDialog(null, "새 비밀번호를 확인해주세요.", "실패", JOptionPane.WARNING_MESSAGE);
					System.out.println("------------------------");
					System.out.printf("새 비밀번호 %9s\n",tfnewpass.getText());
					System.out.printf("새 비밀번호 확인  %4s\n",tfnewpasscheck.getText());
				}
			}else {
				JOptionPane.showMessageDialog(null, "현재 비밀번호를 다시 입력해주세요.", "실패", JOptionPane.WARNING_MESSAGE);
			}
		}
	
	}

	public static void main(String[] args) {
		new PasswordUpdate();
	}
}