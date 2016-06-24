<%@ page import="nadrabank.domain.Lot" %>
<%@ page import="java.util.List" %>
<%@ page import="nadrabank.domain.Exchange" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редагування лоту</title>
    <script src="js/jquery-1.11.1.js"></script>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.css"/>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.structure.css"/>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.theme.css"/>
    <script type="text/javascript" src="js/jquery-ui.js"></script>
    <script>
        <%
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Lot lot = (Lot) request.getAttribute("lott");
        %>
        $(document).ready(function () {
            var payTab= $('#paysTab');
            var dp = $('.datepicker');
            var lotID=$('#lotId');
            dp.datepicker({dateFormat: "yy-mm-dd", dayNamesMin: ["Пн","Вт","Ср","Чт","Пт","Сб","Нд"], monthNames: ["січень","лютий","березень", "квітень", "травень","червень",
                "липень","серпень","вересень","жовтень","листопад","грудень"] });
            function getCountSum() {
                payTab.empty();
                $.ajax({
                    url: "countSumByLot",
                    method: "POST",
                    data: {lotId: lotID.text() },
                    success(countSum){
                        $('#count').text(countSum[0]);
                        $('#sum').text(countSum[1]);
                    }
                });
                $.ajax({
                    url: "paymentsSum",
                    method: "POST",
                    data: {lotId: lotID.text() },
                    success(paymentsSum){
                        $('#paymentsSum').text(paymentsSum);
                    }
                });
                $.ajax({
                    url: "paymentsByLot",
                    method: "POST",
                    data: {lotId: lotID.text() },
                    success(payments){
                       var paysTab = $('#paysTab');
                        for(var i=0; i<payments.length; i++){
                            paysTab.append($('<tr>'+
                                '<td>'+ new Date(payments[i].date).toISOString().substring(0, 10) +'</td>'+
                                '<td>'+ payments[i].paySum +'</td>'+
                            +'</tr>'));
                        }
                    }
                });
            }

            getCountSum();

            $('#delLotButton').on('click', function () {
                if($(this).val()==='1'){
                    $('#delImg').hide();
                    $(this).val('0');
                    $('#lotTh').css('backgroundColor', "#00ffff");
                    $('#count').css('backgroundColor', "#f0ffff");
                    $('#sum').css('backgroundColor', "#f0ffff");
                }
                else {
                    $('#setSoldButton').val('0');
                    $('#soldImg').hide();
                    $('#delImg').show();
                    $(this).val('1');
                    $('#lotTh').css('backgroundColor', "#dc143c");
                    $('#count').css('backgroundColor', "#dc143c");
                    $('#sum').css('backgroundColor', "#dc143c");
                    alert('Увага! Лот буде розформовано після підтвердження!')
                }
            });
            $('#setSoldButton').click( function () {
                if($(this).val()==='1'){
                    $('#soldImg').hide();
                    $(this).val('0');
                    $('#lotTh').css('backgroundColor', "#00ffff");
                    $('#count').css('backgroundColor', "#f0ffff");
                    $('#sum').css('backgroundColor', "#f0ffff");
                }
                else {
                    if($('#ws').val()!="Угода укладена"){
                        alert("Змініть статус на 'Угода укладена!'");
                    }
                    else if($('#factPrice').val()==''){
                        alert('Введіть ціну фактичного продажу!');
                    }

                    else{
                        $('#delImg').hide();
                        $('#soldImg').show();
                        $('#delLotButton').val('0');
                        $(this).val('1');
                        $('#lotTh').css('backgroundColor', "lawngreen");
                        $('#count').css('backgroundColor', "lawngreen");
                        $('#sum').css('backgroundColor', "lawngreen");
                        alert('Увага! Лот буде відмчено як проданий після підтвердження!')
                    }
                }
            });
            $('#showCredits').click( function showCredits() {
                if ($('table').is($('#crdsTab'))) {
                    $('#crdsTab').remove();
                }
                else {
                    $.ajax({
                        url: "selectCRDbyLot",
                        type: "POST",
                        data: {
                            lotId: $('#lotId').text()
                        },
                        success: function (assetList) {
                            var tab = $('<table id="crdsTab" border="light" class="table"></table>');
                            var trHead = $('<tr bgcolor="#bdb76b">' +
                                    '<th>ID</th>'+
                                    '<th>Інвентарний №</th>'+
                                    '<th>Назва активу</th>'+
                                    '<th>Опис обєкту</th>'+
                                    '<th>Регіон</th>'+
                                    '<th>Балансова вартість</th>'+
                                    '<th>Оціночна вартість, грн.</th>'+
                                    '</tr>');

                            tab.append(trHead);
                            for(var i=0; i<assetList.length; i++) {
                                var trR = $('<tr align="center" class="tr">' +
                                        '<td class="idLot">' + assetList[i].id + '</td>' +
                                        '<td>' + assetList[i].inn + '</td>' +
                                        '<td>' + assetList[i].asset_name + '</td>' +
                                        '<td>' + assetList[i].asset_descr + '</td>' +
                                        '<td>' + assetList[i].region + '</td>' +
                                        '<td>' + assetList[i].zb + '</td>' +
                                        '<td>' + assetList[i].rv + '</td>' +
                                        '</tr>');
                                var factPriceTd = trR.find('.factPriceTd');

                                if(assetList[i].isSold){
                                    trR.css('background-color', "lightgreen");
                                }

                                else{
                                    var delCrButt = $('<button class="delCrdButt" value="0" title="Видалити обєкт" height="25px" width="25px">' +
                                            '<img height="22px" width="22px" src="css/images/red-del.png">' +
                                            '</button>').click(function () {
                                        if ($(this).val() === '0') {
                                            $(this).val('1');
                                            $(this).parent().css('background-color', "lightcoral");
                                            $(this).parent().attr('title', "Об'єкт буде видалено зі списку!")
                                        }
                                        else {
                                            $(this).val('0');
                                            $(this).parent().css('background-color', 'lightcyan');
                                            $(this).parent().attr('title', "")
                                        }
                                    });
                                    trR.append(delCrButt);
                                }

                                tab.append(trR);
                            }
                            $('#crdDiv').append(tab);
                        }
                    })
                }
            });

            $('#butAccept').click( function () {
                if ($('#delLotButton').val() === '0') {
                 //   updateCreditsInLot();
                    $('.delCrdButt').each(function(){
                                if($(this).val()==='1'){
                                    var idL = $(this).parent().children().first().text();
                                    $.ajax({
                                        url: "delCrFromLot",
                                        type: 'POST',
                                        data: {crdId: idL},
                                        success(res){
                                            if (res == "0")
                                                alert("Кредит не видалено!");
                                        }
                                    })
                                }
                            });

                    $.ajax({
                        url: "changeLotParams",
                        method: "POST",
                        data: {
                            lotId: $('#lotId').text(),
                            exchange: $('#ex').val(),
                            status: $('#ws').val(),
                            comment: $('#comm').val(),
                            bidStage: $('#bidst').val(),
                            bidDate: $('#bidDate').val(),
                            newspaper: $('#newsp').val(),
                            newsDate: $('#newsDate').val(),
                            newsDate2: $('#newsDate2').val(),
                            regEndDate: $('#regEndDate').val(),
                            countOfParticipants: $('#countOfParticipants').val(),
                            startPrice: $('#startPrice').val(),
                            factPrice: $('#factPrice').val(),
                            isSold: $('#setSoldButton').val()
                        },
                        success(rez){
                            if (rez === "1") {
                                alert("Лот змінено!")
                            }
                            else alert("Лот не змінено!")
                        }
                    });
                    $('#crdsTab').remove();
                    getCountSum();
                }
                else {
                    $.ajax({
                        url: "lotDel",
                        method: "POST",
                        data: {lotID: $('#lotId').text()},
                        success(rez){
                            if (rez === "1") {
                                alert("Лот видалено!");
                                window.close()
                            }
                            else alert("Лот не видалено!")
                        }
                    });
                }
            });
            $('#doc1').on('click', function(){
                $.ajax({
                    url: "setLotsToPrint",
                    type: "GET",
                    data: {lotsId: $('#lotId').text()},
                    success: function(res){
                        if(res=='1') {
                            window.open("download");
                        }
                    }
                });
            });
            $('#paymentsSum').click(function(){

                if(payTab.is(':hidden')){
                    payTab.show();
                }
                else payTab.hide();
            });
            $('#addPay').click(function adder(){
                var payDate =$('#payDate');
                var pay = $('#pay');
                if($(this).val()=='1'){
                    $.ajax({
                        url: "addPayToLot",
                        method: "POST",
                        data: {
                            lotId: $('#lotId').text(),
                            payDate: payDate.val(),
                            pay: pay.val()
                        },
                        success(rez){
                            if (rez === "1") {
                                alert("Платіж додано!");
                                getCountSum();
                                payDate.val(null);
                                pay.val(null);
                                payDate.hide();
                                pay.hide();
                                $('#addPay').val('0');
                                $(this).text('Додати платіж');
                                //adder();
                            }
                            else alert("Платіж НЕ ДОДАНО!")
                        }
                    });
                }
                else {
                    payDate.show();
                    pay.show();
                    $(this).val('1');
                    $(this).text('OK');
                }
            });

            <%if (lot.getItSold()){%>
            $('#delLotTd').hide();
            $('#setSoldTd').hide();
            $('#startPrice').remove();
            $('#startPriceTd').text(<%out.print(lot.getStartPrice());%>);
            $('#factPrice').remove();
            $('#factPriceTd').text(<%out.print(lot.getFactPrice());%>);
            <%}%>
        })

    </script>
