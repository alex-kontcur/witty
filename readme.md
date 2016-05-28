### Scenario

An app server sends messages via HTTP **POST** requests to **/messages** once every 3 seconds, with a JSON object in the request body. 
The JSON object has the following fields:
* Integer missionId - a unique identifier used to ensure indempotence. Every time the app server sends a request, a new missionId should be generated and used.
* Integer seed - a positive integer used to seed the algorithm to be executed. This should be a random integer between 1,000 and 20,000.

The HTTP response status code along with the response body (where applicable) should be printed for every request.
When receiving a message, the receiving app server checks whether the missionId has already been received. 
If the missionId has already been received, the receiving app server should respond with an HTTP 409 status code.
Otherwise, the receiving app server should return the correct answer to the question posed in [Project Euler Problem 21](https://projecteuler.net/problem=21), 
with one minor modification to use seed as the upper limit instead of the static 10,000 in the original problem statement. 

*See updated problem statement below.*

```AsciiDoc
Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).

If d(a) = b and d(b) = a, where a != b, then a and b are an amicable pair and each of a and b are called 
amicable numbers. 

For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; 
therefore d(220) = 284. The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.

Evaluate the sum of all the amicable numbers under seed.
```

The correct answer should be returned in the answer field in a JSON object in the response body.

### Requirements

The solution should be built as a Spring Boot application, using annotation-based configuration. XML-based configuration is allowed where justified. 
Additional libraries may be used if they do not duplicate capabilities native to Spring, although exceptions are allowed where justified.
For this hypothetical scenario, the sending app server and the receiving app server may be one and the same (meaning one project, built and deployed to a single server).
All source code must be the candidate’s original work and should be pushed to a GitHub or Bitbucket repo for review.
