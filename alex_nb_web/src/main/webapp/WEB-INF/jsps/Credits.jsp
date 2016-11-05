<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список кредитів</title>

    <script src="js/jquery-1.11.1.js"></script>
    <script src="js/Monthpicker.js"></script>
    <link rel="stylesheet" media="screen" type="text/css" href="css/monthpicker.css"/>
    <script>
        $(document).ready(function(){

            var portionNum = $('#portion');
            portionNum.keydown(function(event){
                if(event.keyCode==13)
                    getPortion();
            });
            function getPortion() {
                var tab = $('#crdsTab');
                $('.tr').remove();
                $.ajax({
                    url: "creditsByPortions",
                    type: "POST",
                    data: {num: portionNum.val()-1,
                        isSold: $('#isSold').val(),
                        isInLot: $('#isInLot').val(),
                        clientType: $('#fiz_ur_Type').val(),
                        isNbu: $('#isNbu').val(),
                        isFondDec: $('#isFondDec').val()
                    },
                    success: function (crList) {

                        for (var i = 0; i < crList.length; i++) {
                            var creditFields = crList[i].split("||");

                            var trR = $('<tr align="center" class="tr">' +
                                    '<td class="checkTd"><input size="120%" type="checkbox" class="check-asset" title="Обрання кредитів для подальшої обробки"></td>'+
                                    '<td class="lotId" style="background-color: cyan">'+ creditFields[0] +'</td>' +
                                    '<td class="objId">' + creditFields[1] + '</td>' +
                                    '<td class="inn">' + creditFields[2] + '</td>' +
                                    '<td>' + creditFields[3] + '</td>' +
                                    '<td>' + creditFields[4] + '</td>' +
                                    '<td>' + creditFields[5] + '</td>' +
                                    '<td>' + creditFields[6] + '</td>' +
                                    '<td>' + creditFields[7] + '</td>' +
                                    '<td>' + creditFields[8] + '</td>' +
                                    '<td>' + creditFields[9] + '</td>' +
                                    '<td>' + creditFields[10] + '</td>' +
                                    '<td>' + creditFields[11] + '</td>' +
                                    '<td>' + creditFields[12] + '</td>' +
                                    '<td class="dpd">'+creditFields[13]+'</td>' +
                                    '<td class="rv">'+ creditFields[14] +'</td>' +
                                    '<td class="bidStage">' + creditFields[15] + '</td>' +
                                    '<td class="factPrice">' + creditFields[16] + '</td>' +
                                    '<td class="bidResult">' + creditFields[17] + '</td>' +
                                    '<td class="payStatus">' + creditFields[18] + '</td>' +
                                    '<td class="paySum">' + creditFields[19] + '</td>' +
                                    '<td class="residualToPay">' + creditFields[20] + '</td>' +
                                    '<td>' + creditFields[21] + '</td>' +
                                    '<td>' + creditFields[22] + '</td>' +
                                    '<td class="fondDecisionDate">' + creditFields[23] + '</td>' +
                                    '<td class="fondDec">' + creditFields[24] + '</td>' +
                                    '<td class="fondDecNum">' + creditFields[25] + '</td>' +
                                    '<td class="acceptPrice">' + creditFields[26] + '</td>' +
                                    '<td class="planSaleDate" title="клікніть двічі для зміни планової дати">' + creditFields[28] + '</td>' +
                                    '<td class="actSignedDate">' + creditFields[27] + '</td>' +
                                     '</tr>');
                            tab.append(trR);
                        }
                        $('#crdDiv').append(tab);
                        $('.check-asset').change(function(){
                            if($(this).is(':checked')){
                                $(this).parent().parent().css({'background-color': "#00ffff"});
                            }
                            else{
                                $(this).parent().parent().css({'background-color': "white"});
                            }
                        });
                        $('.inn, .objId').click(function(){
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
                        $('.planSaleDate').dblclick(function(){
                            var planSaleTd = $(this);
                            var objId = $(this).parent().find('.objId');
                            var data =$(this).text();
                            $(this).empty();
                            var inp = $('<input type="text"/>');
                            inp.val(data);

                            $(this).append(inp);
                            inp.Monthpicker({
                                format: 'yyyy.mm',
                                minYear: 2016,
                                maxYear: 2030,
                                minValue: null,
                                maxValue: null,
                                monthLabels: ["Січ", "Лют", "Бер", "Кві", "Тра", "Чер", "Лип", "Сер", "Вер", "Жов", "Лис", "Гру"],
                                onSelect: function () {
                                   // $(this).text(inp.val());
                                    $.ajax({
                                        url: "setPlanSaleDate",
                                        type: "GET",
                                        data: {objID: objId.text(),
                                            objType: 0,
                                            planDate: inp.val()
                                        },
                                        success: function(){
                                            planSaleTd.text(inp.val());
                                            inp.remove();
                                        }
                                    });
                                }
                            });
                        });
                    }
                });
            }
            getPortion();

            $('#createLot').click(function(){
                var idList="";
                $(".check-asset:checked").each(function(){
                    idList=idList+','+$(this).parent().parent().find('.objId').text();
                });
                $.ajax({
                    url: "createLotByCheckedCredits",
                    type: "POST",
                    data: {idList: idList.substring(1)},
                    success: function(result){
                        if(result==1) {
                            window.open("lotCreditsCreator1");
                        }
                        else alert("Халепа");
                    }
                });
                /*window.open("lotCreditsCreator")*/
            });
            $('#back').click( function(){
                var nval = parseInt(portionNum.val())-1;
                portionNum.val(nval);
                getPortion();
            });
            $('#forward').click( function(){
                var nval = parseInt(portionNum.val())+1;
                portionNum.val(nval);
                getPortion();
            });
            $('#showFilters').click(function(){
                var filterTab = $('#filterTab');
                if(filterTab.is(":hidden")) {
                    filterTab.show();
                    $('#showFilters').text("приховати")
                }
                    else{
                    filterTab.hide();
                $(this).text("фільтри")}
            });
            $('#filterButton').click(function(){
                $.ajax({
                    url: "countCreditsByFilter",
                    type: "POST",
                    data: {num: portionNum.val()-1,
                        isSold: $('#isSold').val(),
                        isInLot: $('#isInLot').val(),
                        clientType: $('#fiz_ur_Type').val(),
                        isNbu: $('#isNbu').val(),
                        isFondDec: $('#isFondDec').val()
                    },
                    success: function (count) {
                        var countOfPortions = parseInt(count/5000);
                        $('#range').text(countOfPortions+"стор. по 5000 кредитів з " + count);
                    }
                });
                getPortion();
            });
        })
    </script>
    <style type="text/css">
        .tr:hover{
            color: darkblue;
            cursor: pointer;
            background-color: lightcyan;
        }
        #headTr{
            background-color: skyblue;
        }
        select{
            width: 100%;
        }
    </style>
</head>
<body id="bd">
<div>
    <table align="right">
        <tr>
            <td >
                <button id="showFilters">Фільтри</button>
            </td>
        </tr>
    </table>
    <table id="filterTab" align="right" hidden="hidden">
        <tr>
            <td>Продані кредити</td>
            <td>
                <select id="isSold" title="Оберіть чи продані кредити">
                    <option value="10" selected="selected">Всі</option>
                    <option value="0">Ні</option>
                    <option value="1">Так</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>В лотах</td>
            <td>
                <select id="isInLot" title="Оберіть чи кредити в лотах">
                    <option value="10" selected="selected">Всі</option>
                    <option value="0">Ні</option>
                    <option value="1">Так</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Фіз.особи/Юр.особи</td>
            <td>
                <select id="fiz_ur_Type" title="Оберіть тип боржника">
                    <option value="10" selected="selected">Всі</option>
                    <option value="0">Фізичні особи</option>
                    <option value="1">Юридичні особи</option>
                    <option value="2">Списані</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>В заставі НБУ</td>
            <td>
                <select id="isNbu" title="Оберіть чи кредит в заставі НБУ">
                    <option value="10" selected="selected">Всі</option>
                    <option value="0">Ні</option>
                    <option value="1">Так</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Чи є рішення ФГВФО</td>
            <td>
                <select id="isFondDec" title="Оберіть чи по кредиту є рішення фонду">
                    <option value="10" selected="selected">Всі</option>
                    <option value="0">Ні</option>
                    <option value="1">Так</option>
                </select>
            </td>
        </tr>
        <tr>
            <td rowspan="2" align="center">
                <button id="filterButton">Застосувати фільтри</button>
            </td>
        </tr>
    </table>
</div>
<br>
<div>
    <table width="100%" align="left">
        <tr>
            <td>
                <button id="createLot">СТВОРИТИ ЛОТ</button>
            </td>
        </tr>
    </table>
    <table align="center">
        <tr>
            <%
                Long totalCountOfCredits = (Long)request.getAttribute("totalCountOfCredits");
                long countOfPortions = (totalCountOfCredits / 5000+1);
            %>
            <td id="range" colspan="3"
                align="center"><%out.print(countOfPortions+"стор." + " (по 5000 кредитів з " + totalCountOfCredits + ")");%></td>
        </tr>
        <tr>
            <td><button id="back">назад</button></td>
            <td><input value="1" type="number" id="portion" /></td>
            <td><button id="forward">вперед</button></td>
        </tr>
    </table>

    <table id="crdsTab" border="light" class="table">
        <tr id="headTr">
            <th></th>
            <th>ID лоту</th>
            <th>ID_BARS</th>
            <th>ІНН</th>
            <th>N Договору</th>
            <th>Дата аукціону</th>
            <th>Біржа</th>
            <th>Тип клієнта</th>
            <th>Найменування</th>
            <%--<th>Код типу активу</th>
            <th>Код групи активів</th>--%>
            <th>Продукт</th>
            <th>Застава НБУ</th>
            <th>Регіон</th>
            <%--<th>Початок договору</th>
            <th>Кінець договору</th>--%>
            <th>Валюта</th>
            <th>Загальний борг, грн.</th>
            <th>dpd</th>
            <th>Оціночна вартість, грн.</th>
            <th>Стадія торгів</th>
            <th>Ціна реалізації, грн.</th>
            <th>Статус аукціону</th>
            <th>Статус оплати</th>
            <th>Сплачено, грн.</th>
            <th>Залишок оплати, грн.</th>
            <th>Покупець</th>
            <th>Стадія роботи></th>
            <th>Дата прийняття рішення ФГВФО</th>
            <th>Рівень прийняття рішення</th>
            <th>Номер рішення</th>
            <th>Погоджена початкова ціна</th>
            <th>Заплановано реалізувати</th>
            <th>Акт підписано</th>
        </tr>

    </table>
</div>

</body>
</html>
