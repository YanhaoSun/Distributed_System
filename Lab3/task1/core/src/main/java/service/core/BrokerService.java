package service.core;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

// import service.registry.Service;

/**
 * Interface for defining the behaviours of the broker service
 * @author Rem
 *
 */
@WebService
public interface BrokerService{
	@WebMethod
	public List<Quotation> getQuotations(ClientInfo info);
}
