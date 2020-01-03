package cc.mrbird.febs.code.util;

/**
 * 执行linux命令获取公网IP
 * @author lizf
 * @datetime 2019/11/8
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class GetIpUtil {

    /**
     * 获取公网Ip
     * @return
     */
    public static String getOpenIp() {
        StringBuffer sb = new StringBuffer();
        try {
            Process ps = Runtime.getRuntime().exec("curl ip.cip.cc");

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
