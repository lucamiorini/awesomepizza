package contracts.admin

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should update the status of the order"
    request {
        method PUT()
        headers {
            header(authorization(), "Basic YWRtaW46YWRtaW4=")
        }
        url $(producer('/v1/admin/order/1/PROCESSING'), consumer(regex('/v1/admin/order/[\\d]/(PROCESSING|DELIVERED)')))
    }
    response {
        status 200
    }
}