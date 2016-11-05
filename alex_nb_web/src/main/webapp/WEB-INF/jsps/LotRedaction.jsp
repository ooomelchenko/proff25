<%@ page import="nadrabank.domain.Bid" %>
<%@ page import="nadrabank.domain.Lot" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.TreeSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"  %>
<%request.setCharacterEncoding("UTF-8");%>
<%response.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <title>Редагування лоту</title>
    <script src="js/jquery-1.11.1.js"></script>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.css"/>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.structure.css"/>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.theme.css"/>
    <script type="text/javascript" src="js/jquery-ui.js"></script>
    <script type="text/javascript" src="js/docUploads.js"></script>
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
    Set<Bid> bidsHistoryList = (TreeSet<Bid>)request.getAttribute("bidsHistoryList");
    String userName = (String) request.getAttribute("user");
    %>
    $(document).ready(function () {
        var payTab= $('#paysTab');
        var dp = $('.datepicker');
        var lotID=$('#lotId');
        var addPayButt = $('#addPay');
        dp.datepicker({dateFormat: "yy-mm-dd", dayNamesMin: ["Нд","Пн","Вт","Ср","Чт","Пт","Сб"],
            monthNames: ["січень","лютий","березень", "квітень", "травень","червень", "липень","серпень","вересень","жовтень","листопад","грудень"] });
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
                                '<td>'+ payments[i].paySource +'</td>'+
                                +'</tr>'));
                    }
                }
            });
        }
        getFileNames(lotID.text(), $("#objType").val());
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
                $('#lotTh').css('backgroundColor', "sandybrown");
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
                $('#lotTh').css('backgroundColor', "sandybrown");
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
                    alert('Статус буде збереження після підтвердження!')
                }
            }
        });
        $('#showCredits').click(function showCredits() {
            var tab;
            <%if (lot.getLotType()==1){%>
             tab = $('#assetsTab');
            <% }
            if (lot.getLotType()==0){
            %>
             tab = $('#crdtsTab');
            <% } %>

            if(tab.is(':visible')) {
                tab.hide();
            }
            else{
                $('.tR').remove();
                tab.show();
                <%if (lot.getLotType()==1){%>
                $.ajax({
                    url: "selectAssetsbyLot",
                    type: "POST",
                    data: {
                        lotId: $('#lotId').text()
                    },
                    success: function (objList) {

                        for(var i=0; i<objList.length; i++) {
                            var dat = new Date(objList[i].fondDecisionDate );
                            dat.setDate(dat.getDate()+1);
                            var approveNBU = objList[i].approveNBU ? "Так" : "Ні";
                            var trR = $('<tr align="center" class="tR">' +
                                    '<td class="idLot" hidden="hidden">' + objList[i].id + '</td>' +
                                    '<td>' + objList[i].inn + '</td>' +
                                    '<td>' + objList[i].asset_name + '</td>' +
                                    '<td>' + objList[i].asset_descr + '</td>' +
                                    '<td>' + objList[i].region + '</td>' +
                                    '<td>' + objList[i].zb + '</td>' +
                                    '<td>' + objList[i].rv + '</td>' +
                                    '<td>' + objList[i].factPrice + '</td>' +
                                    '<td>' + dat.toISOString().substring(0,10) + '</td>' +
                                    '<td>' + approveNBU + '</td>' +
                                    '</tr>');
                            //var factPriceTd = trR.find('.factPriceTd');

                            if(objList[i].sold){
                                trR.css('background-color', "lightgreen");
                            }
                            else{
                                var delCrButt = $('<button class="delCrdButt" value="0" title="Видалити обєкт">' +
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
                    }
                });
                <% } %>
                <%if (lot.getLotType()==0){%>
                $.ajax({
                    url: "selectCreditsLot",
                    type: "POST",
                    data: {
                        lotId: $('#lotId').text()
                    },
                    success: function (objList) {
                        for(var i=0; i<objList.length; i++) {
                            var dat = new Date(objList[i].fondDecisionDate );
                            dat.setDate(dat.getDate()+1);
                            var approveNBU = objList[i].nbuPladge ? "Так" : "Ні";
                            var trR = $('<tr align="center" class="tR">' +
                                    '<td class="idLot">' + objList[i].id + '</td>' +
                                    '<td>' + objList[i].inn + '</td>' +
                                    '<td>' + objList[i].fio + '</td>' +
                                    '<td>' + objList[i].product + '</td>' +
                                    '<td>' + objList[i].region + '</td>' +
                                    '<td>' + objList[i].zb + '</td>' +
                                    '<td>' + objList[i].rv + '</td>' +
                                    '<td>' + objList[i].factPrice + '</td>' +
                                    '<td>' + dat.toISOString().substring(0,10) + '</td>' +
                                    '<td>' + approveNBU + '</td>' +
                                    '</tr>');
                            //var factPriceTd = trR.find('.factPriceTd');

                            if(objList[i].isSold){
                                trR.css('background-color', "lightgreen");
                            }
                            else{
                                var delCrButt = $('<button class="delCrdButt" value="0" title="Видалити обєкт">' +
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
                    }
                });
                <% } %>
                $('#crdDiv').append(tab);
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
                            url: "delObjectFromLot",
                            type: 'POST',
                            data: {objId: idL,
                            lotId: $('#lotId').text()},
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
        $('#showAddDoc').click(function(){
            var addDocForm = $("#addDocForm");
            if(addDocForm.is(':visible')) {
                $(this).text("+Додати+");
                addDocForm.hide();
            }
            else{
                $(this).text("Приховати");
                addDocForm.show();
            }
        });
        $('#doc1').click(function(){
            $.ajax({
                url: "setLotToPrint",
                type: "GET",
                data: {lotId: $('#lotId').text()},
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
            var payTd= $('.payTd');
            var payDate =$('#payDate');
            var pay = $('#pay');
            var paySource = $('#paySource');
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
                        pay: pay.val(),
                        paySource: paySource.val()
                    },
                    success(rez){
                        if (rez === "1") {
                            alert("Платіж додано!");
                            getCountSum();
                            payDate.val(null);
                            pay.val(null);
                            payTd.hide();
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
                payTd.show();
                addPayButt.val('1');
                addPayButt.text('OK');
            }
        });

        $("#bidDate").click(function () {
            var lotHistoryTr = $(".lotHistoryTr");
            if (lotHistoryTr.is(":hidden"))
                lotHistoryTr.show();
            else
                lotHistoryTr.hide();
        });

        <%if (lot.getItSold()){%>
        $('#delLotTd').hide();
        $('#setSoldTd').hide();
        $('#startPrice').hide();
        $('#startPriceTd').append(<%out.print(lot.getStartPrice());%>);
        $('#factPrice').hide();
        $('#factPriceTd').append(<%out.print(lot.getFactPrice());%>);
        <%}%>
    })
</script>
<style type="text/css" scoped>
    #finInfoTr{
        font-weight: bold
    }
    #butAccept{
        color: white;
        background-color: darkgreen;
        border-radius: 10% ;
        width: 180px ;
        height: 50px;
        font-size: 20px;
        font-weight: bold
    }
    #butAccept:hover{
        background-color: lightgreen;
        color: black;
    }
    #doc1{
        color: darkblue;
        font-weight: bold
    }
    #doc1:hover{
        cursor:pointer;
        background-color: white;
        color: black;
    }
    #fileList, #delLotButton, #setSoldButton, #showCredits, #butAccept, #paymentsSum, #bidDate, .lotHistoryTr
    {
        cursor:pointer;
    }
    #bidDate:hover{
        background-color: lightcyan;
        font-weight: bold
    }
    .lotHistoryTr:hover{
        background-color: white;
        font-weight: bold;
    }
    #delLotButton{
        background-color: red;
        color: black;
        font-weight: bold;
        font-size: 14px;
        height: 50px;
        width: 120px;
    }
    #delLotButton:hover{
        background-color: darkred;
        color: white;
    }
    #setSoldButton{
        background-color: darkgreen;
        color: white;
        font-weight: bold;
        font-size: 15px;
        height: 50px;
    }
    #setSoldButton:hover{
        background-color: lawngreen;
        color: black;
    }
