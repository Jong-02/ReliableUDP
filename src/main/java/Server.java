import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * @ClassName Server
 * @Description TODO
 * @Author illion
 * @Date 2021/4/11 20:10
 * @Version 1.0
 */
public class Server {

    public static void main(String[] args) {
        new Send().start();
        new Receive().start();
    }

    static class Send extends Thread{
        @Override
        public void run(){
            try{
                DatagramSocket datagramSocket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in);
                while(true){
                    String line = scanner.nextLine();
                    if(line.equals("quit")) break;

                    DatagramPacket datagramPacket = new DatagramPacket(line.getBytes(),line.getBytes().length, InetAddress.getByName("127.0.0.1"),8888);

                    System.out.println("Server send:"+line);
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
            try{
                DatagramSocket datagramSocket = new DatagramSocket(6666);
                DatagramPacket datagramPacket = new DatagramPacket(new byte[1024],1024);

                while (true){
                    datagramSocket.receive(datagramPacket);
                    byte[] arr = datagramPacket.getData();
                    int len = datagramPacket.getLength();
                    String ip = datagramPacket.getAddress().getHostAddress();


                    String s1 = new String(arr);
                    JSONObject jsonObject1 = JSONObject.parseObject(s1);
                    String ipAddress = (String) jsonObject1.get("ip");
                    int port = (int) jsonObject1.get("port");
                    System.out.println(ipAddress);
                    System.out.println(port);

                   // int port = datagramPacket.getPort();
                    System.out.println("Client:"+ip+ ":" +port +":" + s1);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
