<%@ page import="nadrabank.domain.Bid" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="nadrabank.domain.Exchange" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="js/jquery-1.11.1.js"></script>
    <script src="js/monthpicker.js"></script>
    <script src="js/lotsMenu.js"></script>
    <script type="text/javascript" src="js/docUploads.js"></script>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.css"/>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.structure.css"/>
    <link rel="stylesheet" media="screen" type="text/css" href="css/jquery-ui.theme.css"/>
    <script type="text/javascript" src="js/jquery-ui.js"></script>

    <script>
        $(document).ready(function () {

            function addDatePicker(dp) {
                dp.datepicker({
                    dateFormat: "yy-mm-dd",
                    dayNamesMin: ["Нд", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"],
                    monthNames: ["січень", "лютий", "березень", "квітень", "травень", "червень",
                        "липень", "серпень", "вересень", "жовтень", "листопад", "грудень"]
                });
            }

            addDatePicker($('.datepicker'));
            addDatePicker($('[datatype = date]'));

            var addBidTr = $('#addBidTr');
            var bidTr = $('.bidTr');
            $('#addBidButt').click(function () {
                if ($(this).val() === "0") {
                    $(this).val('1');
                    addBidTr.show();
                }
                else {
                    $(this).val('0');
                    addBidTr.hide();
                }
            });

            bidTr.dblclick(function () {
                var bid = $(this);
                var bidId = $(this).find('.idBid').text();
                if (bid.next().is('.appendedTr')) {
                    bid.next().remove();
                }
                else {
                    $.ajax({
                        url: "lotsByBid",
                        type: "POST",
                        data: {bidId: bidId},
                        success: function (lots) {
                            if (lots.length > 0) {
                                var tr = $('<tr class="appendedTr"></tr>');
                                var td = $('<td colspan="12"></td>');
                                var tabL = $('<table id="tabL" class="tabL"></table>');

                                var trhL = $('<tr id="trh" class="trh" style="background-color: #00ffff">' +
                                        '<th>ID</th>' +
                                        '<th>№ Лоту</th>' +
                                        '<th>Оціночна вартість, грн.</th>' +
                                        '<th>Статус торгів</th>' +
                                        '<th>Початкова ціна лоту, грн.</th>' +
                                        '<th>Кіль-ть учасників</th>' +
                                        '<th>Ціна реалізації, грн.</th>' +
                                        '<th>Покупець</th>' +
                                        '<th>Статус оплати</th>' +
                                        '<th>Сплачено, грн.</th>' +
                                        '<th>Залишок оплати, грн.</th>' +
                                        '<th>Стадія роботи</th>' +
                                        '<th>Коментар</th>' + '</tr>');

                                tabL.append(trhL);

                                for (var i = 0; i < lots.length; i++) {
                                    var appendedTr = $('<tr bgcolor="lightcyan" class="trL" style="cursor: pointer">' +
                                            '<td class="lotId">' + lots[i].id + '</td>' +
                                            '<td align="center">' + lots[i].lotNum + '</td>' +
                                            '<td align="center" class="sumOfCrd">' + '</td>' +
                                            '<td align="center">' + lots[i].bidStage + '</td>' +
                                            '<td align="center">' + lots[i].startPrice + '</td>' +
                                            '<td align="center">' + lots[i].countOfParticipants + '</td>' +
                                            '<td align="center" class="factPrice">' + lots[i].factPrice + '</td>' +
                                            '<td align="center" class="customer">' + lots[i].customerName + '</td>' +
                                            '<td align="center" class="payStatus">' + '</td>' +
                                            '<td align="center" class="paymentsSum">' + '</td>' +
                                            '<td align="center" class="residualToPay">' + '</td>' +
                                            '<td align="right">' + lots[i].workStage + '</td>' +
                                            '<td>' + lots[i].comment + '</td>' +
                                            '</tr>');
                                    tabL.append(appendedTr);
                                }
                                td.append(tabL);
                                tr.append(td);
                                bid.after(tr);
                            }
                            lotsCalculations();
                        }
                    })
                }
            });

            $('.changeButton').click(function () {
                if ($(this).val() === "1") {
                    $.ajax({
                        url: "changeBidParams",
                        method: "POST",
                        data: {
                            bidId: $(this).parent().parent().find('.idBid').text(),
                            bidDate: $(this).parent().parent().find('.newBD').val(),
                            exId: $(this).parent().parent().find('.ex').val(),
                            newNP: $(this).parent().parent().find('.newNP').val(),
                            newND1: $(this).parent().parent().find('.newND1').val(),
                            newND2: $(this).parent().parent().find('.newND2').val(),
                            newRED: $(this).parent().parent().find('.newRED').val()
                        },
                        success: function (req) {
                            if (req == "1") {
                                alert("Аукціон змінено!");
                                location.href = 'bidMenu';
                            }
                        }
                    });
                }
                else {
                    $(this).val(1);
                    $(this).text("Прийняти");
                    var bd = $(this).parent().parent().find('.bidData');
                    bd.children().show();
                    bd.find('b').hide();
                }
            });
            $('#buttAddBid').click(function () {
                $.ajax({
                    url: "createBid",
                    type: "GET",
                    data: {
                        exId: $('#exId').val(),
                        bidDate: $('#newBidDate').val(),
                        newspaper: $('#newspaper').val(),
                        newsDate1: $('#newsDate1').val(),
                        newsDate2: $('#newsDate2').val(),
                        registrEnd: $('#registrEnd').val()
                    },
                    success: function (res) {
                        if (res === "1") {
                            alert("Аукціон додано!");
                            location.href = 'bidMenu';
                        }
                        else
                            alert("Аукціон НЕ додано!")
                    }
                })
            });
            /*$('.delBidButton').click(function () {
                $.ajax({
                    url: "deleteBid",
                    type: "POST",
                    data: {idBid: $(this).parent().parent().find('.idBid').text()},
                    success: function (res) {
                        if (res === "1") {
                            alert("Аукціон видалено!");
                            location.href = 'bidMenu';
                        }
                        else
                            alert("Аукціон НЕ видалено!")
                    }
                })
            });*/
            $('.showDocs').click(function () {
                $('#addDocForm').show();
                $('#fileList').show();
                $(".fileTr").remove();
                $('#objId').val($(this).parent().parent().find('.idBid').text());
                $('#bidN').text($(this).parent().parent().find('.idBid').text());
                getFileNames($('#objId').val(), $('#objType').val());
            });
            $('.getOgolosh').click(function(){
                $.ajax({
                    url: "setDocToDownload",
                    type: "GET",
                   //data: {objId: $(this).parent().parent().find('.idBid').text(),
                        data: {objId: $('#objId').val(),
                        objType: "bid",
                        docName: null
                    },


                    success: function(res){
                        if(res=='1') {
                            window.open("downloadOgolosh");
                        }
                    }
                });
            });
        })
    </script>
    <style type="text/css">
        #docTd {
            border: thin;
        }
        .bidTr {
            cursor: pointer;
            background-color: #ffffe0
        }
        .bidTr:hover {
            background-color: white;
            color: darkblue;
        }
        #bidN{
            cursor: pointer;
            color: darkblue;
            font-size: larger;
            font-weight: bold;
         }
        #bidN:hover{
            font-size: x-large;
        }
        #dodatok2:hover{
            color: darkblue;
            background-color: white;
            cursor: pointer;
            font-weight: bold;
        }
    </style>
    <title>Торги</title>
