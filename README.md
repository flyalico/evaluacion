# evaluacion
Detallando los servicios desarrollados (url y parámetros de entrada).
get http://localhost:8080/api/peliculas/ --lista todas las peliculas con sus turnos
get http://localhost:8080/api/peliculas/page/0 --retorna las 4 primeras peliculas con sus turnos
post http://localhost:8080/api/peliculas 

    {
        
        "nombre": "Alicia en el pais de las maravillas",
        "fecha": "2016-05-18",
        "estado": "ACTIVO"
    }
graba una nueva peliculas

 put http://localhost:8080/api/peliculas/1 
 {
        
        "nombre": "DOCKER kubernetes",
         "fecha": "2018-01-22",
         "estado": "INACTIVO"
    }
modifica una pelicula
get http://localhost:8080/api/peliculas/1 retorna una sola pelicula
delete http://localhost:8080/api/peliculas/1  elimina la pelicula 1
get http://localhost:8080/api/turnos/ --lista todas las peliculas con sus turnos


 
post http://localhost:8080/api/turnos 

    {
        "hora": "6:05:00",
        "estado": "ACTIVO"
    }
graba un nuevo turno

 put http://localhost:8080/api/turnos/1 
    {
       "hora": "5:00:00",
        "estado": "ACTIVO"  
        
    }
modifica un turno
get http://localhost:8080/api/turnos/1 retorna un turno
delete http://localhost:8080/api/turnos/1  elimina un turno



post http://localhost:8080/api/itemsPeliculas/
{
	 "pelicula": {
                "id": 4 
            },
			"turno": {
                "id": 1
            }
}agrega un turno a la pelicula
put http://localhost:8080/api/itemsPeliculas/1
{
	 "pelicula": {
                "id": 3 
            },
			"turno": {
                "id": 2
            }
} modifica el itemPelicula 
delete http://localhost:8080/api/itemsPeliculas/1 elimina el itemPelicula


La BD esta en el file import.sql
