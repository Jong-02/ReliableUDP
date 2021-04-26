package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName entity.Package
 * @Description TODO
 * @Author illion
 * @Date 2021/4/22 19:59
 * @Version 1.0
 */
@Setter@Getter
@Builder
public class Package {
    private int port;
    private String ip;
    private int flag;
    private String content;
    private int serialNum;
    private int ackSerialNum;
    private int windowsLength;

    public Package() { }

    public Package(int port, String ip, int flag, String content, int serialNum, int ackSerialNum, int windowsLength) {
        this.port = port;
        this.ip = ip;
        this.flag = flag;
        this.content = content;
        this.serialNum = serialNum;
        this.ackSerialNum = ackSerialNum;
        this.windowsLength = windowsLength;
    }

    @Override
    public String toString() {
        return "entity.Package{" +
                "port=" + port +
                ", ip='" + ip + '\'' +
                ", flag='" + flag + '\'' +
                ", content='" + content + '\'' +
                ", serialNum=" + serialNum +
                ", AckSerialNum=" + ackSerialNum +
                ", windowsLength=" + windowsLength +
                '}';
    }

    public static void main(String[] args) {
        PackageBuilder ackPackageBuilder = new PackageBuilder();
        Package aPackage = ackPackageBuilder.ip("127.0.0.1")
                .port(6066)
                .build();
    }
}
