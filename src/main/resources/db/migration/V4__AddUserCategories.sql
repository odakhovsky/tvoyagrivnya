
	CREATE TABLE IF NOT EXISTS `USER_CATEGORY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVE` bit(1) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `OPERATION_TYPE` varchar(255) NOT NULL,
  `PARENT_CATEGORY` int ,
  `MAIN_CATEGORY` int ,
  `OWNER` int ,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_CATEGORY_NAME` (`PARENT_CATEGORY`,`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

ALTER TABLE `USER_CATEGORY` ADD CONSTRAINT `FK_USER_CATEGORY_PARENT` FOREIGN KEY (PARENT_CATEGORY) REFERENCES USER_CATEGORY(ID);
ALTER TABLE `USER_CATEGORY` ADD CONSTRAINT `FK_USER_CATEGORY_MAIN` FOREIGN KEY (MAIN_CATEGORY) REFERENCES CATEGORY(ID);
ALTER TABLE `USER_CATEGORY` ADD CONSTRAINT `FK_USER_CATEGORY_USER` FOREIGN KEY (OWNER) REFERENCES USER(ID);
