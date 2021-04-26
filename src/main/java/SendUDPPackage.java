import entity.ClientInfo;
import entity.Package;
import util.ProtoStuffUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @ClassName SendUDPPackage
 * @Description TODO
 * @Author illion
 * @Date 2021/4/26 20:57
 * @Version 1.0
 */
public class SendUDPPackage implements SendPackage {

    public void sendPackage(ClientInfo clientInfo, Package newPackage){
        try {
            int port = clientInfo.getPort();
            String ip = clientInfo.getIp();

            byte []arr = ProtoStuffUtil.serialize(newPackage);

            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramPacket datagramPacket = new DatagramPacket(arr,arr.length, InetAddress.getByName(ip),port);

            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
