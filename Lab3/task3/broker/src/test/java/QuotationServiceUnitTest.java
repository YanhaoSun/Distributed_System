import org.junit.BeforeClass;
import org.junit.*;
import static org.junit.Assert.assertNotNull;

import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import service.dodgygeezers.DGQService;
import service.girlsallowed.GAQService;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.URL;
import java.text.NumberFormat;

public class QuotationServiceUnitTest {
    @BeforeClass
    public static void setup() {
        Endpoint.publish("http://0.0.0.0:9000/broker", new LocalBrokerService());
        Endpoint.publish("http://0.0.0.0:9001/quotations", new AFQService());
        Endpoint.publish("http://0.0.0.0:9002/quotations", new DGQService());
        Endpoint.publish("http://0.0.0.0:9003/quotations", new GAQService());
    }
    @Test
    public void connectionTest() throws Exception {
        URL wsdlUrl = new URL("http://localhost:9000/broker?wsdl");
        QName serviceName =
                new QName("http://core.service/", "BrokerService");
        Service service = Service.create(wsdlUrl, serviceName);

        QName portName = new QName("http://core.service/", "BrokerServicePort");
        BrokerService brokerService = service.getPort(portName, BrokerService.class);
        for (ClientInfo info : clients) {
            displayProfile(info);
            // Retrieve quotations from the broker and display them...
            for(Quotation quotation : brokerService.getQuotations(info)) {
                displayQuotation(quotation);
            }
            // Print a couple of lines between each client
            System.out.println("\n");
        }
//        assertNotNull(quotation);
    }


    /**
     * Display the client info nicely.
     *
     * @param info
     */
    public static void displayProfile(ClientInfo info) {
        System.out.println("|=================================================================================================================|");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println(
                "| Name: " + String.format("%1$-29s", info.name) +
                        " | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
                        " | Age: " + String.format("%1$-30s", info.age)+" |");
        System.out.println(
                "| Weight/Height: " + String.format("%1$-20s", info.weight+"kg/"+info.height+"m") +
                        " | Smoker: " + String.format("%1$-27s", info.smoker?"YES":"NO") +
                        " | Medical Problems: " + String.format("%1$-17s", info.medicalIssues?"YES":"NO")+" |");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println("|=================================================================================================================|");
    }

    /**
     * Display a quotation nicely - note that the assumption is that the quotation will follow
     * immediately after the profile (so the top of the quotation box is missing).
     *
     * @param quotation
     */
    public static void displayQuotation(Quotation quotation) {
        System.out.println(
                "| Company: " + String.format("%1$-26s", quotation.company) +
                        " | Reference: " + String.format("%1$-24s", quotation.reference) +
                        " | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
        System.out.println("|=================================================================================================================|");
    }

    /**
     * Test Data
     */
    public static final ClientInfo[] clients = {
            new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false),
            new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 1.6, 100, true, true),
            new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 21, 1.78, 65, false, false),
            new ClientInfo("Rem Collier", ClientInfo.MALE, 49, 1.8, 120, false, true),
            new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 1.9, 75, true, false),
            new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 0.45, 1.6, false, false)
    };
}
