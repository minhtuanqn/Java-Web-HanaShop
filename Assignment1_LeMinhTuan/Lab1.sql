use master

CREATE database Assignment1_LeMinhTuan

use Assignment1_LeMinhTuan

create table Accounts (
	username varchar(50) primary key,
	password varchar(20),
	fullname varchar(50),
	role varchar(20),
	email varchar(50),
	status bit
)

create table Orders (
	id int IDENTITY(1,1) primary key,
	createdate datetime,
	username varchar(50),
	total float
)

create table Products (
	id varchar(20) primary key,
	name varchar(100),
	createDate datetime,
	updateDate datetime, 
	status bit, 
	quantityRemain int,
	description varchar(50),
	price float,
	categoryId varchar(30),
	imageSource varchar(100)
)

create table Categories (
	id varchar(30) primary key,
	name varchar(50)
)


create table OrderDetails (
	orderId int foreign key references Orders(id),
	productId varchar(20) foreign key references Products(id),
	Primary key(orderId,productId),
	quantity int
)

ALTER TABLE Orders
	ADD CONSTRAINT fk_Accounts_Orders
	FOREIGN KEY (username)
	REFERENCES Accounts (username);

ALTER TABLE Products
	ADD CONSTRAINT fk_Products_Categories
	FOREIGN KEY (categoryId)
	REFERENCES Categories (id);

--Insert data to Accounts table
insert into Accounts(username, password, fullname,role, email, status) values
('test', '123456', 'Test', 'user','test@gmail.com', 1)
insert into Accounts(username, password, fullname,role, email, status) values
('tuan', '123', 'Le Minh Tuan', 'admin','admin@gmail.com', 1)
insert into Accounts(username, password, fullname,role, email, status) values
('tuanlmse141099@fpt.edu.vn', null, 'Le Minh Tuan', 'admin','tuanlmse141099@fpt.edu.vn', 1)


--Insert data to Categoeies table
insert into Categories(id, name) values
('1', 'cake')
insert into Categories(id, name) values
('2', 'drink')


--Insert data to Products table
insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('cake1','custart cake','1/14/2020',1,15,'delicious', 14000,'1','banh_bong_lan_cuon.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('cake2','fruit crepe','1/14/2020',1,23,'fresh tastes', 20000,'1','banh_crepe_hoa_qua.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('cake3','greentea crepe','1/14/2020',1,15,'good', 19000,'1','banh_crepe_tra_xanh_ngan_lop.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('cake4','strawberry cake','1/14/2020',1,10,'little sour and sweet', 25000,'1','banh_dau_tay.jpg.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('cake5','macaron','1/24/2020',1,11,'sweet meringue', 60000,'1','banh_macaron.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('cake6','garlic bread','1/10/2020',1,9,'cheesy', 50000,'1','banh_mi_bo_toi.webp')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('cake7','brioche bread','1/26/2020',1,7,'light and slightly puffy', 11000,'1','banh_mi_hoa_cuc.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('cake8','mochi cake','1/16/2020',1,6,'sticky and sweet', 1500,'1','banh_mochi.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('cake9','lemon pie','1/17/2020',1,15,'sour and sweet', 40000,'1','banh_pie_chanh.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('cake10','creame puffs','1/17/2020',1,3,'delicious', 10000,'1','banh_su_kem.jpg')


insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink1','Cappuccino','1/14/2020',1,30,'good', 25000,'2','cappuccino.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink2','Ice blended lemon','1/14/2020',1,40,'good', 30000,'2','chanh_tuyet.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink3','Iced almond latte','1/14/2020',1,11,'good', 50000,'2','Iced_almond_latte.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink4','orange juice','1/14/2020',1,50,'good', 30000,'2','nuoc_cam.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink5','Matcha ice blended','1/14/2020',1,27,'good', 55000,'2','matcha_da_xay.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink6','Matcha latte','1/14/2020',1,30,'good', 45000,'2','Matcha_latte.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink7','Lemon juice','1/14/2020',1,30,'good', 20000,'2','nuoc_chanh.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink8','Tomato smoothie','1/14/2020',1,30,'good', 25000,'2','sinh_to_ca_chua.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink9','Mango smoothie','1/14/2020',1,30,'good', 35000,'2','sinh_to_xoai.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink10','Ginger tea','1/14/2020',1,30,'good', 25000,'2','tra_gung.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink11','Black coffee','1/14/2020',1,30,'good', 25000,'2','ca-phe-den-da.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink12','Milk coffee','1/14/2020',1,30,'good', 25000,'2','ca_phe_sua_da.jpg')

insert into Products(id,name,createDate,status,quantityRemain,description,price,categoryId,imageSource) values 
('drink13','Milk tea and bubble','1/14/2020',1,30,'good', 40000,'2','sua_tuoi_tran_chau_duong_den.jpg')


