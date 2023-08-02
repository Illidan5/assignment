## SimpleUserManagementSystem
### By Tim J
SimpleUserManagementSystem is a program that uses a h2 Database

## Database design
The database consists of 2 Tables and one join table

Name	[Users]
Columns [user_id][user_name][user_password]

Name	[Role]
Columns	[role_id][role_name]

Name	[user_role]
Columns	[user_id][role_id]


## Interface design

Get user using Id

GET http://localhost:8080/api/v1/users/{userId}

____________________________________________________
Change user using Id

PUT http://localhost:8080/api/v1/users/{userId}

Body
```json
{
	"user_name": "string",
  	"user_password": "string"
}
```
___________________________________________________
Delete user by Id

DELETE http://localhost:8080/api/v1/users/{userId}
___________________________________________________
Get users with role

GET http://localhost:8080/api/v1/users

___________________________________________________
Create user

POST http://localhost:8080/api/v1/users

Body
```json
{
  "user_id": 0,
  "user_name": "string",
  "user_password": "string",
  "roleList": [
    {
      "role_id": 0,
      "role_name": "string"
    }
  ]
}
```
_________________________________________________
Delete role in user

DELETE http://localhost:8080/api/v1/users/{userId}/roles/{roleId}

## DEPLOY
### To deploy the application on docker
```bash
run - mvn clean install
run - docker build -t app .
run - docker run -p 8080:8080 app
```


To access endpoints http://localhost:8080/swagger-ui/index.html#/

To access db http://localhost:8080/h2-console/



jdbc url jdbc:h2:mem:testdb

username user

password password



