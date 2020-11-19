import java.sql.*;

public class Demo1 {
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
            select(conn);
            insert(conn);
            select(conn);
            update(conn);
            select(conn);
            delete(conn);
            select(conn);
        } catch (Exception e){
            e.printStackTrace();
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

    static void select(Connection conn) throws SQLException {
        String sql = "select * from student";
        PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        int col = rs.getMetaData().getColumnCount();
        System.out.println("============================");
        while (rs.next()) {
            for (int i = 1; i <= col; i++) {
                System.out.print(rs.getString(i) + "\t");
                if ((i == 2) && (rs.getString(i).length() < 8)) {
                    System.out.print("\t");
                }
            }
            System.out.println("");
        }
        System.out.println("============================");
        pstmt.close();
    }

    static void insert(Connection conn) throws SQLException {
        String sql = "insert into student(name,grade,class) values(?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,"LiSi");
        pstmt.setInt(2,2);
        pstmt.setInt(3,3);
        int i = pstmt.executeUpdate();
        System.out.println("i="+i);
        pstmt.close();
    }

    static void update(Connection conn) throws SQLException {
        String sql = "update student set grade = ? where name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,5);
        pstmt.setString(2,"LiSi");
        int i = pstmt.executeUpdate();
        System.out.println("i="+i);
        pstmt.close();
    }

    static void delete(Connection conn) throws SQLException {
        String sql = "delete from student where name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,"LiSi");
        int i = pstmt.executeUpdate();
        System.out.println("i="+i);
        pstmt.close();
    }
}
