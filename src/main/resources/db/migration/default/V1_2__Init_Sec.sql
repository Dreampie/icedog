--create role--

INSERT INTO sec_role(id,name, value, intro, pid,left_code,right_code,created_at)
VALUES (sec_role_id_seq.nextval,'超级管理员','R_ADMIN','',0,1,8, current_timestamp),
       (sec_role_id_seq.nextval,'系统管理员','R_MANAGER','',1,2,7,current_timestamp),
       (sec_role_id_seq.nextval,'会员','R_MEMBER','',2,3,4,current_timestamp),
       (sec_role_id_seq.nextval,'普通用户','R_USER','',2,5,6,current_timestamp);

--create permission--
INSERT INTO sec_permission(id, name, value, url, intro,pid,left_code,right_code, created_at)
VALUES (sec_permission_id_seq.nextval,'管理员目录','P_D_ADMIN','/admin/**','',0,1,6,current_timestamp),
       (sec_permission_id_seq.nextval,'角色权限管理','P_ROLE','/admin/role/**','',1,2,3,current_timestamp),
       (sec_permission_id_seq.nextval,'用户管理','P_USER','/admin/user/**','',1,4,5,current_timestamp),
       (sec_permission_id_seq.nextval,'会员目录','P_D_MEMBER','/member/**','',0,9,10,current_timestamp),
       (sec_permission_id_seq.nextval,'普通用户目录','P_D_USER','/user/**','',0,11,12,current_timestamp);


INSERT INTO sec_role_permission(id,role_id, permission_id)
VALUES (sec_role_permission_id_seq.nextval,1,1),(sec_role_permission_id_seq.nextval,1,2),
       (sec_role_permission_id_seq.nextval,1,3),(sec_role_permission_id_seq.nextval,1,4),
       (sec_role_permission_id_seq.nextval,1,5),

       (sec_role_permission_id_seq.nextval,2,1),(sec_role_permission_id_seq.nextval,2,3),
       (sec_role_permission_id_seq.nextval,2,4),(sec_role_permission_id_seq.nextval,2,5),

       (sec_role_permission_id_seq.nextval,3,4),(sec_role_permission_id_seq.nextval,3,5),

       (sec_role_permission_id_seq.nextval,4,5);

--user data--
--create  admin--
INSERT INTO sec_user(id, username, providername, email, mobile, password, hasher, salt, avatar_url, first_name, last_name, full_name,department_id, created_at)
VALUES (sec_user_id_seq.nextval,'admin','dreampie','wangrenhui1990@gmail.com','18611434552','$shiro1$SHA-256$500000$ZMhNGAcL3HbpTbNXzxxT1Q==$wRi5AF6BK/1FsQdvISIY1lJ9Rm/aekBoChjunVsqkUU=','default_hasher','','','仁辉','王','仁辉·王',1,current_timestamp),
       (sec_user_id_seq.nextval,'liujintong','dreampie','liujintong1990@gmail.com','18511400502','$shiro1$SHA-256$500000$ZMhNGAcL3HbpTbNXzxxT1Q==$wRi5AF6BK/1FsQdvISIY1lJ9Rm/aekBoChjunVsqkUU=','default_hasher','','','金彤','刘','金彤·刘',2,current_timestamp);

--create user_info--
INSERT INTO sec_user_info(id, user_id, creator_id, gender,province_id,city_id,county_id,street,created_at)
VALUES (sec_user_info_id_seq.nextval,1,0,0,1,2,3,'人民大学',current_timestamp),
       (sec_user_info_id_seq.nextval,2,0,0,1,2,3,'人民大学',current_timestamp);

--create user_role--
INSERT INTO sec_user_role(id, user_id, role_id)
VALUES (sec_user_role_id_seq.nextval,1,1),
       (sec_user_role_id_seq.nextval,2,2);