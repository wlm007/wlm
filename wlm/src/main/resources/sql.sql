# 创建数据库
create database wlm character set 'utf8' collate 'utf8_general_ci';

# 用户表
create table wlm.sys_user (
    id int auto_increment comment '主键',
    username varchar(30) comment '姓名',
    password varchar(100) comment '密码',
    age int comment '年龄',
    email varchar(50) comment '电子邮箱',
    dept_no varchar(20) comment '部门编号',
    role_no varchar(20) comment '角色编号',
    PRIMARY KEY (id)
);
alter table `wlm`.`sys_user` comment = '用户表';

#初始化用户 wlm/123456
insert into wlm.sys_user values (null, 'wlm', '$2a$10$sFniAJ3xM41tOQ.KLO.0kuYHqijYWAfoaFtqyvu6.CJUS5JqZcQqC', 15, '123@qq.com', '001', 'admin');

# 角色表
create table wlm.sys_role (
    id int auto_increment comment '主键',
    role_no varchar(20) not null comment '角色编号',
    role_name varchar(30) comment '角色名称',
    id_delete int(1) default 0 comment '是否删除0=否,1=是',
    primary key (id)
);
alter table `wlm`.`sys_role` comment = '角色表';

insert into wlm.sys_role (role_no, role_name) values ('admin', '管理员'), ('user', '普通用户');

# 部门表
create table wlm.sys_dept (
    id int auto_increment comment '主键',
    dept_no varchar(42) not null comment '部门编号',
    parent_no varchar(42) not null comment '父级部门，顶级无父级部门为root',
    dept_name varchar(30) comment '部门名称',
    is_delete int(1) default 0 comment '是否删除0=否,1=是',
    primary key (id)
);
alter table `wlm`.`sys_dept` comment = '部门表';

insert into wlm.sys_dept(dept_no, parent_no, dept_name) values ('root', 'root', '地球国际联盟'),
                                                               ('001', 'root', '中国理事会');

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

create table wlm.wx_users (
    id int auto_increment comment '主键',
    subscribe int(10) not null comment '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息',
    openid varchar(50) comment '用户的标识，对当前公众号唯一',
    language varchar(10) comment '用户的语言，简体中文为zh_CN',
    subscribe_time bigint comment '用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间',
    unionid varchar(50) comment '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。',
    remark varchar(50) comment '公众号运营者对粉丝的备注',
    groupid int(10) comment '用户所在的分组ID（兼容旧的用户分组接口）',
    subscribe_scene varchar(50) comment '返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，ADD_SCENE_REPRINT 他人转载 ,ADD_SCENE_LIVESTREAM 视频号直播，ADD_SCENE_CHANNELS 视频号 , ADD_SCENE_OTHERS 其他',
    qr_scene int comment '二维码扫码场景（开发者自定义）',
    qr_scene_str varchar(50) comment '二维码扫码场景（开发者自定义）',
    primary key (id)
);

create table wlm.wx_users_sign (
    id int comment '主键',
    name varchar(50) not null comment '标签名称',
    count int default 0 comment '此标签下粉丝数',
    primary key (id)
)