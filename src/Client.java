import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) {
        String name = args[0];
        int read;

        if (args.length != 3) {
            System.out.println("Invalid number of arguments.");
            return;
        }

        try
            (Socket socket = new Socket(args[1], Integer.parseInt(args[2]));
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedInputStream in = new BufferedInputStream((new FileInputStream(args[0])));
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            File file = new File(name);
            long fileSize = file.length();
            output.write(name + " " + fileSize + "\n");
            output.flush();
            byte[] buffer = new byte[8192];
            while (fileSize > 0 && (read = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, read);
                fileSize -= read;
                out.flush();
            }
            String line = input.readLine();
            System.out.println(line);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}

