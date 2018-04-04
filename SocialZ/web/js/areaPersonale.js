/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var utente;
var hobbies;
$(document).ready(function () {
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        if (c.indexOf("mittente") !== -1) {
            utente = document.cookie.substr(9);
            break;
        }
    }
    loadImmagineProfilo();
    $(".passVisibility").click(function () {
        if ($(".passVisibility").attr("id") === "open") {
            $(".passVisibility").attr("src", "img/closed_eye.png");
            $("#password").attr("type", "text");
            $(".passVisibility").attr("id", "closed");
        } else {
            $(".passVisibility").attr("src", "img/open_eye.ico");
            $("#password").attr("type", "password");
            $(".passVisibility").attr("id", "open");
        }

    });
    $(".fabImg").click(function () {
        if ($(".fabImg").attr("id") === "cambiaImmagine") {
            $("#buttonContainer").append("<input type=\"file\"/ accept=\"image/*\" id=\"immagine\"/>");
            $(".fabImg").attr("id", "cambiaImmagineDone");
        } else {
            var formData = new FormData();
            formData.append('image', $('#immagine')[0].files[0]);
            $.ajax({
                url: 'caricaImmagine',
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                success: function (data) {
                    $(".profile-picture").attr("src", "img/" + utente + ".png");
                    $("#immagine").remove();
                    alert("Immagine cambiata con successo");
                    location.reload();
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
    $(".fab").click(function () {
        if ($(".fab").attr("id") === "modify") {
            $(".form-element").removeClass("disabled");
            $(".hobbies").removeClass("disabled");
            $(".nuovoHobby").removeClass("disabled");
            $("#fabImg").attr("src", "img/done.png");
            $(".fab").attr("id", "done");
            var selected = $("#permTd").text();
            $("#permTd").text("");
            $("#permTd").append("<select id=\"permesso\"><option value=\"false\">No</option><option value=\"true\">Sì</option></select>");
            $("#permesso").val(selected);
        } else {
            $(".form-element").addClass("disabled");
            $(".hobbies").addClass("disabled");
            $(".nuovoHobby").addClass("disabled");
            $("#fabImg").attr("src", "img/modify_pencil.png");
            $(".fab").attr("id", "modify");
            cambioDati();
        }
    });
    $(document).on("click", ".nuovoHobby", function () {
        if ($(".nuovoHobby").attr("value") === "+") {
            getHobbies();
            $(".fab").addClass("disabled");
        } else {
            $(".fab").addClass("disabled");
            $(".fab").removeClass("disabled");
            hobbies = $("#selectHobby").val();
            for (var i in hobbies) {
                $("table").append("<tr class=\"hobbies\"><td class=\"firstCol inputHobby\">" + hobbies[i] + "</td><td><img src=\"img/bin.png\" width=\"20\"></td></tr>");
            }
            $(".nuovoHobby").remove();
            $("#selectHobby").remove();
        }
    });
    $(".eliminaHobby").click(function () {
        var hobby = $(this).parent().children();
        eliminaHobby(hobby);
    });
});

function getHobbies() {
    var json = "[";
    $('table .hobbies').each(function () {
        json += $(this).find(".firstCol").html() + ",";
    });
    json += "]";
    json = json.replace(",]", "]");
    $.ajax({
        url: 'getHobbies',
        type: 'POST',
        contentType: "application/json",
        data: json,
        success: function (data) {
            var obj = jQuery.parseJSON(data);
            var hobbies = obj.hobbies;
            var select = "<select id=\"selectHobby\" multiple>";
            for (var i in hobbies) {
                select += "<option>" + hobbies[i] + "</option>";
            }
            select += "</select>";
            $(".inputHobby").append(select);
            $(".nuovoHobby").attr("value", "ok");
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
            alert(status + errorObj);
        }
    });
}

function cambioDati() {
    var json = "{utente:" + utente;
    json += ",nome:" + $("#nome").val();
    json += ",cognome:" + $("#cognome").val();
    json += ",password:" + $("#password").val();
    var temp = $("#telefono").val().toString();
    if (temp === "") {
        json += ",telefono:\"\"";
    } else {
        json += ",telefono:" + temp;
    }
    temp = $("#indirizzo").val();
    if (temp === "") {
        json += ",indirizzo:\"\"";
    } else {
        json += ",indirizzo:" + temp;
    }
    json += ",permesso:" + $("#permesso").val();
    if (typeof hobbies !== "undefined") {
        json += ",hobbies:[";
        for (var i in hobbies) {
            json += hobbies[i] + ",";
        }
        json += "]";
        json = json.replace(",]", "]");
    }
    json += "}";
    $.ajax({
        url: 'setNewData',
        type: 'POST',
        contentType: "application/json",
        data: json,
        success: function (data) {
            alert("Dati modificati con successo");
            location.reload();
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
            alert(status + errorObj);
        }
    });
}

function eliminaHobby(hobby) {
    $.ajax({
        url: 'eliminaHobby',
        type: 'POST',
        data: {utente: utente, hobby: hobby.text()},
        success: function (data) {
            alert("Dati modificati con successo");
            hobby.remove();
            //location.reload();
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
            alert(status + errorObj);
        }
    });
}

function loadImmagineProfilo() {
    $.ajax({
        url: 'getImmagine',
        type: 'POST',
        contentType: false,
        processData: false,
        success: function (data) {
            if( data !== ""){
                $("#profile-picture").attr("src", "data:image/jpeg;base64," + data);
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