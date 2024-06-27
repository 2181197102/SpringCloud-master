package com.springboot.cloud.nailservice.nail.service.impl;

import com.springboot.cloud.nailservice.nail.entity.param.UploadImageParam;
import com.springboot.cloud.nailservice.nail.entity.vo.UploadImageVo;
import com.springboot.cloud.nailservice.nail.exception.FileSaveErrorException;
import com.springboot.cloud.nailservice.nail.exception.FileTypeNotAllowedException;
import com.springboot.cloud.nailservice.nail.service.IFileService;
import com.springboot.cloud.nailservice.nail.utils.MyStringsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class FileServiceImpl implements IFileService {
    private static final String DATA_PATH = "/app/server/datas/files";
    private static final String IMAGE_TYPE = ".jpg";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public UploadImageVo saveImage(UploadImageParam uploadImageParam) {
        // 从参数对象中获取上传的图片文件
        MultipartFile[] imageFiles = uploadImageParam.getFile();
        // 生成诊断编码
        String diagnosisCode = MyStringsUtil.getUUID();
        // 文件路径
        String imageFileFinal = "";
        // 设置序列号
        int i = 1;

        for (MultipartFile imageFile : imageFiles) {
            // 获取上传文件的原始文件名
            String filename = imageFile.getOriginalFilename();
            // 验证文件类型是否为图片类型
            verifyFileType(filename, IMAGE_TYPE);
            // 打印日志，记录文件名
            log.debug("file{}", filename);
            try {
                // 创建目标文件夹
                File dest = new File(DATA_PATH);
                dest.mkdirs();
                // 生成新的文件名
                String suffix = getFileSuffix(filename);
                // 使用当前时间的时间戳和用户名作为文件名的一部分，保证文件名的唯一性
                String newFileName = getUserName() + "_" + MyStringsUtil.getTimeSuffix() + "_" + i + suffix;
                i++;
                // 构建文件保存路径
                Path path = Paths.get(DATA_PATH, newFileName);
                // 将上传的文件内容保存到指定路径下
                // 如果文件是Base64编码，则解码
                if (isBase64Encoded(imageFile)) {
                    byte[] decodedBytes = Base64.getDecoder().decode(new String(imageFile.getBytes()));
                    Files.write(path, decodedBytes);
                } else {
                    Files.copy(imageFile.getInputStream(), path);
                }
                // 生成文件路径
                String filePath = DATA_PATH + "/" + newFileName;
                imageFileFinal += filePath + ",";
            } catch (Exception e) {
                // 如果保存文件时发生异常，记录错误日志，并抛出 FileSaveErrorException 异常
                log.error(e.toString());
                throw new FileSaveErrorException(e.getMessage());
            }
        }
        // 将诊断编码与图片路径存入 Redis 缓存，并设置过期时间为 3 小时
        redisTemplate.boundValueOps("diagnosisCode_imagePath_" + diagnosisCode).set(imageFileFinal, 3, TimeUnit.HOURS);
        // 构建上传结果对象
        UploadImageVo uploadImageVo = new UploadImageVo(diagnosisCode, imageFileFinal);
        // 返回上传结果对象
        return uploadImageVo;
    }

    /**
     * 检查文件内容是否是Base64编码的
     *
     * @param file MultipartFile对象
     * @return 如果是Base64编码返回true，否则返回false
     */
    private boolean isBase64Encoded(MultipartFile file) {
        try {
            String content = new String(file.getBytes());
            Base64.getDecoder().decode(content);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private void verifyFileType(String fileName, String type) {
        String fileSuffix = getFileSuffix(fileName).toLowerCase();
        if (!type.equals(fileSuffix)) {
            throw new FileTypeNotAllowedException(fileSuffix);
        }
    }

    private String getFileSuffix(String fileName) {
        int number = fileName.lastIndexOf(".");
        if (number <= 0) {
            return "";
        }
        return fileName.substring(number).toLowerCase();
    }

    private String getUserName() {
        // return UserContextHolder.getInstance().getUsername(); TODO
        return "test_user";
    }
}
