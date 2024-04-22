SET NAMES utf8;

DROP
DATABASE IF EXISTS sc_nsclc;
CREATE
DATABASE sc_nsclc DEFAULT CHARSET utf8mb4;
USE
sc_nsclc;

-- 诊断表
CREATE TABLE `tb_nsclc_diag`
(
    `id`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
    `diagnosis_code`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '诊断编码',
    `patient_name`      varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '患者姓名',
    `doctor_name`       varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '诊断者姓名',
    `image_file_loc`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户上传nrrd原数据地址',
    `mask_file_loc`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户上传nrrd掩膜地址',
    `model_code`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '本次诊断所使用的模型编码',
    `features_xlsx_loc` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '特征地址',
    `sys_diag_result`   int                                                           NOT NULL COMMENT '自动诊断结果，1表示有疗效，0表示无疗效',
    `doc_diag_result`   int                                                           NOT NULL COMMENT '医生诊断结果，1表示有疗效，0表示无疗效',
    `diag_details`      varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '诊断详情',
    `deleted`           varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT 'N' COMMENT '是否已删除Y：已删除，N：未删除',
    `created_time`      datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`      datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
    `updated_by`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX               `idx_diagnosis_code`(`diagnosis_code`) USING BTREE,
    INDEX               `idx_patient_name`(`patient_name`) USING BTREE,
    INDEX               `idx_doctor_name`(`doctor_name`) USING BTREE,
    INDEX               `idx_sys_diag_result`(`sys_diag_result`) USING BTREE,
    INDEX               `idx_doc_diag_result`(`doc_diag_result`) USING BTREE,
    INDEX               `idx_deleted`(`deleted`) USING BTREE,
    INDEX               `idx_model_code`(`model_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '诊断表' ROW_FORMAT = Dynamic;


CREATE TABLE `tb_nsclc_model`
(
    `id`            varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
    `model_code`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'model编码',
    `file_name`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'model的文件名',
    `model_name`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'model的名称',
    `model_loc`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'model的位置',
    `model_details` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'model详情',
    `deleted`       varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT 'N' COMMENT '是否已删除Y：已删除，N：未删除',
    `created_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
    `updated_by`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX           `model_code`(`model_code`) USING BTREE,
    INDEX           `model_name`(`model_name`) USING BTREE,
    INDEX           `idx_deleted`(`deleted`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'model诊断表' ROW_FORMAT = Dynamic;

-- 诊断

use
sc_gateway;
SELECT *
FROM `gateway_route`;

INSERT INTO `sc_gateway`.`gateway_route` (`id`, `route_id`, `uri`, `predicates`, `filters`, `orders`, `description`,
                                          `status`, `created_time`, `updated_time`, `created_by`, `updated_by`)
VALUES ('105', 'app-nsclc', 'lb://nsclc:8456', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/app/nsclc/**\"}}]',
        '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', 100, '非小細胞肺癌診斷', 'Y', now(), now(), 'system',
        'system');

use
sc_admin;

INSERT INTO `sc_admin`.`resource` (`id`, `code`, `type`, `name`, `url`, `method`, `description`, `created_time`,
                                   `updated_time`, `created_by`, `updated_by`)
VALUES ('601', 'app_nsclc:predict', 'app_nsclc', '疗效评估', '/nsclc/predict', 'POST', '預測療效', now(), now(), 'system', 'system'),
       ('602', 'app_nsclc:upload_image', 'app_nsclc', '上传CT影像', '/nsclc/file/upload/image', 'POST', '上傳影像文件', now(), now(), 'system', 'system'),
       ('603', 'app_nsclc:upload_mask', 'app_nsclc', '上传掩膜影像', '/nsclc/file/upload/mask', 'POST', '上傳掩膜文件', now(), now(), 'system', 'system'),
       ('604', 'app_nsclc:save_diag', 'app_nsclc', '保存诊断信息', '/nsclc/save/diag', 'POST', '保存診斷信息', now(), now(), 'system', 'system'),
       ('605', 'app_nsclc:del', 'app_nsclc', '删除诊断信息', '/nsclc/{diagnosisCode}', 'DELETE', '刪除診斷數據', now(), now(), 'system', 'system'),
       ('606', 'app_nsclc:update', 'app_nsclc', '更新诊断信息', '/nsclc/{diagnosisCode}', 'PUT', '更新診斷數據', now(), now(), 'system', 'system'),
       ('607', 'app_nsclc:get', 'app_nsclc', '通过编码获取诊断信息', '/nsclc/{diagnosisCode}', 'GET', '通過code獲取診斷數據', now(), now(), 'system', 'system'),
       ('608', 'app_nsclc:get_all', 'app_nsclc', '获取全部诊断信息', '/nsclc/all', 'GET', '獲取全部診斷數據', now(), now(), 'system', 'system'),
       ('609', 'app_nsclc:query', 'app_nsclc', '查询诊断信息', '/nsclc/conditions', 'POST', '查詢診斷數據', now(), now(), 'system', 'system'),
       ('610', 'app_nsclc:upload_model', 'app_nsclc', '上传model', '/nsclc/model/file/upload/model', 'POST', '上傳model文件', now(), now(), 'system', 'system'),
       ('611', 'app_nsclc:save_model', 'app_nsclc', '保存model信息', '/nsclc/model/save', 'POST', '保存model信息', now(), now(), 'system', 'system'),
       ('612', 'app_nsclc:del_model', 'app_nsclc', '删除model信息', '/nsclc/model/{modelCode}', 'DELETE', '删除model信息', now(), now(), 'system', 'system'),
       ('613', 'app_nsclc:update_model', 'app_nsclc', '更新model信息', '/nsclc/model/{modelCode}', 'PUT', '更新model信息', now(), now(), 'system', 'system'),
       ('614', 'app_nsclc:get_model', 'app_nsclc', '通过编码获取model信息', '/nsclc/model/{modelCode}', 'GET', '通过编码获取model信息', now(), now(), 'system', 'system'),
       ('615', 'app_nsclc:get_all_model', 'app_nsclc', '获取全部model信息', '/nsclc/model/all', 'GET', '获取全部model信息', now(), now(), 'system', 'system'),
       ('616', 'app_nsclc:query_model', 'app_nsclc', '查询model信息', '/nsclc/model/conditions', 'POST', '查询model信息', now(), now(), 'system', 'system');


INSERT INTO `sc_admin`.`applications` (`id`, `app_name`, `description`, `app_icon`, `created_time`, `updated_time`,
                                       `created_by`, `updated_by`)
VALUES ('1777258276589670402', 'nsclc', '非小细胞肺癌疗效评估', '/nsclc/upload', now(),
        now(), 'admin', 'admin');


INSERT INTO `sc_admin`.`user_application_permission` (`id`, `user_id`, `application_id`, `created_time`, `updated_time`,
                                                      `created_by`, `updated_by`)
VALUES ('1777258319740669953', '101', '1777258276589670402', now(), now(), 'admin',
        'admin');
