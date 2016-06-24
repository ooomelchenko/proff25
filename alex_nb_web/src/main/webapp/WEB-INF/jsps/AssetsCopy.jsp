<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список об'єктів</title>
    <script src="js/jquery-1.11.1.js"></script>
    <script>
        $(document).ready(function(){

            var countOfPortions;
            $.ajax({
                url: "getTotalCountOfObjects",
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
                    url: "objectsByPortions",
                    type: "POST",
                    data: {num: portionNum.val()-1},
                    success: function (objList) {
                        for (var i = 0; i < objList.length; i++) {

                            var inLot = (objList[i].lot==null) ? "" : objList[i].lot.id ;
                            var isSold = (objList[i].isSold) ? "Так" : "Ні" ;
                            var approveNBU = (objList[i].approveNBU) ? "Так" : "Ні" ;

                            var trR = $('<tr align="center" class="tr">' +
                                    '<td>' + objList[i].id + '</td>' +
                                    '<td>' + objList[i].assetTypeCode + '</td>' +
                                    '<td>' + objList[i].assetGroupCode + '</td>' +
                                    '<td>' + objList[i].inn + '</td>' +
                                    '<td>' + objList[i].asset_name + '</td>' +
                                    '<td>' + objList[i].asset_descr + '</td>' +
                                    '<td>' + objList[i].viddil + '</td>' +

                                    '<td>' + new Date(objList[i].eksplDate).toISOString().substring(0, 10) + '</td>' +
                                    '<td>' + objList[i].originalPrice + '</td>' +
                                    '<td>' + objList[i].zb + '</td>' +
                                    '<td>' + objList[i].rvNoPdv + '</td>' +

                                    '<td>' + objList[i].region + '</td>' +
                                    '<td>' + objList[i].rv + '</td>' +
                                    '<td class="discPriceTd">'+ objList[i].bidPrice+'</td>' +
                                    '<td class="factPriceTd">'+ objList[i].factPrice +'</td>' +
                                    '<td class="lotId">'+ inLot +'</td>' +
                                    '<td>'+ isSold +'</td>' +
                                    '<td>'+ approveNBU+'</td>'+
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
            <th>Код типу активу</th>
            <th>Код групи активів</th>
            <th>Інвентарний №</th>
            <th>Назва активу</th>
            <th>Опис об'єкту</th>
            <th>Відділення</th>
            <th>Дата введення в експлуатацію</th>
            <th>Первісна вартість</th>
            <th>Балансова вартість</th>
            <th>Оціночна вартість без ПДВ, грн.</th>
            <th>Регіон</th>
            <th>Оціночна вартість, грн.</th>
            <th>Ціна на торгах, грн.</th>
            <th>Ціна факт продажу, грн</th>
            <th>В лоті</th>
            <th>Продано</th>
            <th>Погоджено НБУ</th>
        </tr>
    </table>

</div>

</body>
</html>
