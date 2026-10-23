package io.github.lianjishu.controller.admin;

import io.github.lianjishu.constant.MessageConstant;
import io.github.lianjishu.exception.BaseException;
import io.github.lianjishu.result.Result;
import io.github.lianjishu.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api("通用接口")
public class CommonController {

    private final AliOssUtil aliOssUtil; // 阿里云OSS工具类，用于文件上传

    @Autowired
    public CommonController(AliOssUtil aliOssUtil) {
        this.aliOssUtil = aliOssUtil;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件访问地址
     */
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("上传文件: {}", file.getOriginalFilename());

        // getFilename 去掉 IE 等浏览器可能带的路径前缀
        String orgFilename = StringUtils.getFilename(file.getOriginalFilename());

        if (!StringUtils.hasText(orgFilename)) {
            throw new BaseException(MessageConstant.UPLOAD_FAILED);
        }

        // 无后缀时 suffix 为 null，直接用 UUID 作为 objectName
        String suffix = StringUtils.getFilenameExtension(orgFilename);
        String objectName = UUID.randomUUID() + (suffix != null ? "." + suffix : "");

        String url = aliOssUtil.upload(file.getBytes(), objectName);
        return Result.success(url);
    }
}
