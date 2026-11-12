<%@ page import="DAO.ReaderCard3DAO" %>
<%
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
            <input id="readername" type="text" name="name" required />
          </div>
          <div class="form-row">
            <label for="dob">DOB</label>
            <input id="dob" type="text" name="dob" required />
          </div>
          <div class="form-row">
            <label for="cccd">CCCD</label>
            <input id="cccd" type="text" name="cccd" required />
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
