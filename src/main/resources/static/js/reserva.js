$('#resnote').addClass("disabled");
function deixaNumeroInteiro() {
    let numero = $('#quantidade').val() ;
    let maximo = 0;
    numero -= numero%1;
    if (numero < 1 || numero == ""){
        numero = 1
    }
    $.ajax({
        url: "/define-maximo",
        method: "POST",
        success: function(response){
            maximo = response
            if (numero > maximo){
                numero = maximo
            }
        }
    })
    $('#quantidade').val(numero)
}
