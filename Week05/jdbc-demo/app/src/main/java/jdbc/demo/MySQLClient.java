package jdbc.demo;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kaizen
 * @date 2021/09/06
 */
public class MySQLClient {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private HikariDataSource dataSource;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private Connection getConnectionByHikari() throws SQLException {
        if (dataSource == null) {
            dataSource = getHikariDataSource();
        }
        return dataSource.getConnection();
    }

    private HikariDataSource getHikariDataSource() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    public Long insert(String sql) throws SQLException {
        long id = 0L;
        try (Connection conn = getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql, Statement.RETURN_GENERATED_KEYS);
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    while (generatedKeys.next()) {
                        id = generatedKeys.getLong("id");
                    }
                }
            }
        }
        return id;
    }

    public void update(String sql) throws SQLException {
        try (Connection conn = getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
            }
        }
    }

    public void delete(String sql) throws SQLException {
        try (Connection conn = getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        }
    }

    public List<Student> query(String sql) throws SQLException {
        List<Student> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet res = stmt.executeQuery(sql)) {
                    while (res.next()) {
                        Student student = new Student();
                        student.setId(res.getLong("id"));
                        student.setName(res.getString("name"));
                        student.setGender(res.getInt("gender"));
                        student.setGrade(res.getInt("grade"));
                        student.setScore(res.getInt("score"));
                        list.add(student);
                    }
                }
            }
        }
        return list;
    }

    private void bindArgs(PreparedStatement ps, String... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
    }

    public Long insertByPrepareStatement(String sql, String... args) throws SQLException {
        long id = 0L;
        try (Connection conn = getConnectionByHikari()) {
            try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                bindArgs(ps, args);
                ps.execute();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    while (rs.next()) {
                        id = rs.getLong(1);
                    }
                }
            }
        }
        return id;
    }

    public void updateByPrepareStatement(String sql, String... args) throws SQLException {
        try (Connection conn = getConnectionByHikari()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                bindArgs(ps, args);
                ps.executeUpdate();
            }
        }
    }

    public void deleteByPrepareStatement(String sql, String... args) throws SQLException {
        try (Connection conn = getConnectionByHikari()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                bindArgs(ps, args);
                ps.execute();
            }
        }
    }

    public List<Student> selectByPrepareStatement(String sql, String... args) throws SQLException {
        List<Student> list = new ArrayList<>();
        try (Connection conn = getConnectionByHikari()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                bindArgs(ps, args);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Student student = new Student();
                        student.setId(rs.getLong("id"));
                        student.setName(rs.getString("name"));
                        student.setGender(rs.getInt("gender"));
                        student.setGrade(rs.getInt("grade"));
                        student.setScore(rs.getInt("score"));
                        list.add(student);
                    }
                }
            }
        }
        return list;
    }

    public void printData() throws SQLException {
        String selectSql = "SELECT id, name, gender, grade, score FROM students";
        List<Student> students = this.query(selectSql);
        students.forEach(s -> System.out.println(s.toString()));
    }

    public void printDataByPs(String id) throws SQLException {
        String sql = "SELECT id, name, gender, grade, score FROM students WHERE id = ?";
        List<Student> students = selectByPrepareStatement(sql, id);
        students.forEach(s -> System.out.println(s.toString()));
    }

    public void testPreparedStatement() throws SQLException {
        printDataByPs("0");
        String sql = "INSERT INTO students (name, gender, grade, score) VALUES (?,?,?,?)";
        Long id = insertByPrepareStatement(sql, "小明", "1", "1", "88");
        System.out.println("INSERT");
        printDataByPs(id + "");
        sql = "UPDATE students SET name = ? WHERE id = ?";
        updateByPrepareStatement(sql, "李四", id + "");
        System.out.println("UPDATE");
        printDataByPs(id + "");
        sql = "DELETE FROM students WHERE id = ?";
        deleteByPrepareStatement(sql, id + "");
        printDataByPs(id + "");
    }

    public void testStatement() throws SQLException {
        printData();
        String sql = "INSERT INTO students (name, gender, grade, score) VALUES ('小明', 1, 1, 88)";
        Long id = insert(sql);
        System.out.println("INSERT");
        printData();
        sql = "UPDATE students SET name = '李四' WHERE id = " + id;
        update(sql);
        System.out.println("UPDATE");
        printData();
        sql = "DELETE FROM students WHERE id = " + id;
        delete(sql);
        System.out.println("DELETE");
        printData();
    }

    public static void main(String[] args) throws SQLException {
        MySQLClient client = new MySQLClient();
//        client.testStatement();
        client.testPreparedStatement();
    }
}
