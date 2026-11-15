package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import model.Document3;
import model.LoanSlip3;
import model.LoanSlipItem3;
import model.Reader3;

public class LoanSlip3DAO extends DAO3 {
    private static final String LIST_SQL = "SELECT ls.id, ls.name, ls.documentName, ls.borrowDate, ls.returnDate, ls.status, "
            + "       li.id AS itemId, li.documentName AS itemDocumentName, li.returnDate AS itemReturnDate, "
            + "       li.status AS itemStatus, li.quantity AS itemQuantity, "
            + "       m.idNumber, m.name AS memberName, m.age, m.dob, m.address, m.role, m.note "
            + "FROM tblLoanSlip3 ls "
            + "JOIN tblLoanSlipItem3 li ON COALESCE(li.loanSlipID, li.tblLoanSlip3id) = ls.id "
            + "LEFT JOIN tblMember3 m ON m.idNumber = ls.memberID "
            + "WHERE COALESCE(li.DocumentID, li.tblDocument3id) = ? "
            + "  AND ls.borrowDate BETWEEN ? AND ? "
            + "ORDER BY ls.borrowDate, ls.id";

    private static final String DETAIL_SLIP_SQL = "SELECT ls.id, ls.name, ls.documentName, ls.borrowDate, ls.returnDate, ls.status, "
            + "       m.idNumber, m.name AS memberName, m.age, m.dob, m.address, m.role, m.note "
            + "FROM tblLoanSlip3 ls "
            + "LEFT JOIN tblMember3 m ON m.idNumber = ls.memberID "
            + "WHERE ls.id = ? "
            + "  AND (? IS NULL OR LOWER(TRIM(m.name)) = LOWER(?))";

    private static final String DETAIL_ITEMS_SQL = "SELECT li.id, li.documentName, li.returnDate, li.status, li.quantity, "
            + "       d.id AS documentId, d.name AS docName, d.yearProduce, d.author, "
            + "       d.status AS docStatus, d.quantity AS docQuantity "
            + "FROM tblLoanSlipItem3 li "
            + "LEFT JOIN tblDocument3 d ON d.id = COALESCE(li.DocumentID, li.tblDocument3id) "
            + "WHERE COALESCE(li.loanSlipID, li.tblLoanSlip3id) = ? "
            + "ORDER BY li.id";

    public LoanSlip3DAO() {
        super();
    }

    public List<LoanSlip3> getListLoanSlip(int documentId, String startDate, String endDate) {
        List<LoanSlip3> loanSlips = new ArrayList<>();
        LocalDate start;
        LocalDate end;
        try {
            start = LocalDate.parse(startDate == null ? "" : startDate.trim());
            end = LocalDate.parse(endDate == null ? "" : endDate.trim());
        } catch (DateTimeParseException e) {
            return loanSlips;
        }
        if (start.isAfter(end)) {
            return loanSlips;
        }

        try (PreparedStatement ps = con.prepareStatement(LIST_SQL)) {
            ps.setInt(1, documentId);
            ps.setString(2, start.toString());
            ps.setString(3, end.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reader3 reader = rs.getString("idNumber") == null ? null
                            : new Reader3(
                                    rs.getString("memberName"),
                                    rs.getString("age"),
                                    rs.getString("dob"),
                                    rs.getString("idNumber"),
                                    null,
                                    rs.getString("address"),
                                    rs.getString("role"),
                                    rs.getString("note"),
                                    null);

                    Document3 document = new Document3();
                    document.setId(documentId);
                    document.setName(rs.getString("itemDocumentName"));

                    LoanSlipItem3 item = new LoanSlipItem3(
                            rs.getString("itemDocumentName"),
                            rs.getString("itemReturnDate"),
                            rs.getString("itemStatus"),
                            rs.getInt("itemQuantity"),
                            rs.getInt("itemId"),
                            document);

                    loanSlips.add(new LoanSlip3(
                            rs.getString("name"),
                            rs.getString("documentName"),
                            rs.getString("borrowDate"),
                            rs.getString("returnDate"),
                            rs.getString("status"),
                            rs.getInt("id"),
                            reader,
                            new LoanSlipItem3[] { item }));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Không thể tải danh sách phiếu mượn của tài liệu " + documentId, e);
        }
        return loanSlips;
    }

    public LoanSlip3 getLoanSlipDetail(String customerName, int loanSlipID) {
        String name = (customerName == null || customerName.isBlank()) ? null : customerName.trim();
        LoanSlip3 loanSlip;

        try (PreparedStatement ps = con.prepareStatement(DETAIL_SLIP_SQL)) {
            ps.setInt(1, loanSlipID);
            ps.setString(2, name);
            ps.setString(3, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Reader3 reader = rs.getString("idNumber") == null ? null
                        : new Reader3(
                                rs.getString("memberName"),
                                rs.getString("age"),
                                rs.getString("dob"),
                                rs.getString("idNumber"),
                                null,
                                rs.getString("address"),
                                rs.getString("role"),
                                rs.getString("note"),
                                null);
                loanSlip = new LoanSlip3(
                        rs.getString("name"),
                        rs.getString("documentName"),
                        rs.getString("borrowDate"),
                        rs.getString("returnDate"),
                        rs.getString("status"),
                        rs.getInt("id"),
                        reader,
                        new LoanSlipItem3[0]);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Không thể tải phiếu mượn " + loanSlipID, e);
        }

        List<LoanSlipItem3> items = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(DETAIL_ITEMS_SQL)) {
            ps.setInt(1, loanSlipID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Document3 document = null;
                    int documentId = rs.getInt("documentId");
                    if (!rs.wasNull()) {
                        document = new Document3(
                                rs.getString("docName"),
                                null,
                                rs.getString("yearProduce"),
                                rs.getString("author"),
                                rs.getString("docStatus"),
                                rs.getInt("docQuantity"),
                                documentId,
                                null);
                    }
                    items.add(new LoanSlipItem3(
                            rs.getString("documentName"),
                            rs.getString("returnDate"),
                            rs.getString("status"),
                            rs.getInt("quantity"),
                            rs.getInt("id"),
                            document));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Không thể tải danh sách tài liệu của phiếu mượn " + loanSlipID, e);
        }
        loanSlip.setLoanItems(items.toArray(new LoanSlipItem3[0]));
        return loanSlip;
    }
}
