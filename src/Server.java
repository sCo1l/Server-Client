import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) {
        String[] arrData;
        int read;

        if (args.length != 1) {
            System.out.println("Invalid number of arguments");
            return;
        }

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
                 Socket socket = serverSocket.accept();
                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
                 BufferedWriter output = new BufferedWriter(new OutputStreamWriter (socket.getOutputStream()))) {

                String data = input.readLine();
                arrData = data.split(" ");
                FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Co1l\\Desktop\\" + arrData[0]);
                long fileSize = Long.parseLong(arrData[1]);

                byte[] buffer = new byte[2048];
                while (fileSize > 0 && (read = in.read(buffer, 0, (int) Math.min(buffer.length, fileSize))) != -1) {
                    fileOut.write(buffer, 0, read);
                    fileSize -= read;
                    fileOut.flush();
                }
                fileOut.close();
                output.write("File delivered");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}