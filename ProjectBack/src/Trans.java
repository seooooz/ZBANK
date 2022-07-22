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
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.ImageIcon;

public class Trans implements ActionListener {

	private JFrame f;
	private JButton bMain, bTrans, btidcheck;
	private JLabel ltxid, ltxacc, tfShowAcc;
	private JTextField tfFoundId, tfInputAcc, tfTransMoney;

	private BankIdAccountDAO dao;
	private TransDAO tsdao = new TransDAO();
	private TransVo v;

	private BufferedImage img = null;

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public Trans() {

		dao = new BankIdAccountDAO();
		dao.list(MemberVo.user);
		dao.balan(MemberVo.user);

		f = new JFrame("��ü");

		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(510, 740);

		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Trans.png"));
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

		// ���� ��� �α����� ��� id, account ���
		ltxid = new JLabel(MemberVo.user.getId());
		ltxid.setHorizontalAlignment(JLabel.RIGHT);
		ltxid.setFont(new Font("THE���ǵ�", Font.PLAIN, 15));
		ltxid.setBounds(367, 10, 101, 25);
		f.getContentPane().add(ltxid);

		ltxacc = new JLabel(dao.account);
		ltxacc.setHorizontalAlignment(JLabel.RIGHT);
		ltxacc.setFont(new Font("THE���ǵ�", Font.PLAIN, 15));
		ltxacc.setBounds(367, 37, 101, 25);
		f.getContentPane().add(ltxacc);

		// �˻��� idüũ
		tfFoundId = new JTextField();
		tfFoundId.setFont(new Font("THE���ǵ�", Font.PLAIN, 16));
		tfFoundId.setBounds(198, 216, 140, 21);
		f.getContentPane().add(tfFoundId);

		// ��ư) �˻��� idüũ
		btidcheck = new JButton("");
		btidcheck.setContentAreaFilled(false);
		btidcheck.setBorderPainted(false);
		btidcheck.setFocusPainted(false);
		btidcheck.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\select.png"));
		btidcheck.setRolloverIcon(
				new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\select_1.png"));
		btidcheck.setBounds(213, 255, 66, 39);
		f.getContentPane().add(btidcheck);
		btidcheck.addActionListener(this);

		// �˻��� id�� �´� account ���
		tfShowAcc = new JLabel();
		tfShowAcc.setHorizontalAlignment(JLabel.CENTER);
		tfShowAcc.setFont(new Font("THE���ǵ�", Font.BOLD, 18));
		tfShowAcc.setBounds(177, 299, 140, 39);
		f.getContentPane().add(tfShowAcc);
		// �˻��� id�� �´� account ���
		tfInputAcc = new JTextField();
		tfInputAcc.setFont(new Font("THE���ǵ�", Font.BOLD, 15));
		tfInputAcc.setBounds(209, 435, 140, 21);
		f.getContentPane().add(tfInputAcc);

		// ��ư) �۱�
		bTrans = new JButton("");
		bTrans.setContentAreaFilled(false);
		bTrans.setBorderPainted(false);
		bTrans.setFocusPainted(false);
		bTrans.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\trans.png"));
		bTrans.setRolloverIcon(
				new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\trans_1.png"));
		bTrans.setBounds(198, 577, 97, 58);
		f.getContentPane().add(bTrans);
		bTrans.addActionListener(this);

		// �۱��� �ݾ� ���� ĭ
		tfTransMoney = new JTextField();
		tfTransMoney.setHorizontalAlignment(JLabel.RIGHT);
		tfTransMoney.setFont(new Font("THE���ǵ�", Font.PLAIN, 18));
		tfTransMoney.setBounds(187, 517, 140, 21);
		f.getContentPane().add(tfTransMoney);

		// ��ư) bankŬ���� �������� ���ư���
		bMain = new JButton("");
		bMain.setContentAreaFilled(false);
		bMain.setBorderPainted(false);
		bMain.setFocusPainted(false);
		bMain.setBounds(23, 16, 57, 58);
		bMain.setIcon(new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\main.png"));
		bMain.setRolloverIcon(
				new ImageIcon("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\bt\\main_1.png"));
		bMain.addActionListener(this);
		f.getContentPane().add(bMain);

		f.getContentPane().add(layerpane);
		f.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bMain) {
			f.setVisible(false);
			try {
				new Bank();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == btidcheck) {
			v = new TransVo(tfFoundId.getText());
			boolean ok = tsdao.list(v);
			if (ok == true) {
				TransVo.recive(v);
				tfShowAcc.setText(tsdao.account);
				tfInputAcc.setText(tsdao.account);
			} else {
				JOptionPane.showMessageDialog(null, "�Է��Ͻ� ����ڰ� �����ϴ�.", " ", JOptionPane.WARNING_MESSAGE);
				tfFoundId.setText("");// text�ڽ� �����
				tfFoundId.requestFocus();// Ŀ���� ����
			}
		}

		if (e.getSource() == bTrans) {

			if (tfFoundId.getText().equals("") && tfInputAcc.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "���¹�ȣ�� �Է����ּ���.", "��ȣ �Է�", JOptionPane.WARNING_MESSAGE);
				tfFoundId.requestFocus();
				// �α����� �̿��� ���¿� �۱��� �ݾ��� 0���� Ŀ���� �۱ݰ���
			}else if (tfTransMoney.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "�۱ݾ��� �Է����ּ���.", "����", JOptionPane.PLAIN_MESSAGE);
					tfTransMoney.requestFocus();// Ŀ���� ����
				} else if (dao.balance - Integer.valueOf(tfTransMoney.getText()) >= 0) {
					int cash = dao.balance + Integer.valueOf(tfTransMoney.getText());
					// �۱ݾ��� ���� ���¿� ���� �հ� �ܾ��� 10�ﺸ�� �۾ƾ����� �۱ݰ���
					if (String.valueOf(cash).length() < 10) {
						v = new TransVo(Integer.valueOf(tfTransMoney.getText()));
						TransVo.m(v);
						tsdao.list2(v);
						tsdao.list3(v);
						tsdao.list4(v);
						tsdao.tsnumcount(MemberVo.user);
						f.setVisible(false);
						new Success();
					} else {
						JOptionPane.showMessageDialog(null, "�۱ݾ��� �ִ� �ݾ��� �ʰ��մϴ�.", "�۱� ����", JOptionPane.WARNING_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null, "�ܾ׺��� �۱ݾ��� Ů�ϴ�.", "�۱� ����", JOptionPane.WARNING_MESSAGE);
				}

			
		}

	}

	public static void main(String[] args) {
		new Trans();
	}
}