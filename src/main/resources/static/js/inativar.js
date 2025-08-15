$('#inanote').addClass('disabled');

function inativarNotebook(button){
    let idNotebook = $(button).data('id');
    Swal.fire({
        title: "Tem certeza que deseja inativar esse notebook?",
        text: "Você só deve confirmar caso realmente deseja inativar, casos comuns para isso seriam: dano no notebook e substituição!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Sim",
        cancelButtonText: "Não"
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: '/inativar',
                method: 'POST',
                data: {idNotebook: idNotebook},
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
                            if(response.sucesso){
                                window.location.href = "/";
                            }else{
                                window.location.href = "/inativar-notebook";
                            }
                        },
                        });
                    Toast.fire({
                        icon: response.sucesso ? "success" : "error",
                        title: response.mensagem
                    });
                }
            })
        }
    });
}