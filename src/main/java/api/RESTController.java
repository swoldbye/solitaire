package api;

import com.google.gson.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.PyCard;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RESTController {

    public static Javalin server;
    private JSONArray row;
    private Gson g = new Gson();
    private ArrayList<PyCard> pyCards = new ArrayList<>();

    public void stop() {
        server.stop();
        server = null;
    }

    public void start() {
        if (server != null) return;
        try {
            server = Javalin.create().start(8081);
            server.exception(Exception.class, (e, ctx) -> {
                e.printStackTrace();
            });
            //TODO: lav endpoints (GET og POST)
            server.post("/ImageURL", ctx -> imageURL(ctx));
            server.post("/Hello", ctx -> getFromPython(ctx));
        } catch (Exception e) {
            stop();
            e.printStackTrace();
        }


    }

    private void imageURL(@NotNull Context ctx) throws ExecutionException, InterruptedException, IOException {
        String URL = ctx.queryParam("image_url");
        /*
        PythonHttpRequest(
                "http://localhost:8080/getImage?url=https://i.imgur.com/aZayn96.jpg",
                "GET",
                "",
                "url"
        );
        */

        if (!URL.isEmpty()) {
            System.out.println("Received the url: " + URL);
            System.out.println("... Now sending to the python server for processing");
            simplehttp comm = new simplehttp();
            try {
                comm.getPython(URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //TODO: Create rest client and send
            ctx.status(200).result("Server message: I received your url!!");
        } else {
            ctx.status(500).result("Server Error");
        }
    }

    private void getFromPython(@NotNull Context ctx) throws ExecutionException, InterruptedException, IOException {
        String gameString = ctx.body();

        if (!gameString.isEmpty()) {
            System.out.println("Received the gameString: \n" + gameString + "\n");
            ctx.status(200).result("Server message: Received gameString: \n" + gameString);

            // Handling the JSON string
            JSONArray rawJSONArray = new JSONArray(gameString);
            System.out.println("Første array i JSON-Arrayet: \n" + rawJSONArray.get(0) + "\n");

            JSONArray JSONRow1 = new JSONArray(rawJSONArray.get(0).toString());
            JSONArray JSONRow2 = new JSONArray(rawJSONArray.get(1).toString());
            JSONArray JSONRow3 = new JSONArray(rawJSONArray.get(2).toString());
            JSONArray JSONRow4 = new JSONArray(rawJSONArray.get(3).toString());
            JSONArray JSONRow5 = new JSONArray(rawJSONArray.get(4).toString());
            JSONArray JSONRow6 = new JSONArray(rawJSONArray.get(5).toString());
            JSONArray JSONRow7 = new JSONArray(rawJSONArray.get(6).toString());
            System.out.println("Første objekt i første JSON-Array i JSON-Array'et \n" + JSONRow1.get(0) + "\n");

            System.out.println("*** ROWS ***");

            ArrayList<PyCard> row1 = PythonRowToJavaArrayList(JSONRow1);
            ArrayList<PyCard> row2 = PythonRowToJavaArrayList(JSONRow2);
            ArrayList<PyCard> row3 = PythonRowToJavaArrayList(JSONRow3);
            ArrayList<PyCard> row4 = PythonRowToJavaArrayList(JSONRow4);
            ArrayList<PyCard> row5 = PythonRowToJavaArrayList(JSONRow5);
            ArrayList<PyCard> row6 = PythonRowToJavaArrayList(JSONRow6);
            ArrayList<PyCard> row7 = PythonRowToJavaArrayList(JSONRow7);

            boolean a1 = (boolean) rawJSONArray.get(7);
            boolean a2 = (boolean) rawJSONArray.get(8);
            boolean a3 = (boolean) rawJSONArray.get(9);
            boolean a4 = (boolean) rawJSONArray.get(10);

            System.out.println("\n");
            System.out.println("*** ESSERE ***");
            System.out.println(a1);
            System.out.println(a2);
            System.out.println(a3);
            System.out.println(a4);
            System.out.println("\n");

            System.out.println("*** DECK ***");
            JSONArray deck = new JSONArray(rawJSONArray.get(11).toString());
            PythonRowToJavaArrayList(deck);

            JSONObject extraCardJSON = new JSONObject(rawJSONArray.get(12).toString());
            if (!extraCardJSON.isEmpty()){
                PyCard extraCard = g.fromJson(String.valueOf(extraCardJSON), PyCard.class);
                System.out.println("Extra Card: \n" + extraCardJSON);
            }
        }
    }

    public ArrayList<PyCard> PythonRowToJavaArrayList(JSONArray JSONRow) {
        pyCards.clear();
        for (int i = 0; i < JSONRow.length(); i++) {
            System.out.println("Objekt " + i + ": \n" + JSONRow.getJSONObject(i));
            PyCard p = g.fromJson(String.valueOf(JSONRow.getJSONObject(i)), PyCard.class);
            pyCards.add(p);
        }
        return pyCards;
    }

    public static String PythonHttpRequest(String url, String reqMethod, String body, String paramInput) throws IOException {
        String inputLine;
        String result;
        //Selve URL og connection
        URL url2 = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
        connection.setRequestMethod(reqMethod);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        //if body needs to be added
        if (body.length() > 0) {
            //Evt body
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-length", body.getBytes().length + "");
            connection.setUseCaches(false);
            OutputStream os = connection.getOutputStream();
            os.write(body.getBytes("UTF-8"));
            os.close();
        }

        //if params need to be added
        if (paramInput.length() > 0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("url", paramInput));
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();
        }

        //Udfør
        connection.connect();
        InputStreamReader streamReader = new
                InputStreamReader(connection.getInputStream());

        int status = connection.getResponseCode();
        System.out.println("Python Flask response status: "+status);
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder stringBuilder = new StringBuilder();
        //Check if the line we are reading is not null
        while ((inputLine = reader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        reader.close();
        streamReader.close();
        result = stringBuilder.toString();
        return result;
    }

    /**
     * function needed for adding http params
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */

    private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
