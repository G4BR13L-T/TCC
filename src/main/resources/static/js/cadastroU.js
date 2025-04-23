$('#caduser').addClass("disabled");
$('#nome, #matricula, #email, #senha, #confsenha').on("focusout", function(){
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
    let nome = $('#nome').val().trim();
    let matricula = $('#matricula').val().trim();
    let email = $('#email').val().trim();
    let senha = $('#senha').val().trim();
    let confsenha = $('#confsenha').val().trim();
    let poder = $('input[name="poder"]:checked').val().trim();
    let i=1;
    $.ajax({
        url: "/gerar-cadastro-user",
        method: "POST",
        data: {
            nome: nome,
            matricula: matricula,
            email: email,
            senha: senha,
            confsenha: confsenha,
            poder: poder
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
                        $('#senha').val("").addClass('nao-preenchido');
                        $('#spsenha').text("O campo \"Senha\" precisa ser preenchido!")
                        $('#confsenha').val("").addClass('nao-preenchido');
                        $('#spconfsenha').text("O campo \"Confirmação\" precisa ser preenchido!")
                        if(nome == ""){
                            $('#nome').val("").addClass('nao-preenchido');
                            $('#spnome').text("O campo \"Nome\" precisa ser preenchido!")
                        }
                        if(matricula == ""){
                            $('#matricula').val("").addClass('nao-preenchido');
                            $('#spmatricula').text("O campo \"Matrícula\" precisa ser preenchido!")
                        }
                        if(email == ""){
                            $('#email').val("").addClass('nao-preenchido');
                            $('#spemail').text("O campo \"E-Mail\" precisa ser preenchido!")
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