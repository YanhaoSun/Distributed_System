package service.core;

// import service.registry.Service;

import service.core.ClientInfo;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface to define the behaviour of a quotation service.
 * 
 * @author Rem
 *
 */
//@WebService
@WebService(name="QuotationService",
		targetNamespace="http://core.service/",
		serviceName="QuotationService")
public interface QuotationService {
	@WebMethod
	public Quotation generateQuotation(ClientInfo info);
}
