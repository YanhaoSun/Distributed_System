package service.dodgygeezers;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) {
        Endpoint.publish("http://dodgygeezers:9002/quotations", new DGQService());
        JmDNS jmdns = null;
        try {
            jmdns = JmDNS.create(InetAddress.getLocalHost());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ServiceInfo serviceInfo = ServiceInfo.create(
                "_http._tcp.local.", "sqs", 9002, "path=http://dodgygeezers:9002/quotations?wsdl"
        );
        try {
            jmdns.registerService(serviceInfo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
