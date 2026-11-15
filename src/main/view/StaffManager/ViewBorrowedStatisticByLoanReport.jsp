<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder, java.nio.charset.StandardCharsets, java.time.LocalDate, java.time.format.DateTimeFormatter, java.util.List, DAO.LoanSlip3DAO, model.Member3, model.LoanSlip3, model.LoanSlipItem3, model.Reader3" %>
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

  String startDate = request.getParameter("startDate");
  String endDate = request.getParameter("endDate");
  Integer documentId = null;
  try {
    documentId = Integer.valueOf(request.getParameter("documentId").trim());
  } catch (RuntimeException e) {
  }

  List<LoanSlip3> loanSlips = documentId == null
      ? null
      : new LoanSlip3DAO().getListLoanSlip(documentId, startDate, endDate);

  String backUrl = "ViewDocumentStatisticByLoanReport.jsp?startDate=" + enc(startDate) + "&endDate=" + enc(endDate);
%>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Danh sách lượt mượn</title>
    <link rel="stylesheet" href="style.css" />
  </head>
  <body>
    <main class="register-page">
      <section class="register-card report-card">
        <h2>Danh sách lượt mượn</h2>

        <% if (documentId == null) { %>
          <p class="form-error">Không xác định được tài liệu. Hãy chọn tài liệu từ trang thống kê.</p>
        <% } %>

        <div class="table-wrapper">
          <table class="report-table">
            <thead>
              <tr>
                <th>TT</th>
                <th>Tên người mượn</th>
                <th>Mã người đọc</th>
                <th>Ngày mượn</th>
                <th>Ngày trả</th>
                <th>Trạng thái</th>
              </tr>
            </thead>
            <tbody>
              <% if (loanSlips == null || loanSlips.isEmpty()) { %>
                <tr>
                  <td class="empty-row" colspan="6">Không có lượt mượn nào trong khoảng thời gian này</td>
                </tr>
              <% } else {
                   int index = 1;
                   for (LoanSlip3 loanSlip : loanSlips) {
                     Reader3 reader = loanSlip.getReader();
                     LoanSlipItem3[] items = loanSlip.getLoanItems();
                     LoanSlipItem3 item = (items == null || items.length == 0) ? null : items[0];
                     String returnDate = item != null && item.getReturnDate() != null ? item.getReturnDate() : loanSlip.getReturnDate();
                     String status = item != null && item.getStatus() != null ? item.getStatus() : loanSlip.getStatus();
              %>
                <tr>
                  <td class="number-cell"><%= index++ %></td>
                  <td>
                    <% if (reader != null && reader.getName() != null) {
                         String slipUrl = "ViewLoanSlipStatisticReport.jsp?loanSlipId=" + loanSlip.getId()
                             + "&customerName=" + enc(reader.getName())
                             + "&documentId=" + documentId
                             + "&startDate=" + enc(startDate) + "&endDate=" + enc(endDate);
                    %>
                      <a class="table-link" href="<%= attr(slipUrl) %>"><%= attr(reader.getName()) %></a>
                    <% } %>
                  </td>
                  <td><%= attr(reader != null ? reader.getIdNumber() : null) %></td>
                  <td class="number-cell"><%= attr(displayDate(loanSlip.getBorrowDate())) %></td>
                  <td class="number-cell"><%= attr(displayDate(returnDate)) %></td>
                  <td><%= attr(displayStatus(status)) %></td>
                </tr>
              <%   }
                 } %>
            </tbody>
          </table>
        </div>

        <p class="report-back"><a class="table-link" href="<%= attr(backUrl) %>">&larr; Quay lại thống kê</a></p>
      </section>
    </main>
  </body>
</html>
