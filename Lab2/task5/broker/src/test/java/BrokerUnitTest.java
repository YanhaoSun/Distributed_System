//import service.core.BrokerService;
//import service.core.ClientInfo;
//import service.core.Constants;
//import service.core.Quotation;
//import service.core.QuotationService;
//import org.junit.*;
//import service.broker.LocalBrokerService;
//
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.rmi.server.UnicastRemoteObject;
//import java.util.List;
//
//import static org.junit.Assert.assertNotNull;
//
//public class BrokerUnitTest {
//    private static Registry registry;
//    @BeforeClass
//    public static void setup() {
//        try {
//            registry = LocateRegistry.createRegistry(1099);
//            BrokerService brokerService = new LocalBrokerService(registry);
//            BrokerService quotationService = (BrokerService)
//                    UnicastRemoteObject.exportObject(brokerService,0);
//            registry.bind(Constants.BROKER_SERVICE, quotationService);
//        } catch (Exception e) {
//            System.out.println("Trouble: " + e);
//        }
//    }
//    @Test
//    public void connectionTest() throws Exception {
//        BrokerService service = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
//        assertNotNull(service);
//    }
//    @Test
//    public void generateQuotationTest() throws Exception {
//        BrokerService service = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
//        List<Quotation> result = service.getQuotations(new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false));
//        if (result.isEmpty())
//            System.out.println("Return an empty list of quotations, because no services are generated");
//        assertNotNull(result);
//    }
//}
