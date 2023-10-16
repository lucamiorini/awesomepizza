package contracts.customer

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return order status"
    request {
        method GET()
        url $(producer('/v1/order/1'), consumer(regex('/v1/order/[\\d]')))
    }
    response {
        headers {
            contentType(applicationJson())
        }
        body([
                "pizzaType"      : value(producer($(regex('(MARGHERITA|NAPOLI|DIAVOLA)'))), consumer("MARGHERITA")),
                "shippingAddress": value(producer(anyNonEmptyString()), consumer('testAddress')),
                "status"          : value(producer($(regex('SUBMITTED|PROCESSING|DELIVERED'))), consumer('SUBMITTED'))
        ])
        status 200
    }
}