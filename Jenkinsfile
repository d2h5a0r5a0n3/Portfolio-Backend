pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven'
    }

    environment {
        BACKEND_PORT = '9091'
        MAVEN_OPTS = '-Dmaven.repo.local=C:\\Users\\Dharaneshwar\\.m2\\repository'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/d2h5a0r5a0n3/Portfolio-Backend.git', credentialsId: 'github-credentials'
            }
        }

        stage('Prepare Dependencies') {
            steps {
                echo '🔧 Checking Maven dependencies...'
                script {
                    def repoExists = fileExists('C:\\Users\\Dharaneshwar\\.m2\\repository')
                    if (!repoExists) {
                        echo '📥 Downloading Maven dependencies for the first time...'
                        bat 'mvn dependency:go-offline'
                    } else {
                        echo '✅ Using existing Maven dependencies from cache'
                    }
                }
            }
        }

        stage('Build Backend') {
            steps {
                echo '🏗️ Building Spring Boot Application...'
                bat 'mvn clean install -DskipTests -o'
            }
        }

        stage('Docker Build') {
            steps {
                echo '🐳 Building Docker image with MariaDB driver...'
                bat 'docker build --no-cache -t portfolio-backend .'
            }
        }

        stage('Setup Environment') {
            steps {
                bat 'copy "C:\\ProgramData\\Jenkins\\.jenkins\\secrets\\.env" .env'
            }
        }

        stage('Deploy with Health Check') {
            steps {
                script {
                    echo '🧹 Cleaning up any existing containers and images...'

                    // Stop containers if running
                    bat '''
                        docker ps -q --filter "name=portfolio" | findstr . >nul
                        if %ERRORLEVEL% EQU 0 (
                            echo Stopping running portfolio containers...
                            docker-compose down -v
                        ) else (
                            echo No running containers found.
                        )
                    '''

                    // Remove old image if it exists
                    bat '''
                        docker images -q portfolio-backend | findstr . >nul
                        if %ERRORLEVEL% EQU 0 (
                            echo Removing old portfolio-backend image...
                            docker rmi portfolio-backend
                        ) else (
                            echo No old portfolio-backend image to remove.
                        )
                    '''

                    echo '🐳 Starting MariaDB first...'
                    bat 'docker-compose up -d mariadb'

                    echo '⏳ Waiting for MariaDB to be ready...'
                    retry(12) {
                        sleep time: 10, unit: 'SECONDS'
                        bat 'docker exec portfolio-mariadb mysqladmin ping -h localhost -u root -proot --silent'
                    }

                    echo '🚀 Starting backend service...'
                    bat 'docker-compose up -d backend'
                }
            }
        }

        stage('Verify Backend') {
            steps {
                echo '🔍 Waiting for backend to become healthy...'
                script {
                    def healthCheckPassed = false
                    for (int i = 0; i < 10 && !healthCheckPassed; i++) {
                        sleep time: 15, unit: 'SECONDS'
                        def status = bat(script: "curl -f http://localhost:${BACKEND_PORT}/actuator/health", returnStatus: true)
                        if (status == 0) {
                            echo '✅ Backend health check passed!'
                            healthCheckPassed = true
                        } else {
                            echo "Health check attempt ${i + 1}/10 failed (curl exit code: ${status})"
                        }
                    }

                    if (!healthCheckPassed) {
                        echo '⚠️ Health check failed but continuing...'
                        // Uncomment to fail the build:
                        // error '❌ Backend health check failed after multiple attempts.'
                    }
                }
            }
        }
    }
}
