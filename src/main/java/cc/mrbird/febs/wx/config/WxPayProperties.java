package cc.mrbird.febs.wx.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>title:</p>
 * <p>description:</p>
 * <p>company:</p>
 * <p></p>
 *
 * @author <a href="jerry@kingyon.com">Peng yi</a>
 * @date 2018/7/9
 */
@ConfigurationProperties(prefix = "wechat.pay")
public class WxPayProperties {

    /**
     * 设置微信公众号的appid
     */
    private String appId = "wx56cd4ec78b39aafa";

    /**
     * app的secret
     */
    private String secret = "dcd26c47bbf56b1f65edab99142d98c9";

    /**
     * 微信支付商户号
     */
    private String mchId = "1514128241";

    /**
     * 微信支付商户密钥
     */
    private String mchKey = "dfgkjhrdjghrjmsgbjsfhfbsjfghbnjk";

    /**
     * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
     */
    private String subAppId;

    /**
     * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
     */
    private String subMchId;

    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath;

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getKeyPath() {
        return this.keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) { this.secret = secret; }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
