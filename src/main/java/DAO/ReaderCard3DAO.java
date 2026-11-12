package DAO;

import java.sql.SQLException;
import java.sql.Statement;

public class ReaderCard3DAO extends DAO3{
    public ReaderCard3DAO(){
        super();
    }
    public boolean registerCard(String name, String dob, String cccd, String phone){
        if (name == null || name.isBlank()
                || dob == null || dob.isBlank()
                || cccd == null || cccd.isBlank()
                || phone == null || phone.isBlank()) {
            return false;
        }
        String insertCard = "INSERT INTO tblReaderCard3 (memberID) VALUES ('" + cccd.trim() + "')";
        try (Statement statement = con.createStatement()) {
            statement.executeUpdate(insertCard);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
