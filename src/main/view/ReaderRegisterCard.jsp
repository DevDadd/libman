<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="DAO.ReaderCard3DAO, model.Member3" %>
<%!
  // Escape a value for use inside an HTML attribute; null -> "".
  private static String attr(String s) {
    if (s == null) return "";
    return s.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;");
  }
%>
<%
  request.setCharacterEncoding("UTF-8");

  // Member3 was put in the session by LoginPage.jsp after a successful login.
  Member3 member = (Member3) session.getAttribute("member");
  if (member == null) {
    response.sendRedirect(request.getContextPath() + "/LoginPage.jsp");
    return;
  }

  if ("POST".equalsIgnoreCase(request.getMethod())) {
    boolean registered = new ReaderCard3DAO().registerCard(
        request.getParameter("name"),
        request.getParameter("dob"),
        request.getParameter("cccd"),
        request.getParameter("phone"));

    if (registered) {
      response.sendRedirect(request.getContextPath() + "/UserHome.jsp");
      return;
    }
  }
%>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Reader register card online</title>
    <link rel="stylesheet" href="style.css" />
  </head>
  <body>
    <main class="register-page">
      <section class="register-card">
        <h2>Reader register card online</h2>
        <form class="form-column" method="post">
          <div class="form-row">
            <label for="readername">Reader name</label>
            <input id="readername" type="text" name="name" value="<%= attr(member.getName()) %>" required />
          </div>
          <div class="form-row">
            <label for="dob">DOB</label>
            <input id="dob" type="text" name="dob" value="<%= attr(member.getDob()) %>" required />
          </div>
          <div class="form-row">
            <label for="cccd">CCCD</label>
            <input id="cccd" type="text" name="cccd" value="<%= attr(member.getIdNumber()) %>" required />
          </div>
          <div class="form-row">
            <label for="phone">Phone</label>
            <input id="phone" type="text" name="phone" required />
          </div>
          <button type="submit">Register</button>
        </form>
      </section>
    </main>
  </body>
</html>
