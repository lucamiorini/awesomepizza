package contracts.admin

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return the next order to prepare"
    request {
        method GET()
        headers {
            header(authorization(), "Basic YWRtaW46YWRtaW4=")
        }
        url '/v1/admin/order/'
    }
    response {
        headers {
            contentType(applicationJson())
        }
        body(
                "orderNumber": value(producer(anyInteger()), consumer(1)),
                "username": value(producer(anyNonEmptyString()), consumer('testUser')),
                "shippingAddress": value(producer(anyNonEmptyString()), consumer('testAddress')),
                "pizzaType": value(producer($(regex('(MARGHERITA|NAPOLI|DIAVOLA)'))), consumer("MARGHERITA"))
        )
        status 200
    }
}