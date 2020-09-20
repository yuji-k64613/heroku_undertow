# heroku_undertow

## CircleCIからHerokuへのデプロイ
HerokuのEnvironment Variablesから、以下の値を取得し、CircleCIのProject Settingsに設定する
* HEROKU_API_KEY
* HEROKU_APP_NAME

config.ymlに以下を追加
```
orbs:
  heroku: circleci/heroku@1.2.2
```

```
- heroku/deploy-via-git
```

参考URL
* https://circleci.com/orbs/registry/orb/circleci/heroku
* https://dashboard.heroku.com/account