</style>

<H2>Редагування лоту співробітником <%out.print(userName);%></H2>

<div>
    <table id="lotTabR" border="3" style="font-size: 100% ; font-style: inherit">
        <tr bgcolor="sandybrown" id="lotTh">
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
        <%for (Bid bid: bidsHistoryList){%>
        <tr class="lotHistoryTr" hidden="hidden" align="center">
            <td></td>
            <td colspan="4" align="left">
                <%out.print(bid.getExchange().getCompanyName());%>
            </td>

            <td>
                <%if(bid.getBidDate()!=null)out.print(sdf.format(bid.getBidDate()));%>
            </td>
            <td>
                <%if(bid.getNewspaper()!=null)out.print(bid.getNewspaper());%>
            </td>
            <td>
                <%if(bid.getNews1Date()!=null)out.print(sdf.format(bid.getNews1Date()));%>
            </td>
            <td>
                <%if(bid.getNews2Date()!=null)out.print(sdf.format(bid.getNews2Date()));%>
            </td>
            <td>
                <%if(bid.getRegistrEndDate()!=null)out.print(sdf.format(bid.getRegistrEndDate()));%>
            </td>
        </tr>
        <%}%>
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
            <td id="bidDate" title="натисніть для перегляду історії">
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
                    <option value="<%out.print(bid.getId());%>"<%if (lot.getBid()!=null&&lot.getBid().getId().equals(bid.getId())) {%> selected="selected"<%;}%>>
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

