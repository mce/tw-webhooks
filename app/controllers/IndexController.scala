package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class IndexController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index()= Action {
    Redirect("https://github.com/mce/tw-webhooks")
  }
}
