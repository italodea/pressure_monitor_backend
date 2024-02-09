sudo docker ps -q --filter "name=spring-boot-app-1" | xargs docker stop
sudo docker ps -q --filter "name=spring-boot-app-1" | xargs docker rm
docker images -q --filter "reference=*spring-boot-app" | xargs docker rmi

sudo docker compose up -d