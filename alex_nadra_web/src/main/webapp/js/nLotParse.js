$(document).ready(function(){

    $('#showLots').bind('click',function(){
        $.ajax({
                url: 'lots',
                type: "GET",
                success: function (res) {
                    document.getElementById('lotList').innerHTML = null;
                    if (res.length==0){
                        var inf =  $('<p>Нет ЛОТОВ</p>');
                        $('#lotList').append(inf);
                    }
                    else{
                        var tab = $('<table id="tbl" border="light" class="table" bgcolor="#f0ffff">Таблица лотов</table>');

                        for(var i=0;i<res.length;i++){
                            var tr = $('<tr></tr>');
                            var stroka = res[i].split('|');
                         // var id = stroka[0];
                       //     tr.append($('<td>РЕДАКТИРОВАТЬ<td>'));

                            for(var j=0; j<stroka.length;j++){
                                tr.append($('<td>'+stroka[j]+'</td>'))
                            }
                            tab.append(tr);
                        }
                        $('#lotList').append(tab);
                    }
                }
            }
        )
    });
    $('#hideLots').bind('click',function(){
      $('#tbl').hide();
    })
});