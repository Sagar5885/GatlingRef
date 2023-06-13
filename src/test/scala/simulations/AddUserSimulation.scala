package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._


class AddUserSimulation extends Simulation {

  var httpConf = http.baseUrl("https://reqres.in")
    .header("Accept", value = "application/json")
    .header(name = "content-type", value = "application/json")

  val scn = scenario("Add User")
    .exec(http("add user request")
      .post("/api/users")
      .body(RawFileBody("src/test/resources/bodies/AddUser.json")).asJson
      .check(status is 201))
    .pause(3)
    .exec(http("get user request")
      .get("/api/users/2")
      .check(status is 200))
    .pause(2)
    .exec(http("get user page request")
      .get("/api/users?page=2")
      .check(status is 200))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}
