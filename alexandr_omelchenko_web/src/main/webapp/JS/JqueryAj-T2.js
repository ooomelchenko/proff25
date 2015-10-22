$(document).ready(function(){
        var result;
        $('#button').bind('click', function(){
            $.ajax({url:'/ajax2', type: "POST", success:function(res){
                var clients  = res.split('|');
                result = $('<ul></ul>');
               var  re ='<ul>';
                for(var i = 0; i < clients.length; i++){
                  //  result.append('<li>' + clients[i]+ '</li>');
                    re=re+'<li>' + clients[i]+ '</li>';
                }
                re=re+'</ul>';
                $('body').append(re);
                console.log(re);
            }
            })
        });
    }
);