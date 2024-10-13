package service.broker;

import service.core.QuotationService;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {
        Endpoint.publish("http://0.0.0.0:9000/broker", new LocalBrokerService());
    }
}
