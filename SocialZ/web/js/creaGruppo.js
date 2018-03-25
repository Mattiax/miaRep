/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var utente;
$(document).ready(function () {
    utente = document.cookie.substr(9);
    $("#crea").click(function () {
        ottieniDati();
    });
});

function ottieniDati() {
    var json = "{amministratore:";
    json += utente;
    json += ",nomeGruppo:" + $("#nome").val();
    var descrizione = $("#descrizione").val();
    if (descrizione === "") {
        json += ",descrizione:null";
    } else {
        json += ",descrizione:" + descrizione;
    }
    json += ",partecipanti:[";
    $('#partecipanti input:checked').each(function () {
        json += ($(this).attr('name')) + ",";
    });
    json += "]}";
    json = json.replace(",]}", "]}");
    $.ajax({
        url: 'creaGruppo',
        type: 'POST',
        contentType: "application/json",
        data: json/*,
        success: function (data) {
            alert("ok");
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
        }*/
    });
}