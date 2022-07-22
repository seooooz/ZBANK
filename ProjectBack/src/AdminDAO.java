import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class AdminDAO {
   
    /**
     * 필요한 변수선언
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
     * 로드 연결을 위한 생성자
     * */
    public AdminDAO() {
        try {
            // 로드
            Class.forName(driver);
            // 연결
            con = DriverManager
                    .getConnection(url, user, password);
 
        } catch (ClassNotFoundException e) {
            System.out.println(e + "=> 로드 fail");
        } catch (SQLException e) {
            System.out.println(e + "=> 연결 fail");
        }
    }//생성자
 
    /**
     * DB닫기 기능 메소드
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
     * 인수로 들어온 ID에 해당하는 레코드 검색하여 중복여부 체크하기 리턴값이 true =사용가능 , false = 중복임
     * */
    public boolean getIdByCheck(String id) {
        boolean result = true;
 
        try {
            pstmt = con.prepareStatement("SELECT * FROM admin WHERE id=?");
            pstmt.setString(1, id.trim());
            rs = pstmt.executeQuery(); //실행
            if (rs.next())
                result = false; //레코드가 존재하면 false
 
        } catch (SQLException e) {
            System.out.println(e + "=>  getIdByCheck fail");
        } finally {
            dbClose();
        }
 
        return result;
 
    }//getIdByCheck()
    
 
    /**
     * userlist의 모든 레코드 조회
     * */
    public void userSelectAll(DefaultTableModel t_model) {
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from admin order by adname");
 
            // DefaultTableModel에 있는 기존 데이터 지우기
            for (int i = 0; i < t_model.getRowCount();) {
                t_model.removeRow(0);
            }
 
            while (rs.next()) {
                Object data[] = { rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) };
                
//                account = rs.getString(3);
                
//                System.out.println(account);
 
                t_model.addRow(data); //DefaultTableModel에 레코드 추가
            }
 
        } catch (SQLException e) {
            System.out.println(e + "=> userSelectAll fail");
        } finally {
            dbClose();
        }
    }//userSelectAll()
    
    /**
     * ID에 해당하는 레코드 삭제하기 하기전 tshistory 변경
     * */
    
    public int tsUpdate(String account) {
        int result = 0;
        String sql = "UPDATE tshistory SET sender='0', receiver='0' WHERE sender=? or receiver=?";
 
        try {
            pstmt = con.prepareStatement(sql);
            // ?의 순서대로 값 넣기
            pstmt.setString(1, account);
            pstmt.setString(2, account);
 
            // 실행하기
            result = pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> tsUpdate fail");
        } finally {
            dbClose();
        }
 
        return result;
    }//tsUpdate()
    
    
    
 
    /**
     * ID에 해당하는 레코드 삭제하기
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
     * ID에 해당하는 레코드 수정하기
     * */
    public int userUpdate(AdminJDailogGUI user) {
        int result = 0;
        String sql = "UPDATE admin SET adtype=?, addate = sysdate WHERE adid=?";
 
        try {
            pstmt = con.prepareStatement(sql);
            // ?의 순서대로 값 넣기
            pstmt.setString(1, user.adtype.getText());
            pstmt.setString(2, user.id.getText().trim());
 
            // 실행하기
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
            // ?의 순서대로 값 넣기
            pstmt.setString(1, user.adtype.getText());
            pstmt.setString(2, user.id.getText().trim());
 
            // 실행하기
            result = pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> userMemberUpdate fail");
        } finally {
            dbClose();
        }
 
        return result;
    }//userUpdate()

    
    //admin테이블 adtsnumber 이체, 채우기 할때마다 count해서 update
    public int tsnumcount(AdminJDailogGUI user) {
        int result = 0;
        String sql = "UPDATE admin SET adtsnumber = (SELECT COUNT(tsnumber) FROM TSHISTORY WHERE SENDER =?) WHERE adid =?";
 
        try {
            pstmt = con.prepareStatement(sql);
            // ?의 순서대로 값 넣기
            pstmt.setString(1, user.account.getText());
            pstmt.setString(2, user.id.getText().trim());
 
            // 실행하기
            result = pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> tsnumcountUpdate fail");
        } finally {
            dbClose();
        }
 
        return result;
    }//userUpdate()
 
 
    /**
     * 검색단어에 해당하는 레코드 검색하기 (like연산자를 사용하여 _, %를 사용할때는 PreparedStatemnet안된다. 반드시
     * Statement객체를 이용함)
     * */
    public void getUserSearch(DefaultTableModel dt, String fieldName,
            String word) {
        String sql = "SELECT * FROM admin WHERE " + fieldName.trim()
                + " LIKE '%" + word.trim() + "%'";
 
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
 
            // DefaultTableModel에 있는 기존 데이터 지우기
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
 
}// 클래스끝