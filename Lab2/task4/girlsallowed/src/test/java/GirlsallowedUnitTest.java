import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import service.core.ClientInfo;
import service.core.Constants;
import service.core.QuotationService;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
import service.core.ClientInfo;
import service.core.Constants;
import service.core.QuotationService;
import service.girlsallowed.GAQService;




public class GirlsallowedUnitTest {
    private static Registry registry;
    @BeforeClass
    public static void setup() {
        QuotationService gaqService = new GAQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService) UnicastRemoteObject.exportObject(gaqService,0);
            registry.bind(Constants.GIRLS_ALLOWED_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService) registry.lookup(Constants.GIRLS_ALLOWED_SERVICE);
        assertNotNull(service);
    }
    @Test
    public void generateQuotationTest() throws Exception {
        QuotationService service = (QuotationService) registry.lookup(Constants.GIRLS_ALLOWED_SERVICE);
        assertNotNull(service.generateQuotation(new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false)));
    }
}
