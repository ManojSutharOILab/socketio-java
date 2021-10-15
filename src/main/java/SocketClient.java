import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Scanner;

public class SocketClient {
    private static Socket mSocket;
    private static String SERVER_URL = "http://192.168.1.48:3000";
    public static void main(String[] args) {
        try {
            mSocket = IO.socket(SERVER_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //Getting user id
        System.out.println("Enter your id: ");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.next();

        mSocket.connect();

        //On connect
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                mSocket.emit("msg", "Hello from Java");

                JSONObject object = new JSONObject();
                try {
                    object.put("email", id);

                    //Join by id
                    mSocket.emit("join",object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mSocket.on("msg", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(args[0]);
            }
        });

        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(args[0]);
            }
        });


    }
}
