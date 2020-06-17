package api;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Rest_controller {

    public static Javalin server;

    public void stop(){
        server.stop();
        server = null;
    }

    public void start(){
        if (server!=null) return;
        try{
            server = Javalin.create().start(8081);
            server.exception(Exception.class, (e,ctx)-> {e.printStackTrace();});
            //TODO: lav endpoints (GET og POST)
            server.post("/ImageURL",ctx -> imageURL(ctx));
        }
        catch (Exception e){
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
        if (!URL.isEmpty()){
            System.out.println("Received the url: "+URL);
            System.out.println("... Now sending to the python server for processing");
            //TODO: Create rest client and send
            ctx.status(200).result("Server message: I received your url!!");
        } else {ctx.status(500).result("Server Error");}
    }



}
