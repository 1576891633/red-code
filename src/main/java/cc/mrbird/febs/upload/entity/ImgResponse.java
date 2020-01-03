package cc.mrbird.febs.upload.entity;

import lombok.Data;

/**
 * @author <a href="pigeon@kingyon.com">allen</a>
 * @datetime 2019/10/30
 */
@Data
public class ImgResponse {

    private String code = "0";

    private String msg;

    private Image data;

    @Data
    class Image {
        private String src;
        private String title;
    }

}
