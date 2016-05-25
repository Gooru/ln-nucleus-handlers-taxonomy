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
-  Fetch taxonomy code.

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


**Authorization Header**

```
Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2
```

**Response body**

```json
{
    "subjects": [{
        "id": "GDF.K12.VPA",
        "title": "Visual & Performing Arts",
        "description": null,
        "code": "GDF.K12.VPA",
        "standard_framework_id": "GDF",
        "frameworks": [{
            "standard_framework_id": "TEKS",
            "taxonomy_subject_id": "TEKS.K12.FA",
            "taxonomy_subject_title": "Fine Arts",
            "title": "Texas Essential Knowledge and Skills"
        }]
    }, {
        "id": "GDF.K12.HE",
        "title": "Health",
        "description": null,
        "code": "GDF.K12.HE",
        "standard_framework_id": "GDF",
        "frameworks": [{
            "standard_framework_id": "TEKS",
            "taxonomy_subject_id": "TEKS.K12.HE",
            "taxonomy_subject_title": "Health Education",
            "title": "Texas Essential Knowledge and Skills"
        }]
    }, {
        "id": "GDF.K12.PE",
        "title": "Physical Education",
        "description": null,
        "code": "GDF.K12.PE",
        "standard_framework_id": "GDF",
        "frameworks": [{
            "standard_framework_id": "TEKS",
            "taxonomy_subject_id": "TEKS.K12.PE",
            "taxonomy_subject_title": "Physical Education",
            "title": "Texas Essential Knowledge and Skills"
        }]
    }, {
        "id": "GDF.K12.WL",
        "title": "World Languages",
        "description": null,
        "code": "GDF.K12.WL",
        "standard_framework_id": "GDF"
    }, {
        "id": "GDF.K12.CTE",
        "title": "Career Technical Education",
        "description": null,
        "code": "GDF.K12.CTE",
        "standard_framework_id": "GDF"
    }, {
        "id": "GDF.K12.SLA",
        "title": "Spanish Language Arts",
        "description": null,
        "code": "GDF.K12.SLA",
        "standard_framework_id": "GDF"
    }, {
        "id": "GDF.K12.ELD",
        "title": "English Language Development",
        "description": null,
        "code": "GDF.K12.ELD",
        "standard_framework_id": "GDF",
        "frameworks": [{
            "standard_framework_id": "CA",
            "taxonomy_subject_id": "CA.K12.ELD",
            "taxonomy_subject_title": "English Language Development",
            "title": "California State Standards"
        }, {
            "standard_framework_id": "TEKS",
            "taxonomy_subject_id": "TEKS.K12.SPESL",
            "taxonomy_subject_title": "Spanish Language Arts and Reading and English as a Second Language",
            "title": "Texas Essential Knowledge and Skills"
        }]
    }, {
        "id": "GDF.K12.CS",
        "title": "Computer Science",
        "description": null,
        "code": "GDF.K12.CS",
        "standard_framework_id": "GDF"
    }, {
        "id": "GDF.K12.PF",
        "title": "Personal Finance",
        "description": null,
        "code": "GDF.K12.PF",
        "standard_framework_id": "GDF"
    }, {
        "id": "GDF.K12.HS",
        "title": "Habits of Success",
        "description": null,
        "code": "GDF.K12.HS",
        "standard_framework_id": "GDF"
    }, {
        "id": "GDF.K12.OT",
        "title": "Other",
        "description": null,
        "code": "GDF.K12.OT",
        "standard_framework_id": "GDF"
    }]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "AuthorizatQ2NDE3OTAyNjE3MzoyN2I1MjFkNC05Y2VlLTQ4YzUtYTUwMi0wMmVhNTgzODQzNDY6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus-qa.gooru.org/api/nucleus/v1/taxonomy/subjects?classification_type=k_12
```

