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
    $("#aggiungiHobby").click(function () {
        $("#aggiungiHobby").append("<div class=\"form-element\"><input id=\"hobbyAggiunto\" name=\"hobbyAggiunto\" class=\"form-element-field\" type=\"text\" /><div class=\"form-element-bar\"></div><label class=\"form-element-label\" >Hobby</label></div><input type=\"button\" class=\"done\" id=\"done\" value=\"Richiedi\"/>");
        $('#aggiungiHobby').off('click');
    });
    $("#ciao").click(function () {
        //alert($("#immagine").val())
        var formData = new FormData();
        formData.append('image', $('#immagine')[0].files[0]);
        alert(formData)
        $.ajax({
            url: 'caricaImmagine',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                alert("Richiesta inoltrata con successo");
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
    $(document).on("click", "#done", function () {
        var hobby = $("#hobbyAggiunto").val();
        if (hobby.length > 0) {
            $.ajax({
                url: 'nuovoHobby',
                type: 'POST',
                data: {hobby: hobby},
                success: function (data) {
                    alert("Richiesta inoltrata con successo");
                    $('#hobbyAggiunto').val("");
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
    });
});

