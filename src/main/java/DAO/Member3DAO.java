package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Member3;

public class Member3DAO extends DAO3 {
    public Member3DAO() {
        super();
    }

    public Member3 checkLogin(String name, String password) {
        if (name == null || password == null || name.isBlank() || password.isEmpty()) {
            return null;
        }
        String sql = "SELECT * FROM tblMember3 WHERE name = ? AND password = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new Member3(
                        rs.getString("name"),
                        rs.getString("age"),
                        rs.getString("dob"),
                        rs.getString("idNumber"),
                        rs.getString("password"),
                        rs.getString("address"),
                        rs.getString("role"),
                        rs.getString("note"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not check login", e);
        }
    }
}
