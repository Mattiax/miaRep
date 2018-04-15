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
    addRowHandlers();
    $("#invioMess").click(function () {
        var mess = $("#inputMess").val();
        if (mess !== '') {
            mandaMessaggio(mess);
        }
    });
    $(document).on("click", "#canc", function () {
        eliminaMessaggio($(this).children("#id").text());
    });
    $("table tr").click(function () {
        $("table tr").removeClass('highlighted');
        $(this).addClass('highlighted');
    });
});

function eliminaMessaggio(id) {
    $.ajax({
        url: 'eliminaMessaggioGruppo',
        type: 'POST',
        data: {id: id},
        success: function (data) {
            $('div#chatDiv div:contains(' + id + ')').parent().remove();
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
;


$(document).on("click", "#partecipaBtn", "#dest", function () {
    var dest = $("#dest").text();
    $('#storicoChat').text("");
    $('#storicoChat').append("<p>Hai già richiesto la partecipazione a questo gruppo</p>");
    var msg = "{mittente:" + utente + ", destinatario:" + dest + ", richiesta:\"partecipazione al gruppo\"}";
    $.ajax({
        url: 'richiestaPartecipazione',
        type: 'POST',
        data: {mittente: utente, destinatario: dest, messaggio: msg},
        success: function (data) {
            //var prova = "<div id=\"chatDiv\"><p>" + utente + ":</p><p id=\"messaggioDiv\">" + msg + "</p><p id=\"dataDiv\">" + new Date() + "</p></div></div>";
            //$('#storicoChat').append(prova);
            //$("#inputMess").val("");
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
    var dest = $("#dest").text();
    $.ajax({
        url: 'salvaMessaggioGruppo',
        type: 'POST',
        data: {mittente: utente, destinatario: dest, messaggio: msg},
        success: function (data) {
            var json = $.parseJSON(data);
            var messaggio = "<div id=\"chatDiv\">\
                                <p>Tu:</p>\
                                <div id=\"canc\"\
                                    <p id=\"imagCanc\"><img src=\"img\\bin.png\" width=\"25\" height=\"25\" value=\"e\"></p>\
                                    <p id=\"id\" hidden=\"true\" value=\"" + json.id + "\">" + json.id + "</p>\n\
                                </div>\
                                <div id=\"messaggioDiv\">" + msg + "\
                                    <p id=\"dataDiv\">" + new Date() + "</p>\
                                </div>\
                            </div>";
            $('#storicoChat').append(messaggio);
            $("#inputMess").val("");
            scrollChat();
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
                        var cell = row.getElementsByTagName("p")[0];
                        var id = cell.innerHTML;
                        $("#dest").text(id);
                        showMessages(id);
                    };
                };

        currentRow.onclick = createClickHandler(currentRow);
    }
}

function showMessages(email) {
    $("#componiMessaggio").removeClass("disabled");
    $("#storicoChat").text("");
    $.ajax({
        url: 'getConvGruppo',
        data: {mittente: utente, destinatario: email},
        success: function (data) {
            if (data === "") {
                $('#storicoChat').append("<p>Non fai parte di questo gruppo</p><p><button id=\"partecipaBtn\">PARTECIPA</button></p>");
            } else {
                var obj = jQuery.parseJSON(data);
                if ($.isEmptyObject(obj)) {
                    $('#storicoChat').append("<p>Hai già richiesto la partecipazione a questo gruppo</p>");
                } else {
                    $("#componiMessaggio").removeClass("disabled");
                    makeTable(obj);
                }
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
    var tabella = "";
    for (var i in data)
    {
        var mittente = data[i].mittente;
        if (mittente === utente) {
            tabella += "<div id=\"chatDiv\">\
                                <p>Tu:</p>\
                                <div id=\"canc\"\
                                    <p id=\"imagCanc\"><img src=\"img\\bin.png\" width=\"25\" height=\"25\" value=\"e\"></p>\
                                    <p id=\"id\" hidden=\"true\" value=\"" + data[i].id + "\">" + data[i].id + "</p>\n\
                                </div>\
                                <div id=\"messaggioDiv\">" + data[i].messaggio +
                    "<p id=\"dataDiv\">" + data[i].dataOra + "</p>\
                                </div>";
        } else {
            tabella += "<div id=\"chatDiv\"><p>" + mittente + ":</p><p id=\"messaggioDiv\">" + data[i].messaggio + "</p><p id=\"dataDiv\">" + data[i].dataOra + "</p></div>";
        }
        tabella += "</div>";
    }
    $('#storicoChat').append(tabella);
    scrollChat();
}

function scrollChat() {
    $("#storicoChat").animate({scrollTop: $('#storicoChat').prop("scrollHeight")}, 1000);
}