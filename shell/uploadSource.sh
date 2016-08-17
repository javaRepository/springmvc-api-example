#!/bin/bash

cd /home/liang/intellijIDEA_workspace/testdemo/springmvc-api-example

rm -f source.tar.gz

tar -czvf source.tar.gz pom.xml src

scp -i /home/liang/palmchat/developer/Singapore_test source.tar.gz root@172.17.20.13:/userdata1/jinliang/springmvc-api-example/

rm -f source.tar.gz

ssh -i /home/liang/palmchat/developer/Singapore_test root@172.17.20.13 'bash -s' < /home/liang/intellijIDEA_workspace/testdemo/springmvc-api-example/shell/uploadSourceCode.sh
