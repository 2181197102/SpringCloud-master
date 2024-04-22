package com.springboot.cloud.nailservice.nail.service.impl;

import com.springboot.cloud.common.core.util.UserContextHolder;
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
import java.io.IOException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class FileServiceImpl implements IFileService {
    private static final String DATA_PATH = "/app/server/datas/files";

    private static final String IMAGE_TYPE = ".jpg";

    @Resource
    private RedisTemplate<String, String> redisTemplate;    // RedisTemplate是Spring Data Redis提供的一个模板类，用于简化Redis的操作。
                                                            // RedisTemplate提供了一系列的操作方法，包括对字符串、散列、列表、集合、有序集合等数据结构的操作。
                                                            // RedisTemplate是Spring Data Redis的核心类，封装了对Redis的操作，提供了操作Redis的各种方法。
                                                            // RedisTemplate是一个泛型类，可以指定Key和Value的类型，Key的类型是Redis的Key的类型，Value的类型是Redis的Value的类型。
                                                            // RedisTemplate提供了对Redis的操作方法，包括对字符串、散列、列表、集合、有序集合等数据结构的操作。

    /**
     * 保存上传的图片文件，并返回上传结果信息。
     *
     * @param uploadImageParam 包含上传的图片文件的参数对象
     * @return 上传结果的封装对象 UploadImageVo
     * @throws FileSaveErrorException 如果保存文件时发生异常，则抛出 FileSaveErrorException 异常
     */
    @Override
    public UploadImageVo saveImage(UploadImageParam uploadImageParam) {
        // 从参数对象中获取上传的图片文件
        MultipartFile[] imageFiles = uploadImageParam.getFile();
        // 生成诊断编码
        String diagnosisCode = MyStringsUtil.getUUID();
        //文件路径
        String imageFile_final = "";

        for(MultipartFile imageFile : imageFiles){
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
                String newFileName = getNewFileName(filename,getUserName());
                // 构建文件保存路径
                Path path = Paths.get(DATA_PATH, newFileName);
                // 将上传的文件内容保存到指定路径下
                Files.copy(imageFile.getInputStream(), path);
                // 生成文件路径
                String filePath = DATA_PATH + "/" + newFileName;
                imageFile_final += filePath + ",";
            } catch (IOException | NullPointerException e) {
                // 如果保存文件时发生异常，记录错误日志，并抛出 FileSaveErrorException 异常
                log.error(e.toString());
                throw new FileSaveErrorException(e.getMessage());
            }
        }
        // 将诊断编码与图片路径存入 Redis 缓存，并设置过期时间为 3 小时
        redisTemplate.boundValueOps("diagnosisCode_imagePath_" + diagnosisCode).set(imageFile_final, 3, TimeUnit.HOURS);
        // 构建上传结果对象
        UploadImageVo uploadImageVo = new UploadImageVo(diagnosisCode, imageFile_final);
        // 返回上传结果对象
        return uploadImageVo;
    }


    /**
     * 根据文件名验证文件类型是否符合要求。
     *
     * @param fileName 文件名
     * @param type     预期的文件类型
     * @throws FileTypeNotAllowedException 如果文件类型不符合预期，则抛出 FileTypeNotAllowedException 异常
     */
    private void verifyFileType(String fileName, String type) {
        // 获取文件的后缀名,并转换为小写形式
        String fileSuffix = getFileSuffix(fileName).toLowerCase();
        // 如果文件后缀名与预期的文件类型不相符，则抛出异常
        if (!type.equals(fileSuffix)) {
            throw new FileTypeNotAllowedException(fileSuffix);
        }
    }

    /**
     * 从文件名中提取文件后缀名。
     *
     * @param fileName 文件名
     * @return 文件的后缀名，如果文件名中没有后缀名则返回空字符串
     */
    private String getFileSuffix(String fileName) {
        // 获取文件名中最后一个“.”的索引位置
        int number = fileName.lastIndexOf(".");
        // 如果索引小于等于 0，则说明文件名中没有后缀名，直接返回空字符串
        if (number <= 0) {
            return "";
        }
        // 截取文件名中的后缀部分，并转换为小写形式后返回
        return fileName.substring(number).toLowerCase();
    }

    /**
     * 生成新的文件名，添加时间戳作为文件名的一部分。
     *
     * @param filename 原始文件名
     * @return 添加时间戳后的新文件名
     */
    private String getNewFileName(String filename, String username) {
        // 获取文件的后缀名
        String suffix = getFileSuffix(filename);
        // 使用当前时间的时间戳和用户名作为文件名的一部分，保证文件名的唯一性
        return username + "_" + MyStringsUtil.getTimeSuffix() + suffix;
    }

    private String getUserName() {
        return UserContextHolder.getInstance().getUsername();
    }


}
