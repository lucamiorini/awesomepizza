package contracts.admin

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return the list of the queued orders"
    request {
        method GET()
        headers {
            header(authorization(), "Basic YWRtaW46YWRtaW4=")
        }
        url '/v1/admin/order/all'
    }
    response {
        headers {
            contentType(applicationJson())
        }
        body([
                "orderNumber": value(producer(anyInteger()), consumer(1)),
                "username": value(producer(anyNonEmptyString()), consumer('testUser')),
                "shippingAddress": value(producer(anyNonEmptyString()), consumer('testAddress')),
                "status":value(producer($(regex('SUBMITTED|PROCESSING'))), consumer('SUBMITTED'))
        ])
        status 200
    }
}