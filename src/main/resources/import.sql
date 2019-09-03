INSERT INTO pelicula(estado,fecha,nombre) VALUES('ACTIVO', '2018-01-30','DOCKER');
INSERT INTO pelicula(estado,fecha,nombre) VALUES('ACTIVO', '2018-01-29','ALICIA');
INSERT INTO pelicula(estado,fecha,nombre) VALUES('INACTIVO', '2018-01-26','Locos de amor');
INSERT INTO pelicula(estado,fecha,nombre) VALUES('INACTIVO', '2018-01-26','X Men: Dias del futuro pasado');
INSERT INTO pelicula(estado,fecha,nombre) VALUES('INACTIVO', '2018-01-26','Tortugas Ninjas 2');
INSERT INTO pelicula(estado,fecha,nombre) VALUES('INACTIVO', '2018-01-26','REY LEON');

INSERT INTO turno(estado,hora) VALUES('ACTIVO','8:00:00');
INSERT INTO turno(estado,hora) VALUES('ACTIVO','9:00:00');
INSERT INTO turno(estado,hora) VALUES('ACTIVO','10:00:00');
INSERT INTO turno(estado,hora) VALUES('ACTIVO','14:00:00');
INSERT INTO turno(estado,hora) VALUES('ACTIVO','16:00:00');
INSERT INTO turno(estado,hora) VALUES('ACTIVO','18:00:00');

INSERT INTO item_pelicula(turno_id, pelicula_id) VALUES (1,2);
INSERT INTO item_pelicula(turno_id, pelicula_id)  VALUES (2,2);
INSERT INTO item_pelicula(turno_id, pelicula_id)  VALUES (2,1);
INSERT INTO item_pelicula(turno_id, pelicula_id)  VALUES (6,1);
INSERT INTO item_pelicula(turno_id, pelicula_id)  VALUES (1,3);
INSERT INTO item_pelicula(turno_id, pelicula_id)  VALUES (2,3);
INSERT INTO item_pelicula(turno_id, pelicula_id)  VALUES (3,3);
INSERT INTO item_pelicula(turno_id, pelicula_id)  VALUES (4,3);
INSERT INTO item_pelicula(turno_id, pelicula_id)  VALUES (4,4);
INSERT INTO item_pelicula(turno_id, pelicula_id)  VALUES (5,4);
INSERT INTO item_pelicula(turno_id, pelicula_id)  VALUES (6,4);