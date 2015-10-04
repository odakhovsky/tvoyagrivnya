CREATE TABLE `NOTE` (
    `ID` int(11) NOT NULL AUTO_INCREMENT,
    `TEXT` varchar(5000) NOT NULL,
    `DATE` date not NULL ,
    `ACTIVE` bit(1) DEFAULT NULL,
    `CATEGORY` int ,
    `OWNER` int not NULL ,
    PRIMARY KEY (`ID`)
);
ALTER TABLE `NOTE` ADD CONSTRAINT `FK_NOTE_USER` FOREIGN KEY (OWNER) REFERENCES USER(ID);
ALTER TABLE `NOTE` ADD CONSTRAINT `FK_NOTE_CATEGORY` FOREIGN KEY (CATEGORY) REFERENCES USER_CATEGORY(ID);
