package api;

import com.google.gson.*;
import controller.GameController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.Card;
import model.GameBoard;
import model.PyCard;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RESTController {

    public static Javalin server;
    private Gson g = new Gson();

    public GameBoard gameBoard;

    public GameController gameController;

    public ArrayList<Card> oldCards = new ArrayList<>();

    public ArrayList<Card> visibleCards = new ArrayList<>();

    public void stop() {
        server.stop();
        server = null;
    }

    public void start() {
        if (server != null) return;
        try {
            server = Javalin.create().start(3333);
            server.exception(Exception.class, (e, ctx) -> {
                e.printStackTrace();
            });
            //TODO: lav endpoints (GET og POST)
            server.post("/ImageURL", ctx -> imageURL(ctx));
            server.post("/Hello", ctx -> getFromPython(ctx));
            server.post("/Test", ctx -> testMethod(ctx));
        } catch (Exception e) {
            stop();
            e.printStackTrace();
        }


    }

    private void testMethod(@NotNull Context ctx) throws ExecutionException, InterruptedException, IOException {
        ctx.status(200).result("Server message: Received gameString: \n" + ctx.body());
        System.out.println("hej");
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
            String pythonResponse = "";
            System.out.println("Received the url: " + URL);
            System.out.println("... Now sending to the python server for processing");
            simplehttp comm = new simplehttp();
            try {
                pythonResponse = comm.getPython(URL);
                if (!pythonResponse.isEmpty()) {
                    //getFromPython(pythonResponse);
                    System.out.println("PYTHON RESPONSE : " + pythonResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //TODO: Create rest client and send
            ctx.status(200).result("PYTHON RESPONSE: " + pythonResponse);
        } else {
            ctx.status(500).result("Server Error");
        }
    }

    private void getFromPython(@NotNull Context ctx) throws ExecutionException, InterruptedException, IOException {
        String gameString = ctx.body();
//        String oldString = "";
//        if (!gameString.equals(oldString)){
//
//        }

        if (!gameString.isEmpty()) {
            System.out.println("Received the gameString: \n" + gameString + "\n");
            ctx.status(200).result("Server message: Received gameString: \n" + gameString);

            // Handling the JSON string
            JSONArray rawJSONArray = new JSONArray(gameString);
            //boolean newGame = rawJSONArray.getBoolean(12);
            //System.out.println(newGame);

            if (rawJSONArray.length() != 12) {
                ctx.status(200).result("PYTHON RESPONSE: TAKE NEW PHOTO");
                return;
            }

            JSONArray JSONRow1 = new JSONArray(rawJSONArray.get(0).toString());
            JSONArray JSONRow2 = new JSONArray(rawJSONArray.get(1).toString());
            JSONArray JSONRow3 = new JSONArray(rawJSONArray.get(2).toString());
            JSONArray JSONRow4 = new JSONArray(rawJSONArray.get(3).toString());
            JSONArray JSONRow5 = new JSONArray(rawJSONArray.get(4).toString());
            JSONArray JSONRow6 = new JSONArray(rawJSONArray.get(5).toString());
            JSONArray JSONRow7 = new JSONArray(rawJSONArray.get(6).toString());
            JSONArray JSONDiamond = new JSONArray(rawJSONArray.get(7).toString());
            JSONArray JSONHeart = new JSONArray(rawJSONArray.get(8).toString());
            JSONArray JSONSpade = new JSONArray(rawJSONArray.get(9).toString());
            JSONArray JSONClub = new JSONArray(rawJSONArray.get(10).toString());
            JSONArray JSONDeck = new JSONArray(rawJSONArray.get(11).toString());

            ArrayList<PyCard> pyRow1 = PythonRowToJavaArrayList(JSONRow1);
            ArrayList<PyCard> pyRow2 = PythonRowToJavaArrayList(JSONRow2);
            ArrayList<PyCard> pyRow3 = PythonRowToJavaArrayList(JSONRow3);
            ArrayList<PyCard> pyRow4 = PythonRowToJavaArrayList(JSONRow4);
            ArrayList<PyCard> pyRow5 = PythonRowToJavaArrayList(JSONRow5);
            ArrayList<PyCard> pyRow6 = PythonRowToJavaArrayList(JSONRow6);
            ArrayList<PyCard> pyRow7 = PythonRowToJavaArrayList(JSONRow7);
            ArrayList<PyCard> pyAceD = PythonRowToJavaArrayList(JSONDiamond);
            ArrayList<PyCard> pyAceH = PythonRowToJavaArrayList(JSONHeart);
            ArrayList<PyCard> pyAceS = PythonRowToJavaArrayList(JSONSpade);
            ArrayList<PyCard> pyAceC = PythonRowToJavaArrayList(JSONClub);
            ArrayList<PyCard> pyDeck = PythonRowToJavaArrayList(JSONDeck);

            ArrayList<Card> row1 = PyArrayListToCardArrayList(pyRow1);
            ArrayList<Card> row2 = PyArrayListToCardArrayList(pyRow2);
            ArrayList<Card> row3 = PyArrayListToCardArrayList(pyRow3);
            ArrayList<Card> row4 = PyArrayListToCardArrayList(pyRow4);
            ArrayList<Card> row5 = PyArrayListToCardArrayList(pyRow5);
            ArrayList<Card> row6 = PyArrayListToCardArrayList(pyRow6);
            ArrayList<Card> row7 = PyArrayListToCardArrayList(pyRow7);
            ArrayList<Card> dStack = PyArrayListToCardArrayList(pyAceD);
            ArrayList<Card> hStack = PyArrayListToCardArrayList(pyAceH);
            ArrayList<Card> sStack = PyArrayListToCardArrayList(pyAceS);
            ArrayList<Card> cStack = PyArrayListToCardArrayList(pyAceC);
            ArrayList<Card> deck = PyArrayListToCardArrayList(pyDeck);

            visibleCards = new ArrayList<Card>();
            visibleCards.addAll(row1);
            visibleCards.addAll(row2);
            visibleCards.addAll(row3);
            visibleCards.addAll(row4);
            visibleCards.addAll(row5);
            visibleCards.addAll(row6);
            visibleCards.addAll(row7);
            visibleCards.addAll(deck);


            if (pyRow1.size() == 1 && pyRow2.size() == 1 && pyRow3.size() == 1 && pyRow4.size() == 1 && pyRow5.size() == 1 && pyRow6.size() == 1 && pyRow7.size() == 1
                    && dStack.isEmpty() && hStack.isEmpty() && sStack.isEmpty() && cStack.isEmpty() && deck.isEmpty()) {
                for (Card c : visibleCards) {
                    oldCards.add(c);
                }
                String turn = startNewGame(visibleCards);
                ctx.status(200).result(turn);
            } else {
                for (Card c : oldCards) {
                    for (Card c2 : visibleCards) {
                        if (c.getCard().equals(c2.getCard())) {
                            visibleCards.remove(c2);
                            break;
                        }
                    }
                }

                if (visibleCards.size() > 1) {
                    ctx.status(200).result("PYTHON RESPONSE: TAKE NEW PHOTO");
                } else {
                    Card newCard = new Card(4, 0, false);
                    //visibleCards.trimToSize();
                    if (visibleCards.size() != 0) {
                        newCard = visibleCards.get(0);
                        oldCards.add(newCard);
                    }
                    String turn = gameController.startTurn(newCard);
                    System.out.println(turn);
                    ctx.status(200).result(turn);
                }
            }
        }
    }

    public String startNewGame(ArrayList<Card> cards) {
        gameBoard = new GameBoard(cards);
        gameController = new GameController(gameBoard);
        String move = gameController.play();
        return move;
    }

    public void giveNewCard() {

    }

    public ArrayList<PyCard> PythonRowToJavaArrayList(JSONArray JSONRow) {
        ArrayList<PyCard> pyCards = new ArrayList<>();
        for (int i = 0; i < JSONRow.length(); i++) {
            PyCard p = g.fromJson(String.valueOf(JSONRow.getJSONObject(i)), PyCard.class);
            pyCards.add(p);
        }
        return pyCards;
    }

    public ArrayList<Card> PyArrayListToCardArrayList(ArrayList<PyCard> pyCards) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < pyCards.size(); i++) {
            Card card = new Card(4, 0, true);
            card.setSuit(pyCards.get(i).getSuit());
            card.setLevel(pyCards.get(i).getValue());
            cards.add(card);
        }
        return cards;
    }

//    public ArrayList<Card> CheckForFirstGame

//    public Row ArrayListToRow(ArrayList<PyCard> pyCards, int location){
//
//        return pyCards;
//    }
}
