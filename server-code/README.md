Must be built in Maven

Requires Maven 3

Copy all the files from NonoBackend server package
and remove all package statements

Command to build/compile:
mvn clean install

Command to run server:
java -cp target/classes:target/dependency/* NonoServer
