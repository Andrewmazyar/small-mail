
CREATE TABLE `roles` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));

CREATE TABLE `users` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC));

CREATE TABLE `user_roles` (
    `user_id` BIGINT(11) NOT NULL,
    `role_id` BIGINT(11) NOT NULL,
--     INDEX `user_user_idx` (`user_id` ASC),
--     INDEX `user_role_idx` (`role_id` ASC),
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
  `timeStamp` DATETIME NOT NULL,
  `owner` VARCHAR(225)  NOT NULL,
  `receivers` VARCHAR(255) NOT NULL,
  `user_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_letters_users`
    FOREIGN KEY (`id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);