# student-server
[ Lisa RAOUL - M2 Digital Transformation ]
"Student-server" refers to the cloud integration project.

To run the server, launch the command ./gradlew bootRun.
And to run the tests, launch the command ./gradlew test.

To use Docker, you need two commands:
- docker build --tag=server:latest . to generate an image
- docker run -d -p 8080:8080 server to generate a container