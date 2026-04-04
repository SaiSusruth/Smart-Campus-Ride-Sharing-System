@ECHO OFF
SETLOCAL

REM Minimal Maven Wrapper for Windows (downloads maven-wrapper.jar if missing)

SET "MAVEN_PROJECTBASEDIR=%~dp0"
SET "WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"

IF EXIST "%WRAPPER_JAR%" GOTO RUN_WRAPPER

SET "WRAPPER_JAR_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"

ECHO Downloading Maven wrapper jar...
powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "Invoke-WebRequest -UseBasicParsing -Uri '%WRAPPER_JAR_URL%' -OutFile '%WRAPPER_JAR%'"

IF EXIST "%WRAPPER_JAR%" GOTO RUN_WRAPPER

ECHO Failed to download Maven wrapper jar.
EXIT /B 1

:RUN_WRAPPER
java -jar "%WRAPPER_JAR%" -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" %*

ENDLOCAL

