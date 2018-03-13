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
			password: {
				required: true,
				minlength: 6
			},
			password2: {
				required: true,
				equalTo: "#password"
			},
			email: {
				email: true,
			},
			cognome: {
				cognome: true,
			},
			dataNascita: {
				dataGGMMAAAA: true,
			},
			accCond: {
				required: true,
			}
		},
		messages: {
			password2: {
				equalTo: "Le due password devono essere uguali."
			},
			accCond: {
				required: "E' necessario accettare i termini e le condizioni di servizio sulla privacy per continuare."
			}
		},
		errorElement: "span" // This is to override the label w/ a span
	});
});

jQuery.validator.addMethod("codiceFiscale",
	function(valore, elem) {
		return this.optional(elem) || codFiscValido(valore); 
	},
	"Inserire un Codice Fiscale corretto"
);

jQuery.validator.addMethod("dataGGMMAAAA",
	function(valore, elem) {
		return this.optional(elem) || dataValidaGGMMAAAA(valore); 
	},
	"Inserire una data valida nel formato GG/MM/AAAA"
);
