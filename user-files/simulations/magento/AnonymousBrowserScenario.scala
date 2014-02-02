package magento

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.http.Headers.Names._
import scala.concurrent.duration._
import bootstrap._
import assertions._
import Headers._

object AnonymousBrowserScenario {

    var feedCategoriesURL = csv("magento_category_urls.csv")
    var feedProductsURL = csv("magento_product_urls.csv")

    val homepage =
        exec(http("Get Homepage")
                .get("/")
                .headers(headers_1)
            )
        .pause(2)

    val categories =
        feed(feedCategoriesURL.random)
            .repeat(3) {
                exec(http("Get Catalog Page")
                    .get("${magento_category_url}")
                    .headers(headers_1a)
                )
                .pause(1)
            }
            .pause(2)

    val products =
        feed(feedProductsURL.random)
            .exec(http("Get Product Page")
                .get("${magento_product_url}")
                .headers(headers_1a)
            )


    val scn = scenario("Anonymous Browser")
        .exec( homepage, categories, products )
        
}