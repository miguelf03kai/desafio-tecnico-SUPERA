CREATE TABLE IF NOT EXISTS  PRIORIDADE_ITEM(
	item_Prioridade_Id int not null auto_increment,
	descricao varchar(50),
	cor varchar(50),
	primary key (item_Prioridade_Id) 
);

CREATE TABLE IF NOT EXISTS ESTADO_ITEM(
	item_Estado_Id int not null auto_increment,
	descricao varchar(50),
	primary key (item_Estado_Id) 
);

CREATE TABLE IF NOT EXISTS LISTA(
	lista_Id int not null auto_increment,
	descricao varchar(50),
	primary key (lista_Id)
);

CREATE TABLE IF NOT EXISTS  ITEM(
	item_Id int not null auto_increment,
	descricao varchar(50),
	item_Prioridade_Id int,
	item_Estado_Id int,
	lista_id int,
	primary key (item_Id),
	foreign key (item_Prioridade_Id) references PRIORIDADE_ITEM (item_Prioridade_Id),
	foreign key (item_Estado_Id) references ESTADO_ITEM (item_Estado_Id),
	foreign key (lista_id) references LISTA (lista_id)
);

