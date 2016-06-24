<%@ page import="nadrabank.domain.Asset" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="nadrabank.domain.Exchange" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список об'єктів</title>
    <script src="js/jquery-1.11.1.js"></script>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.css"/>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.structure.css"/>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.theme.css"/>
    <script type="text/javascript" src="js/jquery-ui.js"></script>
    <style type="text/css">
        .lotId {
            cursor: pointer;
            }
    </style>
    <script>
        $(document).ready(function(){
           // var dp = $('.inputFondDecDate');
            var dp = $('#inputFondDecDate');
            dp.datepicker({dateFormat: "yy-mm-dd", dayNamesMin: ["Пн","Вт","Ср","Чт","Пт","Сб","Нд"], monthNames: ["січень","лютий","березень", "квітень", "травень","червень",
                "липень","серпень","вересень","жовтень","листопад","грудень"] });
            var countOfPortions;
            $('.objId').each(function(){
                var obj = $(this);
                $.ajax({
                    url: "getPaySum_Residual",
                    type: "GET",
                    data: {id: obj.text()},
                    success: function (paySum_Residual) {
                        if(paySum_Residual.length>0){
                            obj.parent().find('.paysSum').text(paySum_Residual[0]);
                            obj.parent().find('.residualToPay').text(paySum_Residual[1]);

                            if(paySum_Residual[1]<=0){
                                obj.parent().find('.payStatus').text("100% сплата");
                            }
                            else{
                                if(paySum_Residual[0]==0){
                                    obj.parent().find('.payStatus').text("Очікується оплата");
                                }
                                else obj.parent().find('.payStatus').text("Часткова оплата");
                            }
                        }
                    }
                });
            });
            $('.lotId').dblclick(function(){
                var idL =$(this).text();
                if(idL!=""){
                $.ajax({
                    url: "setRlot",
                    type: "GET",
                    data: {lotID: idL},
                    success: function(){
                        window.open("lotRedactor")
                    }
                })
                }
            });

            $.ajax({
                url: "getTotalCountOfObjects",
                type: "GET",
                success: function (count) {
                    countOfPortions = parseInt(count/5000+1);
                    $('#range').text("1 - "+countOfPortions + "  (по 5000)");
                }
            });
            var portionNum = $('#portion');

            $('#back').click( function(){
                var nval = parseInt(portionNum.val())-1;
                portionNum.val(nval);
            //    getPortion();
            });
            $('#forward').click( function(){
                var nval = parseInt(portionNum.val())+1;
                portionNum.val(nval);
               // getPortion();
            });
            portionNum.keydown(function(event){
                if(event.keyCode==13);
               //     getPortion();
            });

            var redactButton = $('#redactButton');

            redactButton.click(function () {
                if (redactButton.val() === "0") {
                    redactButton.val(1);
                    redactButton.text("Прийняти");
                    $('#inputFondDecDate').show();
                    $('#inputFondDec').show();
                    $('#inputFondDecNum').show();
                }
                else {
                    redactButton.val(0);
                    redactButton.text("Додати рішення");
                    var idList="";
                    $(".check-asset:checked").each(function () {
                        idList=idList+','+$(this).parent().parent().find('.objId').text();
                    });
                    $.ajax({
                        url: "changeFondDec",
                        type: "POST",
                        data: {
                            idList: idList.substring(1),
                            fondDecDate: $('#inputFondDecDate').val(),
                            fondDec: $('#inputFondDec').val(),
                            decNum: $('#inputFondDecNum').val()
                        },
                        success: function (res) {
                            if (res == 1) {
                                alert("Зміни прийнято!");
                                location.href = "assets";
                            }
                        }
                    });
                }
            });
            $('#createLot').click(function(){
                var idList="";
                $(".check-asset:checked").each(function(){
                    idList=idList+','+$(this).parent().parent().find('.objId').text();
                });
                $.ajax({
                    url: "createLotByCheckedAssets",
                    type: "POST",
                    data: {idList: idList.substring(1)},
                    success: function(result){
                        if(result==1) {
                            window.open("lotCreator1");
                        }
                        else alert("Халепа");
                    }
                })
            });
            $('.check-asset').change(function(){
                if($(this).is(':checked')){
                    $(this).parent().parent().css({'background-color': "#00ffff"});
                }
                else{
                    $(this).parent().parent().css({'background-color': "white"});
                }
            });
            $('.inn').click(function(){
                var checkBox = $(this).parent().find('.check-asset');
                if(checkBox.is(':checked')){
                    checkBox.prop('checked', false);
                    $(this).parent().css({'background-color': "white"});
                }
                else{
                    checkBox.prop('checked', true);
                    $(this).parent().css({'background-color': "#00ffff"});

                }
            });

            var bidDateSelector = $('.bidDateSelector');
            var nbuZastSelector = $('.nbuZastSelector');
            var payStatus = $('.payStatusSelector');
            var bidResult = $('.bidResSelector');
            var workStage = $('.workStageSelector');
            var exchange = $('.exchangeSelector');
            var selectors = $('.bidDateSelector, .nbuZastSelector, .payStatusSelector, .bidResSelector, .workStageSelector, .exchangeSelector');
            selectors.mouseover(function(){
                $(this).css({'background-color': "#00ffff"})});
            selectors.mouseout( function(){
                $(this).css({'background-color': "white"})
            });

            bidDateSelector.click(function () {
                $('.bidDateHide').each(function () {
                    $(this).removeClass();
                    $(this).addClass("bidDate");
                });
                var exSelected = $(this).text();
                if (exSelected != "всі дати") {

                    $('.bidDate').each(function () {
                        if ($(this).text() != exSelected) {
                            $(this).removeClass();
                            $(this).addClass("bidDateHide");
                        }
                    });
                }
                doFilters();
            });
            nbuZastSelector.click(function () {
                $('.nbuZastHide').each(function () {
                    $(this).removeClass();
                    $(this).addClass("nbuZast");
                });
                var nbuSelected = $(this).text();
                if (nbuSelected != "всі") {
                    $('.nbuZast').each(function () {
                        if ($(this).text() != nbuSelected) {
                            $(this).removeClass();
                            $(this).addClass("nbuZastHide");
                        }
                    });
                }
                doFilters();
            });
            payStatus.click(function () {
                $('.payStatusHide').each(function () {
                    $(this).removeClass();
                    $(this).addClass("payStatus");
                });
                var payStatusSelected = $(this).text();
                if (payStatusSelected != "всі") {
                    $('.payStatus').each(function () {
                        if ($(this).text() != payStatusSelected) {
                            $(this).removeClass();
                            $(this).addClass("payStatusHide");
                        }
                    });
                }
                doFilters();
            });
            bidResult.click(function () {
                $('.bidResultHide').each(function () {
                    $(this).removeClass();
                    $(this).addClass("bidResult");
                });
                var bidResultSelected = $(this).text();
                if (bidResultSelected != "всі") {
                    $('.bidResult').each(function () {
                        if ($(this).text() != bidResultSelected) {
                            $(this).removeClass();
                            $(this).addClass("bidResultHide");
                        }
                    });
                }
                doFilters();
            });
            workStage.click(function () {
                $('.workStageHide').each(function () {
                    $(this).removeClass();
                    $(this).addClass("workStage");
                });
                var workStageSelected = $(this).text();
                if (workStageSelected != "всі") {
                    $('.workStage').each(function () {
                        if ($(this).text() != workStageSelected) {
                            $(this).removeClass();
                            $(this).addClass("workStageHide");
                        }
                    });
                }
                doFilters();
            });
            exchange.click(function () {
                $('.exchangeNameHide').each(function () {
                    $(this).removeClass();
                    $(this).addClass("exchangeName");
                });
                var exchangeNameSelected = $(this).text();
                if (exchangeNameSelected != "всі") {
                    $('.exchangeName').each(function () {
                        if ($(this).text() != exchangeNameSelected) {
                            $(this).removeClass();
                            $(this).addClass("exchangeNameHide");
                        }
                    });
                }
                doFilters();
            });

            function doFilters(){
                $('.assetTr').show();
                var hideElements =$('.bidDateHide, .nbuZastHide, .payStatusHide, .bidResultHide, .workStageHide, .exchangeNameHide');
                hideElements.each(function(){
                   $(this).parent().hide();
                });
            }

            var i=0;
            $('.spoiler_links').click(function(){
                if(i===0){
                    $(this).children('div.spoiler_body').slideDown('fast');
                    i=1;
                }
                else{
                    $(this).children('div.spoiler_body').slideUp('fast');
                    i=0;}
            });

            $('#printObj').click(function(){

                var lotToPrint = "";
                $('.id:visible').each(function(){
                    lotToPrint +=","+($(this).text());
                });
                $.ajax({
                    url: "setLotsToPrint",
                    type: "GET",
                    data: {lotsId: lotToPrint.substring(1)},
                    success: function(res){
                        if(res=='1') {
                            window.open("download");
                        }
                    }
                });
            })
        })
    </script>
    <style type="text/css">
        .spoiler_body {
            display:none;
            cursor:pointer;
            float:left;
            width:200px;
            background-color: white;
            text-align:center;
            position:absolute;
            z-index:99;}
        .spoiler_body a {padding:0px 10px;}
        .spoiler_links {
            cursor: pointer;
            float:left;
            margin:0px 5px;
            width:220px;
            line-height:1.5;
            text-align:center;}
        .assetTr {
            cursor: default;
        }
        button {
            cursor: pointer;
        }
    </style>
