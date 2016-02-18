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
Authorization: Token ODA4ZDRiZTItNDFjOC00ZTdhLThlNzEtMDQyNTZlNTBkN2IzOk1vbiBGZWIgMTUgMTE6NDA6NTQgSVNUIDIwMTY6MTQ1NTUxNjY1NDIxMg==
```

**Response body**

```json
{
    "standard_frameworks": [{
        "id": "B21",
        "code": "B21",
        "title": "Learning What Matters Competency Sets"
    }, {
        "id": "C3",
        "code": "C3",
        "title": "C3 Framework for Social Studies"
    }, {
        "id": "CAELD",
        "code": "CAELD",
        "title": "California English Language Development Standards"
    }, {
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
curl -i  -H "Content-Type: application/json" -H "Authorization: Token ODA4ZDRiZTItNDFjOC00ZTdhLThlNzEtMDQyNTZlNTBkN2IzOk1vbiBGZWIgMTUgMTE6NDA6NTQgSVNUIDIwMTY6MTQ1NTUxNjY1NDIxMg==" -X GET http://127.0.0.1:8080/api/nucleus/v1/taxonomy/standard-frameworks
```

###Fetch GUT (Gooru universal taxonomy)  subjects.

Get the GUT subjects(default subjects) without  applying  user preferred standard framework transformation. 


**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/subjects |
| Auth | Required |
| Request Body Type | None |
| Request Body  | None |
| Response Code | 200 Successful |

**Request parameters**

| Name | Comment |
|--------|--------|
| classification_type | types (k_12, higher_education, professional_learning) |

**Authorization Header**

```
Authorization: Token ODA4ZDRiZTItNDFjOC00ZTdhLThlNzEtMDQyNTZlNTBkN2IzOk1vbiBGZWIgMTUgMTE6NDA6NTQgSVNUIDIwMTY6MTQ1NTUxNjY1NDIxMg==
```

**Response body**

```json
{
    "subjects": [{
        "id": "K12.SC",
        "title": "Science"
    }, {
        "id": "K12.MA",
        "title": "Math"
    }, {
        "id": "K12.SS",
        "title": "Social Sciences"
    }, {
        "id": "K12.LA",
        "title": "Language Arts"
    }, {
        "id": "K12.WL",
        "title": "World Languages"
    }, {
        "id": "K12.VPA",
        "title": "Visual & Performing Arts"
    }, {
        "id": "K12.HP",
        "title": "Health & Physical Education"
    }, {
        "id": "K12.CTE",
        "title": "Career Technical Education"
    }, {
        "id": "K12.OT",
        "title": "Other"
    }]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token ODA4ZDRiZTItNDFjOC00ZTdhLThlNzEtMDQyNTZlNTBkN2IzOk1vbiBGZWIgMTUgMTE6NDA6NTQgSVNUIDIwMTY6MTQ1NTUxNjY1NDIxMg==" -X GET http://127.0.0.1:8080/api/nucleus/v1/taxonomy/subjects?classification_type=k_12
```

###Fetch taxonomy courses  based on  user preferred  standard framework.


**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/subjects/ |
| Auth | Required |
| Request Body Type | None |
| Request Body  | None |
| Response Code | 200 Successful |


**Authorization Header**

```
Authorization: Token ODA4ZDRiZTItNDFjOC00ZTdhLThlNzEtMDQyNTZlNTBkN2IzOk1vbiBGZWIgMTUgMTE6NDA6NTQgSVNUIDIwMTY6MTQ1NTUxNjY1NDIxMg==
```

**Response body**

```json
{
    "subjects": [{
        "id": "K12.SC",
        "title": "Science"
    }, {
        "id": "K12.MA",
        "title": "Math"
    }, {
        "id": "K12.SS",
        "title": "Social Sciences"
    }, {
        "id": "K12.LA",
        "title": "Language Arts"
    }, {
        "id": "K12.WL",
        "title": "World Languages"
    }, {
        "id": "K12.VPA",
        "title": "Visual & Performing Arts"
    }, {
        "id": "K12.HP",
        "title": "Health & Physical Education"
    }, {
        "id": "K12.CTE",
        "title": "Career Technical Education"
    }, {
        "id": "K12.OT",
        "title": "Other"
    }]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token ODA4ZDRiZTItNDFjOC00ZTdhLThlNzEtMDQyNTZlNTBkN2IzOk1vbiBGZWIgMTUgMTE6NDA6NTQgSVNUIDIwMTY6MTQ1NTUxNjY1NDIxMg==" -X GET http://127.0.0.1:8080/api/nucleus/v1/taxonomy/subjects?classification_type=k_12
```


