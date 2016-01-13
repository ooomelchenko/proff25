
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
                        var result = $('<ul></ul>');
                        for(var i=0;i<res.length;i++){
                            result.append($('<li>'+res[i]+'</li>'));
                        }
                        $('#lotList').append(result);
                    }
                }
            }
        )
    })
});