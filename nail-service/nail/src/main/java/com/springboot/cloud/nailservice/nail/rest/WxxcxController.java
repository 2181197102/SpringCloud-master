    package com.springboot.cloud.nailservice.nail.rest;

    import com.springboot.cloud.common.core.entity.vo.Result;
    import com.springboot.cloud.nailservice.nail.entity.form.NailDiagQueryForm;
    import com.springboot.cloud.nailservice.nail.entity.param.NailDiagQueryParam;
    import com.springboot.cloud.nailservice.nail.entity.param.RegisterParam;
    import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
    import com.springboot.cloud.nailservice.nail.service.IWxxcxService;
    import com.baomidou.mybatisplus.core.metadata.IPage;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
    import io.swagger.annotations.Api;
    import io.swagger.annotations.ApiOperation;
    import lombok.extern.slf4j.Slf4j;

    import javax.annotation.Resource;
    import javax.validation.Valid;

    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.Arrays;
    import java.util.List;
    import java.util.stream.Collectors;

    @RestController
    @RequestMapping("/wxxcx")
    @Api("wxxcx")
    @Slf4j
    public class WxxcxController {

        private static final String DATA_PATH = "/app/server/datas/files"; // 硬编码的文件存储路径

        @Resource
        private IWxxcxService wxxcxService;

        @ApiOperation(value = "诊断模糊查询", notes = "根据查询条件<诊断编码，诊断者姓名，患者姓名，诊断结果准确率>查询诊断，固定openid，确保用户查到的是当前用户所拥有的诊断信息")
        @PostMapping("/fuzzy-query")
        public Result<?> fuzzyQuery(@RequestBody NailDiagQueryForm nailDiagQueryForm) {
            Page<NailDiag> page = new Page<>(nailDiagQueryForm.getCurrent(), nailDiagQueryForm.getSize());

            IPage<NailDiag> resultPage = wxxcxService.fuzzyQuery(page, nailDiagQueryForm.toParam(NailDiagQueryParam.class));

            // 处理 imageFile 字段，将其转换为列表
            List<NailDiag> records = resultPage.getRecords().stream().map(record -> {
                String imageFile = record.getImageFile();
                List<String> imageFiles = Arrays.asList(imageFile.split(",")).stream().filter(path -> !path.isEmpty()).collect(Collectors.toList());
                record.setImageFiles(imageFiles); // 设置解析后的图片路径列表
                return record;
            }).collect(Collectors.toList());

            return Result.successWithPage(records, resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
        }

        @ApiOperation(value = "用户注册", notes = "根据提供的注册信息注册一个新用户")
        @PostMapping("/register")
        public Result register(@Valid @RequestBody RegisterParam registerParam) {
            log.debug("Registering user with information: {}", registerParam);

            boolean isRegistered = wxxcxService.register(registerParam);

            if (isRegistered) {
                return Result.success("User registered successfully");
            } else {
                return Result.fail("User registration failed");
            }
        }

        @ApiOperation(value = "获取图片", notes = "根据图片路径获取图片文件")
        @GetMapping("/image")
        public ResponseEntity<org.springframework.core.io.Resource> getImage(@RequestParam String path) {
            try {
                // 使用硬编码的路径
                Path filePath = Paths.get(DATA_PATH).resolve(path).normalize();
                org.springframework.core.io.Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());

                if (resource.exists()) {
                    // 确保文件内容使用UTF-8编码读取
                    byte[] content = Files.readAllBytes(filePath);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                            .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                            .body(new org.springframework.core.io.ByteArrayResource(content));
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
