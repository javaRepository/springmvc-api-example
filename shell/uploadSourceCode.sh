cd /userdata1/jinliang/springmvc-api-example

rm -rf src* target* pom.xml
tar -xzf source.tar.gz
rm -rvf source.tar.gz

export JAVA_HOME=/userdata1/jdk1.8.0_65
PATH=$PATH:$HOME/bin:/userdata1/jdk1.8.0_65/bin
export PATH

/userdata1/apache-maven-3.3.9/bin/mvn -U clean package -Dmaven.test.skip=true

cp -v /userdata1/jinliang/springmvc-api-example/target/ROOT.war /userdata1/apache-tomcat/webapps/ROOT.war.back

rm -rf /userdata1/jinliang/springmvc-api-example/target/
