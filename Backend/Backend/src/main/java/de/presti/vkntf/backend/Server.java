package de.presti.vkntf.backend;

import lombok.AccessLevel;
import lombok.Getter;

public class Server {

    @Getter(AccessLevel.PUBLIC)
    private static Server instance;

    String[] currentProgramArguments;

    public Server(String[] args) {
        currentProgramArguments = args;
    }

    public Server() {
        instance = this;
    }


}
