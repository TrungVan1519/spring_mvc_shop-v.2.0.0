drop database if exists `online_shop`; -- online shop DB v.2.0.0
create database if not exists `online_shop` character set utf8 collate utf8_general_ci;
use `online_shop`;

--
-- Table structure for `role`
--
drop table if exists `role`;
create table `role`(
	`id` 	int not null auto_increment primary key,
    `name` 	nvarchar(20) not null default 'user',
    
    `createdAt` datetime default current_timestamp,
    `updatedAt` datetime default current_timestamp
) engine=InnoDB auto_increment=1 default charset=utf8;

--
-- Table structure for `user`
--
drop table if exists `user`;
create table `user` (
	`id` 	int not null auto_increment primary key,
    `name` 	nvarchar(20) not null default '',
    `email` varchar(50) not null default '',
    `password` varchar(50) not null default '',
    `id_role` int not null default 1,	-- default be 'user'
    
    `createdAt` datetime default current_timestamp,
    `updatedAt` datetime default current_timestamp,
    
    key `K_user_role` (`id_role`),
    constraint `FK_user_role` foreign key (`id_role`) references `role` (`id`)
) engine=InnoDB auto_increment=1 default charset=utf8;

--
-- Table structure for `category`
--
drop table if exists `category`;
create table `category`(
	`id` 	int not null auto_increment primary key,
    `name`	nvarchar(100) not null default '',
    
    `img`	text default null,
    
    `createdAt` datetime default current_timestamp,
    `updatedAt` datetime default current_timestamp
) engine=InnoDB auto_increment=1 default charset=utf8;

-- 
-- Tabale structure for `product`
--
drop table if exists `product`;
create table `product`(
	`id`	int not null auto_increment primary key,
    `name`	nvarchar(100) not null default '',
    `cost`	int not null default 0,
    
    `description` text default null,
    `img`	text default null,
    `id_category` int default null,
    
    `createdAt` datetime default current_timestamp,
    `updatedAt` datetime default current_timestamp,
    
    key `K_product_category` (`id_category`),
    constraint `FK_product_category` foreign key (`id_category`) references `category` (`id`)
) engine=InnoDB auto_increment=1 default charset=utf8;

--
-- Table structure for `product color`
--
drop table if exists `color`;
create table `color`(
	`id`	int not null auto_increment primary key,
    `name`	nvarchar(50) not null default '',
    
    `createdAt` datetime default current_timestamp,
    `updatedAt` datetime default current_timestamp
) engine=InnoDB auto_increment=1 default charset=utf8;

--
-- Table structure for `product size`
--
drop table if exists `size`;
create table `size`(
	`id`	int not null auto_increment primary key,
    `name`	nvarchar(50) not null default '',
    
    `createdAt` datetime default current_timestamp,
    `updatedAt` datetime default current_timestamp
) engine=InnoDB auto_increment=1 default charset=utf8;

--
-- Table structure for `bill`
--
drop table if exists `bill`;
create table `bill`(
	`id`	int not null auto_increment primary key,
    `name`	nvarchar(100) not null default '',
    `user_name` nvarchar(20) not null default '',
    `status` bit not null default 0, -- 0 means false and not pay money, 1 means true and paid money
    
    `phone_number` char(12) default null,
    `ship_address` text default null,
    
    `createdAt` datetime default current_timestamp,
    `updatedAt` datetime default current_timestamp
) engine=InnoDB auto_increment=1 default charset=utf8;

--
-- Table structure for `sale`
--
drop table if exists `sale`;
create table `sale`(
	`id`	int not null auto_increment primary key,
    `name`	nvarchar(100) not null default '',
    `cost`	int not null default 0,
    
    `description` text default null,
    `img`	text default null,
    `startedAt` nvarchar(50) default null,
    `endAt`	nvarchar(50) default null,
    
    `createdAt` datetime default current_timestamp,
    `updatedAt` datetime default current_timestamp
) engine=InnoDB auto_increment=1 default charset=utf8;


