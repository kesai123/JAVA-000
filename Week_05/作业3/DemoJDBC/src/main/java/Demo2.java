import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Demo2 {
    private static String url = "jdbc:mysql://127.0.0.1:3306/dtz_test?serverTimezone=Asia/Shanghai";
    private static String user = "root";
    private static String password = "root";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) {
        Connection conn = null;
        try{
            conn = getConnection();
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            upgrade(conn);
        } catch (Exception e){
            e.printStackTrace();
            try {
                conn.rollback();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        } finally {
            try{
                conn.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("connect success");
        return connection;
    }

    static void upgrade(Connection conn)  throws SQLException {
        conn.setAutoCommit(false);
        int old_grade = 1;
        int new_grade = 2;

        String sql1 = "insert into gradeclass(grade,class,location,teacher_id) values(?,?,?,?)";
        PreparedStatement pstmt1 = conn.prepareStatement(sql1);
        pstmt1.setInt(1,new_grade);
        pstmt1.setInt(2,1);
        pstmt1.setString(3,"building A");
        pstmt1.setInt(4,1);
        int i = pstmt1.executeUpdate();
        System.out.println("i="+i);
        pstmt1.close();

        String sql2 = "update student set grade = ? where grade = ?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setInt(1,new_grade);
        pstmt2.setInt(2,old_grade);
        int j = pstmt2.executeUpdate();
        System.out.println("j="+j);
        pstmt2.close();
        conn.commit();
    }
}
