package io.github.lianjishu.controller.admin;

import io.github.lianjishu.result.Result;
import io.github.lianjishu.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        String orgFilename = file.getOriginalFilename();
        assert orgFilename != null;
        String subString = orgFilename.substring(orgFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String url = aliOssUtil.upload(file.getBytes(), uuid + subString);

        return Result.success(url);
    }
}
