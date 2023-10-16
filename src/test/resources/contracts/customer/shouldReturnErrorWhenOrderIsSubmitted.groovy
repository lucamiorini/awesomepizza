package contracts.customer

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Returns error when submitting"
    request {
        method POST()
        url '/v1/order/'
        headers {
            contentType(applicationJson())
        }
        body([
                "username"       : value(producer("test"), consumer(anyNonEmptyString())),
                "pizzaType"      : "invalid",
                "shippingAddress": value(producer(anyNonEmptyString()), consumer('testAddress'))
        ])
    }
    response {
        headers {
            contentType(applicationJson())
        }
        body([
                "message"   : value(producer(anyNonEmptyString()), consumer("Invalid type")),
                "statusCode": 400,
                "timestamp" : anyIso8601WithOffset()
        ])
        status 400
    }
}