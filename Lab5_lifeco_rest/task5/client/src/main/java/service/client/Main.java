package service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import service.core.Application;
import service.core.ClientInfo;
import service.core.Quotation;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static HashMap<ClientInfo, Application> applicationMap = new HashMap<>();
    public static void main(String[] args) {
        for (ClientInfo info : clients) {
            invokePost(info);
        }
        System.out.println("\n\n********** Above DEBUG staff is printed by SpringBoot, my result is obtained and printed out successfully below **********\n");
        for (Map.Entry<ClientInfo, Application> applicationEntry: applicationMap.entrySet()){
            ClientInfo clientInfo = applicationEntry.getKey();
            displayProfile(clientInfo);
            for (Quotation quotation: applicationMap.get(clientInfo).quotations){
                displayQuotation(quotation);
            }
            System.out.println("\n");
        }
    }
    public static void invokePost (ClientInfo info) {

        StringEntity stringEntity = new StringEntity(prepareRequest(info));
        HttpPost httpPost = new HttpPost("http://localhost:8083/applications");

        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(httpPost);) {

            // Get HttpResponse Status
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                Application application = antiSerialization(result);
                applicationMap.put(info, application);
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
    private static Application antiSerialization(String result){
        ObjectMapper objectMapper = new ObjectMapper();
        Application application = null;
        try {
            application = objectMapper.readValue(result, Application.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return application;
    }
    private static String prepareRequest (ClientInfo info) {
//        var values = new HashMap<String, String>() {
//            {
//                put("name", info.name);
//                put("gender", info.gender);
//                put("age", info.age);
//                put("height", info.height);
//            }
//        };

        var objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(info);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return requestBody;
    }
    public static void invokeGet(Integer id) {

        try(
                CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();) {
            client.start();

            final SimpleHttpRequest request =
                    SimpleRequestBuilder
                            .get()
                            .setUri("http://localhost:8083/applications/"+id)
//                            .addHeader(
//                                    URLConstants.API_KEY_NAME,
//                                    URLConstants.API_KEY_VALUE)
                            .build();

            Future<SimpleHttpResponse> future =
                    client.execute(request,
                            new FutureCallback<SimpleHttpResponse>() {

                                @Override
                                public void completed(SimpleHttpResponse result) {
                                    String response = result.getBodyText();
                                    System.out.println("response::"+response);
                                }

                                @Override
                                public void failed(Exception ex) {
                                    System.out.println("response::"+ex);
                                }

                                @Override
                                public void cancelled() {
                                    // do nothing
                                }

                            });

            HttpResponse response = null;
            try {
                response = future.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            // Get HttpResponse Status
            System.out.println("response.getCode() in invokeGet = "+response.getCode()); // 200
            System.out.println("response.getReasonPhrase() in invokeGet = "+response.getReasonPhrase()); // OK

        } catch (InterruptedException
                 | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display the client info nicely.
     *
     * @param info
     */
    public static void displayProfile (ClientInfo info){
        System.out.println("|=================================================================================================================|");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println(
                "| Name: " + String.format("%1$-29s", info.name) +
                        " | Gender: " + String.format("%1$-27s", (info.gender == ClientInfo.MALE ? "Male" : "Female")) +
                        " | Age: " + String.format("%1$-30s", info.age) + " |");
        System.out.println(
                "| Weight/Height: " + String.format("%1$-20s", info.weight + "kg/" + info.height + "m") +
                        " | Smoker: " + String.format("%1$-27s", info.smoker ? "YES" : "NO") +
                        " | Medical Problems: " + String.format("%1$-17s", info.medicalIssues ? "YES" : "NO") + " |");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println("|=================================================================================================================|");
    }

    /**
     * Display a quotation nicely - note that the assumption is that the quotation will follow
     * immediately after the profile (so the top of the quotation box is missing).
     *
     * @param quotation
     */
    public static void displayQuotation (Quotation quotation){
        System.out.println(
                "| Company: " + String.format("%1$-26s", quotation.company) +
                        " | Reference: " + String.format("%1$-24s", quotation.reference) +
                        " | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price)) + " |");
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

