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

Taxonomy API's are categorized  into two,

 -  Representing GUT with user preferred/default standard framework. <a href = "https://github.com/Gooru/nucleus-handlers-taxonomy/blob/master/api-docs/GUT-REPRESENTATION-WITH-USER-PREFER-TAXONOMY.MD" >doc</a>, click doc link to see api document.
 
 -  Simple or dummy taxonomy (There is no predefined hierarchy  levels and stored taxonomy data  has not necessarily  to be mapped with GUT representation). <a href = "https://github.com/Gooru/nucleus-handlers-taxonomy/blob/master/api-docs/TAXONOMY.MD" >doc</a>, click doc link to see api document.


To understand build related stuff, take a look at **BUILD_README.md**.


