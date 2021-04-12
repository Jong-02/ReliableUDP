import com.alibaba.fastjson.JSONObject;

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
public class Server {

    static String content = new String();

    static List<JSONObject> jsonObjectList = new List<JSONObject>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<JSONObject> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(JSONObject jsonObject) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends JSONObject> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends JSONObject> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public JSONObject get(int index) {
            return null;
        }

        @Override
        public JSONObject set(int index, JSONObject element) {
            return null;
        }

        @Override
        public void add(int index, JSONObject element) {

        }

        @Override
        public JSONObject remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<JSONObject> listIterator() {
            return null;
        }

        @Override
        public ListIterator<JSONObject> listIterator(int index) {
            return null;
        }

        @Override
        public List<JSONObject> subList(int fromIndex, int toIndex) {
            return null;
        }
    };

    public static void main(String[] args) {
        new Send().start();
        new Receive().start();

        new SendAll().start();
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


    static class SendAll extends Thread{
        @Override
        public void run(){
            try{
                DatagramSocket datagramSocket = new DatagramSocket();

                for(JSONObject jsonObject:jsonObjectList){

                    String ip = (String) jsonObject.get("ip");
                    int port = (int) jsonObject.get("port");

                    DatagramPacket datagramPacket = new DatagramPacket(content.getBytes(),content.getBytes().length, InetAddress.getByName(ip),port);
                    System.out.println("Server send:"+content);
                    datagramSocket.send(datagramPacket);
                }

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
                    content = s1;
                    JSONObject jsonObject1 = JSONObject.parseObject(s1);

                    jsonObjectList.add(jsonObject1);

                    String ipAddress = (String) jsonObject1.get("ip");
                    int port = (int) jsonObject1.get("port");
                    System.out.println(ipAddress);
                    System.out.println(port);

                    System.out.println("Client:"+ip+ ":" +port +":" + s1);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
