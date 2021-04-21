import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName ClientSentP
 * @Description TODO
 * @Author illion
 * @Date 2021/4/13 21:48
 * @Version 1.0
 */
@Getter @Setter
public class ClientSentP {
    private String content;
    private ClientInfo clientInfo;

    public ClientSentP(String content, ClientInfo clientInfo) {
        this.content = content;
        this.clientInfo = clientInfo;
    }

    public ClientSentP() {
    }

    @Override
    public String toString() {
        return "ClientSentP{" +
                "content='" + content + '\'' +
                ", clientInfo=" + clientInfo +
                '}';
    }
}