</head>
<body style="background-color: mintcream">
<button onclick="location.href='index'">Назад до меню</button>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    List<Bid> bidList = (List<Bid>) request.getAttribute("bidList");
    List<Exchange> exchangeList = (List<Exchange>) request.getAttribute("exchangeList");
%>
<H1 align="center">Торги</H1>

<div id="bidDiv" class="view">
    <table align="top">
        <tr align="top">
            <td>
                <button id="addBidButt" value="0">Додати торги</button>
                <table id="bidtbl" border="light" style="background-color: sandybrown">
                    <tr id="addBidTr" hidden="hidden">
                        <td></td>
                        <td><input id="newBidDate" class="datepicker" title="оберіть дату аукціону"></td>
                        <td class="ex">
                            <select id="exId">
                                <%for (Exchange ex : exchangeList) {%>
                                <option value="<%out.print(ex.getId());%>">
                                    <%
                                        out.print(ex.getCompanyName());
                                    %>
                                </option>
                                <%
                                    }
                                %>
                            </select>
                        </td>
                        <td><input type="text" id="newspaper" title="заповніть назву газети"></td>
                        <td><input id="newsDate1" class="datepicker" title="оберіть дату публікації 1"></td>
                        <td><input id="newsDate2" class="datepicker" title="оберіть дату публікації 2"></td>
                        <td><input id="registrEnd" class="datepicker" title="введіть дату кінця реєстрації"></td>
                        <td>
                            <button id="buttAddBid">Підтвердити</button>
                        </td>
                    </tr>

                    <tr id="addTr">
                        <th>ID</th>
                        <th>Дата торгів</th>
                        <th>Біржа</th>
                        <th>Газета</th>
                        <th>Дата публікації 1</th>
                        <th>Дата публікації 2</th>
                        <th>Кінець реєстрації</th>
                    </tr>
                    <%for (Bid bid : bidList) {%>
                    <tr class="bidTr" align="center">
                        <td class="idBid"><%=bid.getId()%></td>
                        <td class="bidData"><input class="newBD" datatype="date" hidden="hidden" value="<%if(bid.getBidDate()!=null)out.print(sdf.format(bid.getBidDate()));%>">
                            <b><%if (bid.getBidDate() != null) out.print(sdf.format(bid.getBidDate()));%></b></td>
                        <td class="bidData">
                            <select class="ex" hidden="hidden">
                                <% for (Exchange ex : exchangeList) {%>
                                <option value="<%=ex.getId()%>" <%
                                    if (ex.getCompanyName().equals(bid.getExchange().getCompanyName()))
                                        out.print("selected=selected");
                                %>>
                                    <%out.print(ex.getCompanyName());%>
                                </option>
                                <%}%>
                            </select>
                            <b><%=bid.getExchange().getCompanyName()%>
                            </b>
                        </td>
                        <td class="bidData"><input class="newNP" type="text" hidden="hidden"
                                                   value="<%if(bid.getNewspaper()!=null)out.print(bid.getNewspaper());%>"><b><%=bid.getNewspaper()%>
                        </b></td>
                        <td class="bidData"><input class="newND1" datatype="date" hidden="hidden"
                                                   value="<%if(bid.getNews1Date()!=null)out.print(sdf.format(bid.getNews1Date()));%>">
                            <b><%if (bid.getNews1Date() != null) out.print(sdf.format(bid.getNews1Date()));%></b></td>
                        <td class="bidData"><input class="newND2" datatype="date" hidden="hidden"
                                                   value="<%if(bid.getNews2Date()!=null)out.print(sdf.format(bid.getNews2Date()));%>">
                            <b><%if (bid.getNews2Date()!= null) out.print(sdf.format(bid.getNews2Date()));%></b></td>
                        <td class="bidData"><input class="newRED" datatype="date" hidden="hidden"
                                                   value="<%if(bid.getRegistrEndDate()!=null)out.print(sdf.format(bid.getRegistrEndDate()));%>">
                            <b><%if(bid.getRegistrEndDate()!= null) out.print(sdf.format(bid.getRegistrEndDate()));%>
                            </b></td>
                        <td>
                            <button class="changeButton">Редагувати</button>
                        </td>
                        <%--<td ><button class="delBidButton">Видалити торги</button></td>--%>
                        <td>
                            <button class="showDocs">Документи</button>
                        </td>

                    </tr>
                    <%}%>
                </table>

            </td>

            <td id="docTd" valign="top" align="center">
                <table id="fileList" border="2" hidden="hidden">
                    <tr>
                        <th style="background-color: lightgoldenrodyellow">
                            Документи по Лоту N <b id="bidN"></b>
                        </th>
                        <td id="dodatok2" title="Клікніть двічі для завантаженн Додатку 2">
                            <a class="getOgolosh">Оголошення</a>
                        </td>
                    </tr>
                </table>
                <table>
                    <tr id="addDocForm" hidden="hidden" align="top">
                        <td align="top">
                            <form method="POST" action="uploadFile" enctype="multipart/form-data" lang="utf8">
                                <input type="text" id="objType" name="objType" value="bid" hidden="hidden">
                                <input type="text" id="objId" name="objId" value="" hidden="hidden">
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
</body>
</html>