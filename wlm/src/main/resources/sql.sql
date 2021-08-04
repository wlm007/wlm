# 创建数据库
CREATE DATABASE wlm CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

# 用户表
CREATE TABLE wlm.sys_user (
    id int NOT NULL COMMENT '主键',
    username varchar(30) comment '姓名',
    password varchar(50) comment '密码',
    age int comment '年龄',
    email varchar(50) comment '电子邮箱',
    dept_no varchar(20) comment '部门编号',
    role_no varchar(20) comment '角色编号',
    PRIMARY KEY (id)
);

# 角色表
create table wlm.sys_role (
    id int not null comment '主键',
    role_no varchar(20) not null comment '角色编号',
    role_name varchar(30) comment '角色名称',
    id_delete int(1) default 0 comment '是否删除0=否,1=是',
    primary key (id)
);

# 部门表
create table wlm.sys_dept (
    id int not null comment '主键',
    dept_no varchar(20) not null comment '部门编号',
    dept_name varchar(30) comment '部门名称',
    id_delete int(1) default 0 comment '是否删除0=否,1=是',
    primary key (id)
);