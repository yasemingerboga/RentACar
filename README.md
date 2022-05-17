# RentACar
## How it works?
<br/>
Firstly, you should add new account. You can use IndividualCustomer add or CorporateCustomer add methods.

![image](https://user-images.githubusercontent.com/68692720/165787147-75ef973b-7e6f-4c15-a7e7-e3cba9ae2243.png)
<br/><br/>
After that, go to Postman and send the Post request for login the application.
<br/><br/>
![image](https://user-images.githubusercontent.com/68692720/165785710-5a2b00f7-277c-4ba5-85db-f284b8370811.png)
<br/><br/>
You can see access_token and refresh_token. Copy the access_token. For developers and for test you can use this token: 
<br/>
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJva3VAZGVuZW1lLmNvbSIsInJvbGVzIjpbXSwiaXNzIjoiL2FwaS9sb2dpbiIsImV4cCI6OTk5OTk5OTk5OX0._ZLavBl5bO9ccc8apM2kqgOfzHxEIFHnUo6dylXyOB0
<br/><br/>
This token intentionally embedded in the code.
<br/><br/>
Then, click Authorize button in Swagger UI.
<br/><br/>
![image](https://user-images.githubusercontent.com/68692720/165784849-c6cb5a93-cf8c-4071-b5dd-63ff3cf36348.png)
<br/><br/>
Paste access_token to Swagger Authorize popup. After that click Authorize!
<br/><br/>
![image](https://user-images.githubusercontent.com/68692720/165786043-9dffb026-b1a5-40b7-b8af-d830a757ff0a.png)
<br/><br/>
Here you go! You can send any requests. Don't forget, if it is 403 Forbidden (such as updateRentalCars method. It is not allowed for every user, you should have "ROLE_ADMIN" role to access it) add roles to user.
