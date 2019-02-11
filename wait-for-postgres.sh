#! /bin/sh

#echo sleeping for postgres
#sleep 5

until (./ping-postgres.sh -i > /dev/null)
do
 echo sleeping for postgres
 sleep 5
done
