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
    user_id             VARCHAR(20)  NOT NULL COMMENT '用户id',
    image_file          VARCHAR(100) NOT NULL COMMENT '用户上传图片,存储图片url',
    diag_result         VARCHAR(500) NOT NULL COMMENT '诊断结果',
    result_accurancy    INT          NOT NULL COMMENT '诊断结果准确率,1：准确,2：不准确',
    feedback            VARCHAR(500) NOT NULL COMMENT '结果反馈',
    deleted             VARCHAR(1)   NOT NULL DEFAULT 'N' COMMENT '是否已删除Y：已删除，N：未删除',
    created_time        DATETIME     NOT NULL DEFAULT now() COMMENT '创建时间',
    updated_time        DATETIME     NOT NULL DEFAULT now() COMMENT '更新时间',
    created_by          VARCHAR(100) NOT NULL COMMENT '创建人',
    updated_by          VARCHAR(100) NOT NULL COMMENT '更新人'
) COMMENT '诊断表';

-- DML准备初始化数据

-- 诊断
INSERT INTO diag (id, user_id, application_id, image_file, diag_result, result_accurancy, feedback, deleted, created_by, updated_by)
VALUES
    ('1', '103', '1', '{"urls":["http://example.com/image1.jpg"]}', 'Positive', 0, 'Accurate diagnosis', 'N', 'admin', 'admin'),
    ('2', '104', '2', '{"urls":["http://example.com/image2.jpg"]}', 'Negative', 1, 'Mostly accurate', 'N', 'admin', 'admin'),
    ('3', '105', '1', '{"urls":["http://example.com/image3.jpg"]}', 'Inconclusive', 2, 'Not accurate', 'N', 'admin', 'admin'),
    ('4', '106', '2', '{"urls":["http://example.com/image4.jpg", "http://example.com/image5.jpg"]}', 'Positive', 0, 'Accurate diagnosis', 'N', 'admin', 'admin');
