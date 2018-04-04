/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
   
    $("#trigger").click(function() {
        $.ajax({
        url: 'getImmagine',
        type: 'POST',
        contentType: false,
        processData: false,
        success: function (data) {
            if( data !== ""){
                $("#immagineProfilo").attr("src", "data:image/jpeg;base64," + data);
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
    });
});
