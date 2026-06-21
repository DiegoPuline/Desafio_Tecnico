
drop table if exists Comercio;
drop table if exists Cidade;

create table Cidade(
  ID int not null AUTO_INCREMENT,
  NOME varchar(100) not null,
  UF varchar(2) not null,
  capital boolean not null,
  PRIMARY KEY ( ID )
);

create table Comercio(
  ID int not null AUTO_INCREMENT,
  NOME varchar(100) not null,
  NOME_RESPONSAVEL varchar(100) not null,
  TIPO varchar(50) not null,
  CIDADE_ID int not null,
  PRIMARY KEY ( ID ),
  FOREIGN KEY (CIDADE_ID) REFERENCES Cidade(ID)
);
