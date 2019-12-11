package controllers

import domain.webhooks.{BalanceEvent, TransferStateChangeEvent}
import javax.inject.Inject
import play.api.libs.json.{JsResult, JsValue}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import services.SlackService

class SlackWebhookController @Inject()(cc: ControllerComponents, slackService: SlackService) extends AbstractController(cc) {

  val slackHost = "https://hooks.slack.com/services"

  def onTransferStateChangeEvent(slug: String) =
    onEvent(slug)(value => value.validate[TransferStateChangeEvent])(e => tsceToSlackMessage(e))

  private def tsceToSlackMessage(event: TransferStateChangeEvent): String = {
    s"""
       | Event received for subscription: *${event.subscriptionId}*
       | Profile: *${event.data.resource.profileId}*, Account: *${event.data.resource.accountId}*
       | Transfer: *${event.data.resource.id}*, Current state: *${event.data.currentState}*, Previous state: *${event.data.previousState}*
          """.stripMargin
  }

  def onBalanceEvent(slug: String) = onEvent(slug)(value => value.validate[BalanceEvent])(e => balanceEventToSlackMessage(e))

  private def balanceEventToSlackMessage(event: BalanceEvent): String = {
    s"""
       | Event received for subscription: *${event.subscriptionId}*
       | Profile: *${event.data.resource.profileId}*, Balance Account: *${event.data.resource.id}*
       | Transaction Amount: *${event.data.amount} ${event.data.currency}*
       | Balance After Transaction: *${event.data.post_transaction_balance_amount}* *${event.data.currency}*
       |""".stripMargin
  }

  def onEvent[T](slug: String)(toJson: JsValue => JsResult[T])(toMessage: T => String) = Action {
    implicit request: Request[AnyContent] =>
      val json = request.body.asJson
      val eventOption = json.map(value => toJson(value))

      eventOption match {
        case Some(result) if result.isSuccess =>
          val message = toMessage(result.get)
          slackService.sendMessage(
            message,
            s"$slackHost/$slug")
          Ok("ok")
        case Some(result) if result.isError =>
          BadRequest(s"Invalid Request ${result}")
        case _ =>
          BadRequest("Invalid Request")
      }
      Ok("ok")
  }

}
