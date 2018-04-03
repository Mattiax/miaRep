/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var utente;
$(document).ready(function () {
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        if (c.indexOf("mittente") !== -1) {
            utente = document.cookie.substr(9);
            break;
        }
    }
    $("#approvaRichiesta").click(function (){
       var id=$("#nRichiesta").text(); 
       var richiedente=$("#richiedente").text(); 
       var gruppo=$("#gruppo").text(); 
       approvaRichiesta(id,richiedente,gruppo);
    });
});

function approvaRichiesta(id,richiedente,gruppo){
       $.ajax({
        url: 'approvaRichiesta',
        type: 'POST',
        data: {id: id,richiedente:richiedente,gruppo:gruppo},
        success: function (data) {
            $('tr#richiesta td:contains(' + id + ')').parent().remove();
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
