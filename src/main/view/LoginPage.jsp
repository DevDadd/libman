<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="DAO.Member3DAO, model.Member3" %>
<%
  request.setCharacterEncoding("UTF-8");
  String error = null;
  String username = request.getParameter("username");

  if ("POST".equalsIgnoreCase(request.getMethod())) {
    Member3 member = new Member3DAO().checkLogin(username, request.getParameter("password"));
    if (member != null) {
      session.setAttribute("member", member);
      response.sendRedirect(request.getContextPath() + "/UserHome.jsp");
      return;
    }
    error = "Sai tài khoản hoặc mật khẩu";
  }
%>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Trang đăng nhập</title>
    <link rel="stylesheet" href="style.css" />
  </head>
  <body>
    <main class="register-page">
      <section class="register-card login-card">
        <h2>Trang đăng nhập</h2>
        <% if (error != null) { %>
          <p class="form-error"><%= error %></p>
        <% } %>
        <form class="form-column" method="post">
          <div class="form-row">
            <label for="username">TK:</label>
            <input id="username" type="text" name="username" autocomplete="username"
                   value="<%= username == null ? "" : username.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;") %>" required />
          </div>
          <div class="form-row">
            <label for="password">MK:</label>
            <input id="password" type="password" name="password" autocomplete="current-password" required />
          </div>
          <button type="submit">Đăng nhập</button>
        </form>
      </section>
    </main>
  </body>
</html>
