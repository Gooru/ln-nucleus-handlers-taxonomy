Taxonomy and APIs
===================

This information is relevant as May 25, 2016. Depending on when you are reading it, it may be outdated.

----------


Taxonomy Tree
-------------

Taxonomy tree consists of a tree like structure with following nodes in the specified order:
> - Subject
> - Course
> - Domain
> - Standards
> - Learning Targets/Micro Standards

The taxonomy tree need not be complete, it may end prematurely also. For example, as of today the taxonomy tree may end at Domain or at Standards.

Taxonomy tree is framework specific. There are different frameworks like CCSS, NGSS, TEKS etc which may support one of more subjects.

Taxonomy Storage
-----------
The way Taxonomy is stored is to have a different tree for each subject and framework combination. Note that one subject can have more than one framework supported, and vice versa. But all subjects may not have all the frameworks. The combination of subject and framework thus acts as a root for Taxonomy tree. Once the root is obtained, the tree traversal is simple. From Rest API perspective, it means use the framework and id of current node and query for next nodes.

> **Note**
> This may not be exactly true for last two nodes which are Standards and Learning Targets. One API call will provide both. 

With this model, there is one catch. One who wishes to traverse the Taxonomy tree need to have subject id and framework id. However, when traversal is starting, it may not be available. For this purpose, there is concept of Universal Subjects. Universal subjects are list of subjects which act as root of internal tree. Universal subjects are not associated with any framework and hence they don't change when framework changes. They are the real lookup of the subject, kind of master list. When one item from this master list is associated with framework, then it becomes framework specific subject and acts as root node for that Subject Framework specific Taxonomy tree.

Taxonomy Id Interpretation
----------
All the nodes in Taxonomy tree are separated with hyphens, in the format Subject-Course-Domain-Standard-Microstandard. Thus breaking the string id with hyphen as delimiter may provide an idea about the whole tree itself and the location of this node in tree (e.g. if there is no hyphen in string, it is subject id, one hyphen would mean it is course id etc).

Interestingly, the node itself may encapsulate more information. If this is the case, then for that node, information is encoded using dot as separator. Currently subject token would encode framework, category/classification and subject itself. Also, the standards, would encode level 0, level 1 and level 2 standards.

> For example, here is how to interpret the CCSS.K12.MA-5-MD-C.03.02-01

> - Subject = CCSS.K12.MA
> --- (framework = CCSS, subject classification = K12, subject = MA)
> - Course = 5
> - Domain = MD
> - Standard = C.03.02
> --- (L0 standard = C, L1 standard = 03, L2 standard = 02)
> - Microstandard (learning target) = 01

Taxonomy Tag Association with Content
---------
The definition of Taxonomy APIs are located [here](https://github.com/Gooru/nucleus-handlers-taxonomy/blob/master/api-docs/TAXONOMY.MD).

Following steps need to be done in order to apply a Taxonomy Tag to content.

* Get the list of Universal Subject
	* The API is located [here](https://github.com/Gooru/nucleus-handlers-taxonomy/blob/master/api-docs/TAXONOMY.MD#fetch-taxonomy-subjects)
	* Do not pass in framework id
	* The response would contain Universal Subjects along with the framework it supports
* User can then select one of Universal Subject and one of the framework which is supported by this Subject
* Use the subject id and framework id to query for Courses with [this](https://github.com/Gooru/nucleus-handlers-taxonomy/blob/master/api-docs/TAXONOMY.MD#fetch-taxonomy-courses) API 
* When user selects a course, then use the subject id, course id and framework id to query for domains associated with that domain using [this](https://github.com/Gooru/nucleus-handlers-taxonomy/blob/master/api-docs/TAXONOMY.MD#fetch-taxonomy-domains) API
* When user selects a domain, then use subject id, course id, domain id and framework id to get list of all standards and micro standards. The hierarchy of standards and micro standards can be established using the parent id field


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
| classification_type | mandatory field, accepted types are k_12, higher_education, and professional_learning |
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
curl -i  -H "Content-Type: application/json" -H "Authorization: Token 00M2EzNjhiNTg4NzA6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/subjects?classification_type=k_12
```

###Fetch taxonomy courses.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/frameworks/{framework-id}/courses |
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
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NzM1ODIwMDI1MTo0Y2M4MWQ4ZS03MjFjLTQ5ZDItYmVmMy00M2EzNjhiNTg4NzA6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/frameworks/TEXS/subjects/TEXS~Math/courses
```

###Fetch taxonomy domains.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/frameworks/{framework-id}/courses/{taxonomy-course-id}/domains |
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
            "id": "TEXS~Math~Math-1~NA-F2",
            "code": "NA-F2",
            "title": "Numeric fraction 2",
            "description": ""
        },
        {
            "id": "TEXS~Math~Math-1~NA-F",
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
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NzM1ODIwMDI1MTo0Y2M4MWQ4ZS03MjFjLTQ5ZDItYmVmMy00M2EzNjhiNTg4NzA6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/frameworks/TEXS/subjects/TEXS~Math/courses/TEXS~Math-Math-1/domains
```

###Fetch taxonomy codes.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/frameworks/{framework-id}/courses/{taxonomy-course-id}/domains/{taxonomy-domain-id}/codes |
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
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NzM1ODIwMDI1MTo0Y2M4MWQ4ZS03MjFjLTQ5ZDItYmVmMy00M2EzNjhiNTg4NzA6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/frameworks/TEXS/subjects/TEXS~Math/courses/TEXS~Math-Math-1/domains/TEXS~Math-Math-1~NA-F2/codes
```
