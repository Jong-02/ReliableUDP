import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * @ClassName Server
 * @Description TODO
 * @Author illion
 * @Date 2021/4/11 20:10
 * @Version 1.0
 */
@Slf4j
public class Server extends Thread{

    // public static Logger logger = LoggerFactory.getLogger("Server.class");

     String content = new String();

    static HashSet<ClientInfo> clientInfoHashSetSet = new HashSet<>();

    public static void main(String[] args) {
        new Server().start();

    }

    private void send(String content, String ip, int port) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramPacket datagramPacket = new DatagramPacket(content.getBytes(), content.getBytes().length, InetAddress.getByName(ip), port);

            log.info("Server send:" + content);

            datagramSocket.send(datagramPacket);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void run() {
        try(DatagramSocket datagramSocket = new DatagramSocket(6666)) {
            DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
            while (true) {
                datagramSocket.receive(datagramPacket);
                ClientSentP clientSentP = ProtoStuffUtil.deserialize(datagramPacket.getData(), datagramPacket.getLength(), ClientSentP.class);

                content = clientSentP.getContent();
                System.out.println("Client send:"+content);

                clientInfoHashSetSet.add(clientSentP.getClientInfo());
                for (ClientInfo clientInfo : clientInfoHashSetSet) {
                    send(content,clientInfo.getIp(),clientInfo.getPort());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    static class Receive extends Thread {
    @Override
    public void run() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(6666);
            DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);

            while (true) {
                datagramSocket.receive(datagramPacket);
                byte[] arr = datagramPacket.getData();

                int length = datagramPacket.getLength();

                byte[] valid = new byte[length];

                System.arraycopy(arr, 0, valid, 0, length);

                ClientSentP clientSentP = ProtoStuffUtil.deserialize(valid, ClientSentP.class);
                ClientInfo clientInfo = clientSentP.getClientInfo();

                hashSet.add(clientInfo);

                content = clientSentP.getContent();

                String ip = clientInfo.getIp();
                int port = clientInfo.getPort();

                System.out.println("Client:" + ip + ":" + port + ":" + content);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 */
}


