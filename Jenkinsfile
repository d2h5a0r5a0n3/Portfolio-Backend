pipeline {
    agent any
    

    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/d2h5a0r5a0n3/Portfolio-Backend.git', credentialsId: 'github-credentials'
            }
        }
        
        stage('Build') {
            steps {
                bat """
                    set JAVA_HOME=C:\\Java\\java-21\\jdk
                    set PATH=%JAVA_HOME%\\bin;C:\\Program Files\\Apache\\Maven\\apache-maven-3.9.9\\bin;%PATH%
                    mvn clean compile
                """
            }
        }
        
        stage('Test') {
            steps {
                bat """
                    set JAVA_HOME=C:\\Java\\java-21\\jdk
                    set PATH=%JAVA_HOME%\\bin;C:\\Program Files\\Apache\\Maven\\apache-maven-3.9.9\\bin;%PATH%
                    mvn test
                """
            }
        }
        
        stage('Package') {
            steps {
                bat """
                    set JAVA_HOME=C:\\Java\\java-21\\jdk
                    set PATH=%JAVA_HOME%\\bin;C:\\Program Files\\Apache\\Maven\\apache-maven-3.9.9\\bin;%PATH%
                    mvn package -DskipTests
                """
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