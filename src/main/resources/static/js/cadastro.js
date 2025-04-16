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