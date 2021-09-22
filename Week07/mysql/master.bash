#!/bin/bash
docker run -d --name mysql-master \
  -e MYSQL_USER=my_user \
  -e MYSQL_DATABASE=my_database \
  -e MYSQL_PASSWORD=my_database_password \
  -e MYSQL_ROOT_PASSWORD=my_root_password \
  -p 3307:3306 \
  -v $(pwd)/mysql-master.cnf:/etc/mysql/conf.d/mysql-master.cnf \
  mysql:8 \
  --log-bin=my