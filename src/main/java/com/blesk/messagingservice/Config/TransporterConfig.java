package com.blesk.messagingservice.Config;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.sockjs.client.InfoReceiver;

import java.io.IOException;
import java.util.List;
import java.net.URI;

@Configuration
public class TransporterConfig implements InfoReceiver {

    @Override
    public String executeInfoRequest( URI infoUrl, HttpHeaders headers) {
        HttpGet getRequest = new HttpGet(infoUrl);
        HttpClient client = HttpClients.createDefault();

        try {
            HttpResponse response = client.execute(getRequest);
            List<String> responseOutput = IOUtils.readLines(response.getEntity().getContent());
            return responseOutput.get(0);
        } catch (IOException ex) {
            return null;
        }
    }
}