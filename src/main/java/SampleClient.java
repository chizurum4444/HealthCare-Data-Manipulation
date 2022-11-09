import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import java.io.IOException;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class SampleClient {

    static IGenericClient client;
    static Bundle response;

    public static void create(){
       // Create a FHIR client
       FhirContext fhirContext = FhirContext.forR4();
       client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
       client.registerInterceptor(new LoggingInterceptor(false));
    }
    
    public static void search(){
        // Search for Patient resources
        response = client
            .search()
            .forResource("Patient")
            .where(Patient.FAMILY.matches().value("SMITH"))
            .sort().ascending(Patient.GIVEN)
            .returnBundle(Bundle.class)
            .execute();    
    }

    public static void read(){
       // Read values from response using the ID's of each entry
       response.getEntry().forEach((entry) -> {
            Patient patient = client.read()
            .resource(Patient.class)
            .withId(entry.getResource().getId())
            .execute();

            //Format: First name , Last name , Birth Date
            System.out.println(patient.getName().get(0).getGivenAsSingleString() + " , " + patient.getName().get(0).getFamily() + " , " + patient.getBirthDate());
        });
    }

    public static void main(String[] theArgs) throws IOException {
        
        create();
        search();
        read();

    } 

}

