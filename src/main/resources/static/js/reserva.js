$('#resnote').addClass("disabled");

let especificidade = 1;
let max = $('#quantidade').data('max');
setMinDataF();

function pesquisaNotbooksDisponiveis(){
    setMinDataF();
    let data = $('#datai').val();
    if(data != null){
        let i = 1;
        $.ajax({
            url: "/notes-disponiveis",
            method: "POST",
            data: {data: data},
            success: function(response){
                $("#notes").html(response);
                let disponiveis = $('input[name="notebook"]');
                let qtd = disponiveis.length;
                $('#qtd').text(qtd);
                max = qtd;
            }
        });
    }else {
        $('#datai').val(now());
    }
}

function setMinDataF(){
    let horarioI = $('#datai').val().substring(11);
    let minute = parseInt(horarioI.substring(3));
    let hour = parseInt(horarioI.slice(0, -3));
    $('#dataf').attr('min', horarioI);
    minute += 50;
    while (minute >= 60){
        minute -= 60;
        hour++;
    }
    let horarioF = hour + ":" + minute;
    $('#dataf').val(horarioF);
}

function consertaHorarioF(){
    let horarioI = $('#datai').val().substring(11);
    let horarioF = $('#dataf').val();
    let hourI = parseInt(horarioI.slice(0, -3));
    let hourF = parseInt(horarioF.slice(0, -3));
    let minuteI = parseInt(horarioI.substring(3));
    let minuteF = parseInt(horarioF.substring(3));
    if (hourF <= hourI){
        hourF = hourI;
        if (minuteF < minuteI){
            minuteF = minuteI;
        }
    }
    horarioF = hourF + ":" + minuteF;
    $('#dataf').val(horarioF);
}

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
    $('#btReserva').addClass('disabled');
    let quantidade = $('#quantidade').val();
    let especifico = true;
    let notebooks = $('input[name="notebook"]:checked');
    let horarioI = $('#datai').val();
    let horarioF = $('#dataf').val();
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
    let i = 1;
    $.ajax({
        url: "/reservar",
        method: "POST",
        data: {
            quantidade: quantidade,
            especifico: especifico,
            notebooks: notebooks,
            horarioI: horarioI,
            horarioF: horarioF
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
