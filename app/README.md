
#Player Service

## Mac Setup

####Open a terminal and execute the following
```
# install mongo
brew install mongodb
sudo mkdir -p /data/db
sudo chmod -R 777 /data
  
# run mongo
mongod
```

####Open a second terminal window and execute the following to get and run the service.
```
# compile code
mvn clean package

# run sonar
mvn clean sonar:sonar
 
# run standalone service
mvn spring-boot:run
```
----------
##Test URL's
####Swagger
[http://localhost:8080/](http://localhost:8080/)

####GET URL's

* Valid player: [http://localhost:8080/player/3](http://localhost:8080/player/3)
* Resource Not Found: [http://localhost:8080/player/100](http://localhost:8080/player/100)
* Force Runtime Exception: [http://localhost:8080/player/11](http://localhost:8080/player/11)

####POST URL and JSON

* [http://localhost:8080/player](http://localhost: 8080/player)

```
{ 
    "number" : "25", 
    "firstName" : "Richard", 
    "lastName" : "Sherman", 
    "status" : "1"
}
```

####Spring actuator endpoints

* [http://localhost:8080/info](http://localhost:8080/info)
* [http://localhost:8080/metrics](http://localhost:8080/metrics)
* [http://localhost:8080/health](http://localhost:8080/health)
* [http://localhost:8080/mappings](http://localhost:8080/mappings)
