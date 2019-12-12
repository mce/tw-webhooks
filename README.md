# tw-webhooks
Forwards [Transferwise webhooks](https://api-docs.transferwise.com/#profile-webhooks) to your slack channels.

### Usage

- Enable webhooks for your slack channel: https://slack.com/apps/A0F7XDUAZ-incoming-webhooks
- Once you enable it, you should have a url like `https://hooks.slack.com/services/TR......`
- For `transfers#state-change` event:
    - Replace `https://hooks.slack.com/services/` with `https://twhooks.xyz/slack/tsce/`
- For `balances#credit` event:
    - Replace `https://hooks.slack.com/services/` with `https://twhooks.xyz/slack/balance/`
- Create a webhook in your account settings page: https://transferwise.com/user/settings

![](./docs/twhooks.png?raw=true "Example")