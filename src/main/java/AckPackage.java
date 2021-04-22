import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName AckPackage
 * @Description TODO
 * @Author illion
 * @Date 2021/4/22 19:59
 * @Version 1.0
 */
@Setter@Getter
public class AckPackage {
    private int port;
    private String ip;
    private String flag;
    private String content;
    private int serialNum;
    private int AckSerialNum;
    private int windowsLength;

    public AckPackage() {
    }

    public AckPackage(int port, String ip, String flag, String content, int serialNum, int ackSerialNum, int windowsLength) {
        this.port = port;
        this.ip = ip;
        this.flag = flag;
        this.content = content;
        this.serialNum = serialNum;
        AckSerialNum = ackSerialNum;
        this.windowsLength = windowsLength;
    }

    @Override
    public String toString() {
        return "AckPackage{" +
                "port=" + port +
                ", ip='" + ip + '\'' +
                ", flag='" + flag + '\'' +
                ", content='" + content + '\'' +
                ", serialNum=" + serialNum +
                ", AckSerialNum=" + AckSerialNum +
                ", windowsLength=" + windowsLength +
                '}';
    }
}
