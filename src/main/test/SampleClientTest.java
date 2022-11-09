
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Contract.AssetContextComponent;
import org.hl7.fhir.r4.model.TestScript.AssertionOperatorType;
import org.hl7.fhir.r4.model.TestScript.AssertionResponseTypes;
import org.junit.Test;

public class SampleClientTest {
    
   @Test
    public void createClient() {
        IGenericClient client;
        FhirContext x;
        
        create();
        AssetContextComponent(x.getFhirContext() , client.getFhirContext());
    }

    @Test
    public void searchClient() {
        IGenericClient client;
        Bundle y;

        create();
        search();
        AssertionResponseTypes(y.getType(), response.getType());
    }

}
