DROP TABLE IF EXISTS department;
DROP SEQUENCE IF EXISTS department_id_seq;
CREATE SEQUENCE department_id_seq START WITH 1;
CREATE TABLE department (
  id   BIGINT  NOT NULL DEFAULT NEXTVAL('department_id_seq') PRIMARY KEY,
  name VARCHAR(50) NOT NULL COMMENT '名称',
  intro TEXT COMMENT '简介',
  leader_id  INT NOT NULL COMMENT '负责人',
  pid     BIGINT DEFAULT 0  COMMENT '父级id',
  left_code   BIGINT DEFAULT 0  COMMENT '数据左边码',
  right_code  BIGINT DEFAULT 0  COMMENT '数据右边码',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);


DROP TABLE IF EXISTS follower;
DROP SEQUENCE IF EXISTS follower_id_seq;
CREATE SEQUENCE follower_id_seq START WITH 1;
CREATE TABLE follower (
  id   BIGINT  NOT NULL DEFAULT NEXTVAL('follower_id_seq') PRIMARY KEY,
  user_id BIGINT    NOT NULL  COMMENT '用户id',
  link_id BIGINT    NOT NULL  COMMENT '联系人id',
  intro TEXT COMMENT '简介',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP
);

DROP TABLE IF EXISTS blog;
DROP SEQUENCE IF EXISTS blog_id_seq;
CREATE SEQUENCE blog_id_seq START WITH 1;
CREATE TABLE blog (
  id   BIGINT  NOT NULL DEFAULT NEXTVAL('blog_id_seq') PRIMARY KEY,
  user_id BIGINT  NOT NULL  COMMENT '用户id',
  topped INT NOT NULL DEFAULT 0 COMMENT '0默认，1置顶',
  type INT NOT NULL DEFAULT 1 COMMENT '1文字2音乐3照片4视频5链接 ',
  tags VARCHAR(30) NOT NULL COMMENT '标签',
  title VARCHAR(50) NOT NULL COMMENT '标题',
  body TEXT NOT NULL COMMENT '内容',
  completed INT NOT NULL DEFAULT 0 COMMENT '0发布 1草稿',
  hit_count INT DEFAULT 0 COMMENT '点击量',
  feed_count INT NOT NULL DEFAULT 0 COMMENT '订阅数',
  replay_count INT NOT NULL DEFAULT 0 COMMENT '评论回复数',
  no_reply INT NOT NULL DEFAULT 0 COMMENT '不允许评论',
  collect_count INT DEFAULT 0 COMMENT '收藏数',
  opened INT DEFAULT 0 COMMENT '是否公开0完全公开，1好友公开，2私密',
  support_count INT NOT NULL DEFAULT 0 COMMENT '支持数',
  oppose_count INT NOT NULL DEFAULT 0 COMMENT '反对数',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);

DROP TABLE IF EXISTS blog_reply;
DROP SEQUENCE IF EXISTS blog_reply_id_seq;
CREATE SEQUENCE blog_reply_id_seq START WITH 1;
CREATE TABLE blog_reply (
  id   BIGINT  NOT NULL DEFAULT NEXTVAL('blog_reply_id_seq') PRIMARY KEY,
  blog_id INT NOT NULL COMMENT '回复博客',
  reply_id INT DEFAULT '0' COMMENT '被回复的内容',
  user_id INT NOT NULL COMMENT '回复用户',
  body TINYTEXT NOT NULL COMMENT '内容',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);