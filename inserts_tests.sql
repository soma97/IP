
insert into user_account (name,surname,username,password,email,country,region,city,image_src,receive_emer_notif, number_of_logins, is_admin, is_approved, is_blocked, is_logged_in)
				values('Milos','Trivundza','Soma','soma','milos.trivundza@hotmail.com','BA','Republika Srpska','Banja Luka', 'media/Somas3548299.jpg','email', 0, true,true,false,false);
                
insert into user_account (name,surname,username,password,email,country,region,city,image_src,receive_emer_notif, number_of_logins, is_admin, is_approved, is_blocked, is_logged_in)
				values('Milos','Trivundza','Soma2','soma','milos.trivundza97@gmail.com','BA','Republika Srpska','Banja Luka', 'media/Somas3548299.jpg','email', 0, false,true,false,false);
                
insert into call_category(category) values('sanacija stete');
insert into call_category(category) values('prikupljanje odjece');
insert into call_category(category) values('prikupljanje osnovnih zivotnih namirnica');
insert into call_category(category) values('transport');

select * from emergency_category;
select * from user_account;

insert into emergency_call(title,description,date,location,image_src,is_deleted,call_category_id) values('Test call','Testni poziv u pomoc',now(),null,'https://upload.wikimedia.org/wikipedia/commons/b/b6/Image_created_with_a_mobile_phone.png',false,2);
