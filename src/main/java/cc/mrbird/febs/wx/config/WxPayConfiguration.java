package cc.mrbird.febs.wx.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>title:</p>
 * <p>description:</p>
 * <p>company:</p>
 * <p></p>
 *
 * @author <a href="jerry@kingyon.com">Peng yi</a>
 * @date 2018/7/9
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayConfiguration {

    @Autowired
    private WxPayProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public WxPayConfig payConfig() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(this.properties.getAppId());
        payConfig.setMchId(this.properties.getMchId());
        payConfig.setMchKey(this.properties.getMchKey());
        payConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
        payConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
        payConfig.setKeyPath(this.properties.getKeyPath());

        return payConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMaInMemoryConfig maConfig() {
        WxMaInMemoryConfig maConfig = new WxMaInMemoryConfig();
        maConfig.setAppid(this.properties.getAppId());
        maConfig.setSecret(this.properties.getSecret());
        return maConfig;
    }

    /**
     * 微信支付配置
     * @param payConfig
     * @return
     */
    @Bean
    public WxPayService wxPayService(WxPayConfig payConfig) {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

    /**
     * 微信小程序配置
     * @param maConfig
     * @return
     */
    @Bean
    public WxMaService wxMaService(WxMaInMemoryConfig maConfig) {
        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(maConfig);
        return wxMaService;
    }
}
