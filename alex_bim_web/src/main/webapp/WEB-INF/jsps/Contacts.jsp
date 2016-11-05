<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Контакты</title>
    <style>
        #map {
            width: 400px;
            height: 300px;
            background-color: #CCC;
        }
    </style>
    <script src="https://maps.googleapis.com/maps/api/js?callback=initMap"
            async defer>
    </script>
    <script>
        function initMap() {
            var mapDiv = document.getElementById('map');
            var map = new google.maps.Map(mapDiv, {
                center: {lat: 50.395327, lng: 30.650503},
                zoom: 8
            });
        }
    </script>
</head>
<body>

<div id="text">
    <H2 style="color: darkblue">Контакты:</H2>
    <H2 style="color: darkblue">Номер телефона</H2><H2>(097)582-20-08 Игорь Александрович</H2>
    <H2 style="color: darkblue">EMAIL</H2><H2>budmereja@gmail.com</H2>
</div>

<div id="map"></div>

</body>
</html>
