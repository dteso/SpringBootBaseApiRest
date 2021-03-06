# SpringBootBaseApiRest
API REST generada con Spring Boot Tool Suite con un CRUD básico de usuario
# Spring Boot Base

Este proyecto puede ser utilizado como plantilla inicial de arranque de cualquier API REST que se quiera trabajar con 
Spring Boot Framework.

Utiliza una base de datos MySql version 8

Se ha generado el proyecto a través de Spring Tool Suite 4

Descarga el jar desde la página https://spring.io/tools en versión Windows ( para este repositorio ).

Descomprímelo y de la carpeta resultante ( contents.zip ) extrae de ella nuevamente la carpeta con la versión de la herramienta recién descargada.

Descomprimirla a nivel de la ruta de tu Workspace puede ser una opción. Como aviso, el contenido descomprimido de esta carpeta pesa unos 750Mb.

Ahora, simplemente copia el proyecto de este repositorio a tu Workspace ( descomprimiéndolo nuevamente ) y ejecuta 
	
	SpringToolSuite4.exe
	
Se te abrirá un IDE tipo Eclipse y el paso inicial será seleccionar el propio Workspace con este mismo repositorio como contenido. 


En caso de que tengas ya Eclipse instalado ve a Help/Market y descarga el plugin directamente.
	

A continuación se explica paso a paso como se ha construido este mini-proyecto así como los conceptos más BÁSCIOS y FUNDAMENTALES de como 
levantar una estructura MVC que resultará en un API con un CRUD elemental completo basándonos en JPA-Hibernate y una Base de datos MySQL. RECUERDA TENER LEVANTADA TU BASE DE DATOS CUANDO LEVANTES EL API:

Spring Starter te ayuda a generar tu proyecto, básica y simplemente, nombrando los nombres de tus paquetes y con una configuración mínima como puede ser la selección de Maven o Gradle. Poco más. Asíque el siguiente contenido puede ser una guía en caso de que quieras desarrollarlo de cero tu mismo tras haber arrancado el Starter e inicado tu proyecto. 


###  1. Generar las clases que van a definir nuestro MVC

entity ----> CLASE - Anotación: @Entity - Cada una de las distintas tablas que van a existir en BD


Dentro de entity, cada propiedad o campo de la base de datos va a estar identificada con la anotación @Column que tiene sus propios atributos tales como:
		- name: 	aunque si no se indica un name, el nombre de la columna quedará definido por el nombre
			    	de la propiedad, puede customizarse y será el que figure realmente en base de datos
		- size: 	para campos tipo String, estaría estableciendo el maxlength
		- nullable: (true o false) para indicar si el campo es requerido false
		- unique: 	impide la coexistencia del mismo valor en este campo para dos registros distintos


repository ----> INTERFAZ - Anotación: @Repository - Es el equivalente al dao y es el que contendrá los métidos que ejecutarán las
				 consultas a través de Hibernate 

service ---> La fachada. Se compone de una interface declarativa y una clase implementadora. Es el puente entre el reporitorio y el controlador que aporta la lógica necesaria para que el controlador no tenga carga de código y pueda limitarse a pintar lo que  el servicio le ofrece transformado, que son los datos consultados desde el repositorio correspondiente.

controller --> Se encarga de pintar los datos procesados por el servicio, si procede.


### 2. Definir la conexión con la base de datos

	src/main/resources/application.propperties
	
	spring.datasource.url = "jdbc:_CONECTOR_://url_base_de_datos:puerto/nombre_bd?useSSL=false&serverTimezone="Europe/Madrid"
	
 - useSSL = false --> Hay que ponerlo así para que funcione inicialmente
 - serverTimezone = 'Codificacion_zona_horaria' 

	spring.datasource.username = usuario_bd
	
	spring.datasource.password = 1234
	
	spring.datasource.driver-class-name= com.mysql.cj.jdbc.Driver
	
	spring.jpa.database-platform = org.hibernate.dialect.MySQL8Dialect --> Seleccionar el correspondiente al  utilizado
	
	spring.jpa.show-sql=true ---> Escupe información sobre las consultas a la base de datos
	
	spring.jpa.hibernate.ddl-auto=create | ( ver opciones... )
	
 - ddl-auto	
	- create: crea contenido a la base de datos cada vez que se conecte
	- create-drop: crea y destruye el esquema al final de la sesión
	- none: no se ejecuta ninguna acción cuando se inicie sesión en la base de datos
	- update: actualiza la base de datos sin borrar ni volver a crear
	- validate: 
		
	logging.level.org.hibernate.SQL=debug ---> Activar log del ORM sobre SQL en debug time
	
