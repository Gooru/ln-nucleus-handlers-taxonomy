Features
-------------

-  Fetch taxonomy standard frameworks.
-  Fetch GUT (Gooru universal taxonomy)  subjects.
-  Fetch taxonomy courses  based on  user preferred  standard framework.
-  Fetch taxonomy domains  based on  user preferred  standard framework.
-  Fetch taxonomy domain standards based on  user preferred  standard framework.
-  Fetch simple/dummy taxonomy  root level codes.
-  Fetch simple/dummy taxonomy  sub-tree level codes.


###Fetch taxonomy standard frameworks

Get the standards frameworks registered in the system that are not part of the Gooru taxonomy

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/standard-frameworks |
| Auth | Required |
| Request Body Type | None |
| Request Body  | None |
| Response Code | 200 Successful |


**Authorization Header**

```
Authorization: Token YWFkYTZhYmMtZWIxMS00NTUwLTk4MWYtM2EzNmE1M2I0OTA3OldlZCBKYW4gMjAgMTE6NDM6MzEgSVNUIDIwMTY6MTQ1MzI3MDQxMTkxNA==
```

**Response body**

```json
{
    "standard_frameworks": [{
        "id": "CCSS",
        "code": "CCSS",
        "title": "Common Core State Standards"
    }, {
        "id": "NGSS",
        "code": "NGSS",
        "title": "Next Generation Science Standards"
    }, {
        "id": "TEKS",
        "code": "TEKS",
        "title": "Texas Essential Knowledge and Skills"
    }]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token YWFkYTZhYmMtZWIxMS00NTUwLTk4MWYtM2EzNmE1M2I0OTA3OlRodSBKYW4gMjEgMTQ6MjA6NDcgSVNUIDIwMTY6MTQ1MzM2NjI0NzMzNg==" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/standard-frameworks
```
  

