
$(document).ready(function(){
    $('#button').bind('click',function(){
        $.ajax({
                url: '/json1',
                type: "POST",
                data: {login: $('#login').val(), password: $('#password').val()},
                success: function (res) {
                    if (res.length==0){
                        $('#info').append('<font id="info" color="RED">'+"Login and password incorrect. Try again."+'</font>');
                    }
                    else{
                        var result = $('<ul></ul>');
                        for(var i=0;i<res.length;i++){
                            result.append($('<li><font color="YELLOW">'+res[i].login+' '+res[i].pass+'</font></li>'));
                        }
                        $('#wrapper').hide();
                        $('#bd').append(result);
                    }
                }
            }
        )
    })
});