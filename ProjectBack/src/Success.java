import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;


public class Success  implements ActionListener{
	private JFrame f;
	private JLabel lreceivername, lreceiverid, lreceiverAcc;
	private JButton btsucc;
	private TransDAO tsdao;
	
	private BufferedImage img = null;
	private JLabel lreceiverdate, lreceivertype, lmybalance;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}
	
	public Success() {	
		
		tsdao = new TransDAO();
		tsdao.succ(TransVo.reciver);
		tsdao.succdate();
		tsdao.tranmybal();
		
		f = new JFrame("성공");
		
		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(510, 740);

		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Success.png"));
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
		
		lreceiverid =  new JLabel(TransVo.reciver.getId());
		lreceiverid.setFont(new Font("THE스피드", Font.BOLD, 17));
		lreceiverid.setBounds(163, 194, 82, 21);
		f.getContentPane().add(lreceiverid);
		
		lreceivername = new JLabel(tsdao.name);
		lreceivername.setBounds(45, 180, 103, 36);
		lreceivername.setHorizontalAlignment(JLabel.CENTER);
		f.getContentPane().add(lreceivername);
		lreceivername.setFont(new Font("THE스피드", Font.BOLD, 26));
		
		
		lreceiverAcc = new JLabel(tsdao.account);
		lreceiverAcc.setFont(new Font("THE스피드", Font.BOLD, 17));
		lreceiverAcc.setBounds(283, 522, 136, 43);
		lreceiverAcc.setHorizontalAlignment(JLabel.RIGHT);
		f.getContentPane().add(lreceiverAcc);
		
		btsucc = new JButton("");
		btsucc.setContentAreaFilled(false);
		btsucc.setBorderPainted(false);
		btsucc.setFocusPainted(false);
		btsucc.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\success.png"));
		btsucc.setRolloverIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\success_1.png"));
		btsucc.setBounds(181, 605, 132, 57);
		f.getContentPane().add(btsucc);
		
		JLabel lreceivercash = new JLabel(Integer.toString(TransVo.mo.getCash())); 
		lreceivercash.setFont(new Font("THE스피드", Font.BOLD, 17));
		lreceivercash.setBounds(283, 397, 136, 30);
		lreceivercash.setHorizontalAlignment(JLabel.RIGHT);
		f.getContentPane().add(lreceivercash);
		
		lreceiverdate = new JLabel(tsdao.tsdate);
		lreceiverdate.setFont(new Font("THE스피드", Font.BOLD, 17));
		lreceiverdate.setBounds(244, 260, 175, 35);
		lreceiverdate.setHorizontalAlignment(JLabel.RIGHT);
		f.getContentPane().add(lreceiverdate);
		
		lreceivertype = new JLabel(tsdao.tstype);
		lreceivertype.setFont(new Font("THE스피드", Font.BOLD, 17));
		lreceivertype.setBounds(316, 319, 103, 35);
		lreceivertype.setHorizontalAlignment(JLabel.RIGHT);
		f.getContentPane().add(lreceivertype);
		
		lmybalance = new JLabel(Integer.toString(tsdao.balance));
		lmybalance.setFont(new Font("THE스피드", Font.BOLD, 16));
		lmybalance.setBounds(283, 458, 136, 36);
		lmybalance.setHorizontalAlignment(JLabel.RIGHT);
		f.getContentPane().add(lmybalance);
		btsucc.addActionListener(this);
		f.getContentPane().add(layerpane);
		f.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btsucc) {

			f.setVisible(false);
			try {
				new Bank();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void main (String[] args) {
		 new Success();
	}
}
	