<div id="bar" style="width: 100%">
    <table>
        <tr>
            <td colspan="2">
            <table id="controlTab" border="1" align="left">
                <tr>
                    <th bgcolor="#00ffff">К-ть об'єктів</th>
                    <td id="count" align="center" bgcolor="#f0ffff"></td>
                    <th bgcolor="#00ffff">№ лоту в публікації</th>
                    <th bgcolor="#00ffff">К-ть учасників</th>
                    <th bgcolor="#00ffff">Покупець</th>
                    <th bgcolor="#00ffff">Дисконт</th>
                    <th bgcolor="#00ffff">Стартова ціна лоту, грн.</th>
                    <th bgcolor="#00ffff">Ціна продажу, грн.</th>
                    <th bgcolor="#00ffff">Залишок до сплати, грн.</th>
                    <th bgcolor="#00ffff">Фактично сплачено, грн.</th>
                    <td class="payTd" hidden="hidden">
                        <input id="payDate" class="datepicker" title="введіть дату платежу">
                    </td>
                    <td class="payTd" hidden="hidden">
                        <input id="pay" type="number" step="0.01" title="введіть суму платежу">
                    </td>
                    <td class="payTd" hidden="hidden">
                        <select id="paySource">
                            <option value="Біржа">
                                Біржа
                            </option>
                            <option value="Покупець">
                                Покупець
                            </option>
                        </select>
                    </td>
                    <td>
                        <button id="addPay">Додати платіж</button>
                    </td>
                    <td rowspan="2" id="soldImg" hidden="hidden">
                        <img height="50px" width="50px" src="css/images/green-round-tick-sign.jpg">
                    </td>
                    <td rowspan="2" id="delImg" hidden="hidden">
                        <img height="50px" width="50px" src="css/images/red-del.png">
                    </td>
                </tr>
                <tr id="finInfoTr">
                    <th bgcolor="#00ffff">Оціночна вартість, грн</th>
                    <td id="sum" align="center" bgcolor="#f0ffff"></td>
                    <td><input id="lotNum" type="text" value="<%if(lot.getLotNum()!=null)out.print(lot.getLotNum());%>"></td>
                    <td><input id="countOfPart" type="number" value="<%out.print(lot.getCountOfParticipants());%>"></td>
                    <td><input id="customerName" type="text" placeholder="ФІО"
                               value='<%if(lot.getCustomerName()!=null)out.print(lot.getCustomerName());%>'></td>
                    <td id="firstStartPriceTd" align="center" title="Дисконт відносно початкової ціни на перших торгах">
                        <%
                            if (lot.getStartPrice() != null && lot.getFirstStartPrice() != null)
                                out.print((new BigDecimal(1).subtract(lot.getStartPrice().divide(lot.getFirstStartPrice(), 4, BigDecimal.ROUND_HALF_UP))).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        %>
                    </td>
                    <td id="startPriceTd" align="center">
                        <input id="startPrice" type="number" step="0.01" title="Ціна лоту з якої стартував аукціон"
                               value="<%out.print(lot.getStartPrice());%>">
                    </td>
                    <td id="factPriceTd" align="center">
                        <input id="factPrice" type="number" step="0.01" title="Ціна за яку фактично продано лот"
                               value=<%out.print(lot.getFactPrice());%>>
                    </td>
                    <td id="residualToPay" align="center" title="Залишок до сплати (ціна продажу-сплчено)">
                    </td>
                    <td id="paymentsSum" datatype="number" align="center" title="Клікніть для розгорнутого перегляду платежів">
                    </td>
                    <td colspan="3">
                        <table id="paysTab" bgcolor="#f0f8ff" border="1" hidden="hidden" width="100%">
                        </table>
                    </td>
                </tr>
            </table>
            </td>
        </tr>
        <tr>
            <td>
            <table align="left">
                <tr>
                    <th id="delLotTd">
                        <button id="delLotButton" value="0">
                            Розформувати лот
                        </button>
                    </th>
                    <th id="setSoldTd">
                        <button id="setSoldButton" value="0" >
                            Акт підписано
                        </button>
                    </th>
                </tr>
                <tr>
                    <td colspan="2">
                        Коментар <input id="comm" value="<%if(lot.getComment()!=null)out.print(lot.getComment());%>">
                    </td>
                </tr>
            </table>
            </td>
            <td>
                <table align="right">
                    <tr>
                        <td align="center">
                            <table id="fileList" border="2" style="background-color: white">
                                <tr>
                                    <th>
                                        Документи по Лоту
                                    </th>
                                </tr>
                            </table>
                            <button id="showAddDoc">
                                +Додати+
                            </button>
                        </td>
                    </tr>
                    <tr id="addDocForm" hidden="hidden" bgcolor="#f5f5dc">
                        <td>
                            <form method="POST" action="uploadFile" enctype="multipart/form-data" lang="utf8">
                                <input type="text" id="objType" name="objType" value="lot" hidden="hidden">
                                <input type="text" name="objId" value="<%out.print(lot.getId());%>" hidden="hidden">
                                Обрати документ для збереження:
                                <br/>
                                <input align="center" type="file" name="file" title="натисніть для обрання файлу"><br/>
                                <input align="center" type="submit" value="Зберегти" title="натисніть щоб зберегти файл">
                            </form>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

</div>

<div id="commandButts" align="right">
    <button id="butAccept" title="натисніть для збереження змін">
        ПІДТВЕРДИТИ
    </button>
</div>

<div id="crdDiv" class="view" style="width: 100%" align="center">
    <table align="center">
        <tr>
            <td align="center">
                <button id="showCredits" style="height: 40px; font-size: 18px; font-weight: bold">Показати список об'єктів</button>
            </td>
            <td align="right" title="натисніть для збереження в форматі .xls">
                <p id="doc1" align="right">Завантажити в .xls</p>
            </td>
        </tr>
    </table>
    <table id="assetsTab" border="light" class="table" hidden="hidden">
        <tr bgcolor="limegreen">
            <th hidden="hidden">ID</th>
            <th>Інвентарний №</th>
            <th>Назва активу</th>
            <th>Опис обєкту</th>
            <th>Регіон</th>
            <th>Балансова вартість</th>
            <th>Оціночна вартість, грн.</th>
            <th>Ціна продажу, грн.</th>
            <th>Дата прийняття рішення ФГВФО</th>
            <th>В заставі НБУ</th>
        </tr>
    </table>
    <table id="crdtsTab" border="light" class="table" hidden="hidden">
        <tr bgcolor="#00bfff">
            <th>ID_Bars</th>
            <th>ІНН</th>
            <th>Назва</th>
            <th>Опис обєкту</th>
            <th>Регіон</th>
            <th>Балансова вартість</th>
            <th>Оціночна вартість, грн.</th>
            <th>Ціна продажу, грн.</th>
            <th>Дата прийняття рішення ФГВФО</th>
            <th>В заставі НБУ</th>
        </tr>
    </table>
</div>

</body>
</html>