# Instrucciones de instalación
Para ejecutar el proyecto en un servidor local se deben seguir los siguientes pasos:
1) Clonar el repositorio en una carpeta del disco duro local.
2) Abrir el proyecto con un IDE para Java EE. Se recomienda Apache Netbeans 17, dado que es el IDE utilizado para el desarrollo del proyecto.
3) El archivo db_tarjetas.sql, que se encuentra en la carpeta raíz del proyecto, es la base de datos del proyecto. La misma se debe importar desde MySQL.
4) La clase Conexion.java contiene el usuario y password de la base de datos. Los mismos se deben modificar de acuerdo a la configuración local de cada usuario. Dicha clase se encuentra en /src/main/java/Modelo.
5) Para ejecutar el proyecto se debe iniciar un servidor local (por ejemplo Apache Tomcat) y un servidor MySQL. El proyecto fue probado exitosamente con Tomcat 8.5 y MySQL 8.0.
6) Por consultas y comentarios dirigirse a leonardo_papa@hotmail.com

