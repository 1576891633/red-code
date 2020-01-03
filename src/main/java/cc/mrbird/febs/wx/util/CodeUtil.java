package cc.mrbird.febs.wx.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pengyi on 2017/8/14 0014.
 */
public class CodeUtil {
    private static long sequence = 0L;
    private static String date;
    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    
    /**
     * 32位UUID
     *
     * @return
     */
    public static String getCode() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.toString();
    }
    
    /**
     * 八位Code
     *
     * @return
     */
    public static String getShortCode() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = getCode();
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString().toUpperCase();
    }

    /**
     * 六位Code
     *
     * @return
     */
    public static String getSorterCode() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = getCode();
        for (int i = 0; i < 6; i++) {
            String str = uuid.substring(i * 4, i * 4 + 2);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString().toUpperCase();
    }
    
    /**
     * 获取20位订单号
     *
     * @return
     */
    public static String getOrderNum() {
        return new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()) + securityCode(4);
    }
    
    
    /**
     * 获取ID号码 9 位
     *
     * @return
     */
    public static String getIDNum(String profix) {
        return profix + new SimpleDateFormat("MMddSS").format(new Date()) + securityCode(3);
    }
    
    
    /**
     * 带日期前缀的32位UUID
     *
     * @return
     */
    public static String getPrefixDateCode() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_" + getCode();
    }
    
    /**
     * 带毫秒数前缀的32位UUID
     *
     * @return
     */
    public static String getPrefixTimeCode() {
        return System.currentTimeMillis() + "_" + getCode();
    }
    
    /**
     * 带毫秒数前缀的8位Code
     *
     * @return
     */
    public static String getPrefixTimeShortCode() {
        return System.currentTimeMillis() + "_" + getShortCode();
    }
    
    /**
     * 带自定义前缀的毫秒8位Code
     *
     * @return
     */
    public static String getPrefixTimeShortCode(String prefix) {
        return prefix + "_" + System.currentTimeMillis() + "_" + getShortCode();
    }
    
    
    /**
     * 获取通用编码
     *
     * @return
     */
    public static String getSn() {
        String time = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        if (date == null || !date.equals(time)) {
            date = time;
            sequence = 0L;
        }
        
        synchronized (CodeUtil.class) {
            ++sequence;
        }
        
        long sequenceNo = Long.parseLong(date);
        sequenceNo += sequence;
        return new StringBuilder().append(sequenceNo).toString();
        
    }
    
    /**
     * 生成随机验证码
     *
     * @param num 位数
     * @return 返回验证码
     */
    private static String securityCode(int num) {
        List<Integer> str = new ArrayList<>();
        while (str.size() < num) {
            str.add((int) (Math.random() * 10));
        }
        StringBuilder stringBuilder = new StringBuilder();
        str.forEach(st -> stringBuilder.append(st.toString()));
        String s = stringBuilder.toString();
        if (s.startsWith("0")) {
            return securityCode(num);
        }
        return s;
    }
    
    /**
     * 生成随机验证码
     *
     * @param num    位数
     * @param repeat 是否可重复(设置不能重复最多10位0~9)
     * @return 返回验证码
     */
    public static String securityCode(int num, boolean repeat) {
        if (repeat) {
            return securityCode(num);
        }
        Set<Integer> str = new HashSet<>();
        while (str.size() < num) {
            str.add((int) (Math.random() * 10));
        }
        StringBuilder stringBuilder = new StringBuilder();
        str.forEach(st -> stringBuilder.append(st.toString()));
        String toString = stringBuilder.toString();
        if (toString.startsWith("0")) {
            System.out.println("================");
            return securityCode(num);
        }
        return toString;
    }
    
    /**
     * 生成随机验证码
     *
     * @param num 位数
     * @return 返回验证码
     */
    private static String securityCodeStr(int num) {
        List<Integer> str = new ArrayList<>();
        while (str.size() < num) {
            str.add(new Random().nextInt(62) % 62);
        }
        StringBuilder stringBuilder = new StringBuilder();
        str.forEach(st -> stringBuilder.append(chars[st]));
        String toString = stringBuilder.toString();
        return toString;
    }
    
    /**
     * 生成随机验证码
     *
     * @param num    位数
     * @param repeat repeat 是否可重复(设置不能重复最多10位0~9) true 可重复 false 不能重复
     * @param hasStr 是否加入字母字符
     * @return 返回验证码
     */
    public static String securityCode(int num, boolean repeat, boolean hasStr) {
        if (hasStr) {
            if (repeat) {
                return securityCodeStr(num);
            } else {
                Set<Integer> str = new HashSet<>();
                while (str.size() < num) {
                    str.add(new Random().nextInt(62) % 62);
                }
                StringBuilder stringBuilder = new StringBuilder();
                str.forEach(st -> stringBuilder.append(chars[st]));
                return stringBuilder.toString();
            }
        } else {
            return securityCode(num, repeat);
        }
        
    }
    
    
    /**
     * 常用获取客户端信息的工具
     */
    private static final Logger logger = LoggerFactory.getLogger(CodeUtil.class);
    
    /**
     * 获取登录用户的IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
    
    /**
     * 通过IP获取地址(需要联网，调用淘宝的IP库)
     *
     * @param ip
     * @return
     */
    public static String getIpInfo(String ip) {
        if (ip.equals("127.0.0.1")) {
            ip = "127.0.0.1";
        }
        String info = "";
        try {
            URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
            HttpURLConnection htpcon = (HttpURLConnection) url.openConnection();
            htpcon.setRequestMethod("GET");
            htpcon.setDoOutput(true);
            htpcon.setDoInput(true);
            htpcon.setUseCaches(false);
            
            InputStream in = htpcon.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null) {
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            JSONObject obj = (JSONObject) JSON.parse(temp.toString());
            if (obj.getIntValue("code") == 0) {
                JSONObject data = obj.getJSONObject("data");
                info += data.getString("country") + " ";
                info += data.getString("region") + " ";
                info += data.getString("city") + " ";
                info += data.getString("isp");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }
    
    /**
     * @param
     * @Title:判断是否存在特殊字符串
     * @author:
     * @date:2017-12-05 10:14
     */
    private static boolean hasEmoji(String content) {
        Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
    
    /**
     * @param
     * @Title:替换字符串中的emoji字符
     * @author:
     * @date:2017-12-05 10:17
     */
    public static String replaceEmoji(String str) {
        if (!hasEmoji(str)) {
            return str;
        } else {
            str = str.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", " ");
            return str.trim();
        }
        
    }
}

    

