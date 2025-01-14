import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Cliente {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader input = null;
        PrintWriter output = null;
        Scanner scanner = null;

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to server at " + SERVER_ADDRESS + ":" + SERVER_PORT);

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(System.in);

            String userInput;
            String serverResponse;

            while (true) {
                System.out.print("Enter message (or 'exit' to quit): ");
                userInput = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }

                output.println(userInput);
                serverResponse = input.readLine();
                System.out.println("Server response: " + serverResponse);
            }

        } catch(IOException e) { // Catch both IOException and SocketException
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            try {
                if (scanner != null) scanner.close();
                if (input != null) input.close();
                if (output != null) output.close();
                if (socket != null) socket.close();
                System.out.println("Connection closed");
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}