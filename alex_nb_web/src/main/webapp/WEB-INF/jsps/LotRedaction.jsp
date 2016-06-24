<%@ page import="nadrabank.domain.Bid" %>
<%@ page import="nadrabank.domain.Lot" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редагування лоту</title>
    <script src="js/jquery-1.11.1.js"></script>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.css"/>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.structure.css"/>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.theme.css"/>
    <script type="text/javascript" src="js/jquery-ui.js"></script>
</head>
<body id="bod" style="background-color: lightcyan">
<script>
    <%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    Lot lot = (Lot) request.getAttribute("lott");
    List<String> bidStatusList = (List<String>) request.getAttribute("bidStatusList");
    List<String> statusList = (List<String>) request.getAttribute("statusList");
    List<String> bidResultList = (List<String>) request.getAttribute("bidResultList");
    List<Bid> allBidsList = (List<Bid>)request.getAttribute("allBidsList");
    String userName = (String) request.getAttribute("user");
    %>
    $(document).ready(function () {
        var payTab= $('#paysTab');
        var dp = $('.datepicker');
        var lotID=$('#lotId');
        var addPayButt = $('#addPay');
        dp.datepicker({dateFormat: "yy-mm-dd", dayNamesMin: ["Пн","Вт","Ср","Чт","Пт","Сб","Нд"], monthNames: ["січень","лютий","березень", "квітень", "травень","червень",
            "липень","серпень","вересень","жовтень","листопад","грудень"] });
        function getCountSum() {
            payTab.empty();
            $.ajax({
                url: "countSumByLot",
                method: "POST",
                data: {lotId: lotID.text() },
                success: function(countSum){
                    $('#count').text(countSum[0]);
                    $('#sum').text(countSum[1]);
                }
            });
            $.ajax({
                url: "paymentsSumByLot",
                method: "POST",
                data: {lotId: lotID.text()},
                success: function (paymentsSum) {
                    $('#paymentsSum').text(paymentsSum);

                    var residualToPay = parseFloat($('#factPrice').val()) - parseFloat(paymentsSum) ;

                    if (isNaN(Number(residualToPay))) {
                        $('#residualToPay').text($('#factPrice').val());
                    }
                    else {
                        $('#residualToPay').text(Number(residualToPay).toFixed(2));
                    }
                }
            });
            $.ajax({
                url: "paymentsByLot",
                method: "POST",
                data: {lotId: lotID.text() },
                success: function(payments){
                    var paysTab = $('#paysTab');
                    for(var i=0; i<payments.length; i++){
                        var d = new Date(payments[i].date );
                        d.setDate(d.getDate()+1);
                        paysTab.append($('<tr>'+
                                '<td>'+ d.toISOString().substring(0, 10) +'</td>'+
                                '<td>'+ payments[i].paySum +'</td>'+
                                +'</tr>'));
                    }
                }
            });
        }

        getCountSum();

        $('#reBidButton').click(function(){
            $.ajax({
                url: "reBidByLot",
                type: "POST",
                data:{lotId: lotID.text()},
                success: function(res){
                    if(res==1)
                    location.reload(true);
                    else alert("Якась халепа!");
                }
            })
        });
        $('#delLotButton').click(function () {
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
        $('#setSoldButton').click(function () {
            if($(this).val()==='1'){
                $('#soldImg').hide();
                $(this).val('0');
                $('#lotTh').css('backgroundColor', "#00ffff");
                $('#count').css('backgroundColor', "#f0ffff");
                $('#sum').css('backgroundColor', "#f0ffff");
            }
            else {
                if($('#ws').val()!="Угода укладена"){
                    alert("Змініть стадію на 'Угода укладена!'");
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
                    alert('Лот буде відмічено як проданий після підтвердження!')
                }
            }
        });
        $('#showCredits').click(function showCredits() {
            if ($('table').is($('#crdsTab'))) {
                $('#crdsTab').remove();
            }
            else {
                $.ajax({
                    url: "selectAssetsbyLot",
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
                                '<th>В заставі НБУ</th>'+
                                '</tr>');

                        tab.append(trHead);
                        for(var i=0; i<assetList.length; i++) {
                            var approveNBU = assetList[i].approveNBU ? "Так" : "Ні";
                            var trR = $('<tr align="center" class="tr">' +
                                    '<td class="idLot">' + assetList[i].id + '</td>' +
                                    '<td>' + assetList[i].inn + '</td>' +
                                    '<td>' + assetList[i].asset_name + '</td>' +
                                    '<td>' + assetList[i].asset_descr + '</td>' +
                                    '<td>' + assetList[i].region + '</td>' +
                                    '<td>' + assetList[i].zb + '</td>' +
                                    '<td>' + assetList[i].rv + '</td>' +
                                    '<td>' + approveNBU + '</td>' +
                                    '</tr>');
                            var factPriceTd = trR.find('.factPriceTd');

                            if(assetList[i].sold){
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
        $('#showBidsButt').click(function(){
            if($(this).val()==0) {
                $('#bidSelector').show();
                $(this).val(1);
            }
            else {
                $('#bidSelector').hide();
                $(this).val(0);
            }
        });

        $('#butAccept').click( function () {
            if ($('#delLotButton').val() === '0') {

                $('.delCrdButt').each(function(){
                    if($(this).val()==='1'){
                        var idL = $(this).parent().children().first().text();
                        $.ajax({
                            url: "delAssetFromLot",
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
                        workStage: $('#ws').val(),
                        comment: $('#comm').val(),
                        bidStage: $('#bidst').val(),
                        lotNum: $('#lotNum').val(),
                        resultStatus: $('#bidResultSt').val(),
                        customer: $('#customerName').val(),
                        startPrice: $('#startPrice').val(),
                        factPrice: $('#factPrice').val(),
                        isSold: $('#setSoldButton').val(),
                        selectedBidId: $('#bidSelector').val(),
                        countOfParticipants: $('#countOfPart').val()
                    },
                    success: function(rez){
                        if (rez === "1") {
                            alert("Лот змінено!");
                            location.reload(true);
                        }
                        else alert("Лот не змінено!")
                    }
                });
             //   $('#crdsTab').remove();
             //   getCountSum();
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

        $('#doc1').click(function(){
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
        addPayButt.click(function adder(){
            var payDate =$('#payDate');
            var pay = $('#pay');
            if($(this).val()=='1'){
                if(isNaN(parseFloat(pay.val()))){
                    alert("Введіть будь-ласка суму платежу в коректному форматі!")
                }
                else{
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
                            addPayButt.val('0');
                            addPayButt.text('Додати платіж');
                            //adder();
                        }
                        else alert("Платіж НЕ ДОДАНО!")
                    }
                });
                }
            }
            else {
                payDate.show();
                pay.show();
                addPayButt.val('1');
                addPayButt.text('OK');
            }
        });

        <%if (lot.getItSold()){%>
        $('#delLotTd').hide();
        $('#setSoldTd').hide();
        $('#startPrice').hide();
        $('#startPriceTd').append(<%out.print(lot.getStartPrice());%>);
        $('#factPrice').hide();
        $('#factPriceTd').append(<%out.print(lot.getFactPrice());%>);
        <%}%>

        $('#openButt').click(function(){
            window.open("file://///srv-co-fss31/DOA_SCAN/");
           // window.open("file://D:");
        })
    })

</script>
<H2>Редагування лоту співробітником <%out.print(userName);%></H2>

<div>
    <table id="lotTabR" border="3" style="font-size: 100% ; font-style: inherit">
        <tr bgcolor="#00ff7f" id="lotTh">
            <th>ID</th>
            <th>Біржа</th>
            <th>Стадія роботи</th>
            <th>Торги</th>
            <th>Статус аукціону</th>
            <th>Дата торгів</th>
            <th>Газета</th>
            <th>Публікація в газеті 1</th>
            <th>Публікація в газеті 2</th>
            <th>Кінець реєстрації</th>
            <th>
                <button id="showBidsButt" style="font-weight: bold">+Обрати аукціон+</button>
            </th>
            <th rowspan="2" type="input">
                <button style="width: 100px; height: 60px; font-weight: bold" id="reBidButton">Повторні торги</button>
            </th>
        </tr>
        <tr id="lotTr" align="center" bgcolor="white">
            <td id="lotId"><%out.print(lot.getId());%></td>
            <td>
                <%if(lot.getBid()!=null && lot.getBid().getExchange()!=null)out.print(lot.getBid().getExchange().getCompanyName());%>
            </td>
            <td>
                <select id="ws">
                    <% for (String ws : statusList) {%>
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
                    <option value="<%out.print(bidst);%>" <%if (bidst.equals(lot.getBidStage())) {%> selected="selected" <%
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
                <select id="bidResultSt">
                    <% for (String resultStatus : bidResultList) {
                    %>
                    <option value="<%out.print(resultStatus);%>" <%if (resultStatus.equals(lot.getStatus())) {%> selected="selected" <%
                            ;
                        }
                    %>>
                        <%
                            out.print(resultStatus);
                        %>
                    </option>
                    <%
                        }
                    %>
                </select>
            </td>
            <td>
                <%if(lot.getBid()!=null&&lot.getBid().getBidDate()!=null)out.print(sdf.format(lot.getBid().getBidDate()));%>
            </td>
            <td>
                <%if(lot.getBid()!=null&&lot.getBid().getNewspaper()!=null)out.print(lot.getBid().getNewspaper());%>
            </td>
            <td>
                <%if(lot.getBid()!=null&&lot.getBid().getNews1Date()!=null)out.print(sdf.format(lot.getBid().getNews1Date()));%>
            </td>
            <td>
                <%if(lot.getBid()!=null&&lot.getBid().getNews2Date()!=null)out.print(sdf.format(lot.getBid().getNews2Date()));%>
            </td>
            <td>
                <%if(lot.getBid()!=null&&lot.getBid().getRegistrEndDate()!=null)out.print(sdf.format(lot.getBid().getRegistrEndDate()));%>
            </td>
            <td>
                <select id="bidSelector" hidden="hidden">
                    <option value="0">
                    </option>
                    <% for (Bid bid : allBidsList) { %>
                    <option value="<%out.print(bid.getId());%>"<%if (lot.getBid()!=null&&lot.getBid().getId()==bid.getId()) {%> selected="selected"<%;}%>>
                        <%
                            out.print(sdf.format(bid.getBidDate()) +" - "+ bid.getExchange().getCompanyName() );
                        %>
                    </option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
    </table>
</div>

<div id="bar">
    <table align="left">
        <tr>
            <th  id="delLotTd">
                <button id="delLotButton" value="0" style="font-weight: bold; color: yellow ;background-color: #dc143c; font-size: 14px; height: 50px; width: 120px">Розформувати лот</button>
            </th>
            <th  id="setSoldTd">
                <button id="setSoldButton" value="0" style="font-weight: bold; background-color: lawngreen; font-size: 15px ; height: 50px; ">Акт підписано</button>
            </th>
        </tr>
        <tr>
            <td colspan="2">
                Коментар <input id="comm" value="<%out.print(lot.getComment());%>">
            </td>
        </tr>
    </table>
    <table id="controlTab" border="1" align="center" >
        <tr>
            <th bgcolor="#00ffff">К-ть об'єктів</th>
            <td id="count" align="center" bgcolor="#f0ffff"></td>
            <th bgcolor="#00ffff">№ лоту в публікації</th>
            <th bgcolor="#00ffff">К-ть учасників</th>
            <th bgcolor="#00ffff">Покупець</th>
            <th bgcolor="#00ffff">Дисконт</th>
            <th bgcolor="#00ffff">Стартова ціна лоту, грн.</th>
            <th bgcolor="#00ffff">Ціна продажу, грн.</th>
            <th bgcolor="#00ffff">Фактично сплачено, грн.</th>
            <th bgcolor="#00ffff">Залишок до сплати, грн.</th>
            <td>
                <input id="payDate" class="datepicker" hidden="hidden" title="введіть дату платежу">
            </td>
            <td>
                <input id="pay" type="number" step="0.01" hidden="hidden" title="введіть суму платежу">
            </td>
            <td>
                <button id="addPay">Додати платіж</button>
            </td>
            <td rowspan="2" id="soldImg" hidden="hidden">
                <img height="50px" width="50px" src="css/images/green-round-tick-sign.jpg" >
            </td>
            <td rowspan="2" id="delImg" hidden="hidden">
                <img height="50px" width="50px" src="css/images/red-del.png" >
            </td>
        </tr>
        <tr>
            <th bgcolor="#00ffff">Оціночна вартість, грн</th>
            <td id="sum" align="center" bgcolor="#f0ffff"></td>
            <td><input id="lotNum" type="text" value="<%if(lot.getLotNum()!=null)out.print(lot.getLotNum());%>"></td>
            <td><input id="countOfPart" type="number" value="<%out.print(lot.getCountOfParticipants());%>"></td>
            <td><input id="customerName" type="text" value='<%if(lot.getCustomerName()!=null)out.print(lot.getCustomerName());%>'></td>
            <td id="firstStartPriceTd" align="center" title="Дисконт відносно початкової ціни на перших торгах">
                <%if(lot.getStartPrice()!=null&&lot.getFirstStartPrice()!=null)out.print((new BigDecimal(1).subtract(lot.getStartPrice().divide(lot.getFirstStartPrice(), 4, BigDecimal.ROUND_HALF_UP) )).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%" );%>
            </td>
            <td id="startPriceTd" align="center">
                <input id="startPrice" type="number" step="0.01" title="Ціна лоту з якої стартував аукціон" value="<%out.print(lot.getStartPrice());%>">
            </td>
            <td id="factPriceTd" align="center">
                <input id="factPrice"  type="number" step="0.01" title="Ціна за яку фактично продано лот" value=<%out.print(lot.getFactPrice());%>>
            </td>
            <td id="paymentsSum" datatype="number" align="center" title="Клікніть для розгорнутого перегляду платежів">
            </td>
            <td id="residualToPay" align="center" title="Залишок до сплати (ціна продажу-сплчено)">
            </td>
            <td colspan="2">
                <table id="paysTab" border="1" hidden="hidden" width="100%">
                </table>
            </td>
        </tr>
    </table>
    <table>
        <tr>
            <td align="left">
                <button id="doc1">Завантажити в форматі .xls</button>
            </td>
            <td><button id="openButt">opener</button></td>

            <%--<td><a href="file://///srv-co-fss31/DOA_SCAN/" target=_blank>server</a></td>--%>
            <td><a href="file:///D:/" target=_blank> C </a></td>
        </tr>
    </table>
</div>
<div>

</div>

<div id="commandButts" align="right">
    <button id="butAccept" style="color: white ;background-color: darkgreen; width: 150px ; height: 50px; font-size: 20px">ПІДТВЕРДИТИ</button>
</div>
<div id="crdDiv" class="view" style="width: 100%" align="center">
    <button id="showCredits">Показати список об'єктів</button>
</div>

</body>
</html>