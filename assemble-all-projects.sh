#!/bin/bash

# Script per effettuare la build di un insieme di progetti Gradle 

echo Assembling all projects  

gradle assemble
#./gradlew assemble

## determina il path relativo in cui si trova lo script
## (rispetto alla posizione da cui � stata richiesta l'esecuzione dello script)
#PATH_TO_SCRIPT=`dirname $0`
#
## determina i progetti da costruire
#PROJECTS=$(ls ${PATH_TO_SCRIPT}/*/build.gradle)
#
## costruisce tutti i progetti
#for project in ${PROJECTS}; do
#	echo ""
#	echo "Now assembling ${project}"
#	gradle --build-file ${project} assemble
#done
