$(document).ready(function () {

    $('#showLots').bind('click', function () {
        var t = $('#ltbl');
        if (t.is('table')) {
            $('#lotList').empty();
        }
        else {
            $.ajax({
                    url: 'lots',
                    type: "GET",
                    success: function (res) {
                        document.getElementById('lotList').innerHTML = null;
                        if (res.length === 1) {
                            var inf = $('<p>НЕМАЄ ЛОТІВ</p>');
                            $('#lotList').append(inf);
                        }
                        else {
                            var tab = $('<table id="ltbl" border="light" class="table" style="background-color: lightcyan">Таблиця лотів</table>');
                            for (var i = 0; i < res.length; i++) {
                                var tr = $('<tr></tr>');
                                var td = $('<td align="center"></td>');
                                if (i != 0) {
                                    var butt = $('<button class="buttonRedactor" style="height: 100%; width: 100%">Редагувати</button>');
                                    butt.on('click', function(){
                                        var idlot = $(this).parent().next().text();
                                        location.href = "/lotRedact";
                                    });
                                    td.append(butt);
                                }
                                tr.append(td);
                                var stroka = res[i].split('|');
                                for (var j = 0; j < stroka.length; j++) {
                                    if (i === 0) {
                                        tr.append($('<th align="center" style="background-color:#bdb76b">' + stroka[j] + '</th>'))
                                    }
                                    /*else {
                                     if (j === 3) {
                                     var status = $('<select class="statusSelector"></select>');
                                     var option0 = $('<option class="opt" value="Новий лот">Новий лот</option>');
                                     var option1 = $('<option class="opt" value="Винесення/Отримання рішення МКУА">Винесення/Отримання рішення МКУА</option>');
                                     var option2 = $('<option class="opt" value="Відправлено до ФГВФО">Відправлено до ФГВФО</option>');
                                     var option3 = $('<option class="opt" value="Отримано погодження від фонду">Отримано погодження від фонду</option>');
                                     var option4 = $('<option class="opt" value="Отримано рішення про доопрацювання">Отримано рішення про доопрацювання</option>');
                                     status.append(option0, option1, option2, option3, option4);
                                     var tds = $('<td></td>');
                                     tds.append(status);
                                     tr.append(tds);
                                     if (option0.val() === stroka[j]) {
                                     option0.attr("selected", "selected")
                                     }
                                     if (option1.val() === stroka[j]) {
                                     option1.attr("selected", "selected")
                                     }
                                     if (option2.val() === stroka[j]) {
                                     option2.attr("selected", "selected")
                                     }
                                     if (option3.val() === stroka[j]) {
                                     option3.attr("selected", "selected")
                                     }
                                     if (option4.val() === stroka[j]) {
                                     option4.attr("selected", "selected")
                                     }
                                     }*/
                                    else tr.append($('<td align="center">' + stroka[j] + '</td>'));
                                    //  }
                                }
                                if (i != 0) {
                                    var buttDel = $('<button class="buttonRedactor" style="color: yellow;background-color: red">Видалити</button>');
                                    buttDel.on('click', function () {
                                        var idlot = $(this).parent().prev().prev().prev().prev().prev().prev().prev().prev().prev().text();

                                        $.ajax({
                                            url: "lotDel",
                                            type: "POST",
                                            data: {lotID: idlot},
                                            success: function (result) {
                                                if (result === "1")
                                                    alert("Лот видалено!");
                                                else alert("Лот не видалено!")
                                            }
                                        });

                                    });
                                    var tdd = $('<td align="center" style="background-color: red"></td>');
                                    tdd.append(buttDel);
                                    tr.append(tdd);
                                }
                                tab.append(tr);
                            }
                            $('#lotList').append(tab);
                        }
                    }
                }
            );
        }
    });
});