package api;

import com.google.gson.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.GameBoard;
import model.PyCard;
import model.Row;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RESTController {

    public static Javalin server;
    private JSONArray row;
    private Gson g = new Gson();


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
        String gameString = ctx.body();

        if (!gameString.isEmpty()) {
            System.out.println("Received the gameString: \n" + gameString + "\n");
            ctx.status(200).result("Server message: Received gameString: \n" + gameString);

            // Handling the JSON string
            JSONArray rawJSONArray = new JSONArray(gameString);

            JSONArray JSONRow1 = new JSONArray(rawJSONArray.get(0).toString());
            JSONArray JSONRow2 = new JSONArray(rawJSONArray.get(1).toString());
            JSONArray JSONRow3 = new JSONArray(rawJSONArray.get(2).toString());
            JSONArray JSONRow4 = new JSONArray(rawJSONArray.get(3).toString());
            JSONArray JSONRow5 = new JSONArray(rawJSONArray.get(4).toString());
            JSONArray JSONRow6 = new JSONArray(rawJSONArray.get(5).toString());
            JSONArray JSONRow7 = new JSONArray(rawJSONArray.get(6).toString());

            System.out.println(JSONRow1);

            JSONArray JSONDiamond = new JSONArray(rawJSONArray.get(7).toString());
            JSONArray JSONHeart = new JSONArray(rawJSONArray.get(8).toString());
            JSONArray JSONSpade = new JSONArray(rawJSONArray.get(9).toString());
            JSONArray JSONClub = new JSONArray(rawJSONArray.get(10).toString());

            JSONArray JSONDeck = new JSONArray(rawJSONArray.get(11).toString());
            System.out.println(JSONDeck);

            ArrayList<PyCard> row1 = PythonRowToJavaArrayList(JSONRow1);
            ArrayList<PyCard> row2 = PythonRowToJavaArrayList(JSONRow2);
            ArrayList<PyCard> row3 = PythonRowToJavaArrayList(JSONRow3);
            ArrayList<PyCard> row4 = PythonRowToJavaArrayList(JSONRow4);
            ArrayList<PyCard> row5 = PythonRowToJavaArrayList(JSONRow5);
            ArrayList<PyCard> row6 = PythonRowToJavaArrayList(JSONRow6);
            ArrayList<PyCard> row7 = PythonRowToJavaArrayList(JSONRow7);

            ArrayList<PyCard> aceD = PythonRowToJavaArrayList(JSONDiamond);
            ArrayList<PyCard> aceH = PythonRowToJavaArrayList(JSONHeart);
            ArrayList<PyCard> aceS = PythonRowToJavaArrayList(JSONSpade);
            ArrayList<PyCard> aceC = PythonRowToJavaArrayList(JSONClub);

            ArrayList<PyCard> deck = new ArrayList(PythonRowToJavaArrayList(JSONDeck));

            System.out.println(row1.get(0).getSuitNumber());

            System.out.println(deck.get(0).getSuitNumber());

            System.out.println(row1);
            System.out.println(deck);


        }
    }

    public ArrayList<PyCard> PythonRowToJavaArrayList(JSONArray JSONRow) {
        ArrayList<PyCard> pyCards = new ArrayList<>();
        for (int i = 0; i < JSONRow.length(); i++) {
            PyCard p = g.fromJson(String.valueOf(JSONRow.getJSONObject(i)), PyCard.class);
            pyCards.add(p);
        }
        return pyCards;
    }

//    public Row ArrayListToRow(ArrayList<PyCard> pyCards, int location){
//
//        return pyCards;
//    }
}
