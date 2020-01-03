package cc.mrbird.febs.code.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>title:</p>
 * <p>description:</p>
 * <p>company:</p>
 * <p></p>
 *
 * @author <a href="jerry@kingyon.com">Peng yi</a>
 * @date 2018/7/9
 */
@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }

    public int getPort() {
        return this.serverPort;
    }

    public String getIp() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address.getHostAddress();
    }

    public String getIpAndPort() {
        return "http://"+getIp()+":"+getPort();
    }
}

