DROP TABLE IF EXISTS User;
CREATE TABLE User (
    id_User INT AUTO_INCREMENT NOT NULL,
    mail_User VARCHAR(255),
    name_User VARCHAR(100),
    mdp_User VARCHAR(255),
    id_droit INT,
    PRIMARY KEY (id_User)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS Score;
CREATE TABLE Score (
    id_Score INT AUTO_INCREMENT NOT NULL,
    score_Score INT,
    date_Score DATE,
    id_User INT,
    PRIMARY KEY (id_Score)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS permission;
CREATE TABLE permission (
    id_droit INT AUTO_INCREMENT NOT NULL,
    roles_droit ENUM('Admin', 'User', 'Guest'),
    PRIMARY KEY (id_droit)
) ENGINE=InnoDB;

ALTER TABLE User ADD CONSTRAINT FK_User_id_droit FOREIGN KEY (id_droit) REFERENCES permission (id_droit);
ALTER TABLE Score ADD CONSTRAINT FK_Score_id_User FOREIGN KEY (id_User) REFERENCES User (id_User);
