CREATE DATABASE IF NOT EXISTS portfolio;
USE portfolio;

-- Create user if not exists
CREATE USER IF NOT EXISTS 'portfolio_user'@'%' IDENTIFIED BY 'portfolio_pass';
GRANT ALL PRIVILEGES ON portfolio.* TO 'portfolio_user'@'%';
FLUSH PRIVILEGES;