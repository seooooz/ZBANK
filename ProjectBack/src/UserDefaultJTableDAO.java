import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class UserDefaultJTableDAO {
   
    /**
     * 필요한 변수선언
     * */
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "c##green";
	String password = "green1234";
	
    Connection con;
    Statement st;
    PreparedStatement ps;
    ResultSet rs;
    
    String account;
 
    /**
     * 로드 연결을 위한 생성자
     * */
    public UserDefaultJTableDAO() {
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
            if (ps != null) ps.close();
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
            ps = con.prepareStatement("SELECT * FROM admin WHERE id=?");
            ps.setString(1, id.trim());
            rs = ps.executeQuery(); //실행
            if (rs.next())
                result = false; //레코드가 존재하면 false
 
        } catch (SQLException e) {
            System.out.println(e + "=>  getIdByCheck fail");
        } finally {
            dbClose();
        }
 
        return result;
 
    }//getIdByCheck()
 
//    /**
//     * userlist 회원가입하는 기능 메소드
//     * */
//    public int userListInsert(UserJDailogGUI user) {
//        int result = 0;
//        try {
//            ps = con.prepareStatement("insert into admin values(?,?,?,?)");
//            ps.setString(1, user.id.getText());
//            ps.setString(2, user.name.getText());
//            ps.setInt(3, Integer.parseInt(user.account.getText()));
//            ps.setString(4, user.adtype.getText());
// 
//            result = ps.executeUpdate(); //실행 -> 저장
// 
//        } catch (SQLException e) {
//            System.out.println(e + "=> userListInsert fail");
//        } finally {
//            dbClose();
//        }
// 
//        return result;
// 
//    }//userListInsert()
 
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
                
                account = rs.getString(3);
 
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
    
    public int tsUpdate() {
        int result = 0;
        String sql = "UPDATE tshistory SET sender='0', receiver='0' WHERE sender=? or receiver=?";
 
        try {
            ps = con.prepareStatement(sql);
            // ?의 순서대로 값 넣기
            ps.setString(1, account);
            ps.setString(2, account);
 
            // 실행하기
            result = ps.executeUpdate();
 
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
            ps = con.prepareStatement("delete from admin where adid = ? ");
            ps.setString(1, id.trim());
            result = ps.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> userDelete fail");
        }finally {
            dbClose();
        }
 
        return result;
    }//userDelete()
    
    public int tsDelete() {
        int result = 0;
        try {
//            ps = con.prepareStatement("delete from tshistory where sender = ? or receiver = ?");
            ps = con.prepareStatement("delete from tshistory where sender = ?");
            ps.setString(1, account);
//            ps.setString(2, account);
            result = ps.executeUpdate();
 
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
            ps = con.prepareStatement("delete from usermember where id = ? ");
            ps.setString(1, id.trim());
            result = ps.executeUpdate();
 
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
            ps = con.prepareStatement("delete from signup where id = ? ");
            ps.setString(1, id.trim());
            result = ps.executeUpdate();
 
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
    public int userUpdate(UserJDailogGUI user) {
        int result = 0;
        String sql = "UPDATE admin SET adtype=?, addate = sysdate WHERE adid=?";
 
        try {
            ps = con.prepareStatement(sql);
            // ?의 순서대로 값 넣기
            ps.setString(1, user.adtype.getText());
            ps.setString(2, user.id.getText().trim());
 
            // 실행하기
            result = ps.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> userUpdate fail");
        } finally {
            dbClose();
        }
 
        return result;
    }//userUpdate()
    
    public int userMemberUpdate(UserJDailogGUI user) {
        int result = 0;
        String sql = "UPDATE usermember SET utype=? WHERE id=?";
 
        try {
            ps = con.prepareStatement(sql);
            // ?의 순서대로 값 넣기
            ps.setString(1, user.adtype.getText());
            ps.setString(2, user.id.getText().trim());
 
            // 실행하기
            result = ps.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e + "=> userMemberUpdate fail");
        } finally {
            dbClose();
        }
 
        return result;
    }//userUpdate()

    
    //admin테이블 adtsnumber 이체, 채우기 할때마다 count해서 update
    public int tsnumcount(UserJDailogGUI user) {
        int result = 0;
        String sql = "UPDATE admin SET adtsnumber = (SELECT COUNT(tsnumber) FROM TSHISTORY WHERE SENDER =?) WHERE adid =?";
 
        try {
            ps = con.prepareStatement(sql);
            // ?의 순서대로 값 넣기
            ps.setString(1, user.account.getText());
            ps.setString(2, user.id.getText().trim());
 
            // 실행하기
            result = ps.executeUpdate();
 
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