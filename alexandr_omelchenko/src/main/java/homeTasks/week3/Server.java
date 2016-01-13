package homeTasks.week3;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        SocketChannel clChannel = null;
        try {
            ServerSocketChannel ssChannel = ServerSocketChannel.open();
            ssChannel.socket().bind(new InetSocketAddress(8189));
            clChannel = ssChannel.accept();}
        catch (IOException e) {
            e.printStackTrace();}

        ByteBuffer buffer = ByteBuffer.allocate(100);
        int readed=0;
        while (true) {
//Сервер ЧИТАЕТ
                readed = clChannel.read(buffer);
                String mess = new String(buffer.array(), 0, readed);
                System.out.println("Get "+mess);
            buffer.flip();
            buffer.clear();
//СЕРВЕР ПИШЕТ
            Scanner scan = new Scanner(System.in);
            String m = scan.nextLine();
            buffer.put(m.getBytes());
            buffer.flip();
            while (buffer.hasRemaining()) {
                clChannel.write(buffer);}
            System.out.println("send "+m);
            buffer.flip();
            buffer.clear();
        }
    }
}