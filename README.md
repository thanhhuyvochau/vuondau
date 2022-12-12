


# Vuon Dau Capstone Project



## Server Deployment



```bash
ssh: ssh root@103.173.255.39 -p 9020
password: cyZc9Kz5BD6brG
```

## Database Server



```bash
url: jdbc:mysql://103.173.255.39:3306/vuondau
username: root
password: OOiyw5qptYd6xXDl
docker cli: docker run -v mysql_data:/var/lib/mysql --rm --network moodle-network  --name mysql8 -e MYSQL_ROOT_PASSWORD=test -p 3306:3306 -d mysql:8

```
## MinIO Server

****

```bash
url: http://103.173.255.39:9001/
username: minioadmin
password: minioadmin

accessKey: mGUnihw7IeJ3C6EQ
secretKey: KLXCOA4g71xV5SChU3f278AYH3HqKE7I
api:s3v4

```

## Swagger



```bash
http://103.173.255.39:8889/swagger-ui/index.html#/
```

## Tech Stack



**Server:** Spring Boot, Keycloak, MySQL, Docker

