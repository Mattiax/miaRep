$(document).ready(function () {
    $("#signIn").validate({
        submitHandler: function (form) {
            form.submit();
        },
        rules: {//sui "name", non sugli "id"
            password: {
                minlength: 6
            },
            password2: {
                equalTo: "#password"
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
        //errorElement: "span", // This is to override the label w/ a span
        errorPlacement: function (errore, element) {
            var errore = errore.text();
            var type = $(element).attr("id");
            switch (type) {
                case "password":
                    if (errore.indexOf("almeno") >= 0) {
                        $("#lungPass1").text(errore);
                    }
                    break;
                case "password2":
                    if (errore.indexOf("uguali") >= 0) {
                        $("#eqPass2").text(errore);
                    }
                    break;
            }
        },
        success: function (errore, element) {
            $("#lungPass1").text("");
            $("#eqPass2").text("");
        }
    });
});

jQuery.validator.addMethod("dataGGMMAAAA",
        function (valore, elem) {
            return this.optional(elem) || dataValidaGGMMAAAA(valore);
        },
        "Inserire una data valida nel formato GG/MM/AAAA"
        );

jQuery.validator.addMethod("telefonoValido",
        function (valore, elem) {
            return this.optional(elem) || telefonoValid(valore);
        },
        "Inserire un numero di telefono valido"
        );

