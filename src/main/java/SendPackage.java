import entity.ClientInfo;
import entity.Package;

/**
 * @ClassName SendPackage
 * @Description TODO
 * @Author illion
 * @Date 2021/4/26 20:55
 * @Version 1.0
 */
public interface SendPackage {
    void sendPackage(ClientInfo clientInfo, Package newPackage);
}
