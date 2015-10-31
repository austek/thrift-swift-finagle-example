package com.example.contact.server;

import com.example.config.Config;
import com.example.server.config.FinagleServerConfig;

public class ContactApplication {
    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.err.println("path to config folder required.");
            System.exit(-10);
        }

        new ContactServer(Config.load(FinagleServerConfig.class, args[0])).start();
    }
}
