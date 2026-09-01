package dummy;

import exceptions.NotValidPortNumber;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DummyServer implements Runnable {
    private String name;
    private int port;

    public DummyServer(String name, int port) throws NullPointerException, NotValidPortNumber {
        if(name == null) throw new NullPointerException();
        this.name = name;

        if(port < 0 || port > 65535) throw new NotValidPortNumber("A port can only be between 0 and 65535.");
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            System.out.println("Serveur " + this.name + " lancé sur le port " + this.port);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client connecté");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String clientInputLine;
                while ((clientInputLine = in.readLine()) != null) {
                    System.out.println(clientInputLine);
                    if (clientInputLine.isEmpty()) {
                        break;
                    }
                }

                out.write("HTTP/1.0 200 OK\r\n");
                out.write("\r\n");
                out.write("<TITLE>" + name + "</TITLE>");
                out.write("<h1>Bonjour, ici le serveur " + this.name + "</h1>");

                System.err.println("Connexion avec le client terminée");
                out.close();
                in.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
