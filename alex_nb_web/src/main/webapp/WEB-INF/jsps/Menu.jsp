<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Головне меню</title>
    <script src="js/jquery-1.11.1.js"></script>
    <script>
        $(document).ready(function () {
            $('#crdButt').click(function(){
                window.open("assets")
            });
        })
    </script>
    <%-- <link href="css/nbStyle1.css" rel="stylesheet" type="text/css"> --%>
</head>

<body id="bd" style="background-color: mintcream">

<h2>Вітаємо, ${userId} !</h2>
<h1 align="center"> ГОЛОВНЕ МЕНЮ </h1>

<div class="menu-button" align="center">
    <button style="width: 150px ; height: 50px; font-size: 30px" id="lotBut" onclick="location.href = 'lotMenu'">Лоти</button>
    <button style="width: 150px ; height: 50px; font-size: 30px" id="exBut" onclick="location.href = 'exMenu'">Біржі</button>
    <button style="width: 150px ; height: 50px; font-size: 30px" id="bidBut" onclick="location.href = 'bidMenu'">Аукціони</button>
    <button style="width: 150px ; height: 50px; font-size: 30px" id="crdButt" >Об'єкти</button>
</div>

<div align="center">
    <br>
    <br>
    <button style="background-color: deepskyblue; color: yellow; width: 200px ; height: 35px; font-size: 25px" id="bidButt" onclick="location.href = ''">Звіти</button>
</div>

</body>
</html>