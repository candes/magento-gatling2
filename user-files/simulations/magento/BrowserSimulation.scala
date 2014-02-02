package magento

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.http.Headers.Names._
import scala.concurrent.duration._
import assertions._

class BrowserSimulation extends Simulation {
    
    val defaultUrlBase = "http://127.0.0.1/magento-git-clone"
    val userOption: Int = Integer.getInteger("users", 10).toInt
    val timeOption: Int = Integer.getInteger("time", 10).toInt
    
    val httpProtocol = http
        .baseURL(defaultUrlBase)
        .acceptCharsetHeader("ISO-8859-1,utf-8;q=0.7,*;q=0.7")
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("en-us;q=0.9,en;q=0.3")
        // .disableFollowRedirect

    setUp(AnonymousBrowserScenario.scn.inject(ramp(userOption) over (timeOption)))
        .protocols(httpProtocol)
		.assertions(
            global.successfulRequests.percent.is(100),
            global.responseTime.mean.lessThan(1000)
        )
}