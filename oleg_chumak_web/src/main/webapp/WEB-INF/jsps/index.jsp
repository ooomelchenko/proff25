<%--
  страница с формой аутентификации
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>
<form action="/request.html" method="get">
  <table>
    <tr>
      <td>Login</td>
      <td><input type="text" name="login"></td>
    </tr>
    <tr>
      <td>Password</td>
      <td><input type="text" name="password"></td>
    </tr>
    <tr>
      <td>
        <input type="submit" value="Sign in">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
