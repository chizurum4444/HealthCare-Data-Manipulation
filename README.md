# HAPI FHIR Playground: Basic

This project is a skeleton project for using [HAPI FHIR](https://hapifhir.io) to access a public [FHIR](http://hl7.org/fhir/) server hosted at [http://hapi.fhir.org/baseR4](http://hapi.fhir.org/baseR4).

### NOTE

* [ ] Click on the link below. It is a FHIR *Search* operation used to look for patients with the name "Smith". (This is a publically accessible test server used by people all over the world) 

  http://hapi.fhir.org/baseR4/Patient?family=SMITH


### Task 1 (COMPLETE):

* [ ] Modify `SampleClient` so that it prints the first and last name, and birth date of each Patient to the screen

* [ ] Sort the output so that the results are ordered by the patient's first name

### Task 2(COMPLETE):

* [ ] Create a text file containing 20 different last names

* [ ] Modify 'SampleClient' so that instead of searching for patients with last name 'SMITH',
      it reads in the contents of this file and for each last name queries for patients with that last name

* [ ] Print the average response time for these 20 searches by implementing an IClientInterceptor that uses
      the requestStopWatch to determine the response time of each request.

* [ ] Run this loop three times, printing the average response time for each loop.  The first two times the loop should
      run as described above.  The third time the loop of 20 searches is run, the searches should be performed with
      caching disabled.

* [ ] If there is enough time between runs, you should expect to see loop 2 with a shorter average response time than loop 1 and 3.

* [ ] Write Unit tests

