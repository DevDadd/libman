# Mac

Run from the project root (DAO3 uses a relative DB path):

```bash
cd /Users/quanganh/IdeaProjects/libman
CATALINA_HOME=/opt/homebrew/opt/tomcat@10/libexec

mvn clean package
rm -rf $CATALINA_HOME/webapps/libman $CATALINA_HOME/webapps/libman.war
cp target/libman.war $CATALINA_HOME/webapps/

export JPDA_ADDRESS=8000
export JPDA_TRANSPORT=dt_socket
$CATALINA_HOME/bin/catalina.sh jpda run
```

Then open http://localhost:8080/libman/ (debugger attaches on port 8000).

# Windows

```powershell
.\debug-tomcat.ps1
```
