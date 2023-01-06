#Springboot Redis Session Management

- Tried out Redis session management in SpringBoot
- Created a RedisController where I can check the values in Redis
	- Turned out Spring creates a hash into the database for each session, Hash contains Spring java classes
- Created a SessionController to get the current principal and current session
