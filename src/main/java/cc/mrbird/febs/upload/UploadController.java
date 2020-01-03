package cc.mrbird.febs.upload;

import cc.mrbird.febs.code.config.IpConfiguration;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.upload.entity.R;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description：
 * @Author ：lizf
 * @Date ：2019-9-20
 */
@Controller
@Slf4j
public class UploadController {

    @Autowired
    IpConfiguration ip;

    @PostMapping(value = "/upload")
    @ResponseBody
    public FebsResponse uploadFile(MultipartFile file) {
        //判断文件是否为空
        if (file.isEmpty()) {
            return new FebsResponse().fail();
        }
        // 获取文件名
        String fileName = RandomUtil.randomString(16) + file.getOriginalFilename();


        String dirPath = getFileDir();

        File dir = new File(dirPath);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            //上传文件
            file.transferTo(new File(dirPath + File.separator + fileName));
            return new FebsResponse().data(fileName).success();
        } catch (IOException e) {
            e.printStackTrace();
            return new FebsResponse().fail();
        }
    }

    @PostMapping(value = "/upload/editor")
    @ResponseBody
    public R uploadEditorFile(MultipartFile file) {
        //判断文件是否为空
        if (file.isEmpty()) {
            return new R().error();
        }
        // 获取文件名
        String fileName = RandomUtil.randomString(16) + file.getOriginalFilename();


        String dirPath = getFileDir();

        File dir = new File(dirPath);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            //上传文件
            file.transferTo(new File(dirPath + File.separator + fileName));
            //本地调试用这行代码
//            String src = ip.getIpAndPort()+"/download/image/" + fileName;
//            log.info("------------"+"http://"+ GetIpUtil.getOpenIp()+":"+ip.getPort()+"/download/image/" + fileName);
//            String src = "http://"+ GetIpUtil.getOpenIp()+":"+ip.getPort()+"/download/image/" + fileName;
            String src = "http://47.112.38.218:8086/download/image/" + fileName;
            Map map = new HashMap();
            map.put("src",src);
            return new R().data(map).code(0);
        } catch (IOException e) {
            e.printStackTrace();
            return new R().error();
        }
    }

    @GetMapping(value = "/download/image/{fileName}")
    @ResponseBody
    public void uploadFile(@PathVariable String fileName, HttpServletResponse response) {
        String dir = getFileDir();

        try {
            FileUtil.writeToStream(dir + File.separator + fileName, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileDir() {
        try {
            return ResourceUtils.getURL("").getPath() + "upload";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return StrUtil.EMPTY;
        }
    }
}
