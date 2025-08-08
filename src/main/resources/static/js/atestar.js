let idReserva = $("#id").text();
let max = $('#id').data('max');

$('#statusRetorno').on('change', function () {
    var valorSelecionado = $(this).val();

    console.log("Status selecionado:", valorSelecionado, "para a reserva de id:", idReserva);
    $('#btAtestar').removeClass('disabled');

    // Você pode adicionar outras ações aqu
    if (valorSelecionado == 6 || valorSelecionado == 7 || valorSelecionado == 11 || valorSelecionado == 12){
        $("#qtdDefeitos").removeClass('d-none');
    }else{
        $("#qtdDefeitos").addClass('d-none');
    }
    if (valorSelecionado == 5 || valorSelecionado == 7 || valorSelecionado == 10 || valorSelecionado == 12){
        $("#qtdDevolvidos").removeClass('d-none');
    }else{
        $("#qtdDevolvidos").addClass('d-none');
    }
});

$('#qtdDefeitos, #qtdDevolvidos').on('change', function () {
   let numero = $(this).val();
   numero -= numero%1;
   if (numero < 1 || numero == ""){
       numero = 1
   }
   if(numero > max){
       numero = max;
   }
   $(this).val(numero);
});

$('#btAtestar').on('click', function () {
    let idStatus = $('#statusRetorno').val();
    let qtdDefeitos = $('#qtdDefeitos').val();
    let qtdDevolvidos = $('#qtdDevolvidos').val();
    if(qtdDefeitos == ""){
        qtdDefeitos = max;
    }
    if(qtdDevolvidos == ""){
        qtdDevolvidos = max;
    }
    $.ajax({
        url: '/atestar',
        method: 'POST',
        data: {
            idReserva: idReserva,
            idStatus: idStatus,
            qtdDevolvidos: qtdDevolvidos,
            qtdDefeitos: qtdDefeitos
        },
        success: function (response) {
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
                    window.location.href = "/";
                },
                });
            Toast.fire({
                icon: response.sucesso ? "success" : "error",
                title: response.mensagem
            });
        }
    })
});