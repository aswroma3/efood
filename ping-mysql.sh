#! /bin/bash -e

# check if mysql is reachable
# sarebbe meglio finire con --execute="show databases;" ma da' un errore

docker run \
   --name mysqlterm --rm \
   -e MYSQL_PORT_3306_TCP_ADDR=$DOCKER_HOST_IP -e MYSQL_PORT_3306_TCP_PORT=3306 -e MYSQL_ENV_MYSQL_ALLOW_EMPTY_PASSWORD=yes \
   mysql:5.7.13  \
   sh -c 'exec mysql --protocol=TCP --host="$MYSQL_PORT_3306_TCP_ADDR" --port="$MYSQL_PORT_3306_TCP_PORT" --version'

#docker run \
#   --name mysqlterm --rm \
#   -e MYSQL_PORT_3306_TCP_ADDR=$DOCKER_HOST_IP -e MYSQL_PORT_3306_TCP_PORT=3306 -e MYSQL_ENV_MYSQL_ROOT_PASSWORD=rootpassword \
#   mysql:5.7.13  \
#   sh -c 'exec mysql --protocol=TCP --host="$MYSQL_PORT_3306_TCP_ADDR" --port="$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD" --version'

#docker run \
#   --name mysqlterm --rm -i \
#   -e MYSQL_PORT_3306_TCP_ADDR=$DOCKER_HOST_IP -e MYSQL_PORT_3306_TCP_PORT=3306 -e MYSQL_ENV_MYSQL_ROOT_PASSWORD=rootpassword \
#   mysql:5.7.13  \
#   sh -c 'exec mysql --host=10.11.1.188 --port=3306 --user=root --password=rootpassword --version'

