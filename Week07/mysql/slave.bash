#!/bin/bash
docker run -d --name mysql-slave-1 \
  -e MYSQL_ROOT_PASSWORD=my_root_password \
  -p 3308:3306 \
  -v $(pwd)/mysql-slave-1.cnf:/etc/mysql/conf.d/mysql-slave-1.cnf \
  mysql:8 \
  --skip-log-bin \
  --skip-log-slave-updates \
  --skip-slave-start