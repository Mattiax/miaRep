/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    addRowHandlers();
    $("#invioMess").click(function () {
        var mess = $("#inputMess").val();
        if (mess !== '') {
            alert($("#dest").text());
            mandaMessaggio(mess);
        }
    });
});


$(document).on( "click","#chatDiv","#pos", function() {
  alert($("#pos").text());
});

function mandaMessaggio(msg) {
    var mit = $("#mittente").text(), dest = $("#dest").text();
    $.ajax({
        // edit to add steve's suggestion.
        //url: "/ControllerName/ActionName",
        url: 'salvaMessaggio',
        type: 'POST',
        data: {mittente: mit, destinatario: dest, messaggio: msg},
        success: function (data) {
            var prova = "<div id=\"chatDiv\"><p>" + mit + ":</p><p id=\"messaggioDiv\">" + msg + "</p><p id=\"dataDiv\">" + new Date() + "</p></div></div>";
            $('#storicoChat').append(prova);
            $("#inputMess").val("");
        },
        statusCode: {
            404: function (content) {
                alert('cannot find resource');
            },
            500: function (content) {
                alert('internal server error');
            }
        },
        error: function (req, status, errorObj) {
            alert(status + errorObj)
        }
    });
}

function addRowHandlers() {
    var table = document.getElementById("tableId");
    var rows = table.getElementsByTagName("tr");
    for (i = 0; i < rows.length; i++) {
        var currentRow = table.rows[i];
        var createClickHandler =
                function (row)
                {
                    return function () {
                        var cell = row.getElementsByTagName("td")[3];
                        var id = cell.innerHTML;
                        $("#dest").text(id);
                        showMessages(id);
                    };
                };

        currentRow.onclick = createClickHandler(currentRow);
    }
}

function showMessages(email) {
    //alert(email);
    /* $.get("/SocialZ/provaa", {mittente:'cane@cane.it',mittenteG:'Deepak', destinatario:email,destinatarioG:'Deepak'}, function (data) {  
     alert(data);
     }); */
    $.ajax({
        // edit to add steve's suggestion.
        //url: "/ControllerName/ActionName",
        url: 'provaa',

        data: {mittente: $("#mittente").text(), mittenteG: 'Deepak', destinatario: email, destinatarioG: 'Deepak'},
        success: function (data) {
            // your data could be a View or Json or what ever you returned in your action method 
            // parse your data here

            //alert("data " + data);
            var obj = jQuery.parseJSON(data);
            /*alert("conv " + obj);
             alert(obj.mittente);*/
            makeTable(obj);

        },
        statusCode: {
            404: function (content) {
                alert('cannot find resource');
            },
            500: function (content) {
                alert('internal server error');
            }
        },
        error: function (req, status, errorObj) {
            alert(status + errorObj)
        }
    });
}

function makeTable(json) {
    var data = json.messaggi;
    //var content = "<table> <th>Mittente</th> <th>Destinatario</th> <th>Messaggio</th> <th>Ora</th>";
    var prova = "";
    for (var i in data)
    {
        prova += "<div id=\"chatDiv\"><p>" + data[i].mittente + ":</p><p id=\"messaggioDiv\">" + data[i].messaggio + "</p><p id=\"dataDiv\">" + data[i].dataOra + "</p><p id=\"pos\" hidden=\"true\">" + data[i].pos + "</p></div>";
        //content += "<tr> <td>" + data[i].mittente + "</td><td>" + data[i].destinatario + "</td><td>" + data[i].messaggio + "</td><td>" + data[i].dataOra + "</td></tr>";
    }
    //content += "</table>";
    prova += "</div>";
    $('#storicoChat').append(prova);
}