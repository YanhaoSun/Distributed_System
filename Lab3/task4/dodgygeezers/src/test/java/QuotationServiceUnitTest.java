import org.junit.BeforeClass;
import org.junit.*;
import static org.junit.Assert.assertNotNull;

import service.dodgygeezers.DGQService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.URL;

public class QuotationServiceUnitTest {
    @BeforeClass
    public static void setup() {
        Endpoint.publish("http://0.0.0.0:9002/quotation", new DGQService());
    }
    @Test
    public void connectionTest() throws Exception {
        URL wsdlUrl = new URL("http://localhost:9002/quotation?wsdl");
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
        assertNotNull(quotation);
    }
}