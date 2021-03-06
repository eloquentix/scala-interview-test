package eloquentix
package opentsdb
package data

import java.time.Instant
import play.api.libs.functional.syntax._
import play.api.libs.json.{ Reads, Writes }

case class Datapoint(
  timestamp: Instant,
  value: Double,
)

object Datapoint {
  implicit val readsDatapoint: Reads[Datapoint] =
    implicitly[Reads[(Long, Double)]].map {
      case (timestamp, value) =>
        val ts = Instant.ofEpochSecond(timestamp)
        Datapoint(ts, value)
    }

  implicit val writesDatapoint: Writes[Datapoint] =
    implicitly[Writes[(Long, Double)]].contramap { d =>
      d.timestamp.getEpochSecond -> d.value
    }
}
