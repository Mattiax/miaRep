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
//cancella messaggi
$(document).on("click", "#imgCanc",'#id', function () {
	alert($("#id").text());/*
	 $.ajax({
	 url: 'eliminaMessaggio',
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
	 });*/
});

function mandaMessaggio(msg) {
	var dest = $("#dest").text();
	$.ajax({
		url: 'salvaMessaggio',
		type: 'POST',
		data: {mittente: utente, destinatario: dest, messaggio: msg},
		success: function (data) {
			var messaggio = "<div id=\"chatDiv\"><p>" + utente + ":</p><p id=\"messaggioDiv\">" + msg + "</p><p id=\"dataDiv\">" + new Date() + "</p></div></div>";
			$('#storicoChat').append(messaggio);
			$("#inputMess").val("");
		},
		statusCode: {
			404: function (content) {
				alert('errore 404');
			},
			500: function (content) {
				alert('errore 500');
			}
		},
		error: function (req, status, errorObj) {
			alert("errore generico:" + status + errorObj);
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
						var cell = row.getElementsByTagName("td")[2];
						var id = cell.innerHTML;
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
		url: 'mostraMessaggio',
		data: {mittente: utente, destinatario: email},
		success: function (data) {
			var obj = jQuery.parseJSON(data);
			makeTable(obj);
		},
		statusCode: {
			404: function (content) {
				alert('Errore 404');
			},
			500: function (content) {
				alert('Errore 500');
			}
		},
		error: function (req, status, errorObj) {
			alert("Errore generico" + status + errorObj);
		}
	});
}

function makeTable(json) {
	
	var data = json.messaggi;
	var tabella = "";
	for (var i in data)
	{
		var mittente = data[i].mittente;
		if (mittente===utente) {
			tabella += "<div id=\"chatDiv\"><p>Tu:</p><p id=\"messaggioDiv\">" + data[i].messaggio + "<img id=\"imgCanc\" src=\"img\\bin.png\" width=\"25\" height=\"25\"></p><p id=\"dataDiv\">" + data[i].dataOra + "</p><p id=\"id\" hidden=\"true\">" + data[i].id + "</p></div>";
		} else {
			tabella += "<div id=\"chatDiv\"><p>" + mittente + ":</p><p id=\"messaggioDiv\">" + data[i].messaggio + "</p><p id=\"dataDiv\">" + data[i].dataOra + "</p></div>";
		}
	}
	tabella += "</div>";
	$('#storicoChat').append(tabella);
}