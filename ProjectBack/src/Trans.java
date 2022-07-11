import java.awt.EventQueue;
import java.awt.Graphics;
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
import java.awt.Font;
import javax.swing.ImageIcon;

public class Trans  implements ActionListener{

	JFrame f;
	JButton bMain, bTrans, btnNewButton;
	JLabel ltxid, ltxacc, tfShowAcc;
	private JTextField tfFoundId;
	private JTextField tfInputAcc;
	private JTextField tfTransMoney;
	BankIdAccountDAO dao;
	TransDAO tsdao;
	TransVo v;
	
	BufferedImage img = null;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}
	
	public Trans() {
		
		dao = new BankIdAccountDAO();
		dao.list(MemberVo.user);
		
		tsdao = new TransDAO();
		
		f = new JFrame("이체");
		
		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(510, 740);

		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Trans.png"));
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
		
		
		tfFoundId = new JTextField();
		tfFoundId.setFont(new Font("THE스피드", Font.PLAIN, 16));
		tfFoundId.setBounds(198, 216, 140, 21);
		f.getContentPane().add(tfFoundId);
		tfFoundId.setColumns(10);
		
		btnNewButton = new JButton("");
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\select.png"));
		btnNewButton.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\select_1.png"));
		btnNewButton.setBounds(213, 255, 66, 39);
		f.getContentPane().add(btnNewButton);
		
		tfShowAcc = new JLabel();
		tfShowAcc.setHorizontalAlignment(JLabel.CENTER);
		tfShowAcc.setFont(new Font("THE스피드", Font.BOLD, 18));
		tfShowAcc.setBounds(177, 299, 140, 39);
		f.getContentPane().add(tfShowAcc);
		
		tfInputAcc = new JTextField();
		tfInputAcc.setFont(new Font("THE스피드", Font.BOLD, 15));
		tfInputAcc.setBounds(209, 435, 140, 21);
		f.getContentPane().add(tfInputAcc);
		bTrans = new JButton("");
		bTrans.setContentAreaFilled(false);
		bTrans.setBorderPainted(false);
		bTrans.setFocusPainted(false);
		bTrans.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\trans.png"));
		bTrans.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\trans_1.png"));
		bTrans.setBounds(198, 577, 97, 58);
		f.getContentPane().add(bTrans);
		
		tfTransMoney = new JTextField();
		tfTransMoney.setHorizontalAlignment(JLabel.RIGHT);
		tfTransMoney.setFont(new Font("THE스피드", Font.PLAIN, 18));
		tfTransMoney.setBounds(187, 517, 140, 21);
		f.getContentPane().add(tfTransMoney);
		bTrans.addActionListener(this);
		btnNewButton.addActionListener(this);
		ltxid = new JLabel(MemberVo.user.getId());
		ltxid.setHorizontalAlignment(JLabel.RIGHT);
		ltxid.setFont(new Font("THE스피드", Font.PLAIN, 15));
		ltxid.setBounds(367, 10, 101, 25);
		f.getContentPane().add(ltxid);
		ltxacc = new JLabel(dao.account);
		ltxacc.setHorizontalAlignment(JLabel.RIGHT);
		ltxacc.setFont(new Font("THE스피드", Font.PLAIN, 15));
		ltxacc.setBounds(367, 37, 101, 25);
		f.getContentPane().add(ltxacc);
		
		bMain = new JButton("");
		bMain.setContentAreaFilled(false);
		bMain.setBorderPainted(false);
		bMain.setFocusPainted(false);
		bMain.setBounds(23, 16, 57, 58);
		bMain.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\main.png"));
		bMain.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\main_1.png"));
		bMain.addActionListener(this);
		f.getContentPane().add(bMain);
		
		
		f.getContentPane().add(layerpane);
		f.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bMain) {
			f.setVisible(false);
			new Bank();
		}
		if(e.getSource() == btnNewButton) {
			v = new TransVo(tfFoundId.getText()); 
			tsdao.list(v);
			TransVo.recive(v);
			tfShowAcc.setText(tsdao.account);
			tfInputAcc.setText(tsdao.account);
		}
		
		if(e.getSource() == bTrans) {
			v = new TransVo(Integer.valueOf(tfTransMoney.getText())); 
			TransVo.m(v);
			tsdao.list2(v);
			tsdao.list3(v);
			tsdao.list4(v);
			tsdao.tsnumcount(MemberVo.user);
			f.setVisible(false);
			new Success();
			
		}
		
	}

	public static void main (String[] args) {
		 new Trans();
	}
}