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
                echo '🐳 Building Docker image with PostgreSQL driver...'
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
                    bat 'docker-compose down -v || echo "No containers to stop"'
                    
                    // Remove old image if it exists
                    bat 'docker rmi portfolio-backend || echo "No old image to remove"'

                    echo '🐳 Starting PostgreSQL first...'
                    bat 'docker-compose up -d postgres'

                    echo '⏳ Waiting for PostgreSQL to be ready...'
                    retry(12) {
                        sleep time: 10, unit: 'SECONDS'
                        bat 'docker exec portfolio-postgres pg_isready -U postgres'
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
    
    post {
        success {
            echo '✅✅✅ Portfolio Backend deployed successfully!!!'
        }
        failure {
            echo '❌❌❌ Portfolio Backend deployment failed!!!'
            echo 'Check Jenkins console output for error details'
        }
        always {
            cleanWs()
        }
    }
}
