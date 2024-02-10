# lite-process
Define a long-running or background process flow consisting of a set of service tasks (parallel/sequential) and exclusive gateways, to execute composite operations.

A lite-process is:
* a set of service tasks and exclusive gateways
* a set of service tasks, user tasks, and exclusive gateways
  
Note: Currently, I'm targeting to implement a lite-process component that can load a process definition, that consists of service tasks and gateways only. 

### Some of the use cases
* Onboarding a customer into a system, where a series of steps must be performed to onboard the customer.
  * Customer Account Creation (validate the account request --> KYC verification --> Systematic Approval)
  * Process the queued message for accepting a hotel booking   
  * Processing a loan request (validate the request --> background verification --> credit check -> amend the loan amount --> accept the load request)
  * Process the online order request (validate the order -> packing service --> shipping service --> delivery service) 

