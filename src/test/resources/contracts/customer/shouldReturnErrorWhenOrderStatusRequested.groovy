package contracts.customer

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return order status"
    request {
        method GET()
        url '/v1/order/-1'
    }
    response {
        headers {
            contentType(applicationJson())
        }
        body([
                "message": value(producer(anyNonBlankString()), consumer("Order with number -1 not found"))
        ])
        status 404
    }
}