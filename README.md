[![Codacy Badge](https://app.codacy.com/project/badge/Grade/22b5188f5b21463da9201889d6b7692f)](https://www.codacy.com/gh/Igor-K39/restaurant_voting_system/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Igor-K39/restaurant_voting_system&amp;utm_campaign=Badge_Grade) 
## Restaurant voting system - online internship graduation project 
Design and implement a REST API using Hibernate/Spring/SpringMVC. Build a voting system for deciding where to have lunch.
### Heroku link (swagger ui): https://rvs-kopyshev.herokuapp.com/
Credentials to sign in:  
user.sd@gmail.com user_password  
admin.sd@gmail.com admin_password

## Tech stack
Maven, Jackson, Spring Web MVC, Spring Data JPA (Hibernate 5), Spring Security, SpringFox Swagger UI, Hibernate Validator, 
JUnit 5, SLF4J (Logback), Ehcache (Spring Cache, Hibernate 2nd level cache), H2 Database, ModelMapper, Lombok  

## Project key logic 
 * 2 types of users: admin and regular users
 * Admin can input a restaurant, and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed  
 * Each restaurant provides new menu each day.

## Caching strategy
Hibernate second level cache as non-expiring to keep 100 entities of Restaurant, Dish

Spring cache:
- Expiring in 3 hours to keep 100 entities of MenuService.get() (evicting on update/delete);
- Non-expiring single cache to keep the return value of MenuService.getAll() (evicting on create, update, delete)

## Domain model 
![domain_model.png](https://raw.githubusercontent.com/Igor-K39/restaurant_voting_system/main/domain_model.png)






