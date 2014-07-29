
INSERT INTO department(id,name,intro,leader_id,pid,left_code,right_code,created_at)
VALUES(department_id_seq.nextval,'董事部','董事部门',1,0,1,4,current_timestamp),
(department_id_seq.nextval,'总裁部','总裁部门',1,1,2,3,current_timestamp);


INSERT INTO follower(id,user_id,link_id,intro,created_at)
VALUES(follower_id_seq.nextval,1,2,'following测试',current_timestamp),
      (follower_id_seq.nextval,2,1,'follower测试',current_timestamp);

INSERT INTO blog(id,user_id,tags,title,body,created_at)
VALUES(blog_id_seq.nextval,1,'设计,扁平化','扁平化设计的优势','扁平化设计继续主导全球网页设计潮流，CSS3代替图片实现的效果大大优化网站性能，提升网站用户体验。响应式布局完美兼容电脑、平板以及手机端，让你的博客能够在任意设备上访问自如，更加提升博客自身档次，有利于多方位推广。',current_timestamp),
(blog_id_seq.nextval,1,'设计,扁平化','扁平化设计的优势','扁平化设计继续主导全球网页设计潮流，CSS3代替图片实现的效果大大优化网站性能，提升网站用户体验。响应式布局完美兼容电脑、平板以及手机端，让你的博客能够在任意设备上访问自如，更加提升博客自身档次，有利于多方位推广。',current_timestamp),
(blog_id_seq.nextval,1,'HTML5,重构,优化','基于HMLT5完全重构代码，优化主题性能','如果你已经开始使用ZanBlog3了，你会发现ZanBlog3的主题文件结构以及代码发生了完完全全的改变。没错，为了提升主题性能，更加契合WordPress主题的开发特色，我们将90%以上的代码进行了重构，并且精益求精，希望能够大幅度提升主题性能，从而有利于站长的网站SEO优化以及维护。另外不用多说，ZanBlog3的代码基于HTML5编写，完全符合W3C规范，初学者同样可以通过ZanBlog3主题来学习HTML的编码知识。',current_timestamp),
(blog_id_seq.nextval,1,'设计,扁平化','扁平化设计的优势','扁平化设计继续主导全球网页设计潮流，CSS3代替图片实现的效果大大优化网站性能，提升网站用户体验。响应式布局完美兼容电脑、平板以及手机端，让你的博客能够在任意设备上访问自如，更加提升博客自身档次，有利于多方位推广。',current_timestamp),
(blog_id_seq.nextval,1,'设计,扁平化','扁平化设计的优势','扁平化设计继续主导全球网页设计潮流，CSS3代替图片实现的效果大大优化网站性能，提升网站用户体验。响应式布局完美兼容电脑、平板以及手机端，让你的博客能够在任意设备上访问自如，更加提升博客自身档次，有利于多方位推广。',current_timestamp),
(blog_id_seq.nextval,1,'设计,扁平化','扁平化设计的优势','扁平化设计继续主导全球网页设计潮流，CSS3代替图片实现的效果大大优化网站性能，提升网站用户体验。响应式布局完美兼容电脑、平板以及手机端，让你的博客能够在任意设备上访问自如，更加提升博客自身档次，有利于多方位推广。',current_timestamp);
