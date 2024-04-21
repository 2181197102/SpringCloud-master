SET NAMES utf8;

DROP DATABASE IF EXISTS sc_nail;
CREATE DATABASE sc_nail DEFAULT CHARSET utf8mb4;
USE sc_nail;

-- 诊断表
DROP TABLE IF EXISTS tb_nail_diag;
CREATE TABLE tb_nail_diag
(
    id                  VARCHAR(20) PRIMARY KEY COMMENT 'id',
    diagnosis_code      VARCHAR(40)  NOT NULL COMMENT '诊断编码',
    docter_name         VARCHAR(20)  NOT NULL COMMENT '诊断者姓名',
    patient_name        VARCHAR(20)  NOT NULL COMMENT '患者姓名',
    image_file          VARCHAR(100) NOT NULL COMMENT '用户上传图片,存储图片url',
    diag_result         VARCHAR(500) NOT NULL COMMENT '诊断结果',
    result_accurancy    INTEGER      NOT NULL COMMENT '诊断结果准确率,1：准确,0：不准确,-1：医生未评测',
    feedback            VARCHAR(500) NOT NULL COMMENT '结果反馈',
    deleted             VARCHAR(1)   NOT NULL DEFAULT 'N' COMMENT '是否已删除Y：已删除，N：未删除',
    created_time        DATETIME     NOT NULL DEFAULT now() COMMENT '创建时间',
    updated_time        DATETIME     NOT NULL DEFAULT now() COMMENT '更新时间',
    created_by          VARCHAR(100) NOT NULL COMMENT '创建人',
    updated_by          VARCHAR(100) NOT NULL COMMENT '更新人',
    INDEX             idx_diagnosis_code (diagnosis_code),
    INDEX             idx_patient_name (patient_name),
    INDEX             idx_result_accurancy (result_accurancy),
    INDEX             idx_deleted (deleted)
) COMMENT '诊断表';

-- DML准备初始化数据

-- 诊断
INSERT INTO tb_nail_diag (id, diagnosis_code, user_id, patient_name, image_file, diag_result, result_accurancy, feedback, created_time, updated_time, created_by, updated_by)
VALUES
    ('1', 'D001', 'user123', 'John Doe', 'https://example.com/image1.jpg', 'Nail infection', 1, 'The diagnosis is accurate.', now(), now(), 'admin', 'admin'),
    ('2', 'D002', 'user456', 'Jane Smith', 'https://example.com/image2.jpg', 'Nail psoriasis', 1, 'The diagnosis is accurate.', now(), now(), 'admin', 'admin'),
    ('3', 'D003', 'user789', 'Alice Johnson', 'https://example.com/image3.jpg,https://example.com/image4.jpg', 'Nail trauma', 0, 'The diagnosis needs further examination.', now(), now(), 'admin', 'admin');





SET NAMES utf8;

DROP
DATABASE IF EXISTS sc_nsclc;
CREATE
DATABASE sc_nsclc DEFAULT CHARSET utf8mb4;
USE
sc_nsclc;

-- 诊断表
DROP TABLE IF EXISTS tb_nsclc_diag;
CREATE TABLE tb_nsclc_diag
(
    id                VARCHAR(20) PRIMARY KEY COMMENT 'id',
    diagnosis_code    VARCHAR(40)  NOT NULL COMMENT '诊断编码',
    patient_name      VARCHAR(20)  NOT NULL COMMENT '患者姓名',
    doctor_name       VARCHAR(20)  NOT NULL COMMENT '诊断者姓名',
    image_file_loc    VARCHAR(100) NOT NULL COMMENT '用户上传nrrd原数据地址',
    image_mask_loc    VARCHAR(100) NOT NULL COMMENT '用户上传nrrd掩膜地址',
    features_xlsx_loc VARCHAR(100) NOT NULL COMMENT '特征地址',
    sys_diag_result   INT          NOT NULL COMMENT '自动诊断结果，1表示有疗效，0表示无疗效',
    doc_diag_result   INT          NOT NULL COMMENT '医生诊断结果，1表示有疗效，0表示无疗效',
    diag_details      VARCHAR(400) NOT NULL COMMENT '诊断详情',
    deleted           VARCHAR(1)   NOT NULL DEFAULT 'N' COMMENT '是否已删除Y：已删除，N：未删除',
    created_time      DATETIME     NOT NULL DEFAULT NOW() COMMENT '创建时间',
    updated_time      DATETIME     NOT NULL DEFAULT NOW() COMMENT '更新时间',
    created_by        VARCHAR(100) NOT NULL COMMENT '创建人',
    updated_by        VARCHAR(100) NOT NULL COMMENT '更新人',
    INDEX             idx_diagnosis_code (diagnosis_code),
    INDEX             idx_patient_name (patient_name),
    INDEX             idx_doctor_name (doctor_name),
    INDEX             idx_sys_diag_result (sys_diag_result),
    INDEX             idx_doc_diag_result (doc_diag_result),
    INDEX             idx_deleted (deleted)
) COMMENT '诊断表';

-- DML准备初始化数据

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
VALUES ('601', 'app_nsclc:predict', 'app_nsclc', 'predict', '/nsclc/predict', 'POST', '預測療效', now(), now(),
        'system', 'system'),
       ('602', 'app_nsclc:upload_image', 'app_nsclc', 'uploda_file_image', '/nsclc/file/upload/image', 'POST',
        '上傳影像文件', now(), now(), 'system', 'system'),
       ('603', 'app_nsclc:upload_mask', 'app_nsclc', 'uploda_file_mask', '/nsclc/file/upload/mask', 'POST',
        '上傳掩膜文件', now(), now(), 'system', 'system'),
       ('604', 'app_nsclc:save_diag', 'app_nsclc', 'save_diag', '/nsclc/save/diag', 'POST', '保存診斷信息', now(),
        now(), 'system', 'system'),
       ('605', 'app_nsclc:del', 'app_nsclc', 'delete_diag', '/nsclc/{diagnosisCode}', 'DELETE', '刪除診斷數據', now(),
        now(), 'system', 'system'),
       ('606', 'app_nsclc:update', 'app_nsclc', 'update_diag', '/nsclc/{diagnosisCode}', 'PUT', '更新診斷數據', now(),
        now(), 'system', 'system'),
       ('607', 'app_nsclc:get', 'app_nsclc', 'get_bycode', '/nsclc/{diagnosisCode}', 'GET', '通過code獲取診斷數據',
        now(), now(), 'system', 'system'),
       ('608', 'app_nsclc:get_all', 'app_nsclc', 'getall', '/nsclc/all', 'GET', '獲取全部診斷數據', now(), now(),
        'system', 'system'),
       ('609', 'app_nsclc:query', 'app_nsclc', 'query', '/nsclc/conditions', 'POST', '查詢診斷數據', now(), now(),
        'system', 'system');

INSERT INTO `sc_admin`.`applications` (`id`, `app_name`, `description`, `app_icon`, `created_time`, `updated_time`,
                                       `created_by`, `updated_by`)
VALUES ('1777258276589670402', 'nsclc', '非小细胞肺癌疗效评估', '/nsclc/upload', now(),
        now(), 'admin', 'admin');


INSERT INTO `sc_admin`.`user_application_permission` (`id`, `user_id`, `application_id`, `created_time`, `updated_time`,
                                                      `created_by`, `updated_by`)
VALUES ('1777258319740669953', '101', '1777258276589670402', now(), now(), 'admin',
        'admin');
