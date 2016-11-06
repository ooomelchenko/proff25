function getFileNames(objId, objType){

    $.ajax({
        url: "getFileNames",
        method: "POST",
        data:{
            objId : objId,
            objType: objType
        },
        success: function(fileNames){
            for(var i=0; i< fileNames.length; i++){
                var trDoc = $('<tr class="fileTr" title="клікніть двічі для перегляду або завантаження" ></tr>').mouseover(function(){
                    $(this).css({'background-color': "#00ffff"})}).mouseout( function(){
                    $(this).css({'background-color': "white"});});

                var tdDoc =($('<td style="color: darkblue; cursor: pointer">'+fileNames[i]+'</td>')).dblclick(function(){
                    $.ajax({
                        url: "setDocToDownload",
                        type: "GET",
                        data: {docName: $(this).text(),
                            objId : objId,
                            objType: objType},
                        success: function(res){
                            if(res=='1') {
                                window.open("downloadDocument");
                            }
                        }
                    });
                    $(this).text();
                });
                trDoc.append(tdDoc);
                $('#fileList').append(trDoc);
            }
        }
    })
}