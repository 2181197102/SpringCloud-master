package com.springboot.cloud.nailservice.nail.entity.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterParam {
    private String openId;

    private String nickName;

    private String userType;
}
