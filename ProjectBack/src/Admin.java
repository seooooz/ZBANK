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
	JMenu m = new JMenu("관리");
	JMenuItem update = new JMenuItem("수정");
	JMenuItem delete = new JMenuItem("삭제");
	JMenuItem quit = new JMenuItem("종료");
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
	 * South 영역에 추가할 Componet들
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
	 * 화면구성 및 이벤트등록
	 */
	public Admin() {

		super("관리자 페이지");
		bdao = new BankIdAccountDAO();

		serach = new JButton("검색");
		serach.setFont(new Font("THE스피드", Font.PLAIN, 14));
		m.setFont(new Font("THE스피드", Font.PLAIN, 13));
		update.setFont(new Font("THE스피드", Font.PLAIN, 14));

		// 메뉴아이템을 메뉴에 추가
		m.add(update);
		delete.setFont(new Font("THE스피드", Font.PLAIN, 14));
		m.add(delete);
		quit.setFont(new Font("THE스피드", Font.PLAIN, 14));
		m.add(quit);
		mb.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		// 메뉴를 메뉴바에 추가
		mb.add(m);

		// 윈도우에 메뉴바 세팅
		setJMenuBar(mb);

		// South영역
		p.setBackground(SystemColor.inactiveCaption);
		combo.setFont(new Font("THE스피드", Font.PLAIN, 14));
		p.add(combo);
		p.add(jtf);
		p.add(serach);

		getContentPane().add(jsp, "Center");
		getContentPane().add(p, "South");

		setSize(800, 400);
		setVisible(true);

		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 이벤트등록
//        insert.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		quit.addActionListener(this);
		serach.addActionListener(this);

		// 모든레코드를 검색하여 DefaultTableModle에 올리기
		dao.userSelectAll(dt);

		// 첫번행 선택.
		if (dt.getRowCount() > 0)
			jt.setRowSelectionInterval(0, 0);

	}// 생성자끝

	/**
	 * main메소드 작성
	 */
	public static void main(String[] args) {
		new Admin();
	}

	/**
	 * 가입/수정/삭제/검색기능을 담당하는 메소드
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == update) {// 수정 메뉴아이템 클릭
			new AdminJDailogGUI(this, "수정");

		} else if (e.getSource() == delete) {// 삭제 메뉴아이템 클릭
			// 현재 Jtable의 선택된 행과 열의 값을 얻어온다.
			int row = jt.getSelectedRow();
			System.out.println("선택행 : " + row);

			Object obj = jt.getValueAt(row, 1);// 행 열에 해당하는 value
			Object obj2 = jt.getValueAt(row, 2);// 행 열에 해당하는 value
			Object obj3 = jt.getValueAt(row, 4);// 행 열에 해당하는 value
			System.out.println("값 : " + obj);

			bdao.adbalan(obj);

			if (obj3.equals("탈퇴신청")) {
				if (bdao.adbalance == 0) { // 탈퇴신청한 사람의 계좌가 0원 일때만 회원 탈퇴 가능
					if (dao.userDelete(obj.toString()) > 0) {
						dao.tsDelete(obj2.toString());
						dao.userMemberDelete(obj.toString());
						dao.signupDelete(obj.toString());
						AdminJDailogGUI.messageBox(this, "탈퇴 되었습니다.");

						// 리스트 갱신
						dao.userSelectAll(dt);
						if (dt.getRowCount() > 0)
							jt.setRowSelectionInterval(0, 0);
					}
				} else {
					AdminJDailogGUI.messageBox(this, "탈퇴 불가능합니다. \n 계좌에 돈이 있습니다.");
					dao.nodeleteuserUpdate(obj.toString());
					dao.nodeleteuserMemberUpdate(obj.toString());
				}
			}else {
				AdminJDailogGUI.messageBox(this, "탈퇴 신청한 이용자가 아닙니다.");
			}

		} else if (e.getSource() == quit) {// 종료 메뉴아이템 클릭
//			System.exit(0);
			setVisible(false);
			new Login();

		} else if (e.getSource() == serach) {// 검색 버튼 클릭
			// JComboBox에 선택된 value 가져오기
			String fieldName = combo.getSelectedItem().toString();
			System.out.println("필드명 " + fieldName);

			if (fieldName.trim().equals("ALL")) {// 전체검색
				dao.userSelectAll(dt);
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else {
				if (jtf.getText().trim().equals("")) {
					AdminJDailogGUI.messageBox(this, "검색단어를 입력해주세요!");
					jtf.requestFocus();
				} else {// 검색어를 입력했을경우
					dao.getUserSearch(dt, comboName.get(fieldName), jtf.getText());
					if (dt.getRowCount() > 0)
						jt.setRowSelectionInterval(0, 0);
				}
			}
		}

	}// actionPerformed()----------

}