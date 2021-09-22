# 部署主从复制 MySQL

1. 使用 `master.bash` 和 `slave.bash` 部署主从节点。
2. 进入 master 节点创建 Replication 用户。

```bash
$ docker exec -it mysql-master mysql -u root -p

mysql> CREATE USER 'repl'@'%' IDENTIFIED BY 'password';
mysql> GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%'; 
```

3. 进入 slave 节点，关联 master 节点。

```bash
$ docker exec -it mysql-slave-1 mysql -u root -p

mysql> CHANGE MASTER TO 
  MASTER_HOST='${masterhost}',
  MASTER_PORT=3307,
  MASTER_USER='repl',
  MASTER_PASSWORD='password',
  GET_MASTER_PUBLIC_KEY=1,
  MASTER_AUTO_POSITION=1;
```
${masterhost} 需要替换 master 节点的 ip

4. 启动 slave。

```bash
mysql> START SLAVE;
```
