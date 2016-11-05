<%@ page import="nadrabank.domain.Bid" %>
<%@ page import="nadrabank.domain.Lot" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Лоти</title>
    <script src="js/jquery-1.11.1.js"></script>
    <script src="js/lotsMenu.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            function checkBids(){
            $('.bidStatus').each(function(){
                if($(this).text()=="Торги не відбулись")
                    $(this).parent().css('color', 'red');
            });
            }
            checkBids();
            var dateSelector = $('.dateSelector');
            var exSelector = $('.exSelector');
            var bidStatusSelector = $('.bidStatusSelector');

            bidStatusSelector.click(function(){
                $('.bidStatusHide').each(function(){
                    $(this).removeClass();
                    $(this).addClass("bidStatus");
                });

                if ($(this).text()!="всі"){
                    var selectedStatus = $(this).text();
                    $('.bidStatus').each(function(){
                        if($(this).text()!=selectedStatus){
                            $(this).removeClass();
                            $(this).addClass("bidStatusHide");
                        }
                    });
                }
                filterLots();
            });

            dateSelector.click(function(){
                $('.bidDateHide').each(function(){
                    $(this).removeClass();
                    $(this).addClass("bidDate");
                });

                if ($(this).text()!="всі дати"){
                    var selectedDate = $(this).text();
                    $('.bidDate').each(function(){
                        if($(this).text()!=selectedDate){
                            $(this).removeClass();
                            $(this).addClass("bidDateHide");
                        }
                    });
                }
                filterLots();
            });
            exSelector.click(function(){
                $('.companyHide').each(function(){
                    $(this).removeClass();
                    $(this).addClass("company");
                });

                if ($(this).text()!="всі біржі"){
                    var exSelected = $(this).text();
                    $('.company').each(function(){
                        if($(this).text()!=exSelected){
                            $(this).removeClass();
                            $(this).addClass("companyHide");
                        }
                    });
                }
                filterLots()
            });

            function filterLots(){
                $('.trL').show();
                $('.bidDateHide').each(function(){
                    $(this).parent().hide();
                });
                $('.companyHide').each(function(){
                    $(this).parent().hide();
                });
                $('.bidStatusHide').each(function(){
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
        });
    </script>
    <style type="text/css">
        .lotId, .bidDate, .bidStatus, .bidStage, .workstage{
            font-weight: bold;
        }
        .dateSelector, .exSelector, .bidStatusSelector :hover{
        background-color: #00ffff;
        }
        .spoiler_body {
            display:none;
            cursor:pointer;
            float:left;
            width:auto;
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
        .trL {
            cursor: pointer;
        }
        .trL:hover {
            background-color : white;
        }
        button {
            cursor: pointer;
        }
    </style>
</head>
<body id="bd" style="background-color: mintcream">
<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    List<Lot> lotList = (List<Lot>) request.getAttribute("lotList");

    TreeSet <Date> dateSet = new TreeSet<Date>();
    for(Lot lot: lotList){
        if(lot.getBid()!=null&&lot.getBid().getBidDate()!=null)
        dateSet.add(lot.getBid().getBidDate());
    }
    TreeSet <String> exchangeSet = new TreeSet<String>();
    for(Lot lot: lotList){
        if(lot.getBid()!=null&&lot.getBid().getExchange()!=null)
        exchangeSet.add(lot.getBid().getExchange().getCompanyName());
    }
%>
<button onclick="location.href='index'">Назад до меню</button>
<H1 align="center">Меню лотів</H1>
<div style="width: 100%">
    <table>
        <tr align="left">
            <td>
                <button id="createSingleLot" class="button" style="height: 40px ; font-size: 100%"
                        onclick="location.href ='lotCreator'">Формування лоту активів
                </button>
            </td>
            <td>
                <img src="css/images/plus.jpeg" width="30px" height="30px"/>
            </td>
            <td>
                <button id="createCrLot" class="button" style="height: 40px ; font-size: 100%"
                        onclick="location.href ='lotCreditsCreator'">Формування лоту кредитів
                </button>
            </td>
        </tr>
    </table>
</div>
<div id="lotsDiv" class="view">
    <table id="lTable" border="light" class="table" style="background-color: lightcyan">
        <tr class="trh" style="background-color: #00ffff">
            <th>ID</th>
            <th title="Натисніть для відображення фільтру">
                <div class="spoiler_links" style="width: 100%; height: 100%">Дата торгів^
                    <div class="spoiler_body" >
                        <b class="dateSelector" >всі дати</b>
                        <br>
                        <% for(Date date: dateSet){%>
                        <b class="dateSelector" ><%out.print(sdf.format(date));%></b>
                        <br>
                        <%}%>
                    </div>
                </div>
            </th>
            <th title="Натисніть для відображення фільтру">
                <div class="spoiler_links" style="width: 100%; height: 100%">Біржа^
                    <div class="spoiler_body" >
                        <b class="exSelector">всі біржі</b>
                        <br>
                        <% for(String exchangeName: exchangeSet){%>
                        <b class="exSelector"><%out.print(exchangeName);%></b>
                        <br>
                        <%}%>
                    </div>
                </div>
            </th>
            <th>Оціночна вартість, грн.</th>
            <th>Торги</th>
            <th title="Натисніть для відображення фільтру">
                <div class="spoiler_links" style="width: 100%; height: 100%">Статус аукціону^
                    <div class="spoiler_body">
                        <b class ="bidStatusSelector">всі</b>
                        <br>
                        <b class ="bidStatusSelector">Торги не відбулись</b>
                        <br>
                        <b class ="bidStatusSelector">Торги відбулись</b>
                    </div>
                </div>
            </th>
            <th>№ Лоту в публікації</th>
            <th>Початкова ціна лоту, грн.</th>
            <%--<th>Кількість зареєстрованих учасиків</th>--%>
            <th>Ціна реалізації, грн.</th>
            <th>Статус оплати</th>
            <th>Сума сплати</th>
            <%--<th>Залишок оплати, грн.</th>--%>
            <th>Стадія роботи</th>
            <th>Покупець</th>
            <th>Акт підписано</th>
            <%--<th>Співробітник</th>--%>
            <th>Коментар</th>
        </tr>
        <%for(Lot lot: lotList){Bid bid = lot.getBid();%>
        <tr class="trL">
            <td align="center" class="lotId" <%if(lot.getLotType()==0) out.print("bgcolor='#add8e6'");%> ><%=lot.getId()%></td>
            <td align="center" class="bidDate"><%if(bid!=null&&bid.getBidDate()!=null){out.print(sdf.format(bid.getBidDate()));}%></td>
            <td align="center" class="company"><%if(bid!=null&&bid.getExchange()!=null)out.print(bid.getExchange().getCompanyName());%></td>
            <td align="center" class="sumOfCrd"></td>
            <td align="center" class="bidStage"><%out.print(lot.getBidStage());%></td>
            <td align="center" class="bidStatus" ><%if(lot.getStatus()!=null)out.print(lot.getStatus());%></td>
            <td align="center" class="lotNum"><%if(lot.getLotNum()!=null)out.print(lot.getLotNum());%></td>
            <td align="center" class="startPrice"><%if(lot.getStartPrice()!=null)out.print(lot.getStartPrice());%></td>
            <td align="center" class="factPrice"><%if(lot.getFactPrice()!=null)out.print(lot.getFactPrice());%></td>
            <td align="center" class="payStatus"></td>
            <td align="center" class="paymentsSum"></td>
            <%--<td align="center" class="residualToPay"></td>--%>
            <td align="center" class="workstage"><%=lot.getWorkStage()%></td>
            <td align="center" class="customer"><%if(lot.getCustomerName()!=null)out.print(lot.getCustomerName());%></td>
            <td align="center" class="aktDate"><%if(lot.getActSignedDate()!=null)out.print(sdf.format(lot.getActSignedDate()));%></td>
            <%--<td align="center" class="user"><%=lot.getUser().getLogin()%></td>--%>
            <td align="center" class="comment"><%if(lot.getComment()!=null)out.print(lot.getComment());%></td>
        </tr>
        <%}%>
    </table>

</div>

</body>
</html>