</head>
<body id="bod" style="background-color: lightcyan">
<%
    List<Exchange> exchangeList = (List<Exchange>) request.getAttribute("exList");
    List<String> statusList = (List<String>) request.getAttribute("statusList");
    List<String> bidStatusList = (List<String>) request.getAttribute("bidStatusList");
%>
<H2>Редагування лоту співробітником <%out.print(lot.getUser().getLogin());%></H2>

<div id="bar">
    <table id="controlTab" border="1" >
        <tr>
            <th>К-ть об'єктів</th>
            <td id="count" align="center" bgcolor="#f0ffff"></td>

            <th>Стартова ціна лоту, грн.</th>
            <th>Ціна продажу, грн.</th>

            <td rowspan="2" id="delImg" hidden="hidden">
                <img height="50px" width="50px" src="css/images/red-del.png" >
            </td>
            <td rowspan="2" id="soldImg" hidden="hidden">
                <img height="50px" width="50px" src="css/images/green-round-tick-sign.jpg" >
            </td>
            <th>
                Фактично сплачено, грн.
            </th>
            <td>
                <input id="payDate" class="datepicker" hidden="hidden" title="введіть дату платежу">
            </td>
            <td>
                <input id="pay" type="number" hidden="hidden" title="введіть суму платежу">
            </td>
            <td>
                <button id="addPay">Додати платіж</button>
            </td>
        </tr>
        <tr>
            <th>Оціночна вартість, грн</th>
            <td id="sum" align="center" bgcolor="#f0ffff"></td>
            <td id="startPriceTd" align="center">
                <input id="startPrice" type="number" step="0.01" title="Ціна лоту з якої стартував аукціон" value="<%out.print(lot.getStartPrice());%>">
            </td>
            <td id="factPriceTd" align="center">
                <input id="factPrice"  type="number" step="0.01" title="Ціна за яку фактично продано лот" value=<%out.print(lot.getFactPrice());%>>
            </td>
            <td id="paymentsSum" datatype="number" align="center" title="Клікніть для розгорнутого перегляду платежів">
            </td>
            <td>
                <table id="paysTab" border="1" hidden="hidden">
                </table>
            </td>
        </tr>

    </table>

