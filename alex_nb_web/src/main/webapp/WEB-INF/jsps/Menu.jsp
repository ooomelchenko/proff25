<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Головне меню</title>
    <script src="js/jquery-1.11.1.js"></script>
    <script>
        $(document).ready(function () {
            $('#assButt').click(function(){
                window.open("assets")
            });
            $('#crdButt').click(function(){
                window.open("credits")
            });
        })
    </script>
    <%-- <link href="css/nbStyle1.css" rel="stylesheet" type="text/css"> --%>
    <style type="text/css">
        button:hover{
            cursor: pointer;
        }
        #reportsButton{
            background-color: darkgreen;
            color: white;
            border-radius: 100%;
            width: 80px ;
            height: 60px;
            font-size: 25px
        }
        #reportsButton:hover{
            background-color: lightgreen;
            color: black;
            width: 100px ;
            height: 75px;
            font-weight: bold;
            font-size: 30px
        }
    </style>
</head>

<body id="bd" style="background-color: mintcream">

<h2>Вітаємо, ${userId} !</h2>
<h1 align="center"> ГОЛОВНЕ МЕНЮ </h1>

<div class="menu-button" align="center">
    <button style="width: 150px ; height: 50px; font-size: 30px" id="lotBut" onclick="location.href = 'lotMenu'">Лоти</button>
    <button style="width: 150px ; height: 50px; font-size: 30px" id="exBut" onclick="location.href = 'exMenu'">Біржі</button>
    <button style="width: 150px ; height: 50px; font-size: 30px" id="bidBut" onclick="location.href = 'bidMenu'">Аукціони</button>
    <button style="width: 150px ; height: 50px; font-size: 30px" id="assButt" >Об'єкти</button>
    <button style="width: 150px ; height: 50px; font-size: 30px" id="crdButt" >Кредити</button>
</div>

<div align="center">
    <br>
    <br>
    <button id="reportsButton" onclick="location.href = 'reports'" title="Перейти до завантаження звітів щодо проведених аукціонів">Звіти</button>
</div>

</body>
</html>