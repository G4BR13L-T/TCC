$('#btEntrar').on("click",function(){
    let matricula = $('#matricula').val().trim();
    let senha = $('#senha').val().trim();

    $.ajax({
        url: "/valida-login",
        method: "POST",
        data: {
            matricula: matricula,
            senha: senha
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
                        $('#senha').val("");
                        $('#senha').addClass('nao-preenchido');
                        $('#spsenha').text("O campo \"Senha\" precisa ser preenchido!")
                        if(matricula == ""){
                            $('#matricula').addClass('nao-preenchido');
                            $('#spmatricula').text("O campo \"Matrícula\" precisa ser preenchido!")
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
})

$('#matricula,#senha').on("focusout", function(){
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