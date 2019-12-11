package domain.webhooks

import java.util.{Date, UUID}

import play.api.libs.json.JsonConfiguration.Aux
import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration, Reads}

case class TransferStateChangeEvent(
                                     data: TSCData,
                                     subscriptionId: UUID,
                                     schemaVersion: String,
                                     sentAt: Date
                                   )

case class TSCData(resource: TSCResource, currentState: String, previousState: String, occurredAt: Date)

case class TSCResource(id: Long, profileId: Long, accountId: Long)

object TransferStateChangeEvent {

  implicit val config: Aux[Json.MacroOptions] = JsonConfiguration(SnakeCase)
  implicit val TSCResourceReads: Reads[TSCResource] = Json.reads[TSCResource]
  implicit val TSCDataReads: Reads[TSCData] = Json.reads[TSCData]
  implicit val transferStateChangeEventReads: Reads[TransferStateChangeEvent] = Json.reads[TransferStateChangeEvent]

}

case class BalanceEvent(
                         data: BalanceData,
                         subscriptionId: UUID,
                         schemaVersion: String,
                         sentAt: Date
                       )

case class BalanceData(resource: BalanceResource, amount: Double, currency: String, post_transaction_balance_amount: Double)
case class BalanceResource(id: Long, profileId: Long)

object BalanceEvent {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit val balanceResourceReads = Json.reads[BalanceResource]
  implicit val balanceDataReads = Json.reads[BalanceData]
  implicit val balanceEventReads = Json.reads[BalanceEvent]
}