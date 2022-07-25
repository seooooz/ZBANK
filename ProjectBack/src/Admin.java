import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.SystemColor;
import java.awt.Font;

public class Admin extends JFrame implements ActionListener {
	JMenu m = new JMenu("����");
	JMenuItem update = new JMenuItem("����");
	JMenuItem delete = new JMenuItem("����");
	JMenuItem quit = new JMenuItem("����");
	JMenuBar mb = new JMenuBar();

	String[] name = { "NAME", "ID", "ACCOUNT", "COUNT", "TYPE", "UPDATE" };

	DefaultTableModel dt = new DefaultTableModel(name, 0) {

		public boolean isCellEditable(int i, int c) {
			return false;
		}
	};
	JTable jt = new JTable(dt);
	JScrollPane jsp = new JScrollPane(jt);

	/*
	 * South ������ �߰��� Componet��
	 */

	JPanel p = new JPanel();
//	String[] comboName = { "  ALL  ", "adname", "adid", "adaccount", "adtype" };
	HashMap<String, String> comboName = new HashMap<String, String>() {
     {
        put("ALL", "ALL");
        put("NAME", "adname");
        put("ID", "adid");
        put("ACCOUNT", "adaccount");
        put("TYPE", "adtype");
     }
  };

//	JComboBox combo = new JComboBox(comboName);
  	JComboBox combo = new JComboBox(comboName.keySet().toArray(new String[0]));
	JTextField jtf = new JTextField(20);
	JButton serach;

	AdminDAO dao = new AdminDAO();
	BankIdAccountDAO bdao;

	/**
	 * ȭ�鱸�� �� �̺�Ʈ���
	 */
	public Admin() {

		super("������ ������");
		bdao = new BankIdAccountDAO();

		serach = new JButton("�˻�");
		serach.setFont(new Font("THE���ǵ�", Font.PLAIN, 14));
		m.setFont(new Font("THE���ǵ�", Font.PLAIN, 13));
		update.setFont(new Font("THE���ǵ�", Font.PLAIN, 14));

		// �޴��������� �޴��� �߰�
		m.add(update);
		delete.setFont(new Font("THE���ǵ�", Font.PLAIN, 14));
		m.add(delete);
		quit.setFont(new Font("THE���ǵ�", Font.PLAIN, 14));
		m.add(quit);
		mb.setFont(new Font("���� ���", Font.PLAIN, 14));
		// �޴��� �޴��ٿ� �߰�
		mb.add(m);

		// �����쿡 �޴��� ����
		setJMenuBar(mb);

		// South����
		p.setBackground(SystemColor.inactiveCaption);
		combo.setFont(new Font("THE���ǵ�", Font.PLAIN, 14));
		p.add(combo);
		p.add(jtf);
		p.add(serach);

		getContentPane().add(jsp, "Center");
		getContentPane().add(p, "South");

		setSize(800, 400);
		setVisible(true);

		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// �̺�Ʈ���
//        insert.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		quit.addActionListener(this);
		serach.addActionListener(this);

		// ��緹�ڵ带 �˻��Ͽ� DefaultTableModle�� �ø���
		dao.userSelectAll(dt);

		// ù���� ����.
		if (dt.getRowCount() > 0)
			jt.setRowSelectionInterval(0, 0);

	}// �����ڳ�

	/**
	 * main�޼ҵ� �ۼ�
	 */
	public static void main(String[] args) {
		new Admin();
	}

	/**
	 * ����/����/����/�˻������ ����ϴ� �޼ҵ�
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == update) {// ���� �޴������� Ŭ��
			new AdminJDailogGUI(this, "����");

		} else if (e.getSource() == delete) {// ���� �޴������� Ŭ��
			// ���� Jtable�� ���õ� ��� ���� ���� ���´�.
			int row = jt.getSelectedRow();
			System.out.println("������ : " + row);

			Object obj = jt.getValueAt(row, 1);// �� ���� �ش��ϴ� value
			Object obj2 = jt.getValueAt(row, 2);// �� ���� �ش��ϴ� value
			Object obj3 = jt.getValueAt(row, 4);// �� ���� �ش��ϴ� value
			System.out.println("�� : " + obj);

			bdao.adbalan(obj);

			if (obj3.equals("Ż���û")) {
				if (bdao.adbalance == 0) { // Ż���û�� ����� ���°� 0�� �϶��� ȸ�� Ż�� ����
					if (dao.userDelete(obj.toString()) > 0) {
						dao.tsDelete(obj2.toString());
						dao.userMemberDelete(obj.toString());
						dao.signupDelete(obj.toString());
						AdminJDailogGUI.messageBox(this, "Ż�� �Ǿ����ϴ�.");

						// ����Ʈ ����
						dao.userSelectAll(dt);
						if (dt.getRowCount() > 0)
							jt.setRowSelectionInterval(0, 0);
					}
				} else {
					AdminJDailogGUI.messageBox(this, "Ż�� �Ұ����մϴ�. \n ���¿� ���� �ֽ��ϴ�.");
					dao.nodeleteuserUpdate(obj.toString());
					dao.nodeleteuserMemberUpdate(obj.toString());
				}
			}else {
				AdminJDailogGUI.messageBox(this, "Ż�� ��û�� �̿��ڰ� �ƴմϴ�.");
			}

		} else if (e.getSource() == quit) {// ���� �޴������� Ŭ��
//			System.exit(0);
			setVisible(false);
			new Login();

		} else if (e.getSource() == serach) {// �˻� ��ư Ŭ��
			// JComboBox�� ���õ� value ��������
			String fieldName = combo.getSelectedItem().toString();
			System.out.println("�ʵ�� " + fieldName);

			if (fieldName.trim().equals("ALL")) {// ��ü�˻�
				dao.userSelectAll(dt);
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else {
				if (jtf.getText().trim().equals("")) {
					AdminJDailogGUI.messageBox(this, "�˻��ܾ �Է����ּ���!");
					jtf.requestFocus();
				} else {// �˻�� �Է��������
					dao.getUserSearch(dt, comboName.get(fieldName), jtf.getText());
					if (dt.getRowCount() > 0)
						jt.setRowSelectionInterval(0, 0);
				}
			}
		}

	}// actionPerformed()----------

}