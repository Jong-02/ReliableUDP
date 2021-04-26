package entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @ClassName entity.ClientInfo
 * @Description TODO
 * @Author illion
 * @Date 2021/4/13 20:24
 * @Version 1.0
 */
@Setter @Getter
public class ClientInfo {
    private String ip;
    private int port;

    public ClientInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public ClientInfo() {
    }

    public boolean equals(ClientInfo clientInfo){
        if(this.ip == clientInfo.getIp()|this.port == clientInfo.getPort() )return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip,port);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(!(obj instanceof ClientInfo)){
            return false;
        }

        ClientInfo clientInfo = (ClientInfo) obj;
        return port ==clientInfo.port && Objects.equals(ip,clientInfo.ip);
    }

    @Override
    public String toString() {
        return "entity.ClientInfo{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
