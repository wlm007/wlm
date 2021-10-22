# 创建数据库
create database wlm character set 'utf8' collate 'utf8_general_ci';

# 用户表
create table wlm.sys_user (
    id int auto_increment comment '主键',
    username varchar(30) comment '姓名',
    password varchar(50) comment '密码',
    age int comment '年龄',
    email varchar(50) comment '电子邮箱',
    dept_no varchar(20) comment '部门编号',
    role_no varchar(20) comment '角色编号',
    PRIMARY KEY (id, username)
);
alter table `wlm`.`sys_user` comment = '用户表';

#初始化用户 wlm/123456
insert into wlm.sys_user values (null, 'wlm', '$2a$10$sFniAJ3xM41tOQ.KLO.0kuYHqijYWAfoaFtqyvu6.CJUS5JqZcQqC', 0, '123@qq.com', 1, 1);

# 角色表
create table wlm.sys_role (
    id int auto_increment comment '主键',
    role_no varchar(20) not null comment '角色编号',
    role_name varchar(30) comment '角色名称',
    id_delete int(1) default 0 comment '是否删除0=否,1=是',
    primary key (id)
);
alter table `wlm`.`sys_role` comment = '角色表';

insert into wlm.sys_role values (1, 'admin', '管理员', 0);

# 部门表
create table wlm.sys_dept (
    id int auto_increment comment '主键',
    dept_no varchar(20) not null comment '部门编号',
    dept_name varchar(30) comment '部门名称',
    id_delete int(1) default 0 comment '是否删除0=否,1=是',
    primary key (id)
);
alter table `wlm`.`sys_dept` comment = '部门表';

insert into wlm.sys_dept values (1, 'root', '地球国际联盟', 0);

# 菜单表
create table wlm.sys_menu (
    id int auto_increment comment '菜单主键',
    parent_id int default 0 comment '父级菜单主键，0=顶级菜单无父级',
    menu_type int not null default 0 comment '菜单类型 0=菜单组 1=功能菜单',
    menu_name varchar(20) not null comment '菜单名',
    menu_sort int comment '菜单在本菜单组中的排序位置',
    menu_fun varchar(20) not null comment '功能菜单对应的模块名',
    primary key (id)
);
alter table `wlm`.`sys_menu` comment = '系统菜单表';

insert into wlm.sys_menu values
    (1, 0, 0, '系统设置', 0, 'groupMenu'),
    (null, 1, 1, '部门管理', 0, 'deptIndex'),
    (null, 1, 1, '角色管理', 1, 'roleIndex'),
    (null, 1, 1, '用户管理', 2, 'userIndex')
    ;