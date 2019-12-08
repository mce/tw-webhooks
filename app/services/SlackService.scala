package services

import javax.inject.Inject
import play.api.libs.json._
import play.api.libs.ws.WSClient

class SlackService @Inject()(ws: WSClient) {

  def sendMessage(message: String, slackUrl: String): Unit = {
    def json = Json.obj(
      "text" -> message,
      "username" -> "tw-webhooks-bot"
    )

    ws.url(slackUrl)
      .post(json)
  }
}
