<%--
  Created by IntelliJ IDEA.
  User: HP-PC
  Date: 2016-02-20
  Time: 23:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>О нас</title>
    <style>
    .gallery img{
    margin:5px; /* внешние отступы картинок */
    border:3px solid #fff; /* рамка картинок */
    }
    a.photo:hover img{
    border:3px solid #034F80; /* изменение цвета рамки при наведении на картинку */
    }
    </style>
</head>
<body>
<p>На рынке с 2012 года.</p>
<div class="gallery" id="images">
    <img src="images/Лицензия1.jpg" alt="Идет загрузка изображения...">
    <img src="images/Лицензия2.jpg" alt="Идет загрузка изображения...">
</div>
</body>
</html>
