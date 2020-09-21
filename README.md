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

## Slackへの通知
* Slack integrationにCircleCI appを追加
* SlackのWebhook URLを取得
* CircleCIのProject Settings、Slack IntegrationにWebhook URLを設定
* config.ymlに以下を追加

```
orbs:
  slack: circleci/slack@3.4.2
```

```
- slack/notify:
	color: "#42e2f4"
	mentions: "circleci,"
	message: A custom message to notify the channel about the latest build
- slack/status:
	fail_only: true
	mentions: "circleci"
```
