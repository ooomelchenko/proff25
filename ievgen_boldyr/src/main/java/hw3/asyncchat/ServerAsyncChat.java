package hw3.asyncchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by IEvgen Boldyr on 06.06.15.
 * Project: proff25
 */

public class ServerAsyncChat {

    static SocketChannel sc;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(30000));
        sc = ssc.accept();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ByteBuffer buffer = ByteBuffer.allocate(128);
                while (true) {
                    try {
                        int bytesRead;
                        buffer.rewind();
                        while ((bytesRead = sc.read(buffer)) > 0) {
                            System.out.print("Client : ");
                            System.out.println(new String(buffer.array(), 0, bytesRead));
                        }
                        buffer.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        ByteBuffer buffer = ByteBuffer.allocate(128);
        Scanner scanner = new Scanner(System.in);
        String message;
        while (true) {
            message = scanner.nextLine();

            buffer.put(message.getBytes());
            buffer.rewind();
            while (buffer.hasRemaining()) {
                sc.write(buffer);
            }
            buffer.clear();
        }
    }
}