</head>
<body id="bd">
<% List<Asset> assetList = (List<Asset>) request.getAttribute("assetList");
    List<String> fondDecisionsList = (List<String>) request.getAttribute("fondDecisionsList");
    List<Date> allBidDates = (List<Date>) request.getAttribute("allBidDates");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    List<String> bidResultList = (List<String>) request.getAttribute("bidResultList");
    List<String> workStages = (List<String>) request.getAttribute("workStages");
    List<String> exchangeList = (List<String>) request.getAttribute("exchangeList");
%>
<div style="width: 100%">
    <div style="width: 100%">
        <table align="center">
            <tr>
                <td id="range" colspan="3" align="center">
                </td>
            </tr>
            <tr>
                <td>
                    <button id="back">назад</button>
                </td>
                <td><input value="1" type="number" id="portion"/></td>
                <td>
                    <button id="forward">вперед</button>
                </td>
            </tr>
        </table>

        <table width="100%" align="center">
            <tr>
                <td align="left">
                    <button id="createLot">СТВОРИТИ ЛОТ</button>
                </td>
                <td align="right">
                    <input id="inputFondDecDate" hidden="hidden" title="Дата прийняття рішення">
                    <select id="inputFondDec" name="decisionSelect" hidden="hidden" title="Рівень прийняття рішення">
                        <%
                            if (fondDecisionsList != null) {
                                for (String decision : fondDecisionsList) {
                        %>
                        <option value="<%=decision%>">
                            <%out.print(decision);%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                    <input type="text" id="inputFondDecNum" hidden="hidden" title="Номер рішення">
                    <button id="redactButton" value="0">Додати рішення фонду</button>
                </td>
            </tr>
        </table>
    </div>
    <table id="crdsTab" border="light" class="table">
        <tr bgcolor="#bdb76b">
            <th></th>
            <th hidden="hidden">ID</th>
            <th>ID лоту</th>
            <th>Інвентарний №</th>
            <th>
                <div class="spoiler_links" style="width: 100%; height: 100%">^Дата аукціону^
                    <div class="spoiler_body" >
                        <b class="bidDateSelector">всі дати</b><br>
                        <% for(Date bidDate: allBidDates){%>
                        <b class="bidDateSelector"><%out.print(sdf.format(bidDate));%></b><br>
                        <%}%>
                    </div>
                </div>
            </th>
            <th>
                <div class="spoiler_links" style="width: 100%; height: 100%">^Біржа^
                    <div class="spoiler_body" >
                        <b class="exchangeSelector">всі</b><br>
                        <% for(String ex: exchangeList){%>
                        <b class="exchangeSelector"><%out.print(ex);%></b><br>
                        <%}%>
                    </div>
                </div>
            </th>
            <th>Найменування</th>
            <th>
                <div class="spoiler_links" style="width: 100%; height: 100%">^Застава НБУ^
                    <div class="spoiler_body" >
                        <b class="nbuZastSelector">всі</b><br>
                        <b class="nbuZastSelector">Так</b><br>
                        <b class="nbuZastSelector">Ні</b><br>
                    </div>
                </div>
            </th>
            <th>Оціночна вартість, грн.</th>
            <th>Статус торгів</th>
            <th>№ Лоту в публікації</th>
            <%--<th>Початкова ціна, грн.</th>--%>
            <th>Кількість зареєстрованих учасників</th>
            <th>Зниження початкової ціни(%)</th>
            <th>Ціна реалізації, грн.</th>
            <th>
                <div class="spoiler_links" style="width: 100%; height: 100%">^Статус аукціону^
                    <div class="spoiler_body" >
                        <b class="bidResSelector">всі</b><br>
                        <% for(String bidres: bidResultList){%>
                        <b class="bidResSelector"><%out.print(bidres);%></b><br>
                        <%}%>
                    </div>
                </div>
            </th>
            <th>Покупець</th>
            <th>
                <div class="spoiler_links" style="width: 100%; height: 100%">^Статус оплати^
                    <div class="spoiler_body" >
                        <b class="payStatusSelector">всі</b><br>
                        <b class="payStatusSelector">Часткова оплата</b><br>
                        <b class="payStatusSelector">100% сплата</b><br>
                    </div>
                </div>
            </th>
            <th>Сума часткової сплати</th>
            <th>Залишок оплати, грн.</th>
            <th>
                <div class="spoiler_links" style="width: 100%; height: 100%">^Стадія роботи^
                    <div class="spoiler_body" >
                        <b class="workStageSelector">всі</b><br>
                        <% for(String ws: workStages){%>
                        <b class="workStageSelector"><%out.print(ws);%></b><br>
                        <%}%>
                    </div>
                </div>
            </th>
            <th>Дата прийняття рішення ФГВФО</th>
            <th>Рівень прийняття рішення</th>
            <th>Номер рішення</th>
            <th>В пропозиції</th>
            <th>Акт підписано</th>
        </tr>

        <%for(Asset asset: assetList){%>
        <tr align="center" class="assetTr">
            <td class="checkTd"><input size="120%" type="checkbox" class="check-asset" title="Обрання кредитів для подальшої обробки"></td>
            <td class="objId" hidden="hidden"><%=asset.getId()%></td>
            <td class="lotId" title="для переходу до ЛОТу подвійний клік"><%if(asset.getLot()!=null)out.print(asset.getLot().getId());%></td>
            <td class="inn"><%=asset.getInn()%></td>
            <td class="bidDate"><%if(asset.getLot()!=null&&asset.getLot().getBid()!=null) out.print(sdf.format(asset.getLot().getBid().getBidDate()));%></td>
            <td class="exchangeName"><%if(asset.getLot()!=null&&asset.getLot().getBid()!=null) out.print(asset.getLot().getBid().getExchange().getCompanyName());%></td>
            <td><%=asset.getAsset_descr()%></td>
            <td class="nbuZast"><%if(asset.isApproveNBU())out.print("Так");else out.print("Ні");%></td>
            <td><%=asset.getRv()%></td>
            <td><%if(asset.getLot()!=null) out.print(asset.getLot().getBidStage());%></td>
            <td><%if(asset.getLot()!=null) out.print(asset.getLot().getLotNum());%></td>
            <%--<td> <%if(asset.getBidPrice()!=null)out.print(asset.getBidPrice());%></td>--%>
            <td><%if(asset.getLot()!=null) out.print(asset.getLot().getCountOfParticipants());%></td>
            <td class="discountPr"></td>
            <td class="factPrice"><%if(asset.getFactPrice()!=null)out.print(asset.getFactPrice());%></td>
            <td class="bidResult"><%if(asset.getLot()!=null) out.print(asset.getLot().getStatus());%></td>
            <td><%if(asset.getLot()!=null) out.print(asset.getLot().getCustomerName());%></td>
            <td class="payStatus"></td>
            <td class="paysSum"></td>
            <td class="residualToPay"></td>
            <td class="workStage"><%if(asset.getLot()!=null) out.print(asset.getLot().getWorkStage());%></td>
            <td><input class="inputFondDecDate" value="<%if(asset.getFondDecisionDate()!=null)out.print(sdf.format(asset.getFondDecisionDate()));%>" hidden="hidden"> <div class="fondDecDat"><%if(asset.getFondDecisionDate()!=null)out.print(sdf.format(asset.getFondDecisionDate()));%></div></td>
            <td>
                <select class="inputFondDec" name="decisionSelect" style="width: 100%" hidden="hidden">
                    <%
                        if (fondDecisionsList != null) {
                            for (String decision : fondDecisionsList) {
                    %>
                    <option value="<%=decision%>" <%if (asset.getFondDecision() != null && asset.getFondDecision().equals(decision)) {%>
                            selected="selected"<%}%>>
                        <%out.print(decision);%>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
                <div class="fondDec"><%if(asset.getFondDecision() != null) out.print(asset.getFondDecision());%></div>
            </td>
            <td><input class="inputFondDecNum" value="<%if(asset.getDecisionNumber()!=null)out.print(asset.getDecisionNumber());%>" hidden="hidden"> <div class="fondDecNum"><%if(asset.getDecisionNumber()!=null)out.print(asset.getDecisionNumber());%></div></td>
            <td><%if(asset.getProposition()!=null) out.print(asset.getProposition());%></td>
            <%--<td> <%if(asset.isSold())out.print("ТАК"); else out.print("НІ");%></td>--%>
            <td><% if(asset.getLot()!=null&&asset.getLot().getActSignedDate()!=null)out.print(sdf.format(asset.getLot().getActSignedDate()));%> </td>
        </tr>
        <%}%>

    </table>

</div>

</body>
</html>