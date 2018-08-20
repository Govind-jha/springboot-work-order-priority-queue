# WORK ORDER PRIORITY QUEUE WEB SERVICES # 
- - - - - - - -
![](https://img.shields.io/badge/build-passing-brightgreen.svg) ![](https://img.shields.io/badge/Coverage-96.7%25-brightgreen.svg) ![](https://img.shields.io/hackage-deps/v/lens.svg) ![](https://img.shields.io/badge/Spring%20Boot-2.0.4.RELEASE-Blue.svg)
## Problem Statement ##
&nbsp;
_Create a web service that accepts work orders and provides a prioritized list to work._
&nbsp;
![](https://thumbs.dreamstime.com/x/asian-business-people-group-sitting-line-queue-door-asia-businesspeople-banner-flat-vector-illustration-70825754.jpg, "Job Schedulers uses priority queue.")
&nbsp;
> For an alternate approach based on `Ticker` ( Ticker is like a pendulum's movement in a clock. Appropriately, one cycle of the event loop is called a tick.), [click here](https://github.com/Govind-jha/springboot-work-order-priority-queue/tree/impl-ticker-based-rank-update).

<!-- TOC depthFrom:1 depthTo:2 withLinks:1 updateOnSave:1 orderedList:0 -->

- [WorkOrder REST API](#work-order-priority-queue-web-services)
	- [Getting Started](#getting-started)
	- [Pre Dev Analysis](#pre-dev-analysis)
	- [API Contract](#api-contract)
	- [License](#license)
<!-- /TOC -->


This project is a Restful API for creating work order queue for Service Desk employee. The work order can be of four types  `normal, priority, VIP, and management override`. Different request types have their own implementation for calculating the rank. The priority queue is sorted from highest ranked to lowest ranked requester ID.

## GETTING STARTED ##
This application is a written in java using [Spring Boot][1] and built using Maven. 

### Prerequisites ###
* Git
* JDK 8 or later
* Maven 3.0 or later

### Quick Start ###
To test the application, just follow the two simple steps mentioned below: 

1. Download the fat jar of this application from [bin](https://github.com/Govind-jha/springboot-work-order-priority-queue/tree/master/bin, "click here.") folder. Now to start the application server, open command prompt and run the downloaded `jar` file,
```
        java -jar workorder-1.0.0.one-jar
```
2. Once the server gets started, download the postman collection form [postman](https://github.com/Govind-jha/springboot-work-order-priority-queue/tree/master/postman, "click here.") folder of the project. Import the collection in [Postman client](https://github.com/postmanlabs) and you will be all set to play around with the API.

### Clone ###
To get started you can simply clone this repository using git:
```
git clone https://github.com/Govind-jha/springboot-work-order-priority-queue.git
cd springboot-work-order-priority-queue
```

### Build an executable JAR ###
You can run the application from the command line using
```
        mvn spring-boot:run
```
Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with
```
        mvn clean package
```
Then you can run the JAR file with
```
        java -jar target/*.jar
```
### Test ###

> Download the postman collection form [postman](https://github.com/Govind-jha/springboot-work-order-priority-queue/tree/master/postman, "click here.") folder. Import the collection in [Postman client](https://github.com/postmanlabs) for smoke testing.

You can test these Micro Services using any tool or language that allows you to make a HTTP request. For example, removing an order using [CURL](https://curl.haxx.se/):
```sh
curl -X DELETE \
  http://localhost:8080/workorder/remove/20 \
  -H 'Cache-Control: no-cache' 
```

Creating a work order in the queue using `require` module in [Node.js](https://github.com/nodejs),

```js
var request = require("request");

var options = { 
    method: 'POST',
      url: 'http://localhost:8080/workorder/addOrder',
      headers: 
       { 
            'Cache-Control': 'no-cache',
            'Content-Type': 'application/json' 
        },
      body: { requesterId: 20, timeOfRequest: 5000000000 },
      json: true 
};

request(options, function (error, response, body) {
  if (error) throw new Error(error);

  console.log(body);
});
```

## PRE DEV ANALYSIS ##

### Object Model ###
```
PriorityQueue<WorkOrder>[ 
    WorkOrder{
        requestorID, 
        requestType,
        requestTimestamp,
        requestDate
    }, ...   
]
```
### Request Type Calculation ###
There are 4 classes of IDs, normal, priority, VIP, and management override. You can determine the class of the ID using the following method,
&nbsp;

| Request Type      | Condition                       |
|-----------------|----------------------------------|
| `NORMAL`      | (requestorID % 3 != 0 && requestorID % 5 != 0) |
| `PRIORITY`    | requestorID % 3 == 0 |
| `VIP` | requestorID % 5 == 0 |
| `MANAGEMENT_OVERRIDE`      | (requestorID % 3 == 0 && requestorID % 5 == 0) |

### Rank Calculation Formula ###
The table below lists the formulas for calculating the rank of different request types,
&nbsp;

| Request Type      | Ranking Function            |
|--------------|----------------------------------|
| `NORMAL`      | secondsSpentInQueue |
| `PRIORITY`    | max( 3 , secondsSpentInQueue * log(secondsSpentInQueue) ) |
| `VIP` | max( 4 , 2 * secondsSpentInQueue * log(secondsSpentInQueue) ) |
| `MANAGEMENT_OVERRIDE`      | max(NORMAL.maxPriority(), PRIORITY.maxPriority(), VIP.maxPriority()) + secondsSpentInQueue |

![picture alt](https://cms-assets.tutsplus.com/uploads/users/34/posts/26706/preview_image/rest.jpg)

## API Contract ##

The WorkOrder Api implements the requirement/rules described in the previous section. It is used to create a prioritized work order queue (a `Priority Queue`) for service request desk employee. It provides fetures like adding or removing an order from the queue based on priority, getting a sorted list of order etc.  

### Response Code And Headers ###
If response status code is `204 NO CONTENT` or `205 RESET CONTENT`, the response will contain header `x-app-diagnostic`. HTTP status `205 RESET CONTENT` means server successfully processed the request, but is not returning any content. Unlike a `204 NO CONTENT` response, this response requires that the requester reset the document view (as the content might have changed).

_Response header `x-app-diagnostic` will contain the cause of no content in the response body._

#### Response Codes

|Status Code|Message|
|-----------|-------|
|`200`    |   Success |
|`201`    |   Content Created|
|`204`  | No Content |
|`205`| Reset Content|
|`404`|Not Found|
|`405`| Method Not Allowed|
|`415`| Usupported Media Type|

- Check the requests content type if you get HTTP Status `415`. 
- Check the requests Method(GET, POST, DELETE) if you get HTTP Status `405`. 

### API Operation ###
The Application will start on port `8080`, you can change this port configuration in application.properties file of the project. 

| Base Resource                    | Description                       |
|:----------------------------|:----------------------------------|
|`/workorder`                   | provides endpoints for performing `create`, `read` and `delete` operations on work order queue.                      |

|API Operation                    | Description                       |Allowed Methods|
|:----------------------------|:----------------------------------|-------|
| `/addOrder`              | An endpoint for adding a ID to queue (enqueue). This endpoint should accept two parameters, the ID to enqueue(`requesterId`) and the time at which the ID was added to the queue(`timeOfRequest`). | `POST` |
| `/getPosition/{requesterId}` | An endpoint to get the position of a speciﬁc ID in the queue. This endpoint should accept one parameter, the ID to get the position of. It should return the position of the ID in the queue indexed from 0.  | `GET` |
| `/getOrderList` | An endpoint for getting the list of IDs in the queue. This endpoint should return a list of IDs sorted from highest ranked to lowest.  |`GET` |
| `/remove/{requesterId}`      | An endpoint for removing a speciﬁc ID from the queue. This endpoint should accept a single parameter, the ID to remove.  |`DELETE` |
|`/removeNextOrder` | An endpoint for getting the top ID from the queue and removing it (dequeue). This endpoint should return the highest ranked ID and the time it was entered into the queue.  |`GET` |
| `/avgWaitTime/{currentTimeInMillis}` | An endpoint to get the average wait time. This endpoint should accept a single parameter, the current time, and should return the average (mean) number of seconds that each ID has been waiting in the queue.  |`GET` |


&nbsp;

## License ##
The package is Open Source Software released under the [License](https://github.com/Govind-jha/springboot-work-order-priority-queue/blob/master/LICENSE). It's developed by [Govind Jha]("https://github.com/Govind-jha/").

[1]: https://github.com/spring-projects/spring-boot
