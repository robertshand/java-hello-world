import java.util.Scanner;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "*** Java - Hello World ! ***\n" +
                    "WELCOME_MSG : " + System.getenv("WELCOME_MSG") + "\n" +
                    "Hostname is : " + getHostname() +
                    "Process ID  : " + getProcessId();

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static int getProcessId() {
        return CLibrary.INSTANCE.getpid();
    }

    private interface CLibrary extends com.sun.jna.Library {
        CLibrary INSTANCE = (CLibrary) com.sun.jna.Native.loadLibrary("c", CLibrary.class);

        int getpid();
    }

    private static String getHostname() {
        String hostname = "Unknown";

        try {
            // InetAddress address;
            // address = InetAddress.getLocalHost();
            // hostname = address.getHostName();
            hostname = execReadToString("hostname");
        } catch (Exception ex) {
            System.out.println("Hostname can not be resolved");
        }

        return hostname;
    }

    public static String execReadToString(String execCommand) throws IOException {
        Process proc = Runtime.getRuntime().exec(execCommand);
        try (InputStream stream = proc.getInputStream()) {
            try (Scanner s = new Scanner(stream).useDelimiter("\\A")) {
                return s.hasNext() ? s.next() : "";
            }
        }
    }

}
