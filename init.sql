-- PostgreSQL initialization script
-- Database 'portfolio' is already created by POSTGRES_DB environment variable

-- Create user if not exists
DO
$$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'portfolio_user') THEN

      CREATE USER portfolio_user WITH PASSWORD 'postgres';
   END IF;
END
$$;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE portfolio TO portfolio_user;
GRANT ALL ON SCHEMA public TO portfolio_user;
GRANT ALL ON ALL TABLES IN SCHEMA public TO portfolio_user;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO portfolio_user;