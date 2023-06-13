package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FeederDemo extends Simulation {

  var httpConf = http.baseUrl("https://computer-database.gatling.io")
    .header("Accept", value = "application/json")
    .header(name = "content-type", value = "application/json")

  val feeder = csv("data/data.csv").circular

  val scen = scenario("Feeder demo")
    .repeat(1) {
      feed(feeder)
        .exec { session =>
          println("Name: " + session("name").as[String])
          println("Job: " + session("job").as[String])
          println("Page: " + session("page").as[String])
          session
        }
        .pause(1)
        .exec(http("Go to ${page}")
          .get("/#{page}")
        )
    }

  setUp(scen.inject(atOnceUsers(3))).protocols(httpConf)
}
