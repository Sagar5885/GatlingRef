package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseAndGetData extends Simulation {

  var httpConf = http.baseUrl("https://gorest.co.in/")
    .header("Authorization", value = "Bearer 66aef05d4f102ea1d1d68dcc90c4aa131df8adbcc6cc9d1034fcef55f0644dde")

  val scn = scenario("Get All Users")
    .exec(http("get users request")
      .get("public/v2/users")
      .check(status.in(200 to 210))
      .check(jsonPath("$[0].id").saveAs("userId")))

    .exec(http("get specific user request")
      .get("public/v2/users/${userId}")
      .check(status.in(200 to 210))
      .check(jsonPath("$.id").is("2677595"))
      .check(jsonPath("$.name").is("Umang Gupta"))
      .check(jsonPath("$.email").is("gupta_umang@mann.example")))


  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)

}
