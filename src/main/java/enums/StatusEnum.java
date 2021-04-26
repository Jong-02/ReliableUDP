package enums;

public enum StatusEnum {
    SEQ(1, "请求报文"),
    ACK(2, "应答报文"),
    FIN(4, "终止报文");

    public static boolean isSEQ(int flag){
        return (flag & SEQ.getNo()) == 1;
    }

    public static boolean isAck(int flag) {
        return (flag & ACK.getNo()) == 1;
    }

    int No;
    String description;

    StatusEnum(int no, String description) {
        No = no;
        this.description = description;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
