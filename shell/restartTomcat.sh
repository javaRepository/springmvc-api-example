#!/bin/bash

ssh -i /home/liang/palmchat/developer/Singapore_test root@172.17.20.13 'bash -s' < /home/liang/intellijIDEA_workspace/testdemo/springmvc-api-example/shell/restartTomcatCode.sh

sleep 8

curl -v -X GET 'http://172.17.20.13:8080/getUser?username=test123456'
