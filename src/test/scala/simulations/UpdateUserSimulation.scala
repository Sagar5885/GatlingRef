package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class UpdateUserSimulation extends Simulation {

  var httpConf = http.baseUrl("https://reqres.in")
    .header("Accept", value = "application/json")
    .header(name = "content-type", value = "application/json")

  val scn = scenario("Update User")
    .exec(http("update user request")
      .put("/api/users/2")
      .body(RawFileBody("src/test/resources/bodies/UpdateUser.json")).asJson
      .check(status.in(200 to 210)))

    .exec(http("delete user request")
      .delete("/api/users/2")
      .check(status.in(200 to 210)))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}
