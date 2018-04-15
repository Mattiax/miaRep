/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        if (c.indexOf("mittente") !== -1) {
            utente = document.cookie.substr(9);
            break;
        }
    }
    $(".approvaRichiesta").click(function () {
        var parent = $(this).parent().parent();
        var id = $(this).parent().parent().children(".nRichiesta").text();
        var richiesta = $(this).parent().parent().children(".richiesta").text();
        approva(id, richiesta, parent);
    });
    $(".eliminaRichiesta").click(function () {
        var parent = $(this).parent().parent();
        var id = $(this).parent().parent().children(".nRichiesta").text();
        eliminaRichiesta(id, parent);
    });
    $(".eliminaGruppo").click(function () {
        var parent = $(this).parent().parent();
        var id = $(this).parent().parent().children(".nome").text();
        alert(id)
        eliminaGruppo(id,parent);
    });
    $(".eliminaUtente").click(function () {
        var parent = $(this).parent().parent();
        var id = $(this).parent().parent().children(".email").text();
        eliminaUtente(id,parent);
    });
});

function approva(id,hobby, parent) {
    hobby=hobby.substr(hobby.indexOf("#%=")+3);
    $.ajax({
        url: 'inserisciNuovoHobby',
        type: 'POST',
        data: {id: id, hobby:hobby},
        success: function (data) {
            parent.remove();
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

function eliminaRichiesta(id, parent) {
    $.ajax({
        url: 'eliminaRichiesta',
        type: 'POST',
        data: {id: id},
        success: function (data) {
            parent.remove();
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

function eliminaUtente(id, parent) {
    alert(id)
    $.ajax({
        url: 'eliminaUtente',
        type: 'POST',
        data: {nome: id},
        success: function (data) {
            parent.remove();
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

function eliminaGruppo(id, parent) {
    alert(id)
    $.ajax({
        url: 'eliminaGruppo',
        type: 'POST',
        data: {nome: id},
        success: function (data) {
            parent.remove();
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
