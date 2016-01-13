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
                    document.getElementById('loginMessage').innerHTML = 'Логін або пароль введені невірно';
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
            authRequest.open('POST', 'login?login=' + login + '&password=' + password, true);
            authRequest.send();
        }
    </script>
</head>

<body id="bd">

<div id="wrapper" class="login-form">
    <div class="header">
        <h1>Введіть логін користувача і пароль </h1>
        <h3 style="color: darkred" id="loginMessage"></h3>
    </div>

    <table class="logTable">
        <tr>
            <td>Логін</td>
            <td>
                <input id="login" type="text" class="username" placeholder="Логін" value="mer"/>
            </td>
        </tr>
        <tr>
            <td>Пароль</td>
            <td>
                <input id="password" type="password" class="password" placeholder="Пароль" value="a44n73"/>
            </td>
        </tr>
    </table>
    <input type="button" class="button" id="authbut" onclick="auth()" value="УВІЙТИ"
           style="font:inherit;color: darkblue; width: 200px; height: 30px ">
</div>

</body>
</html>