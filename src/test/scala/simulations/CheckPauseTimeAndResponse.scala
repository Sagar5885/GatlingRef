package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class CheckPauseTimeAndResponse extends Simulation{

  var httpConf = http.baseUrl("https://reqres.in")
    .header("Accept", value = "application/json")
    .header(name = "content-type", value = "application/json")

  val scn = scenario("Add User")
    .exec(http("get user request")
      .get("/api/users/2")
      .check(status is 200))
    .pause(5)
    .exec(http("get user page request")
      .get("/api/users?page=2")
      .check(status.in(200, 210)))
    .pause(1,10)
    .exec(http("get user not found request")
      .get("/api/users/23")
      .check(status.not(200), status.not(500))
      .check(status is 404))
    .pause(3000.milliseconds)

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}
