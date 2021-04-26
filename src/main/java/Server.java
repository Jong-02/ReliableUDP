import entity.Package;
import entity.ClientInfo;
import lombok.extern.slf4j.Slf4j;
import util.ProtoStuffUtil;

import java.io.IOException;
import java.net.*;
import java.util.*;

import static enums.StatusEnum.isSEQ;

/**
 * @ClassName Server
 * @Description
 * C1 -> Server:
 * Server -> C2 address -> C1
 * C1 -> message -> C2
 *
 *
 * @Author illion
 * @Date 2021/4/11 20:10
 * @Version 1.0
 */
@Slf4j
public class Server extends Thread{
    // public static Logger logger = LoggerFactory.getLogger("Server.class");

    private static volatile int status;
    private static final int RUNNING = 0;
    private static final int STOP = -1;

    private boolean isStop(){
        return status == STOP;
    }




    String content = "";

    static HashSet<ClientInfo> clientInfoHashSetSet = new HashSet<>();
    static Map<String, ClientInfo> clientInfoMap = new HashMap<>();


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


    private void send2(Package aPackage){
        try{
            byte []packet = ProtoStuffUtil.serialize(aPackage);

            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramPacket datagramPacket = new DatagramPacket(packet,packet.length,InetAddress.getByName(aPackage.getIp()), aPackage.getPort());

            datagramSocket.send(datagramPacket);

        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void acknowledge(Package aPackage){
        aPackage.setAckSerialNum(aPackage.getSerialNum()+1);
    }


/*
    @Override
    public void run() {
        try(DatagramSocket datagramSocket = new DatagramSocket(6666)) {
            DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
            while (true) {
                datagramSocket.receive(datagramPacket);
                entity.ClientSentP clientSentP = util.ProtoStuffUtil.deserialize(datagramPacket.getData(), datagramPacket.getLength(), entity.ClientSentP.class);

                content = clientSentP.getContent();
                System.out.println("Client send:"+content);

                clientInfoHashSetSet.add(clientSentP.getClientInfo());
                for (entity.ClientInfo clientInfo : clientInfoHashSetSet) {
                    send(content,clientInfo.getIp(),clientInfo.getPort());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 */

    @Override
    public void run() {
        final int PORT = 6888;
        // 字面量
        try(DatagramSocket datagramSocket = new DatagramSocket(PORT)) {
            DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
            while (!isStop()) {
                datagramSocket.receive(datagramPacket);

                Package aPackage = ProtoStuffUtil.deserialize(datagramPacket.getData(),datagramPacket.getLength(), Package.class);

                content = aPackage.getContent();
                System.out.println("Client send:" + content);
                int flag = aPackage.getFlag();

                if (isSEQ(flag)){
                    acknowledge(aPackage);
                    aPackage.setContent("Received Successfully");
                    send2(aPackage);
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

                entity.ClientSentP clientSentP = util.ProtoStuffUtil.deserialize(valid, entity.ClientSentP.class);
                entity.ClientInfo clientInfo = clientSentP.getClientInfo();

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


    public static void main(String[] args) {
        new Server().start();
    }
}


