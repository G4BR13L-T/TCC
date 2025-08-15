package com.tcc.TCC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControleDeDistribuicaoDeNotebookApplication {
	/**
	 * 4 usuarios: super, gestor, biblioteca, professor;
	 * Requisitos
	 *
	 * *Professores/bibliotecários sempre recebem contas feitas por gerentes.
	 * *Professores/bibliotecários não podem criar suas próprias contas.
	 * *Admin possui liberdade total no sistema e nas contas gerente, professores e bibliotecários.
	 * *A biblioteca confirma todas as devoluções de notebooks.
	 * *O sistema deve verificar a quantidade de notebooks disponíveis antes de permitir a reserva concreta.
	 * *Professores apenas reservam notebooks.
	 * *Bibliotecários adicionam novos notebooks no limite.
	 * *Todos podem reservar notebooks.
	 * *Gerentes têm capacidades CRUD para contas de professores e bibliotecários e registros de notebooks.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ControleDeDistribuicaoDeNotebookApplication.class, args);
	}
}

//quantidade e especificidade se complentam
//To[do]: itens extras recebem confirmação de existencia, precisa ser pedido com pelo menos 3d de antecedencia
//Todo: Dias max e mínimo e horários (tempo máximo por professor para reservervar 72h)
//Todo: Filtro por professor, por data
//Limitar número de reservas por professores = 30 max
//Campo observação na reserva para saber a atividade do professor e assim se necessário remanejar
//Poder deixar notebook inativo, por baixa ou avaria no mesmo
//Limitar cadastro para não dar para criar dois cadastros iguais
//Código de patrimônio tá repetindo
//To[do]: Seleção múltipla
//Inativar notebook
//Limitar a reserva específica para o limite de 30 notebooks
//Todo: Enviar eMails para cada ação realizada pelo sistema (reserva, cadastro, atestamento, em andamento, atrasada...)
