import entity.Package;
import entity.ClientInfo;
import entity.ClientSentP;
import enums.StatusEnum;
import util.ProtoStuffUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

/**
 * @ClassName Client
 * @Description TODO
 * @Author illion
 * @Date 2021/4/11 20:11
 * @Version 1.0
 */
public class Client {
    public static int PORT = 8889;
    public static void main(String[] args) {
        new Send().start();
        new Receive().start();
    }

    static class Send extends Thread{

        @Override
        public void run(){
            /*try {
                DatagramSocket datagramSocket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in);

                entity.ClientSentP clientSentP = new entity.ClientSentP();
                entity.ClientInfo clientInfo = new entity.ClientInfo("127.0.0.1",PORT);

                while (true){
                    String line = scanner.nextLine();
                    if(line.equals("quit")) break;

                    clientSentP.setContent(line);
                    clientSentP.setClientInfo(clientInfo);

                    byte[] packet = util.ProtoStuffUtil.serialize(clientSentP);

                    DatagramPacket datagramPacket = new DatagramPacket(packet,packet.length ,InetAddress.getByName("127.0.0.1"),6666);
                    System.out.println("Client send:"+line);

                    datagramSocket.send(datagramPacket);
                }
                datagramSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

             */
            try {
                DatagramSocket datagramSocket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in);

                ClientSentP clientSentP = new ClientSentP();
                ClientInfo clientInfo = new ClientInfo("127.0.0.1",PORT);

                Timer timer = new Timer();

                while (true){
                    String line = scanner.nextLine();
                    if(line.equals("quit")) break;
                    int randomNum = (int)Math.random()*100;
                    Package aPackage = new Package(PORT,"127.0.0.1", StatusEnum.SEQ.getNo(),line,randomNum,0,100);
                    aPackage.setContent(line);

                    byte[] packet = ProtoStuffUtil.serialize(aPackage);

                    DatagramPacket datagramPacket = new DatagramPacket(packet,packet.length ,InetAddress.getByName("127.0.0.1"),6666);
                    System.out.println("Client send:"+line);

                    datagramSocket.send(datagramPacket);
                }
                datagramSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }



    static class Receive extends Thread{
        @Override
        public void run(){
            try {
                DatagramSocket datagramSocket = new DatagramSocket(PORT);
                DatagramPacket datagramPacket = new DatagramPacket(new byte[1024],1024);

                while (true){
                    datagramSocket.receive(datagramPacket);
                    byte[] arr = datagramPacket.getData();

                    Package aPackage = ProtoStuffUtil.deserialize(arr,datagramPacket.getLength(), Package.class);

                    int serial = aPackage.getSerialNum();
                    int ack = aPackage.getAckSerialNum();

                    if(ack == serial+1){
                        System.out.println("Send Successfully");
                    }
                    else
                        System.out.println("Send failed");

                    int len = datagramPacket.getLength();
                    String ip = datagramPacket.getAddress().getHostAddress();

                    int port = datagramPacket.getPort();
                    System.out.println("Server:"+ip+":"+port+":"+new String(arr,0,len));

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
