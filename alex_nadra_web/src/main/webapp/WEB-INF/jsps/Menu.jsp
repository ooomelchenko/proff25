<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>menu</title>
  <script src="js/jquery-1.11.1.js"></script>
  <script src="js/nLotParse.js"></script>
 <%-- <link href="css/nbStyle1.css" rel="stylesheet" type="text/css"> --%>
</head>

<body id ="bd">

<h2>Приветствуем ${userID}</h2>
<h3>Выберите необходимое действие</h3>

<button id="showLots" class="button">Показать все лоты</button>
<button id="hideLots" class="button">Скрыть список</button>
<button id="changeLots" class="button">Изменить лот</button>
<button id="createLot" class="button" onclick="location.href ='/createLot'">Перейти к созданию лотов ></button>

<div id="lotList" class="view"></div>

</body>

</html>