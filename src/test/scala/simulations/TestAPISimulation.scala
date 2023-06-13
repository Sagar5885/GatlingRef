package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TestAPISimulation extends Simulation{

  //http conf
  val httpRef = http.baseUrl("https://reqres.in")
    .header("Accept", value = "application/json")
    .header(name = "content-type", value = "application/json")

  //scenario
  val scn = scenario("Get User")
    .exec(http("get user request")
      .get("/api/users?page=2")
      .check(status is 200))

  //setup
  setUp(scn.inject(atOnceUsers(10))).protocols(httpRef)
}