select * from tcc.usuario;
insert into tcc.poder (poder,descricao) values 
	('super','Existe apenas 1. Tem acesso total a tudo'),
	('gestor','Tem quase acesso total. Não cria Super-Usuário'),
	('bibliotecário','Pode registrar novos computadores, editar reservas e confirmar devoluções.'),
	('professor','Pode fazer reservas de notbooks específicos/não');
insert into tcc.usuario (matricula,nome,id_poder,senha,email) values
	('00985734','Gabriel Toldo dos Santos',1,'Gmine0810','gabrieltoldo81@gmail.com');
