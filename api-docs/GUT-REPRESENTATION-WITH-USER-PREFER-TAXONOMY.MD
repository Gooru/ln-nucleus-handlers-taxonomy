Features
-------------

-  Fetch taxonomy standard frameworks.
-  Fetch GUT (Gooru universal taxonomy)  subjects.
-  Fetch taxonomy courses  based on  user preferred  standard framework.
-  Fetch taxonomy domains  based on  user preferred  standard framework.
-  Fetch taxonomy domain standards based on  user preferred  standard framework.



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
Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2
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
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/standard-frameworks
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
Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2
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
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/subjects?classification_type=k_12
```

###Fetch taxonomy courses  based on  user preferred  standard framework.


**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/subjects/{default-subject-id}/courses |
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
    "courses": [{
        "default_course_id": "K12.LA~K12.LA.ELA3",
        "code": "CCSS.ELA.L",
        "title": "Language"
    }, {
        "default_course_id": "K12.LA~K12.LA.ELAK",
        "code": "CCSS.ELA.RF",
        "title": "Reading: Foundational Skills"
    }, {
        "default_course_id": "K12.LA~K12.LA.ELA3",
        "code": "CCSS.ELA.RI",
        "title": "Reading: Informational Text"
    }, {
        "default_course_id": "K12.LA~K12.LA.ELA3",
        "code": "CCSS.ELA.RL",
        "title": "Reading: Literature"
    }, {
        "default_course_id": "K12.LA~K12.LA.ELA1",
        "code": "CCSS.ELA.SL",
        "title": "Speaking & Listening"
    }, {
        "default_course_id": "K12.LA~K12.LA.ELA2",
        "code": "CCSS.ELA.W",
        "title": "Writing"
    }]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/subjects/K12.LA/courses
```

###Fetch taxonomy domains  based on  user preferred  standard framework.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/subjects/{default-subject-id}/courses/{default-course-id}/domains |
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
    "domains": [{
        "default_domain_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.AT",
        "code": "CCSS.ELA.L.3",
        "title": "Language"
    }, {
        "default_domain_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.AT",
        "code": "CCSS.ELA.RI.3",
        "title": "Reading: Informational Text"
    }, {
        "default_domain_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.AT",
        "code": "CCSS.ELA.RL.3",
        "title": "Reading: Literature"
    }, {
        "default_domain_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.CID",
        "code": "CCSS.ELA.W.3",
        "title": "Writing"
    }, {
        "default_domain_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.FS",
        "code": "CCSS.ELA.RF.3",
        "title": "Reading: Foundational Skills"
    }, {
        "default_domain_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.RCT",
        "code": "CCSS.ELA.SL.3",
        "title": "Speaking & Listening"
    }]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/subjects/K12.LA/courses/K12.LA~K12.LA.ELA3/domains
```

###Fetch taxonomy domain standards based on  user preferred  standard framework.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/subjects/{default-subject-id}/courses/{default-course-id}/domains/{default-domain-id}/codes |
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
    "codes": [{
        "default_code_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.FS~ELA.RF.3.VIII.4a",
        "code": "CCSS.ELA-Literacy.RF.3.4a",
        "title": "Read grade-level text with purpose and understanding.",
        "type": "standard_level_2",
        "is_selectable": true
    }, {
        "default_code_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.FS~ELA.RF.3.VII.3a",
        "code": "CCSS.ELA-Literacy.RF.3.3a",
        "title": "Identify and know the meaning of the most common prefixes and derivational suffixes.",
        "type": "standard_level_2",
        "is_selectable": true
    }, {
        "default_code_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.FS~ELA.RF.3.VII.3",
        "code": "CCSS.ELA-Literacy.RF.3.3",
        "title": "Know and apply grade-level phonics and word analysis skills in decoding words.",
        "type": "standard_level_1",
        "is_selectable": true
    }, {
        "default_code_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.FS~ELA.RF.3.VIII.4b",
        "code": "CCSS.ELA-Literacy.RF.3.4b",
        "title": "Read grade-level prose and poetry orally with accuracy, appropriate rate, and expression on successive readings.",
        "type": "standard_level_2",
        "is_selectable": true
    }, {
        "default_code_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.FS~ELA.RF.3.VII.3b",
        "code": "CCSS.ELA-Literacy.RF.3.3b",
        "title": "Decode words with common Latin suffixes.",
        "type": "standard_level_2",
        "is_selectable": true
    }, {
        "default_code_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.FS~ELA.RF.3.VIII.4",
        "code": "CCSS.ELA-Literacy.RF.3.4",
        "title": "Read with sufficient accuracy and fluency to support comprehension.",
        "type": "standard_level_1",
        "is_selectable": true
    }, {
        "default_code_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.FS~ELA.RF.3.VIII.4c",
        "code": "CCSS.ELA-Literacy.RF.3.4c",
        "title": "Use context to confirm or self-correct word recognition and understanding, rereading as necessary.",
        "type": "standard_level_2",
        "is_selectable": true
    }, {
        "default_code_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.FS~ELA.RF.3.VII.3c",
        "code": "CCSS.ELA-Literacy.RF.3.3c",
        "title": "Decode multisyllable words.",
        "type": "standard_level_2",
        "is_selectable": true
    }, {
        "default_code_id": "K12.LA~K12.LA.ELA3~K12.LA.ELA3.FS~ELA.RF.3.VII.3d",
        "code": "CCSS.ELA-Literacy.RF.3.3d",
        "title": "Read grade-appropriate irregularly spelled words.",
        "type": "standard_level_2",
        "is_selectable": true
    }]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/subjects/K12.LA/courses/K12.LA~K12.LA.ELA3/domains/K12.LA~K12.LA.ELA3~K12.LA.ELA3.FS/codes
```

