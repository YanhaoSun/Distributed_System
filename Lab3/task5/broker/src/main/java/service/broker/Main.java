package service.broker;

import service.core.QuotationService;

import javax.jmdns.JmDNS;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) {
        LocalBrokerService localBrokerService = new LocalBrokerService();
        Endpoint.publish("http://broker:9000/broker", localBrokerService);
        JmDNS jmDNS = null;
        try {
            jmDNS = JmDNS.create(InetAddress.getLocalHost());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jmDNS.addServiceListener("_http._tcp.local.", localBrokerService);
    }
}