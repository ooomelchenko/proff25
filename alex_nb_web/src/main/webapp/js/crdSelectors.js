$(document).ready(function () {

    $('#regView').bind('click', function () {
        if ($('#regsTab').is('.table') === false) {
            $.ajax({
                    url: 'regions',
                    type: "POST",
                    data: {},
                    success: function (regs) {
                        var rtab = $('<table id="regsTab" class="table" bgcolor="#fffff0" ></table>');
                        var j = 0;
                        var tr = ($('<tr></tr>'));
                        for (var i = 0; i < regs.length; i++) {
                            if (j == 4) {
                                tr = ($('<tr></tr>'));
                                td = ($('<td><input class="checkru" type="checkbox" checked="checked">' + regs[i] + '</td>'));
                                tr.append(td);
                                j = 1;
                            }
                            else {
                                var td = ($('<td><input class="checkru" type="checkbox" checked="checked">' + regs[i] + '</td>'));
                                tr.append(td);
                                rtab.append(tr);
                                j++;
                            }
                        }
                        $('#regSel').append(rtab);
                    }
                }
            )
        }
        else {
            if ($('#regsTab').is(':hidden')) {
                $('#regsTab').show();
            }
            else
                $('#regsTab').hide();
        }
    });
    $('#tpView').bind('click', function () {
        if ($('#typTab').is('.table') === false) {
            $.ajax({
                url: 'crType',
                type: "POST",
                data: {},
                success: function (types) {
                    var ttab = $('<table id="typTab" class="table" bgcolor="#fffff0"></table>');
                    for (var i = 0; i < types.length; i++) {
                        var trt = ($('<tr></tr>'));
                        var tdt = ($('<td><input class="checktyp" type="checkbox" checked="checked">' + types[i] + '</td>'));
                        trt.append(tdt);
                        ttab.append(trt);
                    }
                    $('#tpSel').append(ttab);
                }
            })
        }
        else {
            if ($('#typTab').is(':hidden')) {
                $('#typTab').show();
            }
            else
                $('#typTab').hide();
        }
    });
    $('#curView').bind('click', function () {
        if ($('#curTab').is('.table') === false) {
            $.ajax({
                url: 'getCurs',
                type: "POST",
                data: {},
                success: function (currs) {
                    var ctab = $('<table id="curTab" class="table" bgcolor="#fffff0"></table>');
                    for (var i = 0; i < currs.length; i++) {
                        var crt = ($('<tr></tr>'));
                        var cdt = ($('<td><input class="checkcur" type="checkbox" checked="checked">' + currs[i] + '</td>'));
                        crt.append(cdt);
                        ctab.append(crt);
                    }
                    $('#curSel').append(ctab);
                }
            })
        }
        else {
            if ($('#curTab').is(':hidden')) {
                $('#curTab').show();
            }
            else $('#curTab').hide();
        }
    })
});