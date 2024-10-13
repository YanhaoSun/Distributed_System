import org.junit.BeforeClass;
import org.junit.*;
import static org.junit.Assert.assertNotNull;

import service.auldfellas.AFQService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.URL;
import java.text.NumberFormat;

public class QuotationServiceUnitTest {
    @BeforeClass
    public static void setup() {
        Endpoint.publish("http://0.0.0.0:9001/quotation", new AFQService());
//        try {
//            Thread.sleep(10000); // 5秒延迟，您可以根据实际情况调整
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
    @Test
    public void connectionTest() throws Exception {
        URL wsdlUrl = new URL("http://localhost:9001/quotation?wsdl");
        QName serviceName =
                new QName("http://core.service/", "QuotationService");
        Service service = Service.create(wsdlUrl, serviceName);

        QName portName =
                new QName("http://core.service/", "QuotationServicePort");
        QuotationService quotationService =
                service.getPort(portName, QuotationService.class);
        Quotation quotation = quotationService
                .generateQuotation(new ClientInfo(
                        "Niki Collier", ClientInfo.FEMALE, 49,
                        1.5494, 80, false, false));
        displayQuotation(quotation);
        assertNotNull(quotation);
    }
    public static void displayQuotation(Quotation quotation) {
        System.out.println(
                "| Company: " + String.format("%1$-26s", quotation.company) +
                        " | Reference: " + String.format("%1$-24s", quotation.reference) +
                        " | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
        System.out.println("|=================================================================================================================|");
    }

}