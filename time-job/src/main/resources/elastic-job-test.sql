DROP TABLE IF EXISTS `resume`;
CREATE TABLE `resume` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) DEFAULT NULL,
                          `sex` varchar(255) DEFAULT NULL,
                          `phone` varchar(255) DEFAULT NULL,
                          `address` varchar(255) DEFAULT NULL,
                          `education` varchar(255) DEFAULT NULL,
                          `state` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `resume_bak` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `name` varchar(255) DEFAULT NULL,
                              `sex` varchar(255) DEFAULT NULL,
                              `phone` varchar(255) DEFAULT NULL,
                              `address` varchar(255) DEFAULT NULL,
                              `education` varchar(255) DEFAULT NULL,
                              `state` varchar(255) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS = 1;

DELIMITER ;;
DROP PROCEDURE IF EXISTS batch_insert;
CREATE PROCEDURE batch_insert()
BEGIN
    DECLARE y BIGINT DEFAULT 1;
    WHILE y <= 300
        DO
            insert into resume(`name`,`sex`,`phone`,`address`,`education`,`state`)
            values(CONCAT('name',y),CONCAT('sex',y),CONCAT('phone',y),CONCAT('address',y),'bachelor','未归档'),
                  (CONCAT('name',y),CONCAT('sex',y),CONCAT('phone',y),CONCAT('address',y),'master','未归档'),
                  (CONCAT('name',y),CONCAT('sex',y),CONCAT('phone',y),CONCAT('address',y),'doctor','未归档');
            SET y=y+1;
        END WHILE ;
    commit;
END;;
CALL batch_insert();

TRUNCATE table resume;
TRUNCATE table resume_bak;
UPDATE resume set state="未归档"