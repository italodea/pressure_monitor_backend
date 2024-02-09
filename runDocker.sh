sudo docker container stop pressuremonitor-spring-boot-app-1
sudo docker container rm pressuremonitor-spring-boot-app-1

sudo docker image rm pressuremonitor-spring-boot-app:latest
sudo docker compose up -d