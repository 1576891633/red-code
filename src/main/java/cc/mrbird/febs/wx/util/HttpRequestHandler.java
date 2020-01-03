package cc.mrbird.febs.wx.util;

import cc.mrbird.febs.wx.entity.TransfersDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

@Slf4j
public class HttpRequestHandler
{

    // 请求器的配置
    private static RequestConfig requestConfig;

    // HTTP请求器
    private static CloseableHttpClient httpClient;

    /**
     * 加载证书
     * 
     * @param path
     */
    private static void initCert(String path, TransfersDto transfer) {

        InputStream instream = null;
        SSLContext sslcontext = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            //读取 证书
            instream = new FileInputStream(new File(path));
            if (instream == null) {
                return;
            }
            // 这里写密码..默认是你的MCHID
            keyStore.load(instream, transfer.getMchid().toCharArray());
            sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, transfer.getMchid().toCharArray()).build();
        } catch (Exception e) {
            log.error("官方微信--证书加载失败!{}", e);
            return;
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                log.error("官方微信--关闭流失败!{}", e);
            }
        }
        @SuppressWarnings("deprecation")
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        // 根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();

    }

    /**
     * 通过Https往API post xml数据
     * 
     * @param url
     *            API地址
     * @param xmlObj
     *            要提交的XML数据对象
     * @param path
     *            当前目录，用于加载证书
     * @return
     */
    public static String httpsRequest(String url, String xmlObj, TransfersDto model, String path) {
        // 加载证书
        initCert(path, model);
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        // 设置请求器的配置
        httpPost.setConfig(requestConfig);
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            log.info("官方微信--请求返回结果：{}", result);
        }
        catch (Exception e) {
            log.error("官方微信--请求失败！{}", e);
            return null;
        }
        finally
        {
            httpPost.abort();
        }
        return result;
    }
}
