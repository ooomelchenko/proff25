
<%@ page import="java.util.List" %>
<%@ page import="nadrabank.domain.Exchange" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Створення лоту</title>
    <script src="js/jquery-1.11.1.js"></script>
    <script>
        $(document).ready(function () {
            var ftab = $('#findTab');
            var ltab = $('#lotTab');
            var price = $('#priceId');
            var count = $('#kolId');

            $('#findCrdt').on('click', function () {
                ltab.hide();
                $('#findTab').find('.ftr').remove();
                ftab.show();
                $.ajax({
                    url: "objectsByInNum",
                    method: "POST",
                    data: { inn: $('#inn').val() },
                    success(crdt){
                        for (var i = 0; i < crdt.length; i++) {
                            var tr = $('<tr class="ftr" align="center">' +
                                    '<td class="idLot">' + crdt[i].id + '</td>' +
                                    '<td>' + crdt[i].inn + '</td>' +
                                    '<td>' + crdt[i].asset_name + '</td>' +
                                    '<td>' + crdt[i].asset_descr + '</td>' +
                                    '<td>' + crdt[i].region + '</td>' +
                                    '<td>' + crdt[i].zb + '</td>' +
                                    '<td>' + crdt[i].rv + '</td>' +
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
                            var lids = ltab.find('.idLot');
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
                var tdId = ltab.find('.idLot');

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
                var tdId = ltab.find('.idLot');
                var idl = "";
                for (var i = 0; i < tdId.length; i++) {
                    idl = idl + ',' + tdId[i].innerHTML;
                }
                $.ajax({
                    url: "createSLot",
                    method: "POST",
                    data: {
                        idMass: idl,
                        comment: $('#commId').val(),
                        exchange: $('#exId').val()
                    },
                    success(ok){
                        if (ok == '1') {
                            alert("Лот створено!");
                            $('.ftr').remove();
                            price.text('0');
                            count.text('0');
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
                            <input input id="inn" type="text" style="background-color: lightgreen; width: 100%">
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
                    <tr>
                        <td colspan="1">Біржа</td>
                        <td colspan="2" id="exchangeId">
                            <select id="exId" name="exSelector" style="width: 100%">
                            <%
                                List<Exchange> exchangesList = (List<Exchange>) request.getAttribute("exList");
                                if (exchangesList != null) {
                                    for (Exchange ex : exchangesList) {
                            %>
                            <option value="<%=ex.getId()%>">
                                <%out.print(ex.getCompanyName() + " ЄДРПОУ: " + ex.getInn());%>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select></td>
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

<div id="crdtList" class="view" style="width: 100%">
    <table id="findTab" class="table" border="2" hidden="hidden" bgcolor="#ffffe0">
        <tr align="center" bgcolor="#ffefd5">
            <th>ID</th>
            <th>Інвентарний №</th>
            <th>Назва активу</th>
            <th>Опис об'єкту</th>
            <th>Регіон</th>
            <th>Балансова вартість</th>
            <th>Оціночна вартість, грн.</th>
        </tr>
    </table>
    <table id="lotTab" class="table" border="3" hidden="hidden" bgcolor="lightcyan">
        <tr align="center" bgcolor="#00ffff">
            <th>ID</th>
            <th>Інвентарний №</th>
            <th>Назва активу</th>
            <th>Опис об'єкту</th>
            <th>Регіон</th>
            <th>Балансова вартість</th>
            <th>Оціночна вартість, грн.</th>
        </tr>
    </table>
</div>

</body>
</html>
