# Build, deploy and run Tomcat in debug mode (JDWP on port 8000, HTTP on 8081).
# Run from PowerShell: .\debug-tomcat.ps1
$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot   # DAO3 uses a relative DB path -> must run from project root

$env:JAVA_HOME     = "C:\Program Files\Microsoft\jdk-21.0.13.21-hotspot"
$env:CATALINA_HOME = "$env:USERPROFILE\tools\apache-tomcat-10.1.61"
$mvn = "$env:USERPROFILE\.m2\wrapper\dists\apache-maven-3.8.5-bin\5i5jha092a3i37g0paqnfr15e0\apache-maven-3.8.5\bin\mvn.cmd"

& $mvn -q clean package
if ($LASTEXITCODE -ne 0) { throw "Maven build failed" }

Remove-Item -Recurse -Force "$env:CATALINA_HOME\webapps\libman", "$env:CATALINA_HOME\webapps\libman.war" -ErrorAction SilentlyContinue
Copy-Item target\libman.war "$env:CATALINA_HOME\webapps\"

$env:JPDA_ADDRESS   = "8000"
$env:JPDA_TRANSPORT = "dt_socket"
& "$env:CATALINA_HOME\bin\catalina.bat" jpda run
