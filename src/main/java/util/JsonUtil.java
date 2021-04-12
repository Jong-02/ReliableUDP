package util;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName JsonUtil
 * @Description TODO
 * @Author illion
 * @Date 2021/4/11 21:10
 * @Version 1.0
 */
public class JsonUtil {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip", "127.0.0.1");
        jsonObject.put("port", 6666);
        String s = jsonObject.toJSONString();
        System.out.println(s);
        byte[] bytes = s.getBytes();



        String s1 = new String(bytes);
        JSONObject jsonObject1 = JSONObject.parseObject(s1);
        System.out.println(jsonObject1.get("ip"));
        System.out.println(jsonObject1.get("port"));
    }
}
