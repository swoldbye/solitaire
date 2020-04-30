package server;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.GameBoard;
import model.Row;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Array;
import java.util.concurrent.ExecutionException;

public class Rest_Controller {
    public static Javalin server;
    static GameBoard gameBoard;
    Row[] rows = new Row[7];

    public void startServer() throws Exception {
        if (server != null) return;
        server = Javalin.create().start(8081);
        server.exception(Exception.class,(e,ctx)-> {e.printStackTrace();});
        //TODO: lav endpoints (GET og POST)
        server.get("/newGame",ctx -> newGame(ctx));
    }
    public void stopServer(){
        server.stop();
    }
    /*
             JsonString Example:
                {"Row1":{"Card1":{"suit": 1,"level": 1,"location": 2,"isFaceUp": true}},"Row2":{"Card1":{"suit": 1,"level": 1,"location": 3,"isFaceUp": true}}}
              */
    private void newGame(@NotNull Context ctx) throws ExecutionException, InterruptedException {
        gameBoard = new GameBoard();
        String getRows = ctx.queryParam("Rows");
        String s = "{\"Row1\":{\"Card1\":{\"suit\": 1,\"level\": 1,\"location\": 2,\"isFaceUp\": true}},\"Row2\":{\"Card1\":{\"suit\": 1,\"level\": 1,\"location\": 3,\"isFaceUp\": true}}}";

        Object obj = JSONValue.parse(getRows);
        JSONObject jsonObject = (JSONObject) obj;

        String suit = (String) jsonObject.get("Row1");
        System.out.println("suit: "+suit);
        //TODO: Konverter json string af spillet til java solitaire objekt
        System.out.println("Starting new game!" + suit);
    }
    private void getNextRound(@NotNull Context ctx){
        String query = ctx.queryParam("");
    }

}