###Fetch taxonomy courses.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/frameworks/{framework-id}/subjects/{taxonomy-subject-id}/courses |
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
        "id": "TEKS.K12.PE-K",
        "code": "TEKS.K12.PE-K",
        "title": "Kindergarten"
    }, {
        "id": "TEKS.K12.PE-1",
        "code": "TEKS.K12.PE-1",
        "title": "Grade 1"
    }, {
        "id": "TEKS.K12.PE-2",
        "code": "TEKS.K12.PE-2",
        "title": "Grade 2"
    }, {
        "id": "TEKS.K12.PE-3",
        "code": "TEKS.K12.PE-3",
        "title": "Grade 3"
    }, {
        "id": "TEKS.K12.PE-4",
        "code": "TEKS.K12.PE-4",
        "title": "Grade 4"
    }, {
        "id": "TEKS.K12.PE-5",
        "code": "TEKS.K12.PE-5",
        "title": "Grade 5"
    }, {
        "id": "TEKS.K12.PE-6",
        "code": "TEKS.K12.PE-6",
        "title": "Grade 6"
    }, {
        "id": "TEKS.K12.PE-7",
        "code": "TEKS.K12.PE-7",
        "title": "Grade 7"
    }, {
        "id": "TEKS.K12.PE-8",
        "code": "TEKS.K12.PE-8",
        "title": "Grade 8"
    }, {
        "id": "TEKS.K12.PE-FPF",
        "code": "TEKS.K12.PE-FPF",
        "title": "Foundations of Personal Fitness"
    }, {
        "id": "TEKS.K12.PE-AOE",
        "code": "TEKS.K12.PE-AOE",
        "title": "Adventure/Outdoor Education"
    }, {
        "id": "TEKS.K12.PE-AA",
        "code": "TEKS.K12.PE-AA",
        "title": "Aerobic Activities"
    }, {
        "id": "TEKS.K12.PE-IS",
        "code": "TEKS.K12.PE-IS",
        "title": "Individual Sports"
    }, {
        "id": "TEKS.K12.PE-TS",
        "code": "TEKS.K12.PE-TS",
        "title": "Team Sports"
    }]
}

```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ2NDE3OTAyNjE3MzoyN2I1MjFkNC05Y2VlLTQ4YzUtYTUwMi0wMmVhNTgzODQzNDY6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus-qa.gooru.org/api/nucleus/v1/taxonomy/frameworks/TEKS/subjects/TEKS.K12.PE/courses
```

###Fetch taxonomy domains.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/frameworks/{framework-id}/subjects/{taxonomy-subject-id}/courses/{taxonomy-course-id}/domains |
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
        "id": "TEKS.K12.PE-K-MOV",
        "code": "TEKS.K12.PE-K-MOV",
        "title": "Movement"
    }, {
        "id": "TEKS.K12.PE-K-PAH",
        "code": "TEKS.K12.PE-K-PAH",
        "title": "Physical activity and health"
    }, {
        "id": "TEKS.K12.PE-K-SD",
        "code": "TEKS.K12.PE-K-SD",
        "title": "Social development"
    }]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ2NDE3OTAyNjE3MzoyN2I1MjFkNC05Y2VlLTQ4YzUtYTUwMi0wMmVhNTgzODQzNDY6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus-qa.gooru.org/api/nucleus/v1/taxonomy/frameworks/TEKS/subjects/TEKS.K12.PE/courses/TEKS.K12.PE-K/domains
