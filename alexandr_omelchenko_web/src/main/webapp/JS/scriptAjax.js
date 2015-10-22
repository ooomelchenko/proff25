var req;

function start(){
    console.log('in start');
    req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if(req.readyState==4 && req.status==200){
          //  alert(req.responseText);
            var name = document.getElementById("sr");
            name.innerHTML = req.responseText;
        }
    }
}

function ajaxF(){
    console.log('in ajaxF');
    req.open("POST", '/ajax1?login=' + document.getElementById('l').value+'&password=' + document.getElementById('p').value, true);//
req.send();
}