<%@ page import="nadrabank.domain.Exchange" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Біржі</title>
    <script src="js/jquery-1.11.1.js"></script>
    <script>
        $(document).ready(function () {
            $(".idEx").each(function(){
                var idEx=$(this);
                $.ajax({
                    url: "countSumLotsByExchange",
                    type: "POST",
                    data: {exId: idEx.text()},
                    success: function (count_sum) {
                        idEx.parent().find('.countLots').text(count_sum[0]);
                        idEx.parent().find('.rv').text(count_sum[1]);
                    }
                });
                $.ajax({
                    url: "countBidsByExchange",
                    type: "GET",
                    data: {exId: idEx.text()},
                    success: function (countBids) {
                        idEx.parent().find('.countBids').text(countBids);
                    }
                });

            });
            /*$('#extbl').empty();
            $.ajax({
                url: 'exchanges',
                type: "GET",
                success: function (res) {
                    if (res.length === 1) {
                        var inf = $('<p>НЕМАЄ ЖОДНОЇ БІРЖІ</p>');
                        $('#exDiv').append(inf);
                    }
                    else {
                        var tab = $('#extbl');
                        for (var i = 0; i < res.length; i++) {
                            var stroka = res[i].split('|');

                            if (i === 0) {
                                var tr = $('<tr class="trh" ></tr>');
                                for (var j = 0; j < stroka.length; j++) {
                                    tr.append($('<th align="center" bgcolor="#00ff7f">' + stroka[j] + '</th>'))
                                }
                            }
                            else{
                                var tr = $('<tr class="tr"></tr>');
                                for (var k = 0; k < stroka.length; k++) {
                                    tr.append($('<td align="center">' + stroka[k] + '</td>'));
                                }
                            }
                            tr.mouseover( function(){
                                $(this).css({'background-color': "#00ffff"})
                            });
                            tr.mouseout( function(){
                                $(this).css({'background-color': "lightcyan"})
                            });
                            tab.append(tr);
                        }
                    }
                    var butt = $('<button class="buttonRedactor" style="height: 100%; width: 100%">Лоти</button>');
                    butt.on('click', function () {
                        var idEx = $(this).parent().children().first().text();
                        $.ajax({
                            url: "setRex",
                            type: "GET",
                            data: {exId: idEx},
                            success: function () {
                                window.open('exLots');
                            }
                        });
                    });
                    $('tr.tr').append(butt);
                }
            });*/
            var butt = $('<button class="buttonRedactor" style="height: 100%; width: 100%">Лоти</button>');
            butt.click(function () {
                var idEx = $(this).parent().find('.idEx');
                $.ajax({
                    url: "setRex",
                    type: "GET",
                    data: {exId: idEx.text()},
                    success: function () {
                        window.open('exLots');
                    }
                });
            });
            $('.exTr').append(butt);
        });
    </script>
</head>
<%
    List<Exchange> exchangeList = (List<Exchange>) request.getAttribute("exchangesList");
%>

<body style="background-color: mintcream">
<button onclick="location.href='index'">Назад до меню</button>
<H1 align="center">Меню біржі</H1>

<div id="exDiv" class="view">
    <table id="extbl" border="light" style="background-color: lightcyan">
        <tr align="center" bgcolor="#00ff7f">
            <th>Номер</th>
            <th>Назва</th>
            <th>Кількість торгів</th>
            <th>Кількість лотів</th>
            <th>Оціночна вартість</th>
        </tr>
        <%for (Exchange ex: exchangeList){%>
        <tr class="exTr">
            <td class="idEx"><%=ex.getId()%></td>
            <td><%=ex.getCompanyName()%></td>
            <td class="countBids" align="center"></td>
            <td class="countLots" align="center"></td>
            <td class="rv" align="left"></td>
        </tr>
        <%}%>
    </table>
</div>
</body>
</html>