```

###Fetch taxonomy codes.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/frameworks/{framework-id}/subjects/{taxonomy-subject-id}/courses/{taxonomy-course-id}/domains/{taxonomy-domain-id}/codes |
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
        "id": "TEKS.K12.PE-K-MOV-01.01",
        "parent_taxonomy_code_id": "TEKS.K12.PE-K-MOV-01",
        "code": "TEKS.PE.K.1.A",
        "title": "Travel in different ways in a large group without bumping into others or falling.",
        "code_type": "standard_level_2",
        "is_selectable": false
    }, {
        "id": "TEKS.K12.PE-K-MOV-02.01",
        "parent_taxonomy_code_id": "TEKS.K12.PE-K-MOV-02",
        "code": "TEKS.PE.K.2.A",
        "title": "Identify selected body parts such as head, back, chest, waist, hips, arms, elbows, wrists, hands, fingers, legs, knees, ankles, feet, and toes.",
        "code_type": "standard_level_2",
        "is_selectable": false
    }, {
        "id": "TEKS.K12.PE-K-MOV-01",
        "code": "TEKS.PE.K.1",
        "title": "The student demonstrates competency in fundamental movement patterns and proficiency in a few specialized movement forms.",
        "code_type": "standard_level_1",
        "is_selectable": false
    }, {
        "id": "TEKS.K12.PE-K-MOV-02.02",
        "parent_taxonomy_code_id": "TEKS.K12.PE-K-MOV-02",
        "code": "TEKS.PE.K.2.B",
        "title": "Demonstrate movement forms of various body parts such as head flexion, extension, and rotation.",
        "code_type": "standard_level_2",
        "is_selectable": false
    }, {
        "id": "TEKS.K12.PE-K-MOV-01.02",
        "parent_taxonomy_code_id": "TEKS.K12.PE-K-MOV-01",
        "code": "TEKS.PE.K.1.B",
        "title": "Demonstrate clear contrasts between slow and fast movement when traveling.",
        "code_type": "standard_level_2",
        "is_selectable": false
    }, {
        "id": "TEKS.K12.PE-K-MOV-02",
        "code": "TEKS.PE.K.2",
        "title": "The student applies movement concepts and principles to the learning and development of motor skills.",
        "code_type": "standard_level_1",
        "is_selectable": false
    }, {
        "id": "TEKS.K12.PE-K-MOV-01.03",
        "parent_taxonomy_code_id": "TEKS.K12.PE-K-MOV-01",
        "code": "TEKS.PE.K.1.C",
        "title": "Demonstrate non-locomotor (axial) movements such as bend and stretch.",
        "code_type": "standard_level_2",
        "is_selectable": false
    }, {
        "id": "TEKS.K12.PE-K-MOV-01.04",
        "parent_taxonomy_code_id": "TEKS.K12.PE-K-MOV-01",
        "code": "TEKS.PE.K.1.D",
        "title": "Maintain balance while bearing weight on a variety of body parts.",
        "code_type": "standard_level_2",
        "is_selectable": false
    }, {
        "id": "TEKS.K12.PE-K-MOV-01.05",
        "parent_taxonomy_code_id": "TEKS.K12.PE-K-MOV-01",
        "code": "TEKS.PE.K.1.E",
        "title": "Walk forward and sideways the length of a beam without falling.",
        "code_type": "standard_level_2",
        "is_selectable": false
    }, {
        "id": "TEKS.K12.PE-K-MOV-01.06",
        "parent_taxonomy_code_id": "TEKS.K12.PE-K-MOV-01",
        "code": "TEKS.PE.K.1.F",
        "title": "Demonstrate a variety of relationships such as under, over, behind, next to, through, right, left, up, down, forward, backward, and in front of.",
        "code_type": "standard_level_2",
        "is_selectable": false
    }, {
        "id": "TEKS.K12.PE-K-MOV-01.07",
        "parent_taxonomy_code_id": "TEKS.K12.PE-K-MOV-01",
        "code": "TEKS.PE.K.1.G",
        "title": "Roll sideways (right or left) without hesitating.",
        "code_type": "standard_level_2",
        "is_selectable": false
    }, {
        "id": "TEKS.K12.PE-K-MOV-01.08",
        "parent_taxonomy_code_id": "TEKS.K12.PE-K-MOV-01",
        "code": "TEKS.PE.K.1.H",
        "title": "Toss a ball and catch it before it bounces twice.",
        "code_type": "standard_level_2",
        "is_selectable": false
    }]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ2NDE3OTAyNjE3MzoyN2I1MjFkNC05Y2VlLTQ4YzUtYTUwMi0wMmVhNTgzODQzNDY6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus-qa.gooru.org/api/nucleus/v1/taxonomy/frameworks/TEKS/subjects/TEKS.K12.PE/courses/TEKS.K12.PE-K/domains/TEKS.K12.PE-K-MOV/codes
```

###Fetch taxonomy code

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/codes/{taxonomy-code-id} |
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
    "id": "TEKS.K12.PE-K-MOV-01.01",
    "parent_taxonomy_code_id": "TEKS.K12.PE-K-MOV-01",
    "code": "TEKS.PE.K.1.A",
    "title": "Travel in different ways in a large group without bumping into others or falling.",
    "code_type": "standard_level_2",
    "is_selectable": false
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ2NDE3OTAyNjE3MzoyN2I1MjFkNC05Y2VlLTQ4YzUtYTUwMi0wMmVhNTgzODQzNDY6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus-qa.gooru.org/api/nucleus/v1/taxonomy/codes/TEKS.K12.PE-K-MOV-01.01
```
