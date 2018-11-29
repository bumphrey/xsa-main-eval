# xsa-main-eval
------------------
steps to test:
```sh
mvn clean package
xs push xsa-main-eval
xs logs xsa-main-eval --recent
xs env xsa-main-eval
xs delete xsa-main-eval
```
