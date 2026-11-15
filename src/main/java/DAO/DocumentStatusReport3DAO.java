package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import model.Document3;
import model.DocumentStatusReport3;
import model.LoanSlip3;
import model.LoanSlipItem3;

public class DocumentStatusReport3DAO extends DAO3 {
    private static final String RETURNED = "RETURNED";
    private static final String REPORT_SQL = "SELECT d.id, d.name, d.yearProduce, d.author, d.status, d.quantity, "
            + "       SUM(li.quantity) AS totalBorrowed, "
            + "       SUM(CASE WHEN li.status = ? THEN li.quantity ELSE 0 END) AS totalReturned, "
            + "       SUM(CASE WHEN li.status = ? THEN 0 ELSE li.quantity END) AS currentBorrowed "
            + "FROM tblLoanSlipItem3 li "
            + "JOIN tblLoanSlip3 ls ON ls.id = COALESCE(li.loanSlipID, li.tblLoanSlip3id) "
            + "JOIN tblDocument3 d ON d.id = COALESCE(li.DocumentID, li.tblDocument3id) "
            + "WHERE ls.borrowDate BETWEEN ? AND ? "
            + "GROUP BY d.id, d.name, d.yearProduce, d.author, d.status, d.quantity "
            + "ORDER BY totalBorrowed DESC, d.name";

    public DocumentStatusReport3DAO() {
        super();
    }

    public List<DocumentStatusReport3> getDocumetStatusReport(String startDate, String endDate) {
        List<DocumentStatusReport3> reports = new ArrayList<>();
        LocalDate start;
        LocalDate end;
        try {
            start = LocalDate.parse(startDate == null ? "" : startDate.trim());
            end = LocalDate.parse(endDate == null ? "" : endDate.trim());
        } catch (DateTimeParseException e) {
            return reports;
        }
        if (start.isAfter(end)) {
            return reports;
        }

        try (PreparedStatement ps = con.prepareStatement(REPORT_SQL)) {
            ps.setString(1, RETURNED);
            ps.setString(2, RETURNED);
            ps.setString(3, start.toString());
            ps.setString(4, end.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Document3 document = new Document3(
                            rs.getString("name"),
                            null,
                            rs.getString("yearProduce"),
                            rs.getString("author"),
                            rs.getString("status"),
                            rs.getInt("quantity"),
                            rs.getInt("id"),
                            null);

                    LoanSlipItem3 item = new LoanSlipItem3();
                    item.setDocument(document);
                    item.setDocumentName(document.getName());

                    LoanSlip3 loanSlip = new LoanSlip3();
                    loanSlip.setDocumentName(document.getName());
                    loanSlip.setLoanItems(new LoanSlipItem3[] { item });

                    reports.add(new DocumentStatusReport3(
                            document.getStatus(),
                            rs.getInt("totalBorrowed"),
                            rs.getInt("totalReturned"),
                            rs.getInt("currentBorrowed"),
                            loanSlip));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Không thể tải báo cáo thống kê tài liệu", e);
        }
        return reports;
    }
}
