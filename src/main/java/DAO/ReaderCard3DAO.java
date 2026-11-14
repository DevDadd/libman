package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReaderCard3DAO extends DAO3{
    private static final int CARD_VALID_YEARS = 3;

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
        String expiredDate = LocalDate.now().plusYears(CARD_VALID_YEARS).toString();

        String insertCard = "INSERT INTO tblReaderCard3 (expiredDate, memberID) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(insertCard)) {
            ps.setString(1, expiredDate);
            ps.setString(2, cccd.trim());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
