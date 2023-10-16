package contracts.customer

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Returns the number of the new submitted order"
    request {
        method POST()
        url '/v1/order/'
        headers {
            contentType(applicationJson())
        }
        body([
                "username"       : value(producer("test"), consumer(anyNonEmptyString())),
                "pizzaType"      : value(producer("MARGHERITA"), consumer($(regex('(MARGHERITA|NAPOLI|DIAVOLA)')))),
                "shippingAddress": value(producer(anyAlphaNumeric()), consumer('testAddress'))
        ])
    }
    response {
        headers {
            contentType(applicationJson())
        }
        body(
                "orderNumber": value(producer($(anyInteger())), consumer(1)),
                "status": 'SUBMITTED'
        )
        status 200
    }
}