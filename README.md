# Simple Web Server written in 'Java' 
Displays 
 * $WELCOME_MSG
 * Hostname
 * $PID

## To Run:

```
export WELCOME_MSG="Hello World"
javac -cp .:./jna-4.2.1.jar:jna-platform-4.2.1.jar Server.java
java -cp .:./jna-4.2.1.jar:jna-platform-4.2.1.jar Server
```

## To Test:

```
curl http://localhost:80
*** Java - Hello World ! ***
WELCOME_MSG : Hello World
Hostname is : <your-hostname>
Process ID  : 98
```

## Docker

Can be used to create a docker image and run within a container

### To Build
```
docker build -t robertshand/Java-hello-world .
```

### To Run
```
docker run -d -p 80:80 -e WELCOME_MSG="Hello World" robertshand/Java-hello-world
```

### To Test
```
curl http://$(docker-machine ip default):80
```

Result is

```
*** Java - Hello World ! ***
WELCOME_MSG : Hello World
Hostname is : 9ffe7e421652
Process ID  : 1%
```