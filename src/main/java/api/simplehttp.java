package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class simplehttp {
    public static void main(String[] args) throws IOException {

    }

    public String getPython(String imgur_url) throws IOException {
        URL url = new URL("http://35.246.214.109:8080/testpicture?url="+imgur_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(22000);
        if(con.getResponseCode()==200){
            String response = con.getResponseMessage();
            System.out.println("PYTHON RESPONSE: "+response);
            String responseBody = readResponse(con);
            return  responseBody;
        }
        return "";
    }

    public String readResponse(HttpURLConnection con){
        BufferedReader in = null;
        String responseBody = "";
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            responseBody = content.toString();
            System.out.println("HTTP RESPONSE: "+responseBody);
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

}
