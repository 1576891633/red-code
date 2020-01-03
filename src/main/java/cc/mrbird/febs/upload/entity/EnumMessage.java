package cc.mrbird.febs.upload.entity;

/**
 * <p>title:</p>
 * <p>description:</p>
 * <p>company:</p>
 * <p></p>
 * 通用枚举接口，字典扫描接口
 *
 * @author <a href="jerry@kingyon.com">Peng yi</a>
 * @date 2018/12/3
 */
public interface EnumMessage {
    
    /**
     * 获取枚举自定义消息
     *
     * @return
     */
    String getMessage();
    
    /**
     * 获取枚举值
     *
     * @return
     */
    Integer getValue();
    
    /**
     * 枚举名称
     *
     * @return
     */
    String name();

}
