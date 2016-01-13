<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>menu</title>
    <script src="js/jquery-1.11.1.js"></script>
    <script src="js/nLotParse.js"></script>
    <%-- <link href="css/nbStyle1.css" rel="stylesheet" type="text/css"> --%>
</head>

<body id ="bd" style="background-color: mintcream">

<h2>Вітаємо, ${userId} !</h2>
<h3>Виберіть необхідну дію</h3>

<button id="showLots" class="button" >Показати всі лоти / Приховати</button>
<button id="createLot" class="button" onclick="location.href ='/lotForm'">Перейти до створення лоту > </button>

<div id="lotList" class="view"></div>

</body>

</html>