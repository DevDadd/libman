<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder, java.nio.charset.StandardCharsets, java.time.LocalDate, java.time.format.DateTimeFormatter, DAO.LoanSlip3DAO, model.Member3, model.LoanSlip3, model.Reader3" %>
<%!
  private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private static String attr(String s) {
    if (s == null) return "";
    return s.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;");
  }

  private static String displayDate(String s) {
    if (s == null || s.isBlank()) return "";
    try {
      return LocalDate.parse(s.trim()).format(DISPLAY_DATE);
    } catch (RuntimeException e) {
      return s;
    }
  }

  private static String displayStatus(String status) {
    if (status == null) return "";
    switch (status.toUpperCase()) {
      case "RETURNED": return "Đã trả";
      case "BORROWING": return "Chưa trả";
      case "OVERDUE": return "Quá hạn";
      default: return status;
    }
  }

  private static String enc(String s) {
    return URLEncoder.encode(s == null ? "" : s.trim(), StandardCharsets.UTF_8);
  }
%>
<%
  request.setCharacterEncoding("UTF-8");

  Member3 member = (Member3) session.getAttribute("member");
  if (member == null) {
    response.sendRedirect(request.getContextPath() + "/LoginPage.jsp");
    return;
  }

  String documentId = request.getParameter("documentId");
  String startDate = request.getParameter("startDate");
  String endDate = request.getParameter("endDate");

  LoanSlip3 loanSlip = null;
  try {
    loanSlip = new LoanSlip3DAO().getLoanSlipDetail(
        request.getParameter("customerName"),
        Integer.parseInt(request.getParameter("loanSlipId").trim()));
  } catch (NullPointerException | NumberFormatException e) {
  }

  String backUrl = "ViewBorrowedStatisticByLoanReport.jsp?documentId=" + enc(documentId)
      + "&startDate=" + enc(startDate) + "&endDate=" + enc(endDate);
%>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Danh sách phiếu mượn</title>
    <link rel="stylesheet" href="style.css" />
  </head>
  <body>
    <main class="register-page">
      <section class="register-card report-card">
        <h2>Danh sách phiếu mượn</h2>

        <div class="table-wrapper">
          <table class="report-table">
            <thead>
              <tr>
                <th>TT</th>
                <th>Người mượn</th>
                <th>Nhân viên</th>
                <th>Ngày lập</th>
                <th>Ngày trả tài liệu</th>
                <th>Trạng thái</th>
              </tr>
            </thead>
            <tbody>
              <% if (loanSlip == null) { %>
                <tr>
                  <td class="empty-row" colspan="6">Không tìm thấy phiếu mượn</td>
                </tr>
              <% } else {
                   Reader3 reader = loanSlip.getReader();
              %>
                <tr>
                  <td class="number-cell">1</td>
                  <td><%= attr(reader != null ? reader.getName() : null) %></td>
                  <td></td>
                  <td class="number-cell"><%= attr(displayDate(loanSlip.getBorrowDate())) %></td>
                  <td class="number-cell"><%= attr(displayDate(loanSlip.getReturnDate())) %></td>
                  <td><%= attr(displayStatus(loanSlip.getStatus())) %></td>
                </tr>
              <% } %>
            </tbody>
          </table>
        </div>

        <p class="report-back"><a class="table-link" href="<%= attr(backUrl) %>">&larr; Quay lại danh sách lượt mượn</a></p>
      </section>
    </main>
  </body>
</html>