-- ------------------------- Association --------------------------
-- 
-- Tabke structure for `product details`
--
drop table if exists `product_detail`;
create table `product_detail`(
	`id`	int not null auto_increment primary key,
    `qty`	int not null default 0,
    
    `id_product` int default null,
    `id_color` int default null,
    `id_size` int default null,
    
    `createdAt` datetime default current_timestamp,
    `updatedAt` datetime default current_timestamp,
    
    key `K_productDetail_product` (`id_product`),
    key `K_productDetail_color` (`id_color`),
    key `K_productDetail_size` (`id_size`),
    constraint `FK_productDetail_product` foreign key (`id_product`) references `product` (`id`),
    constraint `FK_productDetail_color` foreign key (`id_color`) references `color` (`id`),
    constraint `FK_productDetail_size` foreign key (`id_size`) references `size` (`id`)
) engine=InnoDB auto_increment=1 default charset=utf8;

--
-- Table structure for `bill details`
--
drop table if exists `bill_detail`;
create table `bill_detail`(
	`id_bill` int not null,
    `id_product_detail` int not null,
    `cost` 	int not null default 0,
    `qty`	int not null default 0,
    
    `createdAt` datetime default current_timestamp,
    `updatedAt` datetime default current_timestamp,
    
    primary key (`id_bill`, `id_product_detail`),
    key `K_billDetail_bill` (`id_bill`),
    key `K_billDetail_productDetail` (`id_product_detail`),
    constraint `FK_billDetail_bill` foreign key (`id_bill`) references `bill` (`id`),
    constraint `FK_billDetail_productDetail` foreign key (`id_product_detail`) references `product_detail` (`id`)
) engine=InnoDB auto_increment=1 default charset=utf8;

--
-- Table structure for `sale detail`
--
drop table if exists `sale_detail`;
create table `sale_detail`(
    `id_sale` int not null,
	`id_product` int not null,
    
    `createdAt` datetime default current_timestamp,
    `updatedAt` datetime default current_timestamp,
    
    primary key (`id_sale`, `id_product`),
    key `K_saleDetail_sale` (`id_sale`),
    key `K_saleDetail_product` (`id_product`),
    constraint `FK_saleDetail_sale` foreign key (`id_sale`) references `sale` (`id`),
    constraint `FK_saleDetail_product` foreign key (`id_product`) references `product` (`id`)
) engine=InnoDB auto_increment=1 default charset=utf8;


-- ------------------------- Dumping data --------------------------
INSERT INTO `online_shop`.`role` (`id`, `name`) VALUES ('1', 'user');
INSERT INTO `online_shop`.`role` (`id`, `name`) VALUES ('2', 'employee');
INSERT INTO `online_shop`.`role` (`id`, `name`) VALUES ('3', 'admin');

INSERT INTO `online_shop`.`user` (`id`, `name`, `email`, `password`, `id_role`) VALUES ('1', 'Đậu Xanh', 'dauXanh@gmail.com', '123', '3');
INSERT INTO `online_shop`.`user` (`id`, `name`, `email`, `password`, `id_role`) VALUES ('2', 'Rau Muống', 'rauMuong@gmail.com', '123', '2');
INSERT INTO `online_shop`.`user` (`id`, `name`, `email`, `password`, `id_role`) VALUES ('3', 'Khoai Sắn', 'khoaiSan@gmail.com', '123', '1');

INSERT INTO `online_shop`.`category` (`id`, `name`) VALUES ('1', 'Quần');
INSERT INTO `online_shop`.`category` (`id`, `name`) VALUES ('2', 'Áo');
INSERT INTO `online_shop`.`category` (`id`, `name`) VALUES ('3', 'Giầy');
INSERT INTO `online_shop`.`category` (`id`, `name`) VALUES ('4', 'Găng tay, khăn quàng cổ');
INSERT INTO `online_shop`.`category` (`id`, `name`) VALUES ('5', 'Kính');
INSERT INTO `online_shop`.`category` (`id`, `name`) VALUES ('6', 'Mũ');
INSERT INTO `online_shop`.`category` (`id`, `name`) VALUES ('7', 'Phụ kiện đi kèm');

INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('1', 'Áo 1', '1', '100% là áo', 'ao-1.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('2', 'Áo 2', '2', '100% là áo', 'ao-2.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('3', 'Áo 3', '3', '100% là áo', 'ao-3.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('4', 'Áo 4', '4', '100% là áo', 'ao-4.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('5', 'Áo 5', '1', '100% là áo', 'ao-5.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('6', 'Áo 6', '1', '100% là áo', 'ao-6.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('7', 'Áo 7', '1', '100% là áo', 'ao-7.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('8', 'Áo 8', '1', '100% là áo', 'ao-8.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('9', 'Áo 9', '1', '100% là áo', 'ao-9.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('10', 'Áo đôi 1', '1', '100% là áo', 'ao-doi-1.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('11', 'Áo đôi 2', '1', '100% là áo', 'ao-doi-2.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('12', 'Áo đôi 3', '1', '100% là áo', 'ao-doi-3.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('13', 'Áo đôi 4', '1', '100% là áo', 'ao-doi-4.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('14', 'Áo đôi 5', '1', '100% là áo', 'ao-doi-5.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('15', 'Áo đôi 6', '1', '100% là áo', 'ao-doi-6.jpg', '2');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('16', 'Giầy', '1', '100% là giầy', 'giay.jpg', '3');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('17', 'Quần 1', '1', '100% là quần', 'quan-1.jpg', '1');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('18', 'Quần 2', '1', '100% là quần', 'quan-2.jpg', '1');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('19', 'Quần 3', '1', '100% là quần', 'quan-3.gif', '1');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('20', 'Quần 4', '1', '100% là quần', 'quan-4.jpg', '1');
INSERT INTO `online_shop`.`product` (`id`, `name`, `cost`, `description`, `img`, `id_category`) VALUES ('21', 'Quần 5', '1', '100% là quần', 'quan-5.jpg', '1');

INSERT INTO `online_shop`.`color` (`id`, `name`) VALUES ('1', 'Đỏ');
INSERT INTO `online_shop`.`color` (`id`, `name`) VALUES ('2', 'Cam');
INSERT INTO `online_shop`.`color` (`id`, `name`) VALUES ('3', 'Vàng');
INSERT INTO `online_shop`.`color` (`id`, `name`) VALUES ('4', 'Lục');
INSERT INTO `online_shop`.`color` (`id`, `name`) VALUES ('5', 'Lam');
INSERT INTO `online_shop`.`color` (`id`, `name`) VALUES ('6', 'Tràm');
INSERT INTO `online_shop`.`color` (`id`, `name`) VALUES ('7', 'Tím');

INSERT INTO `online_shop`.`size` (`id`, `name`) VALUES ('1', 'S');
INSERT INTO `online_shop`.`size` (`id`, `name`) VALUES ('2', 'M');
INSERT INTO `online_shop`.`size` (`id`, `name`) VALUES ('3', 'L');
INSERT INTO `online_shop`.`size` (`id`, `name`) VALUES ('4', 'XL');
INSERT INTO `online_shop`.`size` (`id`, `name`) VALUES ('5', 'XXL');

INSERT INTO `online_shop`.`product_detail` (`id`, `qty`, `id_product`, `id_color`, `id_size`) VALUES ('1', '10', '1', '1', '1');
INSERT INTO `online_shop`.`product_detail` (`id`, `qty`, `id_product`, `id_color`, `id_size`) VALUES ('2', '10', '1', '2', '2');
INSERT INTO `online_shop`.`product_detail` (`id`, `qty`, `id_product`, `id_color`, `id_size`) VALUES ('3', '10', '2', '3', '3');
INSERT INTO `online_shop`.`product_detail` (`id`, `qty`, `id_product`, `id_color`, `id_size`) VALUES ('4', '20', '10', '4', '4');
INSERT INTO `online_shop`.`product_detail` (`id`, `qty`, `id_product`, `id_color`, `id_size`) VALUES ('5', '20', '10', '5', '5');
INSERT INTO `online_shop`.`product_detail` (`id`, `qty`, `id_product`, `id_color`, `id_size`) VALUES ('6', '20', '11', '1', '1');
INSERT INTO `online_shop`.`product_detail` (`id`, `qty`, `id_product`, `id_color`, `id_size`) VALUES ('7', '20', '11', '2', '2');
