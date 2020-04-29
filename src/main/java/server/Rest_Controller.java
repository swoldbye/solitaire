package server;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public class Rest_Controller {
    public static Javalin server;

    public void startServer() throws Exception {
        if (server != null) return;
        server = Javalin.create().start(8081);
        server.exception(Exception.class,(e,ctx)-> {e.printStackTrace();});
        //TODO: lav endpoints (GET og POST)
        server.get("/login",ctx -> newGame(ctx));
    }

    private static void newGame(@NotNull Context ctx) throws ExecutionException, InterruptedException {
        String spilDAO = ctx.queryParam("spilObjekt");
        //TODO: Konverter json string af spillet til java solitaire objekt
        System.out.println("Starting new game!");
    }
}
