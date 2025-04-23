function logout(){
    Swal.fire({
        title: "Tem certeza que deseja sair?",
        text: "Você srá desconectado e terá que fazer login novamente para acessar os recursos da página!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Sim",
        cancelButtonText: "Não"
    }).then((result) => {
        if (result.isConfirmed) {
            $.get("/logout",function(){window.location.href = "/"});
            //window.location.href = "/";
        }
    });
}

$("#btnLogout").click(logout);