</div >
<div>
    <table id="lotTabR" border="3" style="font-size: 100% ; font-style: inherit">
        <tr bgcolor="#00ffff" id="lotTh">
            <th>ID</th>
            <th>Біржа</th>
            <th>Стадія роботи</th>
            <th>Торги</th>
            <th>Дата торгів</th>
            <th>Газета</th>
            <th>Публікація в газеті 1</th>
            <th>Публікація в газеті 2</th>
            <th>Кінець реєстрації</th>
            <th>К-ть учасників</th>
        </tr>
        <tr id="lotTr" align="center" bgcolor="white">
            <td id="lotId"><%out.print(lot.getId());%></td>
            <td>
                <select id="ex">
                    <% for (Exchange ex : exchangeList) {
                    %>
                    <option value="<%=ex.getId()%>" <%if (ex.getId() == lot.getBid().getExchange().getId()) {%>
                            selected="selected" <%
                            ;
                        }
                    %>>
                        <%
                            out.print(ex.getCompanyName() + " ЄДРПОУ: " + ex.getInn());
                        %>
                    </option>
                    <%
                        }
                    %>
                </select>
            </td>
            <td>
                <select id="ws">
                    <% for (String ws : statusList) {
                    %>
                    <option value="<%out.print(ws);%>" <%if (ws.equals(lot.getWorkStage())) {%> selected="selected" <%
                            ;
                        }
                    %>>
                        <%
                            out.print(ws);
                        %>
                    </option>
                    <%
                        }
                    %>
                </select>
            </td>
            <td>
                <select id="bidst">
                    <% for (String bidst : bidStatusList) {
                    %>
                    <option value="<%out.print(bidst);%>" <%if (bidst.equals(lot.getBid().getBidStage())) {%> selected="selected" <%
                            ;
                        }
                    %>>
                        <%
                            out.print(bidst);
                        %>
                    </option>
                    <%
                        }
                    %>
                </select>
            </td>
            <td>
                <input id="bidDate" class="datepicker"  value=<%if(lot.getBidDate()!=null)out.print(sdf.format(lot.getBidDate()));%> >
            </td>
            <td>
                <input id="newsp" title="газета в якій опублікована інформація стосовно торгів" value="<%out.print(lot.getNewspaper());%>" >
            </td>
            <td>
                <input id="newsDate" class="datepicker" value=<%if(lot.getNews1Date()!=null)out.print(sdf.format(lot.getNews1Date()));%> >
            </td>
            <td>
                <input id="newsDate2" class="datepicker" value=<%if(lot.getNews2Date()!=null)out.print(sdf.format(lot.getNews2Date()));%> >
            </td>
            <td>
                <input id="regEndDate" class="datepicker" value=<%if(lot.getRegistrEndDate()!=null)out.print(sdf.format(lot.getRegistrEndDate()));%> >
            </td>
            <td>
                <input id="countOfParticipants" type="number" value=<%out.print(lot.getCountOfParticipants());%> >
            </td>
        </tr>
    </table>
</div>
    <div id="comment">
        <table align="left">
            <tr>
                <td align="left">
                    <button id="doc1">Завантажити в форматі .xls</button>
                </td>
                <td>
                    Коментар
                </td>
                <td>
                    <input id="comm" value="<%out.print(lot.getComment());%>">
                </td>
            </tr>
        </table>
        <br/>
        <table align="rigth">
            <tr>
                <th rowspan="2" id="delLotTd">
                    <button id="delLotButton" value="0" style="font-weight: bold; color: yellow ;background-color: #dc143c; font-size: 14px; height: 50px; width: 120px">Розформувати лот</button>
                </th>
                <th rowspan="2" id="setSoldTd">
                    <button id="setSoldButton" value="0" style="font-weight: bold; background-color: lawngreen; font-size: 15px ; height: 50px; ">Лот продано</button>
                </th>
            </tr>
        </table>
    </div>
    <div id="commandButts" align="right">
        <button id="butAccept" style="color: white ;background-color: darkgreen; width: 150px ; height: 50px; font-size: 20px">ПІДТВЕРДИТИ</button>
    </div>
<div id="crdDiv" class="view" style="width: 100%" align="center">
    <button id="showCredits">Показати список об'єктів</button>
</div>

</body>
</html>