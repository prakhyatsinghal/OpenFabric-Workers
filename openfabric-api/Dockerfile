FROM openjdk:15
ADD build/libs/*.jar dockerapp.jar
EXPOSE 8080 
ENTRYPOINT ["java", "-jar", "dockerapp.jar"]                            