<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Member3" %>
<%
  Member3 member = (Member3) session.getAttribute("member");
  if (member == null) {
    response.sendRedirect(request.getContextPath() + "/LoginPage.jsp");
    return;
  }
%>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Trang chủ thư viện</title>
    <link rel="stylesheet" href="style.css" />
  </head>
  <body>
    <main class="home-page">
      <a class="reader-card-button" href="ReaderRegisterCard.jsp">Đăng ký thẻ bạn đọc trực tuyến</a>
    </main>
  </body>
</html>
