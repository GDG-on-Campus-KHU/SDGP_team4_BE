CREATE DATABASE IF NOT EXISTS `gdg_db`;
USE gdg_db;

-- 유저 생성 (존재하지 않으면)
CREATE USER IF NOT EXISTS `team4`@`localhost` IDENTIFIED BY 'gdg1234';
CREATE USER IF NOT EXISTS `team4`@`%` IDENTIFIED BY 'gdg1234';

-- 권한 부여
GRANT ALL PRIVILEGES ON `gdg_db`.* TO `team4`@`localhost`;
GRANT ALL PRIVILEGES ON `gdg_db`.* TO `team4`@`%`;

-- 변경 사항 적용
FLUSH PRIVILEGES;
