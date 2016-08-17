/userdata1/apache-tomcat/bin/shutdown.sh

sleep 3

kill $(ps aux | grep '/userdata1/apache-tomcat/conf/logging.properties' | awk '{print $2}')

rm -rf /userdata1/apache-tomcat/webapps/ROOT/ /userdata1/apache-tomcat/webapps/ROOT.war

cp -v /userdata1/apache-tomcat/webapps/ROOT.war.back /userdata1/apache-tomcat/webapps/ROOT.war

/userdata1/apache-tomcat-palmcall/bin/startup.sh
