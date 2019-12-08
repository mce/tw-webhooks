package controllers

import domain.webhooks.TransferStateChangeEvent
import domain.webhooks.TransferStateChangeEvent._
import javax.inject.Inject
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import services.SlackService

class SlackWebhookController @Inject()(cc: ControllerComponents, slackService: SlackService) extends AbstractController(cc) {

  def onTransferStateChangeEvent(slug: String) = Action {
    implicit request: Request[AnyContent] =>
      val json = request.body.asJson
      val eventOption = json.map(value => value.validate[TransferStateChangeEvent])

      eventOption match {
        case Some(result) if result.isSuccess =>
          val message = tsceToSlackMessage(result.get)
          slackService.sendMessage(
            message,
            s"https://hooks.slack.com/services/$slug")
          Ok("ok")
        case Some(result) if result.isError =>
          BadRequest(s"Invalid Request ${result}")
        case _ =>
          BadRequest("Invalid Request")
      }
  }

  private def tsceToSlackMessage(event: TransferStateChangeEvent): String = {
    s"""
       | Event received for subscription: *${event.subscriptionId}*
       | Profile: *${event.data.resource.profileId}*, Account: *${event.data.resource.accountId}*
       | Transfer: *${event.data.resource.id}*, Current state: *${event.data.currentState}*, Previous state: *${event.data.previousState}*
          """.stripMargin
  }

}
