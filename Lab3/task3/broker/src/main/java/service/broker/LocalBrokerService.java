package service.broker;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//import com.sun.media.jfxmediaimpl.HostUtils;
import org.junit.BeforeClass;
import service.auldfellas.AFQService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import service.dodgygeezers.DGQService;
import service.girlsallowed.GAQService;
//import service.dodgygeezers.DGQService;
//import service.girlsallowed.GAQService;
//import service.registry.ServiceRegistry;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding.*;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;


/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
@WebService(name="BrokerService",
		targetNamespace="http://core.service/",
		serviceName="BrokerService")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public class LocalBrokerService implements BrokerService {
	LinkedList<String> wsdlUrlList = new LinkedList<String>(){{
		add("http://localhost:9001/quotations?wsdl");
		add("http://localhost:9002/quotations?wsdl");
		add("http://localhost:9003/quotations?wsdl");
	}};
	@WebMethod
	public LinkedList<Quotation> getQuotations(ClientInfo info) {

		LinkedList<Quotation> quotations = new LinkedList<Quotation>();
		for (String url : wsdlUrlList) {
			URL wsdlUrl = null;
			try {
				wsdlUrl = new URL(url);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			QName serviceName =
					new QName("http://core.service/", "QuotationService");
			Service service = Service.create(wsdlUrl, serviceName);
			QName portName =
					new QName("http://core.service/", "QuotationServicePort");
			QuotationService quotationService =
					service.getPort(portName, QuotationService.class);
			Quotation quotation = quotationService.generateQuotation(info);
			quotations.add(quotation);
		}
		return quotations;
	}
}
