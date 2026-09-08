package model;

import exceptions.NotValidPortNumber;

public class BackendServer {
    private String host;
    private int port;
    private boolean isAlive;

    public BackendServer(String host, int port) throws NullPointerException, NotValidPortNumber {
        if(host == null) throw new NullPointerException();
        this.host = host;

        if (port < 0 || port > 65535) throw new NotValidPortNumber("A port can only be between 0 and 65535.");
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public String getHost() {
        return this.host;
    }

    public boolean getStatus() {
        return this.isAlive;
    }

    public void setStatus(boolean reachable){
        this.isAlive = reachable;
    }
}
