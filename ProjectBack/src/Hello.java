import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Hello implements ActionListener{
	private JFrame f;
	JTextField tfId, tfPw;
	private final JPanel panel_1 = new JPanel();
	private JLabel lblNewLabel, lname, lid, tfcAcc;
	HelloDAO dao;
//	BankIdAccountDAO bdao;
	
//	String n, String i
	public Hello() {	
		
		dao = new HelloDAO();
//		bdao = new BankIdAccountDAO();
		dao.list(UserVo.user);
		dao.list1(UserVo.user);
//		bdao.list(MemberVo.user);
		
		
		f = new JFrame("축하");
		tfId = new JTextField();
		tfPw = new JTextField();
		
		f.setSize(350, 300);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.window);
		panel.setBounds(0, 0, 334, 65);
		f.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lthx = new JLabel("\uAC00\uC785\uC744 \uCD95\uD558\uD569\uB2C8\uB2E4.");
		lthx.setFont(new Font("굴림", Font.BOLD, 15));
		lthx.setBounds(161, 16, 147, 33);
		panel.add(lthx);
		
		lname = new JLabel(dao.name);
		lname.setFont(new Font("굴림", Font.PLAIN, 15));
		lname.setBounds(37, 22, 116, 21);
		panel.add(lname);
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setBounds(0, 64, 334, 197);
		f.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		lblNewLabel = new JLabel("ID");
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 14));
		lblNewLabel.setBounds(65, 45, 29, 15);
		panel_1.add(lblNewLabel);
		
		lid =  new JLabel(UserVo.user.getId());
		lid.setBounds(157, 42, 116, 21);
		panel_1.add(lid);
		
		JButton btgLogin = new JButton("로그인 하러 가기");
		btgLogin.setBounds(101, 149, 132, 23);
		panel_1.add(btgLogin);
		btgLogin.addActionListener(this);
		
		JLabel lblNewLabel_1 = new JLabel("\uACC4\uC88C\uBC88\uD638");
		lblNewLabel_1.setForeground(SystemColor.textHighlight);
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(65, 93, 67, 15);
		panel_1.add(lblNewLabel_1);
		
		tfcAcc = new JLabel(dao.account);
		tfcAcc.setBounds(157, 90, 116, 21);
		panel_1.add(tfcAcc);
		f.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("로그인 하러 가기")) {

			f.setVisible(false);
			new Login();
		}
	}

	public static void main (String[] args) {
		 new Hello();
	}
}
	