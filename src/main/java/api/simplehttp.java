package api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class simplehttp {
    public static void main(String[] args) throws IOException {

    }

    public void getPython(String imgur_url) throws IOException {
        URL url = new URL("http://35.246.214.109:8080/testpicture?url="+imgur_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
    }
}
