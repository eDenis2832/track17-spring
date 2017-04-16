package track.messenger;

import sun.font.TrueTypeFont;
import track.messenger.messages.Message;
import track.messenger.net.Protocol;
import track.messenger.net.StringProtocol;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


/**
 *
 */
public class Main {
    public static void main (String[] args) {
        Socket cs;
        InputStream in;
        OutputStream out;
        Protocol p = new StringProtocol();
        int read;

        byte[] buf = new byte[1024 * 60];
        try {
            ServerSocket ss = new ServerSocket(19000);
            while (true) {
                cs = ss.accept();
                in = cs.getInputStream();
                out = cs.getOutputStream();
                read = in.read(buf);
                Message msg = p.decode(Arrays.copyOf(buf, read));

                System.out.print(msg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
