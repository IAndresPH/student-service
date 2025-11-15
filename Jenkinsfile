pipeline {
    agent any

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['develop', 'qa', 'release.s.2025.08', 'release.s.2025.09', 'release.s.2025.10'],
            description: 'Ambiente a usar (solo informativo)'
        )
        string(name: 'BRANCH', defaultValue: 'develop', description: 'Rama a construir')
        string(name: 'IMAGE_TAG', defaultValue: '', description: 'Tag de la imagen Docker')
        string(name: 'DOCKER_REGISTRY_HOST', defaultValue: '', description: 'Registry para push (vacío = no push)')
    }

    environment {
        MVN_CMD = "mvn -B -U"
        IMAGE_NAME = "student-service"
        IMAGE_TAG = "${IMAGE_TAG ?: BUILD_NUMBER}"
        FULL_IMAGE = "${DOCKER_REGISTRY_HOST ? DOCKER_REGISTRY_HOST + '/' : ''}${IMAGE_NAME}:${IMAGE_TAG}"

        ENV_DEPLOY_FILE = ".env.deploy"

        DOCKER_REGISTRY_CRED = "docker-registry-creds"
        SSH_SERVER_CRED = "ssh-server"

        SERVER_HOST = "localhost"
    }

    options {
        timestamps()
        ansiColor('xterm')
    }

    stages {

        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: "*/${BRANCH}"]],
                    userRemoteConfigs: [[
                        url: 'https://github.com/IAndresPH/student-service.git',
                        credentialsId: 'github-https'
                    ]]
                ])
            }
        }

        stage('Copy .env from Server') {
            steps {
                sshagent([SSH_SERVER_CRED]) {
                    sh """
                        # Copiar el .env existente del servidor como .env.deploy
                        scp deployer@${SERVER_HOST}:/apps/config/.env ${ENV_DEPLOY_FILE}
                        echo ".env.deploy copiado desde servidor correctamente"
                    """
                }
            }
        }

        stage('Maven Package') {
            steps {
                sh "${MVN_CMD} clean package -DskipTests -DskipITs"
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build --pull -t ${FULL_IMAGE} ."
            }
        }

        stage('Docker Push') {
            when {
                expression { return DOCKER_REGISTRY_HOST?.trim() }
            }
            steps {
                withCredentials([usernamePassword(
                    credentialsId: DOCKER_REGISTRY_CRED,
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh """
                        set -e
                        echo \$DOCKER_PASS | docker login ${DOCKER_REGISTRY_HOST} --username \$DOCKER_USER --password-stdin
                        docker push ${FULL_IMAGE}
                        docker logout ${DOCKER_REGISTRY_HOST}
                    """
                }
            }
        }

        stage('Deploy to Server') {
            steps {
                sshagent([SSH_SERVER_CRED]) {
                    sh """
                        scp ${ENV_DEPLOY_FILE} deployer@${SERVER_HOST}:/apps/config/.env.deploy
                        ssh deployer@${SERVER_HOST} 'cd /apps/docker && docker compose down && docker compose pull && docker compose up -d'
                    """
                }
            }
        }

    }

    post {
        success {
            echo "Pipeline completo. Imagen generada: ${FULL_IMAGE}"
        }
        failure {
            echo "Pipeline falló."
        }
        always {
            cleanWs()
        }
    }
}