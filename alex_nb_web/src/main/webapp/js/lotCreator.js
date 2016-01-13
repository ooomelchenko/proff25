function getCheckedRu() {
    var chkRu = $(".checkru").map(function (indx, element) {
        return $(element);
    });
    var regs = "";
    for (var i = 0; i < chkRu.length; i++) {
        if (chkRu[i].prop('checked')) {
            regs = regs + "," + chkRu[i].parent().text();
        }
    }
    return regs.substring(1);
}
function getCheckedTypes() {
    var chkTp = $(".checktyp").map(function (ind, elem) {
        return $(elem);
    });
    var types = "";
    for (var j = 0; j < chkTp.length; j++) {
        if (chkTp[j].prop('checked')) {
            types = types + "," + chkTp[j].parent().text();
        }
    }
    return types.substring(1);
}
function getCheckedCurr() {
    var chkCur = $('.checkcur').map(function (indx, element) {
        return $(element);
    });
    var curr = "";
    for (var i = 0; i < chkCur.length; i++) {
        if (chkCur[i].prop('checked')) {
            curr = curr + "," + chkCur[i].parent().text();
        }
    }
    return curr.substring(1);
}

$(document).ready(function () {
    $('#showParam').bind('click', function () {
        $.ajax({
            url: 'selectedParam',
            type: "POST",
            data: {
                curs: getCheckedCurr(),
                types: getCheckedTypes(),
                regions: getCheckedRu(),
                dpdmin: $('#dpdmin').val(),
                dpdmax: $('#dpdmax').val(),
                zbmin: $('#zbmin').val(),
                zbmax: $('#zbmax').val()
            },
            success: function (res) {
                document.getElementById('zbId').innerHTML = res[0];
                document.getElementById('kolId').innerHTML = res[1];
            }
        });
    });
    $('#showCred').bind('click', function () {
        if (document.getElementById('crdList').hasChildNodes()) {
            document.getElementById('crdList').innerHTML = null
        }
        else {
            $.ajax({
                url: 'selectedParam',
                type: "POST",
                data: {
                    curs: getCheckedCurr(),
                    types: getCheckedTypes(),
                    regions: getCheckedRu(),
                    dpdmin: $('#dpdmin').val(),
                    dpdmax: $('#dpdmax').val(),
                    zbmin: $('#zbmin').val(),
                    zbmax: $('#zbmax').val()
                },
                success: function (res) {
                    document.getElementById('zbId').innerHTML = res[0];
                    document.getElementById('kolId').innerHTML = res[1];
                }
            });
            $.ajax({
                url: 'selectedCRD',
                type: "POST",
                data: {
                    curs: getCheckedCurr(),
                    types: getCheckedTypes(),
                    regions: getCheckedRu(),
                    dpdmin: $('#dpdmin').val(),
                    dpdmax: $('#dpdmax').val(),
                    zbmin: $('#zbmin').val(),
                    zbmax: $('#zbmax').val()
                },
                success: function (res) {
                    if (res.length == 0) {
                        var inf = $('<p>Кредитів із заданими параметрами не знайдено</p>');
                        $('#crdList').append(inf);
                    }
                    else {
                        var tab = $('<table id="tbl" border="light" class="table" style="background-color: lightcyan">Вибірка кредитів</table>');

                        for (var i = 0; i < res.length; i++) {
                            var tr = $('<tr></tr>');
                            var stroka = res[i].split('|');
                            for (var j = 0; j < stroka.length; j++) {
                                if (i == 0) {
                                    tr.append($('<th style="background-color:#bdb76b">' + stroka[j] + '</th>'))
                                }
                                else tr.append($('<td>' + stroka[j] + '</td>'))
                            }
                            tab.append(tr);
                        }
                        $('#crdList').append(tab);
                    }
                }
            })
        }
    });
    $('#createLot').bind('click', function () {
        $.ajax({
                url: 'createLot',
                type: "GET",
                data: {
                    companyId: $('#compId').val(),
                    comment: $('#commId').val(),
                    curs: getCheckedCurr(),
                    types: getCheckedTypes(),
                    regions: getCheckedRu(),
                    dpdmin: $('#dpdmin').val(),
                    dpdmax: $('#dpdmax').val(),
                    zbmin: $('#zbmin').val(),
                    zbmax: $('#zbmax').val()
                },
                success: function (res) {
                    if (res == 0) {
                        alert("Лот не створено!");
                    }
                    else {
                        alert("Лот створено успішно!");
                    }
                }
            }
        )
    });
});