package com.tcc.TCC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
