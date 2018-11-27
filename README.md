# xsa-main-eval
------------------
steps to test:
```sh
mvn clean package
xs push xsa-main-eval
xs logs xsa-main-eval --recent
xs env xsa-main-eval
# to start the echo client provide hostname and port
mvn exec:java -Dexec.args="<hostname> <port>"
xs delete xsa-main-eval
```
