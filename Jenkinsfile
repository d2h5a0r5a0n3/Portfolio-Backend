pipeline {
    agent any
    
    environment {
        JAVA_HOME = 'C:\\Java\\java-21\\jdk'
        PATH = "${JAVA_HOME}\\bin;C:\\Program Files\\Apache\\Maven\\apache-maven-3.9.9\\bin;${env.PATH}"
        MAVEN_OPTS = '-Dmaven.repo.local=C:\\Users\\Dharaneshwar\\.m2\\repository'
        BACKEND_PORT = '9091'
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
                        echo '📥 Downloading Maven dependencies for first time...'
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
                bat 'docker build -t portfolio-backend .'
            }
        }
        
        stage('Setup Environment') {
            steps {
                bat 'copy "C:\\ProgramData\\Jenkins\\.jenkins\\secrets\\.env" .env'
            }
        }
        
        stage('Deploy with Health Check') {
            steps {
                echo '🐳 Starting MySQL first...'
                bat 'docker-compose down -v'
                bat 'docker-compose up -d mysql'
                
                echo '⏳ Waiting for MySQL to be ready...'
                script {
                    retry(12) {
                        sleep time: 10, unit: 'SECONDS'
                        bat 'docker exec portfolio-mysql mysqladmin ping -h localhost -u root -proot --silent'
                    }
                }
                
                echo '🚀 Starting backend service...'
                bat 'docker-compose up -d backend'
            }
        }
        
        stage('Verify Backend') {
            steps {
                echo '🔍 Waiting for backend to become healthy...'
                script {
                    retry(10) {
                        sleep time: 15, unit: 'SECONDS'
                        bat "curl --fail http://localhost:${BACKEND_PORT}/actuator/health || exit 0"
                    }
                }
            }
        }
    }
    
    post {
        success {
            echo '✅✅✅ Portfolio Backend deployed successfully!!!'
        }
        failure {
            echo '❌❌❌ Portfolio Backend deployment failed!!!'
            bat 'docker-compose logs'
        }
        always {
            cleanWs()
        }
    }
}