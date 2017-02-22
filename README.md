Nucleus Taxonomy
================

This is the Taxonomy handler for Project Nucleus. 

This project contains just one main verticle which is responsible for listening for taxonomy address on message bus. 

DONE
----
* Configured listener
* Provided a initializer and finalizer mechanism for components to initialize and clean up themselves
* Created a data source registry and register it as component for initialization and finalization
* Provided Hikari connection pool from data source registry
* Processor layer is created which is going to take over the message processing from main verticle once message is read
* Logging and app configuration
* Transactional layer to govern the transaction
* DB layer to actually do the operations
* Transformer and/or writer layer so that output from DB layer could be transformed and written back to message bus
* Decide on using plain JDBC or light weight ORM like ActiveJDBC
* Implemented taxonomy API's 

## Taxonomy API's

 -  Simple or dummy taxonomy (There is no predefined hierarchy  levels and stored taxonomy data  has not necessarily  to be mapped with GUT representation). <a href = "https://github.com/Gooru/nucleus-handlers-taxonomy/blob/master/api-docs/TAXONOMY.MD" >doc</a>, click doc link to see api document.

## Taxonomy Transformation API

Transformation is an act where given the packet for taxonomy for a content and standards preferences for the user, the system would be able to transform the packet into user defined standards preference. 

Note that this API would be internal and will be used internally by Core components to do the transformation. This won’t be exposed to outside world. To start with we shall not expose any API even internally. The components of core cluster will directly utilize the message bus to send this information to taxonomy handler and upon receiving the response, they will send out response to callers.

This API have two parameters as  input one is user preferred  subject based framework and  content  standards or micro-standards JSON  packed. The sample JSON packet will look like below.

#### Sample  user’s standard preferences JSON packet

```json
{
    "K12.MA": "C3",
    "K12.LA": "CCSS",
    "K12.SC": "CCSS"
}

```

#### Sample  taxonomy JSON   packet

```json
{
    "33edd417-b652-4701-a1d0-7ef5d04c60eb": {
        "CA.K12.SC-6-E-05.04": {
            "code": "CA.SCI.6.LS.5d",
            "title": "Students know different kinds of organisms may play similar ecological roles in similar biomes.",
            "parent_title": "Science",
            "framework_code": "CA"
        },
        "CA.K12.SC-7-EV-03.05": {
            "code": "CA.SCI.7.LS.3e",
            "title": "Students know that extinction of a species occurs when the environment changes and the adaptive characteristics of a species are insufficient for its survival.",
            "parent_title": "Science",
            "framework_code": "CA"
        }
    }
}

```

Above sample json packet were  key  is content id and value is taxonomy JSON packet of content. It can have mutiple key value pair data.

To understand build related stuff, take a look at **BUILD_README.md**.


