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
    doctor_name         VARCHAR(20)  NOT NULL COMMENT '诊断者姓名',
    patient_name        VARCHAR(20)  NOT NULL COMMENT '患者姓名',
    image_file          VARCHAR(100) NOT NULL COMMENT '用户上传图片,存储图片url',
    diag_result         VARCHAR(500) NOT NULL COMMENT '诊断结果',
    result_accuracy    INTEGER      NOT NULL COMMENT '诊断结果准确率,1：准确,0：不准确,-1：医生未评测',
    feed_back            VARCHAR(500) NOT NULL COMMENT '结果反馈',
    deleted             VARCHAR(1)   NOT NULL DEFAULT 'N' COMMENT '是否已删除Y：已删除，N：未删除',
    created_time        DATETIME     NOT NULL DEFAULT now() COMMENT '创建时间',
    updated_time        DATETIME     NOT NULL DEFAULT now() COMMENT '更新时间',
    created_by          VARCHAR(100) NOT NULL COMMENT '创建人',
    updated_by          VARCHAR(100) NOT NULL COMMENT '更新人',
    INDEX             idx_diagnosis_code (diagnosis_code),
    INDEX             idx_patient_name (patient_name),
    INDEX             idx_result_accurancy (result_accuracy),
    INDEX             idx_deleted (deleted)
) COMMENT '诊断表';

-- DML准备初始化数据

-- 诊断
INSERT INTO tb_nail_diag (id, diagnosis_code, doctor_name, patient_name, image_file, diag_result, result_accuracy, feed_back, created_time, updated_time, created_by, updated_by)
VALUES
    ('1', 'D001', 'user123', 'John Doe', 'https://example.com/image1.jpg', 'Nail infection', 1, 'The diagnosis is accurate.', now(), now(), 'admin', 'admin'),
    ('2', 'D002', 'user456', 'Jane Smith', 'https://example.com/image2.jpg', 'Nail psoriasis', 1, 'The diagnosis is accurate.', now(), now(), 'admin', 'admin'),
    ('3', 'D003', 'user789', 'Alice Johnson', 'https://example.com/image3.jpg,https://example.com/image4.jpg', 'Nail trauma', 0, 'The diagnosis needs further examination.', now(), now(), 'admin', 'admin'),
    ('4', 'D004', 'test_doctor_2', 'test_patient_2', '/home/zeno/test_image/JGY1.JPG,/home/zeno/test_image/JZJB2.JPG', '', -1, '', now(), now(), 'admin', 'admin'),
    ('5', 'D005', '', 'test_patient_2', '/home/zeno/test_image/JGY1.JPG,/home/zeno/test_image/JZJB2.JPG,/home/zeno/test_image/YXBJ16.JPG', '', -1, '', now(), now(), 'admin', 'admin'),
    ('6', 'D006', 'tset', 'test', 'test', '', -1, '', now(), now(), 'admin', 'admin'),
    ('7', '0ad228ffa5b34798aaae05dcad0185c6', 'tset', 'test', 'test', '', -1, '', now(), now(), 'admin', 'admin');



    use
sc_gateway;
SELECT *
FROM `gateway_route`;

INSERT INTO `sc_gateway`.`gateway_route` (`id`, `route_id`, `uri`, `predicates`, `filters`, `orders`, `description`,
                                          `status`, `created_time`, `updated_time`, `created_by`, `updated_by`)
VALUES ('106', 'app-nail', 'lb://nail:8457', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/app/nail/**\"}}]',
        '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', 100, '甲病诊断', 'Y', now(), now(), 'system','system');

use
sc_admin;

INSERT INTO `sc_admin`.`resource` (`id`, `code`, `type`, `name`, `url`, `method`, `description`, `created_time`,
                                   `updated_time`, `created_by`, `updated_by`)
VALUES ('701', 'app_nail:predict', 'app_nail', '甲病诊断', '/nail/model/predict', 'POST', '甲病诊断', now(), now(), 'system', 'system'),
       ('702', 'app_nail:upload_image', 'app_nail', '上传照片', '/nail/file/upload/image', 'POST', '上传照片文件', now(), now(), 'system', 'system'),
       ('703', 'app_nail:save_diag', 'app_nail', '保存诊断信息', '/nail/save/diag', 'POST', '保存诊断信息', now(), now(), 'system', 'system'),
       ('704', 'app_nail:del', 'app_nail', '删除诊断信息', '/nail/{diagnosisCode}', 'DELETE', '删除诊断信息', now(), now(), 'system', 'system'),
       ('705', 'app_nail:update', 'app_nail', '更新诊断信息', '/nail/{diagnosisCode}', 'PUT', '更新诊断信息', now(), now(), 'system', 'system'),
       ('706', 'app_nail:get', 'app_nail', '通过编码获取诊断信息', '/nail/{diagnosisCode}', 'GET', '通过编码获取诊断信息', now(), now(), 'system', 'system'),
       ('707', 'app_nail:get_all', 'app_nail', '获取全部诊断信息', '/nail/all', 'GET', '获取全部诊断信息', now(), now(), 'system', 'system'),
       ('708', 'app_nail:query', 'app_nail', '查询诊断信息', '/nail/conditions', 'POST', '查询诊断信息', now(), now(), 'system', 'system');

INSERT INTO `sc_admin`.`applications` (`id`, `app_name`, `description`, `app_icon`, `created_time`, `updated_time`,
                                       `created_by`, `updated_by`)
VALUES ('1782679296742236161', 'nail', '甲病诊断', '/nail/upload', now(), now(), 'admin', 'admin');


INSERT INTO `sc_admin`.`user_application_permission` (`id`, `user_id`, `application_id`, `created_time`, `updated_time`,
                                                      `created_by`, `updated_by`)
VALUES ('1778361259708813314', '101', '1782679296742236161', now(), now(), 'admin','admin');
