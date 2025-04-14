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
    let nome = $('#nome').val();
    let matricula = $('#matricula').val();
    let email = $('#email').val();
    let senha = $('#senha').val();
    let confsenha = $('#confsenha').val();
    let poder = $('input[name="poder"]:checked').val();
    let i=1;
    $.ajax({
        url: "/gerar-cadastro",
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
            alert(response)
        }
    });
});