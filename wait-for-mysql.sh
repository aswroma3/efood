#! /bin/sh

until (./ping-mysql.sh -i > /dev/null)
do
 echo sleeping for mysql
 sleep 5
done
