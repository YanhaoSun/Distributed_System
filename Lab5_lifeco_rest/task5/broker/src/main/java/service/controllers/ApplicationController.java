package service.controllers;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;
import service.core.Application;
import service.core.ClientInfo;
import service.core.Quotation;

@RestController
public class ApplicationController {
    private Map<Integer, Application> applications = new TreeMap<>();
    @Value("8083")
    private int port;
    @RequestMapping("/applications/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable Integer id) {
//        System.out.println("enter here");
        Application application = applications.get(id);
//        System.out.println("get application: "+application.id);
        if (application == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(application);
    }
    LinkedList<String> serviceUrlList = new LinkedList<String>(){{
        add("http://auldfellas:8080/quotations");
        add("http://dodgygeezers:8081/quotations");
        add("http://girlsallowed:8082/quotations");
    }};
    @RequestMapping("/applications")
//    @PostMapping(value="/quotations", consumes="application/json")
    public ResponseEntity<Application> createApplication(@RequestBody ClientInfo info) {
        Application application = new Application(info);
        for (String url: serviceUrlList){
            RestTemplate template = new RestTemplate();
            ResponseEntity<Quotation> response = template.postForEntity(url, info, Quotation.class);
            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                System.out.println("Location of resource:" + response.getHeaders().getLocation().toString());
            }
            application.quotations.add(response.getBody());
        }
        applications.put(application.id, application);
        String url = "http://"+getHost()+"/applications/"
                + application.id;
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", url)
                .header("Content-Location", url)
                .body(application);
    }
    private String getHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress() + ":" + port;
        } catch (UnknownHostException e) {
            return "localhost:" + port;
        }
    }
}
