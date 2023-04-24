package com.eonmux;

import io.activej.eventloop.Eventloop;
import io.activej.http.*;
import io.activej.inject.annotation.Eager;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.Module;
import io.activej.launcher.Launcher;
import io.activej.promise.Promise;
import io.activej.service.ServiceGraphModule;

import java.util.TimeZone;

import static io.activej.http.HttpMethod.GET;

public class Router extends Launcher {


    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Provides Eventloop eventloop() {
        return Eventloop.create();
    }

    @Provides AsyncServlet servlet() {
        return RoutingServlet.create().map(GET, "/", request -> request.loadBody().then(() -> {
            test();

            return Promise.of(HttpResponse.ofCode(200).withPlainText("Ran"));
        }));
    }


    @Provides @Eager AsyncHttpServer server(Eventloop eventloop, AsyncServlet servlet) {
        return AsyncHttpServer.create(eventloop, servlet).withListenPort(80);
    }

    @Override protected Module getModule() {
        return ServiceGraphModule.create();
    }

    @Override protected void run() throws Exception {
        System.out.println("HTTP Server is now available at http://" +
                           "127.0.0.1" +
                           ":" +
                           "80");
        awaitShutdown();
    }

    public static void main(String[] args) throws Exception {
        Launcher launcher = new Router();
        launcher.launch(args);
    }

    public static void test() {
        try {
            Main.example1();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try {
            Main.example2();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try {
            Main.example3();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
