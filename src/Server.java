import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Color.Text;

public class Server implements Runnable {
    
    private ArrayList<ConnectionHandler> conections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;
    
    public Server() {
        conections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(5050);
            pool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                conections.add(handler);
                pool.execute(handler);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void broadcast(String message) {
        for (ConnectionHandler ch : conections)
            if (ch != null)
                ch.sendMessage(message);
    }

    public void shutdown() {
        try {
            done = true;
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler ch : conections) {
                ch.shutdown();
            }
        } catch (IOException e) {
            // e.printStackTrace();
            // TODO Auto-generated catch block
        }
    }

    class ConnectionHandler implements Runnable {

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                // out.println("NickName: ");
                // nickname = in.readLine();
                // System.out.println(nickname + " Connected!");
                broadcast("New User join");
                String messege;

                while ((messege = in.readLine()) != null) {
                    if (messege.startsWith("/nick")) {
                        String[] messegeSplit = messege.split(" ", 2);
                        if (messegeSplit.length == 2) {
                            broadcast(nickname + " renamed themselve to " + messegeSplit[1]);
                            System.out.println(nickname + " renamed themselve to " + messegeSplit[1]);
                            nickname = messegeSplit[1];
                            out.println("Success change nickname");
                        } else {
                            out.println("No nickname provider");
                        }

                    } else if (messege.startsWith("/quit")) {
                        broadcast(nickname+" left the chat!");
                        shutdown();
                    } else {
                        // broadcast(nickname + ": " + messege);
                        // broadcast(Text.color(nickname, messege));
                        broadcast(messege);
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
                shutdown();
            }

        }

        public void shutdown() {
            try {
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

    }
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

}
