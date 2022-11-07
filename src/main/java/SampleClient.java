import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class SampleClient {

    public static void main(String[] theArgs) {
        
        // Create a FHIR client
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
        client.registerInterceptor(new LoggingInterceptor(false));

        // Search for Patient resources
        Bundle response = client
            .search()
            .forResource("Patient")
            .where(Patient.FAMILY.matches().value("SMITH"))
            .sort().ascending(Patient.GIVEN)
            .returnBundle(Bundle.class)
            .execute();

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

}
