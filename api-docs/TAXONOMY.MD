Features
--------------

-  Fetch taxonomy subjects.
-  Fetch taxonomy courses.
-  Fetch taxonomy domains.
-  Fetch taxonomy codes.

###Fetch taxonomy subjects.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/subjects |
| Auth | Required |
| Request Body Type | None |
| Request Body  | None |
| Response Code | 200 Successful |

**Request Param**

| Name | comment |
|------------------------|--------|
| classification_type | mandatory field, accpected types are k_12, higher_education, and professional_learning |
| framework_id        | optional field |

**Authorization Header**

```
Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2
```

**Response body**

```json
{
    "subjects": [
        {
            "id": "GUF~Math",
            "title": "mathematics ",
            "description": "global mathematics ",
            "code": "GUF mathematics",
            "standard_framework_id": "GUF",
            "frameworks": [
                {
                    "standard_framework_id": "TEXS",
                    "taxonomy_subject_id": "TEXS~Math",
                    "title": "TEXS"
                },
                {
                    "standard_framework_id": "CSS",
                    "taxonomy_subject_id": "CSS~Math",
                    "title": "CSS"
                }
            ]
        },
        {
            "id": "GUF~Science",
            "title": "Science",
            "description": "Science",
            "code": "",
            "standard_framework_id": "GUF",
            "frameworks": [
                {
                    "standard_framework_id": "TEXS",
                    "taxonomy_subject_id": "TEXS~Science",
                    "title": "TEXS"
                }
            ]
        },
        {
            "id": "GUF~EA",
            "title": "English language arts ",
            "description": "",
            "code": "EA",
            "standard_framework_id": "GUF",
            "courses": [
                {
                    "id": "GUF~EA~Eng1",
                    "code": "English one",
                    "title": "English one, two, and three"
                }
            ]
        }
    ]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token 00M2EzNjhiNTg4NzA6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://127.0.0.1:8080/api/nucleus/v1/taxonomy/subjects?classification_type=k_12
```

###Fetch taxonomy courses.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/{framework-id}/courses |
| Auth | Required |
| Request Body Type | None |
| Request Body  | None |
| Response Code | 200 Successful |

**Authorization Header**

```
Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2
```

**Response body**

```json
{
    "courses": [
        {
            "id": "TEXS~Math-Math-1",
            "code": "TEXS.Math",
            "title": "Mathematics",
            "description": ""
        },
        {
            "id": "TEXS~Math~Math2",
            "code": "TEXS.Math2",
            "title": "Mathematics 2"
        }
    ]
}

```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NzM1ODIwMDI1MTo0Y2M4MWQ4ZS03MjFjLTQ5ZDItYmVmMy00M2EzNjhiNTg4NzA6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://127.0.0.1:8080/api/nucleus/v1/taxonomy/frameworks/TEXS/subjects/TEXS~Math/courses
```

###Fetch taxonomy domains.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/{framework-id}/courses/{taxonomy-course-id}/domains |
| Auth | Required |
| Request Body Type | None |
| Request Body  | None |
| Response Code | 200 Successful |

**Authorization Header**

```
Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2
```

**Response body**

```json
{
    "domains": [
        {
            "id": "TEXS~Math-Math-1~NA-F2",
            "code": "NA-F2",
            "title": "Numeric fraction 2",
            "description": ""
        },
        {
            "id": "TEXS~Math-Math-1~NA-F",
            "default_taxonomy_domain_id": "",
            "code": "NA-F ",
            "title": "Numeric fraction",
            "description": ""
        }
    ]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NzM1ODIwMDI1MTo0Y2M4MWQ4ZS03MjFjLTQ5ZDItYmVmMy00M2EzNjhiNTg4NzA6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://127.0.0.1:8080/api/nucleus/v1/taxonomy/frameworks/TEXS/subjects/TEXS~Math/courses/TEXS~Math-Math-1/domains
```

###Fetch taxonomy codes.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/{framework-id}/courses/{taxonomy-course-id}/domains/{taxonomy-domain-id}/codes |
| Auth | Required |
| Request Body Type | None |
| Request Body  | None |
| Response Code | 200 Successful |

**Authorization Header**

```
Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2
```

**Response body**

```json
{
    "codes": [
        {
            "code": "NA.F2.PHS",
            "title": "physics",
            "description": "",
            "code_type": "standard_level_0",
            "is_selectable": false
        }
    ]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NzM1ODIwMDI1MTo0Y2M4MWQ4ZS03MjFjLTQ5ZDItYmVmMy00M2EzNjhiNTg4NzA6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://127.0.0.1:8080/api/nucleus/v1/taxonomy/frameworks/TEXS/subjects/TEXS~Math/courses/TEXS~Math-Math-1/domains/TEXS~Math-Math-1~NA-F2/codes
```
