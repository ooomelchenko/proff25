<%@ page import="nadrabank.domain.Asset" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Створення лоту</title>
    <script src="js/jquery-1.11.1.js"></script>
    <script>
        $(document).ready(function () {
            var ftab = $('#findTab');
            var ltab = $('#lotTab');

            calculate();

            $('#findCrdt').on('click', function () {
                ltab.hide();
                $('#findTab').find('.ftr').remove();
                ftab.show();
                $.ajax({
                    url: "objectsByInNum",
                    method: "POST",
                    data: {inn: $('#in').val()},
                    success(asset){
                        for (var i = 0; i < asset.length; i++) {
                            var approveNBU = asset[i].approveNBU ? "Так" : "Ні";
                            var tr = $('<tr class="ftr" align="center">' +
                                    '<td class="idAsset">' + asset[i].id + '</td>' +
                                    '<td>' + asset[i].inn + '</td>' +
                                    '<td>' + asset[i].asset_name + '</td>' +
                                    '<td>' + asset[i].asset_descr + '</td>' +
                                    '<td>' + asset[i].region + '</td>' +
                                    '<td>' + asset[i].zb + '</td>' +
                                    '<td>' + asset[i].rv + '</td>' +
                                    '<td>' + approveNBU + '</td>' +
                                    '</tr>');
                            var addButton = $('<button id="addButton">Додати в лот</button>');
                            addButton.on('click', function () {
                                var thisTr = $(this).parent();
                                ltab.append(thisTr);
                                $(this).remove();

                                var delButton = $('<button id="delButton">Видалити</button>');
                                thisTr.append(delButton);
                                delButton.on('click', function () {
                                    $(this).parent().remove();
                                    calculate();
                                });
                                calculate();
                            });
                            var messageTd = $('<td bgcolor="#00ffff">Вже додано до лоту</td>');

                            ftab.append(tr);
                            tr.append(addButton);

                            var idF = tr.children().first().text();
                            var lids = ltab.find('.idAsset');
                            for (var j = 0; j < lids.length; j++) {
                                if (lids[j].innerHTML == idF) {
                                    addButton.remove();
                                    tr.append(messageTd);
                                }
                            }
                        }
                    }
                });
            });
            $('#showLCrdts').on('click', function () {
                ftab.hide();
                ltab.show();
            });
            function calculate() {
                var tdId = ltab.find('.idAsset');

                var idl = "";
                for (var i = 0; i < tdId.length; i++) {
                    idl = idl + ',' + tdId[i].innerHTML;
                }
                $.ajax({
                    url: "sumByInvs",
                    method: "POST",
                    data: {idMass: idl},
                    success(summ){
                        if (summ)
                            $('#priceId').text(summ);
                        $('#kolId').text(tdId.length);
                    }
                });
            }

            $('#createLot').on('click', function createLot() {
                var tdId = ltab.find('.idAsset');
                var idl = "";
                for (var i = 0; i < tdId.length; i++) {
                    idl = idl + ',' + tdId[i].innerHTML;
                }
                $.ajax({
                    url: "createSLot",
                    method: "POST",
                    data: {
                        idMass: idl,
                        comment: $('#commId').val()
                    },
                    success(ok){
                        if (ok == '1') {
                            alert("Лот створено!");
                           // window.close();
                            //window.open("lotRedactor");
                            location.href="lotRedactor";
                        }
                        else
                            alert("Лот не створено!");
                    }
                });
            })
        })
    </script>
</head>

<body style="background-color: mintcream">
<button onclick="location.href='lotMenu'">Назад</button>
<div id="credSort" class="choice-box">
    <H1 align="center">Створення нового лоту</H1>
    <table width="100%">
        <tr>
            <td>
                <table>
                    <tr>
                        <td colspan="2">Інвентарний номер</td>
                        <td colspan="3">
                            <input input id="in" type="text" style="background-color: lightgreen; width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"></td>
                        <td colspan="3">
                            <button id="findCrdt" class="button" style="width: 100%">Знайти</button>
                        </td>
                    </tr>
                </table>
            </td>

            <td align="top">
                <table>
                    <tr>
                        <td colspan="1">Коментар</td>
                        <td colspan="2"><input id="commId" type="text" style="width: 100%"></td>
                    </tr>
                </table>
                <table>
                    <tr>
                        <td>
                            <table border="1" class="table" style="background-color: lightcyan" id="tblParam">
                                <tr>
                                    <th>Ціна лоту, грн.</th>
                                    <th>К-ть об'єктів</th>
                                </tr>
                                <tr>
                                    <td id="priceId" align="center">0</td>
                                    <td id="kolId" align="center">0</td>
                                </tr>
                                <tr>
                                    <td style="border: none" colspan="2">
                                        <button id="showLCrdts" class="button"
                                                style="background-color: cyan; width: 100%"> Показати список лоту
                                        </button>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <button style="font: inherit; color: darkblue; width: 120px; height: 60px"
                                    id="createLot" class="button">СТВОРИТИ ЛОТ
                            </button>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>

    </table>
</div>

<div id="assetList" class="view" style="width: 100%">
    <table id="findTab" class="table" border="2" hidden="hidden" bgcolor="#ffffe0">
        <tr align="center" bgcolor="#ffefd5">
            <th>ID</th>
            <th>Інвентарний №</th>
            <th>Назва активу</th>
            <th>Опис об'єкту</th>
            <th>Регіон</th>
            <th>Балансова вартість</th>
            <th>Оціночна вартість, грн.</th>
            <th>В заставі НБУ</th>
        </tr>
    </table>
    <table id="lotTab" class="table" border="3" hidden="hidden">
        <tr align="center" bgcolor="#90ee90">
            <th>ID</th>
            <th>Інвентарний №</th>
            <th>Назва активу</th>
            <th>Опис об'єкту</th>
            <th>Регіон</th>
            <th>Балансова вартість</th>
            <th>Оціночна вартість, грн.</th>
            <th>В заставі НБУ</th>
        </tr>
        <% List<Asset> assetList = (List<Asset>) request.getAttribute("assetList"); %>
        <% for(Asset asset: assetList){
        if(asset.getLot()==null && !asset.getFondDecision().equals("Відправлено до ФГВФО") && !asset.getFondDecision().equals("") )
        {
        %>
        <tr align="center">
            <td class="idAsset"><%=asset.getId()%></td>
            <td><%=asset.getInn()%></td>
            <td><%=asset.getAsset_name()%></td>
            <td><%=asset.getAsset_descr()%></td>
            <td><%=asset.getRegion()%></td>
            <td><%=asset.getZb()%></td>
            <td><%=asset.getRv()%></td>
            <td><%if(asset.isApproveNBU())out.print("Так");else out.print("Ні");%></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>

</body>
</html>