# lite-process
Define a long-running or background process flow consisting of a set of service tasks (parallel/sequential) and exclusive gateways, to execute composite operations.

A lite-process is:
* a set of service tasks and exclusive gateways
* a set of service tasks, user tasks, and exclusive gateways
  
Note: Currently, I'm targeting to implement a lite-process component that can load a process definition, that consists of service tasks and gateways only. 

### Some use cases
* Onboarding a customer into a system, where a series of steps must be performed to onboard the customer.
* Customer Account Creation (validate the account request --> KYC verification --> Systematic Approval)
* Process a queued message for accepting a hotel booking   
* Process a loan request (validate the request --> background verification --> credit check -> amend the loan amount --> accept the load request)
* Process an online order request (validate the order -> packing service --> shipping service --> delivery service) 

### An example lite-process configuration
```json
{
  "name": "order-process model",
  "key": "orderProcess",
  "version": 1,
  "async": true,
  "serviceTasks": [
    {
      "executionOrder": 1,
      "name": "validateOrder",
      "handler": "validateOrder",
      "mandatoryVariables": [
        "orderId","orderStatus","orderItems"
      ],
      "exclusiveGateway":
        {
            "name": "is valid order",
            "expression": "outboundVariables['orderStatus'] == 'CONFIRMED'",
            "onTrue": "packingService",
            "onFalse": "pendingQueue"
        }
    },
    {
      "executionOrder": 2,
      "name": "packingService",
      "handler": "packingService",
      "mandatoryVariables": [
        "orderId","orderStatus","orderItems"
      ],
      "exclusiveGateway":
        {
          "name": "is packing completed",
          "expression": "outboundVariables['orderStatus'] == 'ReadyToShip'",
          "onTrue": "shippingService",
          "onFalse": "pendingQueue"
        }
    },
    {
      "executionOrder": 3,
      "name": "shippingService",
      "handler": "shippingService",
      "mandatoryVariables": [
        "orderId","orderStatus","orderItems", "shippingAddress"
      ],
      "exclusiveGateway":
        {
          "name": "is shipping completed",
          "expression": "outboundVariables['orderStatus'] == 'ReadyToDelivery'",
          "onTrue": "deliveryService",
          "onFalse": "pendingQueue"
        }
    },
    {
      "executionOrder": 4,
      "name": "deliveryService",
      "handler": "deliveryService",
      "mandatoryVariables": [
        "orderId","orderStatus","orderItems", "deliveryAddress"
      ],
      "nextTask": "_end"
    }
  ]
}
```
