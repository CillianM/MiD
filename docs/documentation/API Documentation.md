
# MiD Api Documentation

* [Overview](#overview)
    + [URI scheme](#uri-scheme)
    +  [Security Policy](#security-policy)
  * [Resources](#resources)
    + [Certificate-controller](#certificate-controller)
      - [getCertificate](#getcertificate)
      - [deleteCertificate](#deletecertificate)
    + [Identity-type-controller](#identity-type-controller)
      - [createIdentityType](#createidentitytype)
      - [getIdentityTypes](#getidentitytypes)
      - [getIdentityTypeFields](#getidentitytypefields)
      - [getPartyIdentityTypes](#getpartyidentitytypes)
      - [getIdentityType](#getidentitytype)
      - [updateIdentityType](#updateidentitytype)
      - [deleteIdentityType](#deleteidentitytype)
    + [Key-controller](#key-controller)
      - [createKey](#createkey)
      - [updateToken](#updatetoken)
      - [updateKey](#updatekey)
      - [deleteKey](#deletekey)
      - [getKey](#getkey)
    + [Party-controller](#party-controller)
      - [createParty](#createparty)
      - [getParties](#getparties)
      - [getParty](#getparty)
      - [updateParty](#updateparty)
      - [deleteParty](#deleteparty)
    + [Request-controller](#request-controller)
      - [createRequest](#createrequest)
      - [getRecipientRequests](#getrecipientrequests)
      - [getSenderRequests](#getsenderrequests)
      - [getRequest](#getrequest)
      - [updateRequest](#updaterequest)
      - [rescindRequest](#rescindrequest)
    + [Submission-controller](#submission-controller)
      - [createSubmission](#createsubmission)
      - [getPartySubmissions](#getpartysubmissions)
      - [getUserSubmissions](#getusersubmissions)
      - [getSubmission](#getsubmission)
      - [updateSubmission](#updatesubmission)
    + [User-controller](#user-controller)
      - [createUser](#createuser)
      - [getUser](#getuser)
      - [updateUser](#updateuser)
      - [deleteUser](#deleteuser)
      - [updateUserToken](#updateusertoken)
  * [Definitions](#definitions)
    + [CertificateDTO](#certificatedto)
    + [Field](#field)
    + [IdentityTypeDTO](#identitytypedto)
    + [InformationRequestDTO](#informationrequestdto)
    + [KeyDTO](#keydto)
    + [NewKeyDTO](#newkeydto)
    + [NewPartyDTO](#newpartydto)
    + [NewUserDTO](#newuserdto)
    + [PartyDTO](#partydto)

## Overview
This document outlines the endpoints that are accessible within MiD's backend

### URI scheme
*Host* : mid-secure.ie  
*BasePath* : /mid

### Security Policy
To allow for authentication of users MiD has a security policy that makes use of the basic authentication headers within a HTTP request. To make a correct call you most adhere to how these are structure to successfully make a call.
A user must create a 256 bit RSA public private key pair. The public key must then be converted to base 64 and transmitted along with the rest of the information required to create a user.
The key can be generated within the below code:
```
KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");  
keyPairGenerator.initialize(2048);  
KeyPair keyPair = keyPairGenerator.generateKeyPair();  
profile.setPrivateKey( Base64.encodeToString(keyPair.getPrivate().getEncoded(),Base64.DEFAULT).replace("\n",""));  
profile.setPublicKey(Base64.encodeToString(keyPair.getPublic().getEncoded(),Base64.DEFAULT).replace("\n",""));
```
When a user is created (party or standard user) they are given an authentication token. This token must be encrypted using the users private key and included in all calls to MiD.
Encryption can be accomplished with the below method:
```

Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(HashUtil.Base64ToByte(keyString)));  
cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
return Base64.encodeToString(cipher.doFinal(text.getBytes("UTF-8")), Base64.DEFAULT);  
```
This will encrypt your text into a base64 string that can be decrypted and verified by the server


## Resources

### Certificate-controller
Certificate Controller


#### getCertificate
```
GET /certificate/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CertificateDTO](#certificatedto)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


<a name="deletecertificateusingdelete"></a>
#### deleteCertificate
```
DELETE /certificate/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CertificateDTO](#certificatedto)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


### Identity-type-controller
Identity Type Controller


#### createIdentityType
```
POST /identitytype
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**identityTypeToCreate**  <br>*required*|identityTypeToCreate|[IdentityTypeDTO](#identitytypedto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[IdentityTypeDTO](#identitytypedto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


<a name="getidentitytypesusingget"></a>
#### getIdentityTypes
```
GET /identitytype
```


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|< [IdentityTypeDTO](#identitytypedto) > array|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getIdentityTypeFields
```
GET /identitytype/fields
```


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|< string > array|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getPartyIdentityTypes
```
GET /identitytype/party/{partyId}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**partyId**  <br>*required*|partyId|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|< [IdentityTypeDTO](#identitytypedto) > array|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getIdentityType
```
GET /identitytype/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[IdentityTypeDTO](#identitytypedto)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### updateIdentityType
```
PUT /identitytype/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|
|**Body**|**identityTypeDTO**  <br>*required*|identityTypeDTO|[IdentityTypeDTO](#identitytypedto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[IdentityTypeDTO](#identitytypedto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### deleteIdentityType
```
DELETE /identitytype/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[IdentityTypeDTO](#identitytypedto)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


### Key-controller
Key Controller


#### createKey
```
POST /key
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**keyToCreate**  <br>*required*|keyToCreate|[KeyDTO](#keydto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[NewKeyDTO](#newkeydto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### updateToken
```
PUT /key/token/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|
|**Body**|**tokenDTO**  <br>*required*|tokenDTO|[TokenDTO](#tokendto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[TokenDTO](#tokendto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### updateKey
```
PUT /key/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|
|**Body**|**keyDTO**  <br>*required*|keyDTO|[KeyDTO](#keydto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[KeyDTO](#keydto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### deleteKey
```
DELETE /key/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[KeyDTO](#keydto)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getKey
```
GET /key/{ownerId}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**ownerId**  <br>*required*|ownerId|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[KeyDTO](#keydto)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


### Party-controller
Party Controller


#### createParty
```
POST /party
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**partyToCreate**  <br>*required*|partyToCreate|[PartyDTO](#partydto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[NewPartyDTO](#newpartydto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getParties
```
GET /party
```


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|< [PartyDTO](#partydto) > array|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getParty
```
GET /party/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[PartyDTO](#partydto)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### updateParty
```
PUT /party/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|
|**Body**|**newPartyName**  <br>*required*|newPartyName|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[PartyDTO](#partydto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### deleteParty
```
DELETE /party/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[PartyDTO](#partydto)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


### Request-controller
Request Controller


#### createRequest
```
POST /request
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**informationRequestDTO**  <br>*required*|informationRequestDTO|[InformationRequestDTO](#informationrequestdto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[RequestDTO](#requestdto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getRecipientRequests
```
GET /request/recipient/{recipientId}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**recipientId**  <br>*required*|recipientId|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|< [RequestDTO](#requestdto) > array|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getSenderRequests
```
GET /request/sender/{senderId}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**senderId**  <br>*required*|senderId|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|< [RequestDTO](#requestdto) > array|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getRequest
```
GET /request/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[RequestDTO](#requestdto)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### updateRequest
```
PUT /request/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|
|**Body**|**informationRequestDTO**  <br>*required*|informationRequestDTO|[InformationRequestDTO](#informationrequestdto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[RequestDTO](#requestdto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### rescindRequest
```
DELETE /request/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[RequestDTO](#requestdto)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


### Submission-controller
Submission Controller


#### createSubmission
```
POST /submission
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**submissionToCreate**  <br>*required*|submissionToCreate|[SubmissionDTO](#submissiondto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[SubmissionDTO](#submissiondto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getPartySubmissions
```
GET /submission/party/{partyId}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**partyId**  <br>*required*|partyId|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|< [SubmissionDTO](#submissiondto) > array|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getUserSubmissions
```
GET /submission/user/{userId}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|userId|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|< [SubmissionDTO](#submissiondto) > array|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getSubmission
```
GET /submission/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[SubmissionDTO](#submissiondto)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### updateSubmission
```
PUT /submission/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|
|**Body**|**submissionToUpdate**  <br>*required*|submissionToUpdate|[SubmissionDTO](#submissiondto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[SubmissionDTO](#submissiondto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


### User-controller
User Controller


#### createUser
```
POST /user
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**userToCreate**  <br>*required*|userToCreate|[UserDTO](#userdto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[NewUserDTO](#newuserdto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### getUser
```
GET /user/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[UserDTO](#userdto)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### updateUser
```
PUT /user/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|
|**Body**|**userDTO**  <br>*required*|userDTO|[UserDTO](#userdto)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[UserDTO](#userdto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### deleteUser
```
DELETE /user/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[UserDTO](#userdto)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


#### updateUserToken
```
PUT /user/{id}/token
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|string|
|**Body**|**token**  <br>*required*|token|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[UserDTO](#userdto)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json`


## Definitions

### CertificateDTO

|Name|Schema|
|---|---|
|**createdAt**  <br>*optional*|string|
|**createdBy**  <br>*optional*|string|
|**creatorName**  <br>*optional*|string|
|**id**  <br>*optional*|string|
|**ownedBy**  <br>*optional*|string|
|**ownerName**  <br>*optional*|string|
|**status**  <br>*optional*|string|
|**submissionHash**  <br>*optional*|string|


### Field

|Name|Schema|
|---|---|
|**name**  <br>*optional*|string|
|**type**  <br>*optional*|string|


### IdentityTypeDTO

|Name|Schema|
|---|---|
|**coverImg**  <br>*optional*|string|
|**fields**  <br>*optional*|< [Field](#field) > array|
|**iconImg**  <br>*optional*|string|
|**id**  <br>*optional*|string|
|**name**  <br>*optional*|string|
|**partyId**  <br>*optional*|string|
|**status**  <br>*optional*|string|
|**versionNumber**  <br>*optional*|integer (int32)|


### InformationRequestDTO

|Name|Schema|
|---|---|
|**certificateId**  <br>*optional*|string|
|**identityTypeFields**  <br>*optional*|string|
|**identityTypeValues**  <br>*optional*|string|
|**indentityTypeId**  <br>*optional*|string|
|**recipientId**  <br>*optional*|string|
|**senderId**  <br>*optional*|string|
|**status**  <br>*optional*|string|


### KeyDTO

|Name|Schema|
|---|---|
|**id**  <br>*optional*|string|
|**keyStatus**  <br>*optional*|string|
|**publicKey**  <br>*optional*|string|
|**userId**  <br>*optional*|string|

### NewKeyDTO

|Name|Schema|
|---|---|
|**id**  <br>*optional*|string|
|**keyStatus**  <br>*optional*|string|
|**publicKey**  <br>*optional*|string|
|**token**  <br>*optional*|string|
|**userId**  <br>*optional*|string|

### NewPartyDTO

|Name|Schema|
|---|---|
|**id**  <br>*optional*|string|
|**keyId**  <br>*optional*|string|
|**name**  <br>*optional*|string|
|**partyToken**  <br>*optional*|string|
|**publicKey**  <br>*optional*|string|
|**status**  <br>*optional*|string|

### NewUserDTO

|Name|Schema|
|---|---|
|**fcmToken**  <br>*optional*|string|
|**id**  <br>*optional*|string|
|**keyId**  <br>*optional*|string|
|**nickname**  <br>*optional*|string|
|**publicKey**  <br>*optional*|string|
|**status**  <br>*optional*|string|
|**userToken**  <br>*optional*|string|

### PartyDTO

|Name|Schema|
|---|---|
|**id**  <br>*optional*|string|
|**keyId**  <br>*optional*|s