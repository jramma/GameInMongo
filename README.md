# TwoDiceGame-JwtSecurity-Mongo

API de un juego de dados que consiste en sacar un 7 para ganar. En el juego tenemos un registro de usuarios, partidas y ranquings. Todo está encritado y con Json Web Tokens.

## Instalación

El proyecto puede ser descargado en un archivo .zip y hacerlo funcionar en tu IDE.

***Importante!:*** tengo instalado y uso lombok en mi IDE. Si no tienes lombok tendrás que crear setters, getters y constructores de las clases.
  
La API funciona con MongoDB, es necesario tener instalado previamente MongoDb porque utiliza el localHost por defecto. Una vez instalado, crear una base de datos con nombre "game" que es la indicada en aplications.properties de la API. Tienes la base de datos exportada en la carpeta Game de este repósitorio.

## Endpoints
1. http://localhost:8080/auth/create  - POST

Crear un usuario. Si el nombre se repite con alguno de la base de datos lanza error. El nombre, password y rol no pueden estar vacíos, sino lanza error.


2. http://localhost:8080/auth/login  - POST

Enlace para iniciar sesión. Si inicia sesión correctamente te devuelve el JWT token que necesitas para acceder al resto de la API.


3. http://localhost:8080/editProfile  - PUT

Enlace para editar el nombre. Debes enviar el nuevo String (nombre) que deseas tener y poner tu JWT token. Se actualizará el nombre y te devolverá el usuario con su nuevo nombre.


4. http://localhost:8080/getJugadas/{id}  - GET

Te devolverá las partidas de un jugador en concreto, el que indiques en la {id}. PD: las Id se autoincrementan en la API empezando por el 1 (no son ObjectID de mongo).


5. http://localhost:8080/jugar  - POST

Jugar una partida.

6. http://localhost:8080/winner  -GET

Te devuelve el jugador con mejor ranquing.

7. http://localhost:8080/loser  - GET

Te devuelve el jugador con peor ranquing.


8. http://localhost:8080/eliminarPartidas  - DELETE

Elimina las partidas del usuario que este logueado.


## API Design

La estructura de los paquetes es la siguiente:

- Controller: aporta los endpoints con la inyección del repositorio de compañías.
- Domain: aquí se encuentra la clase Company que refiere a la colección de compañías. Además tengo 3 DTO que nos sirven para ejecutar el tercer endpoint.
- Repository: interfaz con MongoRepository importado y implementado. Nos sirve para comunicar el controller con la colección "companies".
- Config: contiene un swagger con el que puedes hacer el Test de los diferentes GET del Controller sin necesidad de Postman y tienes un poco de información de más sobre el proyecto.

