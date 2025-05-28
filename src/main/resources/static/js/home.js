function cancelarReserva(botao){
    Swal.fire({
        title: "Tem certeza que deseja cancelar esta reserva?",
        text: "Ela não será excluída, mas você não poderá voltar atrás!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Sim",
        cancelButtonText: "Não"
    }).then((result) => {
        if (result.isConfirmed) {
            let reservaId = $(botao).data('idres');

            $.ajax({
                url: "/cancelar-reserva",
                method: "POST",
                data: {reservaId: reservaId},
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
                            window.location.href = "/";
                        },
                        });
                    Toast.fire({
                        icon: response.sucesso ? "success" : "error",
                        title: response.mensagem
                    });
                }
            });
        }
    });
}