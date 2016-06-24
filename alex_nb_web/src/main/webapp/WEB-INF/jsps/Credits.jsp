<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список об'єктів</title>
    <script src="js/jquery-1.11.1.js"></script>
    <script>
        $(document).ready(function(){

            var countOfPortions;
            $.ajax({
                url: "getTotalCountOfCredits",
                type: "GET",
                success: function (count) {
                    countOfPortions = parseInt(count/5000+1);
                    $('#range').text("1 - "+countOfPortions + "  (по 5000)");
                }
            });
            var portionNum = $('#portion');
            function getPortion() {
                var tab = $('#crdsTab');
                $('.tr').remove();
                $.ajax({
                    url: "creditsByPortions",
                    type: "POST",
                    data: {num: portionNum.val()-1},
                    success: function (crList) {
                        for (var i = 0; i < crList.length; i++) {

                            var inLot = "";
                            if(crList[i].lot!=null){
                                inLot= crList[i].lot.id;
                            }
                            var trR = $('<tr align="center" class="tr">' +
                                    '<td>' + crList[i].id + '</td>' +
                                    '<td>' + crList[i].inn + '</td>' +
                                    '<td>' + crList[i].contractNum + '</td>' +
                                    '<td>' + crList[i].fio + '</td>' +
                                    '<td>' + crList[i].region + '</td>' +
                                    '<td>' + crList[i].assetTypeCode + '</td>' +
                                    '<td>' + crList[i].assetGroupCode + '</td>' +
                                    '<td>' + crList[i].clientType + '</td>' +
                                    '<td>' + new Date(crList[i].contractStart).toISOString().substring(0, 10) + '</td>' +
                                    '<td>' + new Date(crList[i].contractEnd).toISOString().substring(0, 10) + '</td>' +
                                    '<td>' + crList[i].curr + '</td>' +
                                    '<td>' + crList[i].product + '</td>' +
                                    '<td>' + crList[i].zb + '</td>' +
                                    '<td>' + crList[i].dpd + '</td>' +
                                    '<td>' + crList[i].creditPrice + '</td>' +
                                    '<td class="discPriceTd">'+ crList[i].discountPrice+'</td>' +
                                    '<td class="factPriceTd">'+ crList[i].factPrice +'</td>' +
                                    '<td class="lotId">'+ inLot +'</td>' +
                                    '</tr>');
                            tab.append(trR);
                        }
                        $('#crdDiv').append(tab);
                    }
                })
            }
            getPortion();

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
            portionNum.keydown(function(event){
                if(event.keyCode==13)
                    getPortion();
            })

        })
    </script>
</head>
<body id="bd">
<div>
    <table align="center">
        <tr>
            <td id="range" colspan="3" align="center">
            </td>
        </tr>
        <tr>
            <td><button id="back">назад</button></td>
            <td><input value="1" type="number" id="portion" /></td>
            <td><button id="forward">вперед</button></td>
        </tr>
    </table>
    <table id="crdsTab" border="light" class="table">
        <tr bgcolor="#bdb76b">
            <th>ID</th>
            <th>ІНН</th>
            <th>N Договору</th>
            <th>ФІО</th>
            <th>Регіон</th>
            <th>Код типу активу</th>
            <th>Код групи активів</th>
            <th>Тип клієнта</th>
            <th>Початок договору</th>
            <th>Кінець договору</th>
            <th>Валюта</th>
            <th>Продукт</th>
            <th>Загальний борг, грн.</th>
            <th>dpd</th>
            <th>Оціночна вартість, грн.</th>
            <th>Стартова ціна, грн.</th>
            <th>Ціна фактичного продажу, грн</th>
            <th>В лоті</th>
        </tr>
    </table>

</div>

</body>
</html>
