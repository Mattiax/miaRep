var alreadyRun = false;
window.onload = function() {
	if (alreadyRun) {
		return;
	};
	alreadyRun = true;
	init();
}


function init() {
var frmDati;
	frmDati = document.getElementById("registrazione");
	// uso la funz anonima per poter passare parametri al gestore dell'evento
	frmDati.onsubmit = function(){return frmRegValida(frmDati)}; //NB: il gestore deve RESTITUIRE t/f!
	frmDati.onreset = function(){frmRegReset(frmDati)};
	frmDati.userName.focus();
}

function frmRegReset(objForm) {
	objForm.userName.focus();
}

function frmRegValida(objForm) {
	if (objForm.userName.value == "" || objForm.userName.value == "undefined") {
		alert("Campo obbligatorio: nome utente");
		objForm.userName.focus();
		return false;
	} else if (objForm.password.value == "" || objForm.password.value == "undefined") {
		alert("Campo obbligatorio: password");
		objForm.password.focus();
		return false;
	} else if (objForm.password.value != objForm.password2.value) {
		alert("Le due password sono diverse");
		objForm.password.focus();
		return false;
	} else if (!emailValido(objForm.email.value)) {
		alert("Inserire un indirizzo e-mail valido");
		objForm.email.focus();
		return false;
	} else if (!dataValidaGGMMAAAA(objForm.dataNascita.value)) {
		alert("Inserire una data valida\n(nel formato gg/mm/aaaa)");
		objForm.dataNascita.focus();
		return false;
	} else if (getRadioValue(objForm.sesso) == "undefined") {
		alert("Selezionare il sesso");
		objForm.maschio.focus();  //NB: non "sesso"!
		return false;
	} else if (objForm.occupazione.value == "" || objForm.occupazione.value == "undefined") {
		alert("Selezionare un'occupazione");
		objForm.occupazione.focus();
		return false;
	} else if (objForm.provincia.value == "" || objForm.provincia.value == "undefined") {
		alert("Selezionare la provincia");
		objForm.provincia.focus();
		return false;
	}
/*
	alert("Nome: " + objForm.userName.value);
	alert("Interessi: " + getCheckboxValue(frmDati.interessi));
	alert("Provincia: " + objForm.provincia.value);
	alert("Note: " + objForm.note.value);
*/
	return true;
}

function getRadioValue(objRadio) {
	var j;
	for(var j = 0; j < objRadio.length; j++) {
		if(objRadio[j].checked) {
			return objRadio[j].value;
		}
	}
	return "undefined";
}

function getCheckboxValue(objChk) {
	var j, valore="";
	for(var j = 0; j < objChk.length; j++) {
		if(objChk[j].checked) {
			valore = valore + objChk[j].value + ";";
		}
	}
	return valore.replace(/;$/,""); // elimina il ";" alla fine
}

