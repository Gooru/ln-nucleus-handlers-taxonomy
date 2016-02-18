Features
--------------

-  Fetch simple/dummy taxonomy  root level codes.
-  Fetch simple/dummy taxonomy  sub-tree levels codes.

###Fetch simple/dummy taxonomy  root level codes.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/standard-frameworks/{standard-framework-id}/codes |
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
        "id": "TEKS~TEKS.ELA",
        "code": "TEKS.ELA",
        "title": "English Language Arts and Reading",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.M",
        "code": "TEKS.M",
        "title": "Math",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.S",
        "code": "TEKS.S",
        "title": "Science",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.SS",
        "code": "TEKS.SS",
        "title": "Social Studies",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.HE",
        "code": "TEKS.HE",
        "title": "Health Education",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.PE",
        "code": "TEKS.PE",
        "title": "Physical Education",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.FA",
        "code": "TEKS.FA",
        "title": "Fine Arts",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.SPESL",
        "code": "TEKS.SPESL",
        "title": "Spanish Language Arts and Reading and English as a Second Language",
        "is_selectable": false
    }]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/standard-frameworks/TEKS/codes
```

###Fetch simple/dummy taxonomy  sub-tree levels codes.

**Rest API details**

| Name | Summary |
|------------------------|--------|
| HTTP Method | GET |
| End Point | /api/nucleus/{version}/taxonomy/{standard-framework-id}/codes/{code-id}/codes |
| Auth | Required |
| Request Body Type | None |
| Request Body  | None |
| Response Code | 200 Successful |

**Note**

   Use this API to get the all child codes based on the code-id.

**Authorization Header**

```
Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2
```

**Response body**

```json
{
    "codes": [{
        "id": "TEKS~TEKS.ELA~TEKS.ELA.K",
        "code": "TEKS.ELA.K",
        "title": "Kindergarten",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.1",
        "code": "TEKS.ELA.1",
        "title": "Grade 1",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.2",
        "code": "TEKS.ELA.2",
        "title": "Grade 2",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.3",
        "code": "TEKS.ELA.3",
        "title": "Grade 3",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.4",
        "code": "TEKS.ELA.4",
        "title": "Grade 4",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.5",
        "code": "TEKS.ELA.5",
        "title": "Grade 5",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.6",
        "code": "TEKS.ELA.6",
        "title": "Grade 6",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.7",
        "code": "TEKS.ELA.7",
        "title": "Grade 7",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.8",
        "code": "TEKS.ELA.8",
        "title": "Grade 8",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.R",
        "code": "TEKS.ELA.R",
        "title": "Reading",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.S",
        "code": "TEKS.ELA.S",
        "title": "Speech",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.E1",
        "code": "TEKS.ELA.E1",
        "title": "English I",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.E2",
        "code": "TEKS.ELA.E2",
        "title": "English II",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.E3",
        "code": "TEKS.ELA.E3",
        "title": "English III",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.E4",
        "code": "TEKS.ELA.E4",
        "title": "English IV",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.IS",
        "code": "TEKS.ELA.IS",
        "title": "Independent Study in English",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.RHS",
        "code": "TEKS.ELA.RHS",
        "title": "Reading I, II, III",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.CRSS",
        "code": "TEKS.ELA.CRSS",
        "title": "College Readiness and Study Skills",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.VMAP",
        "code": "TEKS.ELA.VMAP",
        "title": "Visual Media Analysis and Production",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.CM",
        "code": "TEKS.ELA.CM",
        "title": "Contemporary Media",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.LG",
        "code": "TEKS.ELA.LG",
        "title": "Literary Genres",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.CR",
        "code": "TEKS.ELA.CR",
        "title": "Creative Writing",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.RTW",
        "code": "TEKS.ELA.RTW",
        "title": "Research and Technical Writing",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.PWS",
        "code": "TEKS.ELA.PWS",
        "title": "Practical Writing Skills",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.H",
        "code": "TEKS.ELA.H",
        "title": "Humanities",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.PS",
        "code": "TEKS.ELA.PS",
        "title": "Public Speaking I, II, III",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.CA",
        "code": "TEKS.ELA.CA",
        "title": "Communication Applications ",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.OI",
        "code": "TEKS.ELA.OI",
        "title": "Oral Interpretation I, II, III",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.D",
        "code": "TEKS.ELA.D",
        "title": "Debate I, II, III",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.ISS",
        "code": "TEKS.ELA.ISS",
        "title": "Independent Study in Speech ",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.J",
        "code": "TEKS.ELA.J",
        "title": "Journalism",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.ISJ",
        "code": "TEKS.ELA.ISJ",
        "title": "Independent Study in Journalism",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.ABJ",
        "code": "TEKS.ELA.ABJ",
        "title": "Advanced Broadcast Journalism I, II, III",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.PJ",
        "code": "TEKS.ELA.PJ",
        "title": "Photojournalism ",
        "is_selectable": false
    }, {
        "id": "TEKS~TEKS.ELA~TEKS.ELA.AJ",
        "code": "TEKS.ELA.AJ",
        "title": "Advanced Journalism: Yearbook I, II, III/Newspaper I, II, III/Literary Magazine",
        "is_selectable": false
    }]
}
```

**CURL snippet**

```posh
curl -i  -H "Content-Type: application/json" -H "Authorization: Token MTQ1NTc5OTI3NDU0Mzphbm9ueW1vdXM6YmE5NTZhOTctYWUxNS0xMWU1LWEzMDItZjhhOTYzMDY1OTc2" -X GET http://nucleus.gooru.org/api/nucleus/v1/taxonomy/standard-frameworks/TEKS/codes/TEKS~TEKS.ELA/codes
```

