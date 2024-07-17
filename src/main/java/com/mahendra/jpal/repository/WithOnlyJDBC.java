package com.mahendra.jpal.repository;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;

@Component
public class WithOnlyJDBC {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/School";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public void doTransactions(){

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Create a new record
//            String insertQuery = "INSERT INTO teacher (teacher_id, teacher_name) VALUES (?, ?)";
//            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
//            pstmt.setLong(1, 1234);
//            pstmt.setString(2, "johndoe@example.com");
//            int rowsAffected = pstmt.executeUpdate();
//            System.out.println("Inserted " + rowsAffected + " rows.");

            // Retrieve a record

//            String selectQuery = "SELECT * FROM teacher WHERE teacher_id = ?";
//            PreparedStatement pstmt = conn.prepareStatement(selectQuery);
//            pstmt = conn.prepareStatement(selectQuery);
//            pstmt.setLong(1, 1234);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("teacher_id");
//                String name = rs.getString("teacher_name");
//
//                System.out.println(id + ": " + name );
//            }

//            // Update a record
//            String updateQuery = "UPDATE users SET email = ? WHERE id = ?";
//            pstmt = conn.prepareStatement(updateQuery);
//            pstmt.setString(1, "newemail@example.com");
//            pstmt.setInt(2, 1);
//            rowsAffected = pstmt.executeUpdate();
//            System.out.println("Updated " + rowsAffected + " rows.");
//
//            // Delete a record
//            String deleteQuery = "DELETE FROM users WHERE id = ?";
//            pstmt = conn.prepareStatement(deleteQuery);
//            pstmt.setInt(1, 1);
//            rowsAffected = pstmt.executeUpdate();
//            System.out.println("Deleted " + rowsAffected + " rows.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void test(){
        doTransactions();
    }

}
