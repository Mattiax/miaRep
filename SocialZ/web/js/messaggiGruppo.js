/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var utente;
$(document).ready(function () {
    utente = document.cookie.substr(9);
    addRowHandlers();
    $("#invioMess").click(function () {
        var mess = $("#inputMess").val();
        if (mess !== '') {
            alert($("#dest").text());
            mandaMessaggio(mess);
        }
    });
});


$(document).on("click", "#chatDiv", "#pos", function () {
    alert($("#pos").text());
    $.ajax({
        url: 'eliminaMessaggioGruppo',
        type: 'POST',
        data: {id: $("#pos").text()},
        success: function (data) {
            //elimina div
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
});

function mandaMessaggio(msg) {
    var mit = $("#mittente").text(), dest = $("#dest").text();
    $.ajax({
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

    var table = document.getElementById("tabellaGruppi");
    var rows = table.getElementsByTagName("tr");
    for (i = 0; i < rows.length; i++) {
        var currentRow = table.rows[i];
        var createClickHandler =
                function (row)
                {
                    return function () {
                        var cell = row.getElementsByTagName("td")[0];
                        var id = cell.innerHTML;
                        alert(id);
                        $("#dest").text(id);
                        showMessages(id);
                    };
                };

        currentRow.onclick = createClickHandler(currentRow);
    }
}

function showMessages(email) {
    $("#storicoChat").text("");
    $.ajax({
        url: 'getConvGruppo',
        data: {mittente: utente, destinatario: email},
        success: function (data) {
            if (data === "") {
                $('#storicoChat').append("<p>Non fai parte di questo gruppo</p><p><button id=\"partecipaBtn\">PARTECIPA</button></p>");
            } else {
                var obj = jQuery.parseJSON(data);
                makeTable(obj);
            }
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
    var prova = "";
    for (var i in data)
    {
        prova += "<div id=\"chatDiv\"><p>" + data[i].mittente + ":</p><p id=\"messaggioDiv\">" + data[i].messaggio + "</p><p id=\"dataDiv\">" + data[i].dataOra + "</p><p id=\"pos\" hidden=\"true\">" + data[i].pos + "</p></div>";
    }
    prova += "</div>";
    $('#storicoChat').append(prova);
}