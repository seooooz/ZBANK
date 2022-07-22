import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class AdminDAO {
   
    /**
     * �ʿ��� ��������
     * */
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "c##green";
	String password = "green1234";
	
	private Connection con;
	private Statement st;
	private PreparedStatement pstmt;
	private ResultSet rs;
    
    String account;
 
    /**
     * �ε� ������ ���� ������
     * */
    public AdminDAO() {
        try {
            // �ε�
            Class.forName(driver);
            // ����
            con = DriverManager
                    .getConnection(url, user, password);
 
        } catch (ClassNotFoundException e) {
            System.out.println(e + "=> �ε� fail");
        } catch (SQLException e) {
            System.out.println(e + "=> ���� fail");
        }
    }//������
 
    /**
     * DB�ݱ� ��� �޼ҵ�
     * */
    public void dbClose() {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (pstmt != null) pstmt.close();
        } catch (Exception e) {
            System.out.println(e + "=> dbClose fail");
        }
    }//dbClose() ---
 
    /**
     * �μ��� ���� ID�� �ش��ϴ� ���ڵ� �˻��Ͽ� �ߺ����� üũ�ϱ� ���ϰ��� true =��밡�� , false = �ߺ���
     * */
    public boolean getIdByCheck(String id) {
        boolean result = true;
 
        try {
            pstmt = con.prepareStatement("SELECT * FROM admin WHERE id=?");
            pstmt.setString(1, id.trim());
            rs = pstmt.executeQuery(); //����
            if (rs.next())
                result = false; //���ڵ尡 �����ϸ� false
 
        } catch (SQLException e) {
            System.out.println(e + "=>  getIdByCheck fail");
        } finally {
            dbClose();
        }
 
        return result;
 
    }//getIdByCheck()
    
 
    /**
     * userlist�� ��� ���ڵ� ��ȸ
     * */
    public void userSelectAll(DefaultTableModel t_model) {
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from admin order by adname");
 
            // DefaultTableModel�� �ִ� ���� ������ �����
            for (int i = 0; i < t_model.getRowCount();) {
                t_model.removeRow(0);
            }
 
            while (rs.next()) {
                Object data[] = { rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) };
                
//                account = rs.getString(3);
                
//                System.out.println(account);
 
                t_model.addRow(data); //DefaultTableModel�� ���ڵ� �߰�
            }
 
        } catch (SQLException e) {
            System.out.println(e + "=> userSelectAll fail");
        } finally {
            dbClose();
        }
    }//userSelectAll()
    
    /**
     * ID�� �ش��ϴ� ���ڵ� �����ϱ� �ϱ��� tshistory ����
     * */
    
    public int tsUpdate(String account) {
        int result = 0;
        String sql = "UPDATE tshistory SET sender='0', receiver='0' WHERE sender=? or receiver=?";
 
        try {
            pstmt = con.prepareStatement(sql);
            // ?�� ������� �� �ֱ�
            pstmt.setString(1, account);
            pstmt.setString(2, account);
 
            // �����ϱ�
            result = pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> tsUpdate fail");
        } finally {
            dbClose();
        }
 
        return result;
    }//tsUpdate()
    
    
    
 
    /**
     * ID�� �ش��ϴ� ���ڵ� �����ϱ�
     * */
    public int userDelete(String id) {
        int result = 0;
        try {
            pstmt = con.prepareStatement("delete from admin where adid = ? ");
            pstmt.setString(1, id.trim());
            result = pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> userDelete fail");
        }finally {
            dbClose();
        }
 
        return result;
    }//userDelete()
    
    
    public int tsDelete(String account) {
        int result = 0;
        try {
            pstmt = con.prepareStatement("delete from tshistory where sender = ? or receiver = ?");
            pstmt.setString(1, account);
            pstmt.setString(2, account);
            result = pstmt.executeUpdate();
            
            System.out.println(account);
 
        } catch (SQLException e) {
            System.out.println(e + "=> tsDelete fail");
        }finally {
            dbClose();
        }
 
        return result;
    }//signupDelete()
    
    public int userMemberDelete(String id) {
        int result = 0;
        try {
            pstmt = con.prepareStatement("delete from usermember where id = ? ");
            pstmt.setString(1, id.trim());
            result = pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> userDelete fail");
        }finally {
            dbClose();
        }
 
        return result;
    }//userMemberDelete()
    
    public int signupDelete(String id) {
        int result = 0;
        try {
            pstmt = con.prepareStatement("delete from signup where id = ? ");
            pstmt.setString(1, id.trim());
            result = pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> signupDelete fail");
        }finally {
            dbClose();
        }
 
        return result;
    }//signupDelete()
 
    /**
     * ID�� �ش��ϴ� ���ڵ� �����ϱ�
     * */
    public int userUpdate(AdminJDailogGUI user) {
        int result = 0;
        String sql = "UPDATE admin SET adtype=?, addate = sysdate WHERE adid=?";
 
        try {
            pstmt = con.prepareStatement(sql);
            // ?�� ������� �� �ֱ�
            pstmt.setString(1, user.adtype.getText());
            pstmt.setString(2, user.id.getText().trim());
 
            // �����ϱ�
            result = pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> userUpdate fail");
        } finally {
            dbClose();
        }
 
        return result;
    }//userUpdate()
    
    public int userMemberUpdate(AdminJDailogGUI user) {
        int result = 0;
        String sql = "UPDATE usermember SET utype=? WHERE id=?";
 
        try {
            pstmt = con.prepareStatement(sql);
            // ?�� ������� �� �ֱ�
            pstmt.setString(1, user.adtype.getText());
            pstmt.setString(2, user.id.getText().trim());
 
            // �����ϱ�
            result = pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> userMemberUpdate fail");
        } finally {
            dbClose();
        }
 
        return result;
    }//userUpdate()

    
    //admin���̺� adtsnumber ��ü, ä��� �Ҷ����� count�ؼ� update
    public int tsnumcount(AdminJDailogGUI user) {
        int result = 0;
        String sql = "UPDATE admin SET adtsnumber = (SELECT COUNT(tsnumber) FROM TSHISTORY WHERE SENDER =?) WHERE adid =?";
 
        try {
            pstmt = con.prepareStatement(sql);
            // ?�� ������� �� �ֱ�
            pstmt.setString(1, user.account.getText());
            pstmt.setString(2, user.id.getText().trim());
 
            // �����ϱ�
            result = pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> tsnumcountUpdate fail");
        } finally {
            dbClose();
        }
 
        return result;
    }//userUpdate()
 
 
    /**
     * �˻��ܾ �ش��ϴ� ���ڵ� �˻��ϱ� (like�����ڸ� ����Ͽ� _, %�� ����Ҷ��� PreparedStatemnet�ȵȴ�. �ݵ��
     * Statement��ü�� �̿���)
     * */
    public void getUserSearch(DefaultTableModel dt, String fieldName,
            String word) {
        String sql = "SELECT * FROM admin WHERE " + fieldName.trim()
                + " LIKE '%" + word.trim() + "%'";
 
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
 
            // DefaultTableModel�� �ִ� ���� ������ �����
            for (int i = 0; i < dt.getRowCount();) {
                dt.removeRow(0);
            }
 
            while (rs.next()) {
                Object data[] = { rs.getString(1), rs.getString(2),
                        rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6)  };
 
                dt.addRow(data);
            }
 
        } catch (SQLException e) {
            System.out.println(e + "=> getUserSearch fail");
        } finally {
            dbClose();
        }
 
    }//getUserSearch()
 
}// Ŭ������