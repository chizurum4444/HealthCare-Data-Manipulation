import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import ca.uhn.fhir.rest.api.CacheControlDirective;
import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class SampleClientIntermediate implements IClientInterceptor{

    static IGenericClient client;
    static Bundle response;
    static int sum;
    static int length;
    static ArrayList<Integer> runTimes = new ArrayList<>();     // ArrayList for run times
    static ArrayList<String> lastNames = new ArrayList<>();     // ArrayList for last names 

    // Create a FHIR client
    public static void create(){
        FhirContext fhirContext = FhirContext.forR4();
        client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
        client.registerInterceptor(new LoggingInterceptor(false));   // Logging Interceptor
        SampleClientIntermediate sci = new SampleClientIntermediate();
        client.registerInterceptor(sci);                                        // IClient Interceptor
    }
    
    // Reading in names from a text file and storing in a list
    public static void read() throws IOException{
        BufferedReader bufReader;
        bufReader = new BufferedReader(new FileReader("/Users/cza/Desktop/assessment/src/main/test/resources/LastName.txt")); // Change absolute path accordingly
        String line;
        line = bufReader.readLine();
        while (line != null) {
            lastNames.add(line);
            line = bufReader.readLine();
        }

        bufReader.close(); 
    }

    // Search for client
    public static void search(){
        // Loop through list of last names
        for(String lastName : lastNames){
            // Search for Patient resources
            response = client
                .search()
                .forResource("Patient")
                .where(Patient.FAMILY.matches().value(lastName))
                .sort().ascending(Patient.GIVEN)
                .returnBundle(Bundle.class)
                .cacheControl(new CacheControlDirective().setNoCache(true))    // Comment out to enable cacheControl
                .execute();
        };
    }

    @Override
    public void interceptRequest(IHttpRequest theRequest) {}

    // Response interceptor for getting time of runs
    @Override
    @Hook(Pointcut.CLIENT_RESPONSE)
    public  void interceptResponse(IHttpResponse theResponse) throws IOException{
        Long timing = theResponse.getRequestStopWatch().getMillis();
        System.out.println("Time taken in milliseconds: " + (timing));  
        int timingIntegar = timing.intValue();
        runTimes.add(timingIntegar);
        int [] runTimes2 = runTimes.stream().mapToInt(Integer::intValue).toArray();        
        sum = Arrays.stream(runTimes2).sum();   
        length = runTimes2.length;     
    }

    public static void main(String[] theArgs) throws IOException {

        create();
        read();
        search();
        System.out.println("Average Time taken in milliseconds: " + (sum/length));
       
    }


}

// SAMPLE RUNS
// Run 1: 906
// Run 2: 723
// Run 3: 811

