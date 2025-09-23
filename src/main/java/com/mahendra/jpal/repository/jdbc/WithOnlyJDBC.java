package com.mahendra.jpal.repository.jdbc;

import com.mahendra.jpal.entity.Teacher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class WithOnlyJDBC {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/School";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public void doTransactions(){

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Create a new record
            String insertQuery = "INSERT INTO teacher (teacher_id, teacher_name) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setLong(1, 1234);
            pstmt.setString(2, "johndoe@example.com");
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " rows.");

            // Retrieve a record

            String selectQuery = "SELECT * FROM teacher WHERE teacher_id = ?";
             pstmt = conn.prepareStatement(selectQuery);
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setLong(1, 1234);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("teacher_id");
                String name = rs.getString("teacher_name");

                System.out.println(id + ": " + name );
            }

            // Update a record
            String updateQuery = "UPDATE teacher SET teacher_name = ? WHERE teacher_id = ?";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, "newemail@example.com");
            pstmt.setInt(2, 1);
            rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " rows.");

            // Delete a record
            String deleteQuery = "DELETE FROM teacher WHERE teacher_id = ?";
            pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, 1234);
            rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " rows.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Teacher> getAllTeachers(){

        List<Teacher> teacherList = null;

        String query = "select * from teacher";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Step 1: Load the JDBC driver (optional for newer Java versions)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish the connection
           //already established in try block

            // Step 3: Create a statement
            Statement statement = conn.createStatement();

            // Step 4: Execute the query
            ResultSet resultSet = statement.executeQuery(query);

            teacherList = new ArrayList<>();

            // Step 5: Process the result set
            while (resultSet.next()) {
                Teacher teacher = new Teacher();

                int teacherId = resultSet.getInt("teacher_id");
                String teacherName = resultSet.getString("teacher_name");

                teacher.setTeacherId(teacherId);
                teacher.setTeacherName(teacherName);

                teacherList.add(teacher);

                System.out.println("Teacher ID: " + teacherId + ", Teacher Name: " + teacherName);
            }

            // Step 6: Close the resources
            resultSet.close();
            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return teacherList;
    }

    @PostConstruct
    public void test(){
        doTransactions();
    }

}

/*
*
* JDBC only with JAVA
*
* 1. You need to establish connection and open the connection using Driver and close it manually
* 2. You need to write queries and add them in prepared statements and manually execute them
* 3. Then extract objects from resultSets by iteration
*
* All this you have to do manually which gives you complete control.
* More boiler plate code. Exhausting for all entity classes to implement*/
