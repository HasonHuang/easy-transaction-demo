INSERT INTO `easytrans-user`.`user`(`id`, `name`, `status`, `created_time`, `updated_time`) VALUES
(1, 'Tom', 'SUCCESS', NOW(), NOW()),
(2, 'Bill', 'SUCCESS', NOW(), NOW()),
(3, 'John', 'SUCCESS', NOW(), NOW());

INSERT INTO `easytrans-account`.`account`(`id`, `user_id`, `amount`, `created_time`, `updated_time`) VALUES
(1, 1, '10000.00', NOW(), NOW()),
(2, 2, '10000.00', NOW(), NOW()),
(3, 3, '10000.00', NOW(), NOW());

INSERT INTO `easytrans-account`.`integral`(`id`, `user_id`, `amount`, `created_time`, `updated_time`) VALUES
(1, 1, '10000.00', NOW(), NOW()),
(2, 2, '10000.00', NOW(), NOW()),
(3, 3, '10000.00', NOW(), NOW());
