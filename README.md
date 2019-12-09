# tw-webhooks
Forwards [Transferwise webhooks](https://api-docs.transferwise.com/#profile-webhooks) to your slack channels.

### Usage

#### Transfer State Change Event(`transfers#state-change`)

- Enable webhooks for your slack channel: https://slack.com/apps/A0F7XDUAZ-incoming-webhooks
- Once you enable it, you should have a url like `https://hooks.slack.com/services/TR......`
- Replace `https://hooks.slack.com/services/` with `https://twhooks.xyz/slack/tsce/`
- Create a webhook in your account settings page: https://transferwise.com/user/settings

![](./docs/twhooks.png?raw=true "Example")