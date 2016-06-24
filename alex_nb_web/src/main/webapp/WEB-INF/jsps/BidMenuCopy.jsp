<%@ page import="nadrabank.domain.Exchange" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="js/jquery-1.11.1.js"></script>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.css"/>
    <%--<link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.min.css"/>--%>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.structure.css"/>
    <%--<link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.structure.min.css"/>--%>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.theme.css"/>
    <%--<link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.theme.min.css"/>--%>
    <script type="text/javascript" src="js/jquery-ui.js"></script>
    <%--<script type="text/javascript" src="js/jquery-ui.min.js"></script>--%>

    <script>
        $(document).ready(function () {
        $('#datepicker').datepicker({dateFormat: "yy-mm-dd", dayNamesMin: ["Пн","Вт","Ср","Чт","Пт","Сб","Нд"], monthNames: ["січень","лютий","березень", "квітень", "травень","червень",
            "липень","серпень","вересень","жовтень","листопад","грудень"] });

        var addTr = $('#addTr');
        $.ajax({
            url: 'bids',
            type: "GET",
            success: function (res) {
                if (res.length === 1) {
                    var inf = $('<p>НЕМАЄ ТОРГІВ</p>');
                    $('#bidDiv').append(inf);
                }
                else {
                    for (var i = 0; i < res.length; i++) {
                        var stroka = res[i].split('|');
                        if (i === 0) {
                            var tr = $('<tr class="trh"></tr>');
                            for (var j = 0; j < stroka.length; j++) {
                                tr.append($('<th align="center" style="background-color: #bdb76b">' + stroka[j] + '</th>'))
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
                        addTr.before(tr);
                    }
                }
            }
        });
        $('#addBid').click(function(){
            if($(this).val()==="0") {
                $('#addTr').show();
                $(this).text("Прийняти");
                $(this).val("1")
            }
            else{
                var status =$('#st').val();
                var exId = $('#ex').val();
                var bidDate = $('#datepicker').val();
                $.ajax({
                    url: "createBid",
                    type: "GET",
                    data: {status: status, exId: exId, bidDate: bidDate },
                    success(confirm){
                        if(confirm==='1'){
                            alert("Торги додано");
                            location.reload();}
                        else
                            alert("Торги не додано!!!");
                    }
                })
            }
        });
    })
    </script>
    <title>Торги</title>
</head>
<body style="background-color: mintcream">
<button onclick="location.href='/'">Назад до меню</button>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    List<Exchange> exchangeList = (List<Exchange>) request.getAttribute("exList");
    List<String> bidStatusList = (List<String>) request.getAttribute("bidStatusList");
%>
<H1 align="center">Торги</H1>

<div id="bidDiv" class="view">
    <table id="bidtbl" border="light" style="background-color: lightcyan">
        <tr hidden="hidden" id="addTr">
            <td></td>
            <td>
                <select id="st">
                    <% for (String status : bidStatusList) {

                        %>
                    <option value="<%=status%>">
                        <%
                            out.print(status);
                        %>
                    </option>
                    <%
                        }
                    %>
                </select>
            </td>
            <td>
                <select id="ex">
                    <% for (Exchange ex : exchangeList) {
                    %>
                    <option value="<%=ex.getId()%>">
                        <%
                            out.print(ex.getCompanyName() + " ЄДРПОУ: " + ex.getInn());
                        %>
                    </option>
                    <%
                        }
                    %>
                </select>
            </td>

            <td><input id="datepicker" align="center"></td>
        </tr>
        <tr style="background-color: mintcream">
            <td colspan="3"></td>
            <td align="center"> <button id="addBid" value="0">Додати</button> </td>
        </tr>

    </table>

</div>
</body>
</html>