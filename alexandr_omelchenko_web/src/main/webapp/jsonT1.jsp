<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
  <script src="JS/jquery-1.11.1.js"></script>
  <script src="JS/Json.js"></script>
</head>
<body id="bd">

<div id="wrapper" class="login-form">
  <table name="login-form" class="login-form">
    <div class="header">
      <h1>Login</h1>
                <span>
                    <font id="info"></font>
                </span>
    </div>

    <div class="content">
      <input id="login" type="text" class="input username" placeholder="Username" />
      <input id="password" type="password" class="input password" placeholder="Password" />
    </div>
    <div class="footer">
      <button class="button" id="button">OK</button>
    </div>

  </table>
</div>

</body>
</html>