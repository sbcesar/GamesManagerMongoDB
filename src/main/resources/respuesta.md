## 2. Teoría. Responde, usando tus palabras, a las siguientes preguntas.
- ### 2.1 ¿Qué ventajas e inconvenientes encuentras al usar una base de datos documental con MongoDB?

RESPUESTA: Al usar un lenguaje NoSQL y que todo está regido por Documentos lo encuentro mucho más sencillo pero a su vez un poco más caótico. Quizás sea por la falta de práctica o por el poco conocimiento de este pero algo que si tengo claro es que es más flexible en este ámbito. Aún así creo que me siento más cómodo usando bases de datos con MySQL y SpringBoot aunque me duela decirlo porque base de datos no me gusta nada y NoSQL de hecho es lo que más se aleja a ello.
- ### 2.2 ¿Cuáles han sido los principales problemas que has tenido al desarrollar la aplicación?

RESPUESTA: He tenido que pensar en un principio cómo iba a ser el crud y queda bastante que mejorar de ello pero el principal problema que veo es que o usas todo el desarrollo de la aplicación en base a Document() o usas las entidades que creas. Me refiero a que por ejemplo, a la hora de insertar un documento (juego) a la base de datos, o usas Document() o usas la entidad Videojuego::class.java. Aunque creo que es acostumbrarse.
- ### 2.3 ¿Cómo solucionarías el problema de la inconsistencia de datos en una base de datos documental? (El hecho de que en los documentos de una colección no sea obligatorio seguir un esquema único)

RESPUESTA: Creo que de algo de lo que me he dado cuenta en el desarrollo es que me hubiera servido saber que las validaciones de datos son bastante importantes en tal inconsistencia de datos documentales. Ayuda a solventar bastante la inconsistencia de esta.