### 3. Iniciamos la aplicación para que la/s entidad/es generada/s 
	
click derecho sobre el proyecto RunAs/SpringBootApp

### 4. Se habrá generado la tabla y podremos verlo a través del manager o IDE configurado para el control de la base de datos MySQL

### 5. Repository

1. Anotación @Repository sobre la interfaz creada
2. Extenderá de JpaRepository al que se le van a pasar las clases correspondientes, normalemente la clase a mapear en BD y el tipo de su Id

*******************************************************************************************************************************************

*  NOTA: La interfaz CrudRepository extiende de Repository y JpaRepository extiende de PagingAndSortingRepository y QueryByExampleExecutor. *  Si vamos a paginar del lado del servidor, vamos a necesitar realizar las consultas con los parámetros de paginación como atributos de la 
*  misma. Con CrudRepository vamos a tener que paginar nosotros en el front o decalarar métodos adicionales. Jpa ofrece una implementación  *  más completa

*******************************************************************************************************************************************


### 6. Service

1. Crear interfaz con los métodos a codificar cuando se implemente la misma. (interface MiServicio)

2. Se debe generar una Clase de implementación de la interfaz anterior y en la que realizaremos la inyección del repositorio
   gracias a la anotación @Autowired, par apoder usar los métodos del propio repositorio sin necesidad de invocar al constructor
   de la clase inyectada.	

	public class MiServicioImpl implements MiServicio{
	....
		@Autowired --> Facilita la inyección de dependencias sin tener que instanciar, en este caso del repositorio 
						  a utilizar
		private MiRepositorio miRepositorio;
		
		
		
		@Override
		@Transactional(readOnly = true) ---> de esta forma decimos que su ejecución no modifica el estado de la 
												 base de datos. Si pusiéramos false, si que estamos indicando que se 
												 van a hacer cambios en la base de datos. 
		public MiEntidad findById (Long id){
		  ...
		  return entidad;
		}
		
		
		
		@Override
		@Transactional(readOnly = false) ---> De esta forma indicamso que le ejecución de este método implica
													  cambios en la base de datos. 
		public MiEntidad save (MiEntidad entidad){
		  ...
		  return entidad;
		}
		
	}
	
como JPA implementa métodos transaccionales de forma que hay que decir con la anotación @Transactional a cada método como se va a ejecutar esa transacción

	@Transactional(readOnly = true) ---> de esta forma decimos que su ejecución no modifica el estado de la 
												 base de datos. Si pusiéramos false, si que estamos indicando que se 
												 van a hacer cambios en la base de datos. 
												 
### 7. Controller

1. Incluir la anotación @RestController. COmbina la anotación @Controller y @ResponseBody

	@Controller: define que la clase es un controlador.
	
	@ResponseBody: Se utiliza para indicar que el valor de retorno de nuestros métidos del controlador deben 					 usarse como el cuerpo de respuesta de la solicitud. Le indicamos, pues, que va a devolver por defecto un json

2. @RequestMapping("/api/users"), permite definir la ruta de entrada a través de la cual se va a acceder a ejecutar nuestro Controller.

a partir de aquí vamos a poder definir el comportamiento para cada verbo PUT; POST; GET; DELETE mediante las anotaciones correspondientes

	@GetMapping("/{id}") |  @RequstMapping(method = (GET))	
	@PostMapping |  @RequstMapping(method = (POST))					
	@PutMapping("/{id}") |  @RequstMapping(method = (PUT))																@DeleteMapping("/{id}")	| @RequstMapping(method = (DELETE))														
																							
	
	
	
	
