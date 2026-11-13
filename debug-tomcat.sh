#!/usr/bin/env bash
# Build, deploy and run Tomcat in debug mode (JDWP on port 8000, HTTP on 8081).
# Run from Git Bash: ./debug-tomcat.sh
set -e
cd "$(dirname "$0")"   # DAO3 uses a relative DB path -> must run from project root

: "${JAVA_HOME:=/c/Program Files/Microsoft/jdk-21.0.13.21-hotspot}"
: "${CATALINA_HOME:=$HOME/tools/apache-tomcat-10.1.61}"
export JAVA_HOME CATALINA_HOME

mvn -q clean package
rm -rf "$CATALINA_HOME/webapps/libman" "$CATALINA_HOME/webapps/libman.war"
cp target/libman.war "$CATALINA_HOME/webapps/"

export JPDA_ADDRESS=8000
export JPDA_TRANSPORT=dt_socket
"$CATALINA_HOME/bin/catalina.sh" jpda run
