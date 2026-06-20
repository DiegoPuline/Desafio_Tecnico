
drop table if exists Comercio;
drop table if exists Cidade;

create table Cidade(
  id int not null AUTO_INCREMENT,
  nome varchar(100) not null,
  uf varchar(2) not null,
  capital boolean not null,  
  PRIMARY KEY ( ID )
);

create table Comercio(
  id int not null AUTO_INCREMENT,
  nome varchar(100) not null,
  nome_responsavel varchar(100) not null,
  tipo varchar(50) not null,
  cidade_id int not null,
  PRIMARY KEY ( id ),
  FOREIGN KEY (cidade_id) REFERENCES Cidade(id)
);
