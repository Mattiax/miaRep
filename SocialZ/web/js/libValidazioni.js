function codFiscValido(codFisc) {
	var rexCodFisc = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
	codFisc = codFisc.toUpperCase();
	return rexCodFisc.test(codFisc);
}

function emailValido(email) {
	var rexEmail = /^([A-Z0-9_.-])+@(([A-Z0-9-])+.)+([A-Z0-9]{2,4})+$/;
	email = email.toUpperCase();
	return rexEmail.test(email);
}

function dataValidaGGMMAAAA(strData){
	var gg, mm, aaaa, data;
	var rexDataGGMMAAAA = /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;
	//NB: bisogna togliere lo "0" in testa, perch� � l'indicatore di n� ottale!
   // oppure: sul parseInt si specifica (come 2� parametro) base 10
   if (!rexDataGGMMAAAA.test(strData)) {
	    return false;
	} else {
		gg = parseInt(strData.substr(0, 2), 10);
		mm = parseInt(strData.substr(3, 2), 10);
		aaaa = parseInt(strData.substr(6), 10);
		data = new Date(aaaa, mm-1, gg);
		return (data.getFullYear() == aaaa) && (data.getMonth()+1 == mm) && (data.getDate() == gg);
	}
}

function telefonoValid(tel){
    var rexTel= /^[0-9]+$/;
    return rexTel.test(tel);
}
/*function dataValida(strData){
	var objData = Date.parse(strData);
	return objData;
}

function dataRange(dataMin, dataMax){
	var gg, mm, aaaa, data;
	var rexDataGGMMAAAA = /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;
	if (!rexDataGGMMAAAA.test(strData)) {
	    return false;
	} else {
		gg = parseInt(strData.substr(0, 2));
		mm = parseInt(strData.substr(3, 2));
		aaaa = parseInt(strData.substr(6));
		data = new Date(aaaa, mm-1, gg);
		return (data.getFullYear() == aaaa) && (data.getMonth()+1 == mm) && (data.getDate() == gg);
	}
}*/

