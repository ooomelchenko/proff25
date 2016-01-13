<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>log_in</title>
  <script>
  var authRequest = new XMLHttpRequest();
  authRequest.onreadystatechange = function () {
  if (authRequest.readyState == 4
  && authRequest.status == 200) {
  if (authRequest.responseText == '0') {
    document.getElementById('loginMessage').innerHTML = 'Логин или пароль введены неверно';
  }
  else {
    window.location = '/';
  }
  }
  };
  function auth() {
    sendAuthRequest(document.getElementById('login').value, document.getElementById('password').value);
  }
  function sendAuthRequest(login, password) {
  authRequest.open('POST', 'login?login='+login + '&password=' + password,true);
  authRequest.send();
  }
  </script>
</head>

<body id="bd">

<div id="wrapper" class="login-form">
    <div class="header">
      <h1>Введите имя пользователя и пароль </h1>
    </div>
    <div class="content">
      <p id="loginMessage"></p>
      <input id="login" type="text" class="username" placeholder="Логин" />
      <input id="password" type="password" class="password" placeholder="Пароль" />
    </div>
</div>
<button class="button" id="button" onclick="auth()">OK</button>
</body>
</html>