$('#resnote').addClass("disabled");

let max = $('#quantidade').data('max');
let especificidade = 1

function deixaNumeroInteiro() {
    let numero = $('#quantidade').val();
    numero -= numero%1;
    if (numero < 1 || numero == ""){
        numero = 1
    }
    if(numero > max){
        numero = max;
    }
    $('#quantidade').val(numero)
}

function especifico(){
    if (especificidade == 1){
        $('#notes').removeClass('d-none');
        especificidade += 1;
    }else{
        $('#notes').addClass('d-none');
        let selecionados = $('input[name="notebook"]');
        selecionados.prop('checked', false);
        especificidade -= 1;
    }
}

function reservar(){
    let quantidade = $('#quantidade').val();
    let especifico = true;
    let notebooks = $('input[name="notebook"]:checked');
    if(notebooks.length == 0){
        notebooks = "";
        especifico = false;
    }else{
        let notee = notebooks;
        notebooks = "";
        quantidade = notee.length;
        for(let i = 0; i < quantidade; i++){
            notebooks += $(notee[i]).data('id')
            if(i != quantidade - 1){
                notebooks += ";"
            }
        }
    }
    let i=1;
    $.ajax({
        url: "/reservar",
        method: "POST",
        data: {
            quantidade: quantidade,
            especifico: especifico,
            notebooks: notebooks
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
                        window.location.href = "/reserva-notebook";
                    }
                },
                });
            Toast.fire({
                icon: response.sucesso ? "success" : "error",
                title: response.mensagem
            });
        }
    });
}
