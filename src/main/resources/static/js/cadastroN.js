$('#cadnote, #cadnotee').addClass("disabled");

function deixaNumeroInteiro() {
    let numero = $('#numero').val().trim();
    numero -= numero%1;
    if (numero < 1 || numero == ""){
        numero = 1
    }
    $('#numero').val(numero)
}

$('#numero, #codigo').on("focusout", function(){
    let campo = $(this).val().trim();
    let nomeCampo = $(this).attr("id");
    let placeholder = $(this).attr("placeholder");

    if(campo == ''){
        $(this).addClass('nao-preenchido');
        $("#sp" + nomeCampo).text("O campo \"" + placeholder + "\" precisa ser preenchido!");
    }else{
        $(this).removeClass('nao-preenchido');
        $("#sp" + nomeCampo).text("");
    }
});

$('#btCadastro').on("click",function(){
    let numero = $('#numero').val().trim();
    let codigo = $('#codigo').val().trim();
    let i=1;
    $.ajax({
        url: "/gerar-cadastro-note",
        method: "POST",
        data: {
            numero: numero,
            codigo: codigo
        },
        success: function(response){
            const Toast = Swal.mixin({
                toast: true,
                position: "top-end",
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.onmouseenter = Swal.stopTimer;
                    toast.onmouseleave = Swal.resumeTimer;
                },
                didClose: function(){
                    if(response.sucesso){
                        window.location.href = "/";
                    }else{
                        if(numero == ""){
                            $('#numero').val("").addClass('nao-preenchido');
                            $('#spnumero').text("O campo \"Número\" precisa ser preenchido!")
                        }
                        if(codigo == ""){
                            $('#codigo').val("").addClass('nao-preenchido');
                            $('#spcodigo').text("O campo \"Código de Patrimônio\" precisa ser preenchido!")
                        }
                    }
                },
                });
            Toast.fire({
                icon: response.sucesso ? "success" : "error",
                title: response.mensagem
            });
        }
    });
});
