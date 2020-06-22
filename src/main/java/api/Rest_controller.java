package api;

import com.google.gson.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.PyCard;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Rest_controller {

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
        //        // 1) Call python server
        //        // 2) Receive Game Object
        //        // 3) Calculate move
        //        // 4) Return move string
        if (!URL.isEmpty()) {
            System.out.println("Received the url: " + URL);
            System.out.println("... Now sending to the python server for processing");
            //TODO: Create rest client and send
            ctx.status(200).result("Server message: I received your url!!");
        } else {
            ctx.status(500).result("Server Error");
        }
    }

    private void getFromPython(@NotNull Context ctx) throws ExecutionException, InterruptedException, IOException {
        String cardsString = ctx.body();

        if (!cardsString.isEmpty()) {
            System.out.println("Received the cardsString: \n" + cardsString + "\n");
            ctx.status(200).result("Server message: Received cardsString: \n" + cardsString);

            // Handling the JSON string
            JSONArray rawJSONArray = new JSONArray(cardsString);
            System.out.println("Første array i JSON-Arrayet: \n" + rawJSONArray.get(0) + "\n");

            JSONArray row1 = new JSONArray(rawJSONArray.get(0).toString());
            System.out.println("Første objekt i første JSON-Array i JSON-Array'et \n" + row1.get(0) + "\n");

            System.out.println("*** ROWS ***");
            PythonRowToJavaArrayList(row1);
//            PythonRowToJavaArrayList(row2);
//            PythonRowToJavaArrayList(row3);
//            PythonRowToJavaArrayList(row4);
//            PythonRowToJavaArrayList(row5);
//            PythonRowToJavaArrayList(row6);
//            PythonRowToJavaArrayList(row7);

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

    public void PythonRowToJavaArrayList(JSONArray row) {
        pyCards.clear();
        for (int i = 0; i < row.length(); i++) {
            System.out.println("Objekt " + i + ": \n" + row.getJSONObject(i));
            PyCard p = g.fromJson(String.valueOf(row.getJSONObject(i)), PyCard.class);
            pyCards.add(p);
        }

    }
}
