package com.springboot.cloud.nsclcservice.nsclc.service.impl;

import com.springboot.cloud.nsclcservice.nsclc.entity.param.UploadImageParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.UploadMaskParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.UploadModelParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.UploadImageVo;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.UploadMaskVo;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.UploadModelVo;
import com.springboot.cloud.nsclcservice.nsclc.exception.FileSaveErrorException;
import com.springboot.cloud.nsclcservice.nsclc.exception.FileTypeNotAllowedException;
import com.springboot.cloud.nsclcservice.nsclc.service.IFileService;
import com.springboot.cloud.nsclcservice.nsclc.utils.MyStringsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月08日 7:57 PM
 **/
@Service
@Slf4j
public class FileServiceImpl implements IFileService {

    private static final String DATA_PATH = "/app/server/datas/files";

    private static final String IMAGE_MASK_TYPE = ".nrrd";

    private static final String MODEL_TYPE = ".pkl";

    private static final String MODEL_PATH = "/app/server/datas/files";
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public UploadImageVo saveImage(UploadImageParam uploadImageParam) {
        MultipartFile imageFile = uploadImageParam.getFile();
        String filename = imageFile.getOriginalFilename();
        verifyFileType(filename, IMAGE_MASK_TYPE);

        log.debug("file{}", filename);
        try {
            File dest = new File(DATA_PATH);
            dest.mkdirs();
            String newFileName = getNewFileName(filename);
            Path path = Paths.get(DATA_PATH, newFileName);
            Files.copy(uploadImageParam.getFile().getInputStream(), path);
            String diagnosisCode = MyStringsUtil.getUUID();
            UploadImageVo uploadImageVo = new UploadImageVo(diagnosisCode, DATA_PATH + "/" + newFileName);
            redisTemplate.boundValueOps("diagnosisCode_imagePath_" + diagnosisCode).set(DATA_PATH + "/" + newFileName, 3, TimeUnit.HOURS);
            return uploadImageVo;
        } catch (Exception e) {
            log.error(e.toString());
            throw new FileSaveErrorException(e.getMessage());
        }
    }

    @Override
    public UploadMaskVo saveMask(UploadMaskParam uploadMaskParam) {
        MultipartFile imageFile = uploadMaskParam.getFile();
        String filename = imageFile.getOriginalFilename();
        verifyFileType(filename, IMAGE_MASK_TYPE);

        log.debug("file{}", filename);
        try {
            File dest = new File(DATA_PATH);
            dest.mkdirs();
            String newFileName = getNewFileName(filename);
            Path path = Paths.get(DATA_PATH, newFileName);
            Files.copy(uploadMaskParam.getFile().getInputStream(), path);
            UploadMaskVo uploadMaskVo = new UploadMaskVo(uploadMaskParam.getDiagnosisCode(), DATA_PATH + "/" + newFileName);
            return uploadMaskVo;
        } catch (Exception e) {
            log.error(e.toString());
            throw new FileSaveErrorException(e.getMessage());
        }
    }

    @Override
    public UploadModelVo saveModel(UploadModelParam uploadModelParam) {
        MultipartFile modelFile = uploadModelParam.getFile();
        String filename = modelFile.getOriginalFilename();
        verifyFileType(filename, MODEL_TYPE);

        log.debug("file{}", filename);
        try {
            File dest = new File(MODEL_PATH);
            dest.mkdirs();
            String newFileName = getNewFileName(filename);
            Path path = Paths.get(MODEL_PATH, newFileName);
            Files.copy(uploadModelParam.getFile().getInputStream(), path);
            String modelCode = MyStringsUtil.getUUID();
            UploadModelVo uploadModelVo = new UploadModelVo(modelCode, DATA_PATH + "/" + newFileName, filename);
            return uploadModelVo;
        } catch (Exception e) {
            log.error(e.toString());
            throw new FileSaveErrorException(e.getMessage());
        }

    }

    private void verifyFileType(String fileName, String type) {
        String fileSuffix = getFileSuffix(fileName);
        if (!type.equals(fileSuffix)) {
            throw new FileTypeNotAllowedException(fileSuffix);
        }
    }

    /**
     * 获取文件后缀名
     */
    private String getFileSuffix(String fileName) {
        int number = fileName.lastIndexOf(".");
        if (number <= 0) {
            return "";
        }
        return fileName.substring(number).toLowerCase();
    }

    private String getNewFileName(String filename) {
        return filename.replace(getFileSuffix(filename), "-" + MyStringsUtil.getTimeSuffix() + getFileSuffix(filename));
    }
}
