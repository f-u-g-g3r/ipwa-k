package com.ipwa.kp.database;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

@Component
public class DatabaseInitializer {
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeDatabase() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement checkStatement = connection.prepareStatement("SELECT 1 FROM internship_coordinator WHERE email = ?");
            checkStatement.setString(1, "coordinatorEmail");
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO internship_coordinator (email, password) VALUES (?, ?)");
                insertStatement.setString(1, "coordinatorEmail");
                insertStatement.setString(2, passwordEncoder.encode("password"));
                insertStatement.executeUpdate();
                insertStatement.close();
            }

            PreparedStatement checkStatement2 = connection.prepareStatement("SELECT 1 FROM students WHERE email = ?");
            checkStatement2.setString(1, "userEmail");
            ResultSet resultSet2 = checkStatement2.executeQuery();
            if (!resultSet2.next()) {
                PreparedStatement insertStatement2 = connection.prepareStatement("INSERT INTO students (email, username, password) VALUES (?, ?, ?)");
                insertStatement2.setString(1, "userEmail");
                insertStatement2.setString(2, "userUsername");
                insertStatement2.setString(3, passwordEncoder.encode("password"));
                insertStatement2.executeUpdate();
                insertStatement2.close();
            }

            PreparedStatement checkStatement3 = connection.prepareStatement("SELECT 1 FROM teachers WHERE email = ?");
            checkStatement3.setString(1, "teacherEmail");
            ResultSet resultSet3 = checkStatement3.executeQuery();
            if (!resultSet3.next()) {
                PreparedStatement insertStatement3 = connection.prepareStatement("INSERT INTO teachers (email, username, password) VALUES (?, ?, ?)");
                insertStatement3.setString(1, "teacherEmail");
                insertStatement3.setString(2, "teacherUsername");
                insertStatement3.setString(3, passwordEncoder.encode("password"));
                insertStatement3.executeUpdate();
                insertStatement3.close();
            }

            PreparedStatement checkStatement4 = connection.prepareStatement("SELECT 1 FROM companies WHERE email = ?");
            checkStatement4.setString(1, "companyEmail");
            ResultSet resultSet4 = checkStatement4.executeQuery();
            if (!resultSet4.next()) {
                PreparedStatement insertStatement4 = connection.prepareStatement("INSERT INTO companies (email, username, password) VALUES (?, ?, ?)");
                insertStatement4.setString(1, "companyEmail");
                insertStatement4.setString(2, "companyUsername");
                insertStatement4.setString(3, passwordEncoder.encode("password"));
                insertStatement4.executeUpdate();
                insertStatement4.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
