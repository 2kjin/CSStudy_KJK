package server;

import com.google.gson.Gson;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private static final int PORT = 12345;
  private static final int THREAD_POOL_SIZE = 10;
  private ServerSocket serverSocket;
  private ExecutorService pool;
  private Gson gson = new Gson();

  public Server() throws IOException {
    serverSocket = new ServerSocket(PORT);
    pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
  }

  public void start() {
    System.out.println("Server started on port " + PORT);
    try {
      while (true) {
        Socket clientSocket = serverSocket.accept();
        pool.execute(new ClientHandler(clientSocket));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      stop();
    }
  }

  public void stop() {
    try {
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    pool.shutdown();
  }

  class ClientHandler implements Runnable {
    private Socket clientSocket;

    ClientHandler(Socket socket) {
      this.clientSocket = socket;
    }

    @Override
    public void run() {
      try {
        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

        String jsonData = in.readUTF();
        Command command = gson.fromJson(jsonData, Command.class);

        String response = processCommand(command);

        out.writeUTF(response);
        out.flush();

        in.close();
        out.close();
        clientSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private String processCommand(Command command) {
      if (command.action.equals("create")) {
        return gson.toJson(new Response("Product created successfully", 200));
      }
      return gson.toJson(new Response("Invalid command", 400));
    }
  }

  static class Command {
    String action;
    Object data;
  }

  static class Response {
    String message;
    int status;

    Response(String message, int status) {
      this.message = message;
      this.status = status;
    }
  }

  public static void main(String[] args) {
    try {
      Server server = new Server();
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
