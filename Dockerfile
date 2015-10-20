FROM java:7
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac -cp .:./jna-4.2.1.jar:jna-platform-4.2.1.jar Server.java

ENTRYPOINT ["java", "-cp", ".:jna-4.2.1.jar:jna-platform-4.2.1.jar", "Server"]