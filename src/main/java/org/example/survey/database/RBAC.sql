create table permission
(
    id              bigint auto_increment comment '权限ID'
        primary key,
    permission_code varchar(100)                       not null comment '权限编码',
    permission_name varchar(100)                       not null comment '权限名称',
    description     varchar(255)                       null comment '权限描述',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint permission_code
        unique (permission_code)
)
    comment '权限表';

create table role
(
    id          bigint auto_increment comment '角色ID'
        primary key,
    role_code   varchar(50)                        not null comment '角色编码',
    role_name   varchar(50)                        not null comment '角色名称',
    description varchar(255)                       null comment '角色描述',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint role_code
        unique (role_code)
)
    comment '角色表';

create table role_permission
(
    id            bigint auto_increment comment '主键ID'
        primary key,
    role_id       bigint                             not null comment '角色ID',
    permission_id bigint                             not null comment '权限ID',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint uk_role_permission
        unique (role_id, permission_id),
    constraint fk_role_permission_permission
        foreign key (permission_id) references permission (id)
            on delete cascade,
    constraint fk_role_permission_role
        foreign key (role_id) references role (id)
            on delete cascade
)
    comment '角色权限关联表';

create table user
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    user_name   varchar(50)                        not null comment '用户名',
    pass_word   varchar(100)                       not null comment '密码',
    status      tinyint  default 1                 not null comment '状态：0-禁用，1-正常',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '用户表';

create table user_role
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    user_id     bigint                             not null comment '用户ID',
    role_id     bigint                             not null comment '角色ID',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint uk_user_role
        unique (user_id, role_id),
    constraint fk_user_role_role
        foreign key (role_id) references role (id)
            on delete cascade,
    constraint fk_user_role_user
        foreign key (user_id) references user (id)
            on delete cascade
)
    comment '用户角色关联表';

CREATE TABLE survey (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '问卷ID',
                        title VARCHAR(100) NOT NULL COMMENT '问卷标题',
                        description VARCHAR(500) COMMENT '问卷描述',
                        status TINYINT NOT NULL DEFAULT 0 COMMENT '问卷状态：0草稿，1已发布，2已关闭',
                        creator_id BIGINT NOT NULL COMMENT '创建人用户ID',
                        create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除，1已删除',
                        INDEX idx_creator_id (creator_id),
                        INDEX idx_status (status)
) COMMENT='问卷表';

CREATE TABLE survey_question (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '题目ID',
                                 survey_id BIGINT NOT NULL COMMENT '所属问卷ID',
                                 question_title VARCHAR(255) NOT NULL COMMENT '题目内容',
                                 question_type TINYINT NOT NULL COMMENT '题目类型：1单选，2多选，3填空，4打分',
                                 required TINYINT NOT NULL DEFAULT 1 COMMENT '是否必填：0否，1是',
                                 sort INT NOT NULL DEFAULT 0 COMMENT '题目排序，数字越小越靠前',
                                 create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 INDEX idx_survey_id (survey_id)
) COMMENT='问卷题目表';

CREATE TABLE survey_option (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '选项ID',
                               question_id BIGINT NOT NULL COMMENT '所属题目ID',
                               option_content VARCHAR(255) NOT NULL COMMENT '选项内容',
                               sort INT NOT NULL DEFAULT 0 COMMENT '选项排序，数字越小越靠前',
                               create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               INDEX idx_question_id (question_id)
) COMMENT='题目选项表';