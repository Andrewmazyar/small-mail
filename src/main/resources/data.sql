
CREATE TABLE `roles` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`role_name` ASC));

CREATE TABLE `users` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC));

CREATE TABLE `user_roles` (
    `user_id` BIGINT(11) NOT NULL,
    `role_id` BIGINT(11) NOT NULL,
    CONSTRAINT `user_role_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `users` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `user_role`
        FOREIGN KEY (`role_id`)
        REFERENCES `roles` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE `letters` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `theme` VARCHAR(150) ,
  `body` VARCHAR(1024) ,
  `timeStamp` VARCHAR(225) NOT NULL,
  `owner` VARCHAR(225)  NOT NULL,
  `receivers` VARCHAR(255) NOT NULL,
  `user_id` BIGINT(11) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_letters_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
);

CREATE TABLE `user_letters` (
    `user_id` BIGINT(11) NOT NULL,
    `letter_id` BIGINT(11) NOT NULL,
    CONSTRAINT `user_letter_user`
    FOREIGN KEY (`user_id`)
        REFERENCES `users` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT `user_letter`
        FOREIGN KEY (`letter_id`)
        REFERENCES `letters` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO roles(role_name)
VALUES ('APICALL');
INSERT INTO roles(role_name)
VALUES ('USER');

INSERT INTO users(email, password)
VALUES ('user@gmail.com', '$2a$10$iuQfUtCVLuz8C51UapGFZOghIns8wDS.qvdo02bUeVG4.Q2hS84rC');

INSERT  INTO user_roles(role_id, user_id)
VALUES(2,1);

INSERT INTO users(email, password)
VALUES ('president@gmail.com', '$2a$10$4t1yu5IydQ5mclhPcQ3fWenYpgipE2e2dxKmhr6HQnT3xVPZwGFBW');

INSERT  INTO user_roles(role_id, user_id)
VALUES(2,2);

INSERT INTO users(email, password)
VALUES ('minister@gmail.com', '$2a$10$xpjgE4GHf0o2fdSl2TVuleNhoSdLCmb8m9/cyPP0722X7BaCSbrP.');

INSERT  INTO user_roles(role_id, user_id)
VALUES(2,3);

INSERT INTO users(email, password)
VALUES ('deputy@gmail.com', '$2a$10$jdFgvEG2GoZ1im4iPWqd0eQ.KjAUuXUhfZ2mP/wMuTy8IERuNgiji');

INSERT  INTO user_roles(role_id, user_id)
VALUES(2,4);

INSERT INTO users(email, password)
VALUES ('citizen@gmail.com', '$2a$10$SRhgUH3KmBaDQuNLcOdezeCtpgntNw0wHZvrAX9l5N0kFS/Z4E5Ia');

INSERT  INTO user_roles(role_id, user_id)
VALUES(2,5);

