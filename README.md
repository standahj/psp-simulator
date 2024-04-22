## Build, Run, Test Instructions

This project is designed for Java 11 and higher, it was developed using Java 17.

Switch the project __root__ directory.

First, build the executable jar file:

```mvn package shade:shade```

To build the docker image:

```docker build . -t stan-hejny-psp-simulator```

To run the docker image:

```docker run -p 8080:8080 stan-hejny-psp-simulator```

Alteratively (no docker), start the SpringBoot application by command:

```java -jar target/psp-simulator.jar```

To execute set of tests, after starting the application (docker container or Java from cmdline) run (in the project root directory):

```bash ./psp-test.sh```

The valid credit card numbers used in this testing were taken from here: https://support.bluesnap.com/docs/test-credit-card-numbers

## System Design Notes

The aim is to develop a simple PSP system that handles payment requests and simulates interactions with a payment acquirer.

The project includes two applications: the PSP system itself and the Payment Acquirer simulation.
Both applications are started together, but have separate context root for the REST API, and do not share any classes, in order to make those 2 conceptually completely separate.

PSP system accepts the card transaction detais, normalizes and validates values, stores the transaction to "storage" and all valid transactions are than forwarded (via the separate REST request to acquirer's API) to the Payment Acquirer.
Acquirer simply approves all transactions with card number ending with even digit and denies all transactions for cards with number ending with the odd digit.

Application is written in SpringBoot using Webflux reactive pattern that allows non-blocking calls to IO operations that may take long time to complete. See https://spring.io/reactive.
This allows for asynchronous, non-blocking, and efficient execution of tasks without waiting for each other, thereby achieving more application throughput while using less of HW resources.

Errors during the execution are handled by dedicated exception handler that Spring registers out of the box for the application.

## Notes

Code does attempt to normalize input values, the following inputs are allowed:

card number:
"3742-4545-5400-126",
"4263 9826 4026 9999",
"4263982640269999"

expiry date (July 2027):
"07/27",
"7/27",
"0727",
"727",
"07/2027",
"7/2027",
"7.2027",
"07.2027"

Code stores all incoming transactions to DB, but only those that pass validation check are forwarded to acquirer for authorization.
