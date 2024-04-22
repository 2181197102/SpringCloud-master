package com.springboot.cloud.nsclcservice.nsclc.entity.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月08日 8:48 PM
 **/
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UploadModelParam {

    private MultipartFile file;

}
