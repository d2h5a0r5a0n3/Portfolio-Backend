pipeline {
    agent any
    

    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-repo/portfolio.git'
            }
        }
        
        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        
        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'
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
        
        stage('Deploy') {
            steps {
                bat 'docker-compose down'
                bat 'docker-compose up -d'
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
    }
}