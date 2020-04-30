package server;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.GameBoard;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public class Rest_Controller {
    public static Javalin server;
    static GameBoard gameBoard;

    public void startServer() throws Exception {
        if (server != null) return;
        server = Javalin.create().start(8081);
        server.exception(Exception.class,(e,ctx)-> {e.printStackTrace();});
        //TODO: lav endpoints (GET og POST)
        server.get("/login",ctx -> newGame(ctx));
    }
    public void stopServer(){
        server.stop();
    }

    private void newGame(@NotNull Context ctx) throws ExecutionException, InterruptedException {
        gameBoard = new GameBoard();
        String getQueryExample = ctx.queryParam("Row1"); // http://URL?Row1=xx?Row2=yy?Row3=zz osv

        //TODO: Konverter json string af spillet til java solitaire objekt
        System.out.println("Starting new game!");
    }
    private void getNextRound(){

    }

}
