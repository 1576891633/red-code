package cc.mrbird.febs.wx.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtil {





    public static String send(String url, Map<String,String> map,String encoding) throws ParseException, IOException {
        return send(url, map, encoding, null);
    }

	/** 
     * 模拟请求 
     *  
     * @param url       资源地址 
     * @param map   参数列表 
     * @param encoding  编码 
     * @return 
     * @throws ParseException
     * @throws IOException 
     */  
    public static String send(String url, Map<String,String> map,String encoding,Map<String,String> headers) throws ParseException, IOException{
        String body = "";
  
        //创建httpclient对象  
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象  
        HttpPost httpPost = new HttpPost(url);

        //设置连接超时
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(30000).build();

        httpPost.setConfig(requestConfig);

        //装填参数  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){  
            for (Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }  
        }  
        //设置参数到请求对象中  
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //设置header信息  
        //指定报文头【Content-type】、【User-Agent】
        if (headers == null || headers.isEmpty()){
            httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        }else {
            headers.keySet().stream().forEach(he ->{
                Header bh =new BasicHeader(he,headers.get(he));
                httpPost.addHeader(bh);
            });
        }

        //执行请求操作，并拿到结果（同步阻塞）  
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体  
        HttpEntity entity = response.getEntity();
        if (entity != null) {  
            //按指定编码转换结果实体为String类型  
            body = EntityUtils.toString(entity, encoding);
        }  
        EntityUtils.consume(entity);
        //释放链接  
        response.close();  
        return body;  
    }

    /**
     * 模拟请求
     *
     * @param url       资源地址
     * @param map   参数列表
     * @param encoding  编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String get(String url, Map<String,String> map,String encoding) throws ParseException, IOException{
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();


        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        StringBuffer urlBuffer = new StringBuffer(url);
        if(map!=null){
            for (Entry<String, String> entry : map.entrySet()) {
                if (url.endsWith("?")){
                    urlBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }else if (url.contains("?") && url.endsWith("&")){
                    urlBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }else {
                    urlBuffer.append("?").append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //创建get方式请求对象
        HttpGet httpGet = new HttpGet(url);

        //设置连接超时
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(30000).build();

        httpGet.setConfig(requestConfig);
        //设置参数到请求对象中

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpGet);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }
}
