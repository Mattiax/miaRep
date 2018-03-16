$(document).ready(function(){
	document.getElementById("signIn").onreset = function(){$("#email").focus();};
	$("#email").focus();
		
	$("#signIn").validate({
		submitHandler: function(form) {
			form.submit();
		},
		rules: { //sui "name", non sugli "id"
			nome: {
				required: true,
			},
                        sesso: {
				required: true,
			},
                        privacy: {
				required: true,
			},
                        cognome: {
				required: true,
			},
			password: {
				required: true,
				minlength: 6
			},
			passwordRip: {
				required: true,
				//equalTo: "#password"
			},
			email: {
				email: true,
			},
			telefono: {
				telefonoValido: true,
			},
			dataNascita: {
                            required: true,
				dataGGMMAAAA: true,
			},
			accCond: {
				required: true,
			}
		},
		messages: {
			passwordRip: {
				equalTo: "Le due password devono essere uguali."
			},
			accCond: {
				required: "E' necessario accettare i termini e le condizioni di servizio sulla privacy per continuare."
			}
		},
		errorElement: "span" // This is to override the label w/ a span
	});
});

jQuery.validator.addMethod("dataGGMMAAAA",
	function(valore, elem) {
		return this.optional(elem) || dataValidaGGMMAAAA(valore); 
	},
	"Inserire una data valida nel formato GG/MM/AAAA"
);

jQuery.validator.addMethod("telefonoValido",
	function(valore, elem) {
		return this.optional(elem) || telefonoValid(valore); 
	},
	"Inserire un numero di telefono valido"
);

