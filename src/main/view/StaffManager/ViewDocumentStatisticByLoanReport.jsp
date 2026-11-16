<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder, java.nio.charset.StandardCharsets, java.util.List,DAO.DocumentStatusReport3DAO, model.Member3, model.DocumentStatusReport3, model.Document3, model.LoanSlip3, model.LoanSlipItem3" %>
<%!
  private static String attr(String s) {
    if (s == null) return "";
    return s.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;");
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

  boolean filtered = startDate != null && !startDate.isBlank()
      && endDate != null && !endDate.isBlank();
  boolean invalidRange = filtered && startDate.trim().compareTo(endDate.trim()) > 0;
  List<DocumentStatusReport3> reports = (filtered && !invalidRange)
      ? new DocumentStatusReport3DAO().getDocumetStatusReport(startDate, endDate)
      : null;
%>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Thống kê tài liệu theo lượt mượn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" />
  </head>
  <body>
    <main class="register-page">
      <section class="register-card report-card">
        <h2>Thống kê tài liệu theo lượt mượn</h2>

        <form class="form-column" method="get">
          <div class="form-row">
            <label for="startDate">Ngày bắt đầu</label>
            <input id="startDate" type="date" name="startDate" value="<%= attr(startDate) %>" required />
          </div>
          <div class="form-row">
            <label for="endDate">Ngày kết thúc</label>
            <input id="endDate" type="date" name="endDate" value="<%= attr(endDate) %>" required />
          </div>
          <button type="submit" class="filter-button">Lọc</button>
        </form>
        <% if (invalidRange) { %>
          <p class="form-error">Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.</p>
        <% } %>

        <div class="table-wrapper">
          <table class="report-table">
            <thead>
              <tr>
                <th>TT</th>
                <th>Tên</th>
                <th>NXB</th>
                <th>Tổng số lần mượn</th>
                <th>Tổng số lần trả</th>
                <th>Đang mượn</th>
              </tr>
            </thead>
            <tbody>
              <% if (reports == null || reports.isEmpty()) { %>
                <tr>
                  <td class="empty-row" colspan="6">
                    <%= reports == null ? "Chọn khoảng thời gian rồi ấn Lọc" : "Không có tài liệu nào được mượn trong khoảng thời gian này" %>
                  </td>
                </tr>
              <% } else {
                   int index = 1;
                   for (DocumentStatusReport3 report : reports) {
                     LoanSlip3 loanSlip = report.getLoanSlip();
                     LoanSlipItem3[] items = loanSlip == null ? null : loanSlip.getLoanItems();
                     Document3 document = (items == null || items.length == 0) ? null : items[0].getDocument();
                     String name = document != null ? document.getName()
                         : (loanSlip != null ? loanSlip.getDocumentName() : null);
                     String producer = document != null ? document.getProducer() : null;

                     String detailUrl = "ViewBorrowedStatisticByLoanReport.jsp"
                         + "?documentId=" + (document != null && document.getId() != null ? document.getId() : "")
                         + "&startDate=" + URLEncoder.encode(startDate.trim(), StandardCharsets.UTF_8)
                         + "&endDate=" + URLEncoder.encode(endDate.trim(), StandardCharsets.UTF_8);
              %>
                <tr>
                  <td class="number-cell"><%= index++ %></td>
                  <td><a class="table-link" href="<%= attr(detailUrl) %>"><%= attr(name) %></a></td>
                  <td><%= attr(producer) %></td>
                  <td class="number-cell"><%= report.getTotalBorrowed() %></td>
                  <td class="number-cell"><%= report.getTotalReturned() %></td>
                  <td class="number-cell"><%= report.getCurrentBorrowed() %></td>
                </tr>
              <%   }
                 } %>
            </tbody>
          </table>
        </div>
      </section>
    </main>
  </body>
</html>
