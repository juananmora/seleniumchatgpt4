#!/usr/bin/env python3

import os
import subprocess

class DaggerTask:
    def __init__(self, github_repo, dockerhub_repo):
        self.github_repo = github_repo
        self.dockerhub_repo = dockerhub_repo

    def download_project(self):
        # Clonar el repositorio de GitHub
        os.system(f"git clone {self.github_repo}")

    def build_project(self):
        # Navegar al directorio del proyecto
        os.chdir(os.path.basename(self.github_repo).split(".")[0])
        # Ejecutar "mvn clean package"
        subprocess.call("mvn clean package", shell=True)

    def build_docker_image(self):
        # Construir la imagen de Docker
        subprocess.call("docker build -t myimage .", shell=True)

    def publish_to_dockerhub(self):
        # Etiquetar la imagen con el repositorio de DockerHub
        subprocess.call(f"docker tag myimage {self.dockerhub_repo}", shell=True)
        # Publicar la imagen en DockerHub
        username = "juananmora"
        password = "gloyjonas"
        subprocess.call(f"docker login -u {username} -p {password}", shell=True)
        subprocess.call(f"docker push {self.dockerhub_repo}", shell=True)
        

# Uso de la clase DaggerTask
dagger_task = DaggerTask("https://github.com/juananmora/camas", "juananmora/camas:latest")
dagger_task.download_project()
dagger_task.build_project()
dagger_task.build_docker_image()
dagger_task.publish_to_dockerhub()