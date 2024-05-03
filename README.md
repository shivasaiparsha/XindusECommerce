This documentation covers how to built XindusECommerce System using Spring boot framework

*************Features Of XindusEcommerce****************

User Authentication         :   Secure user login and signup with Spring Security.
Wishlist API                :   Manage wishlists via RESTful endpoints (/add, /get, /delete).
Authentication Required     :   Only logged-in users can access wishlists and he can manage the WishList items.
BinarySearch Algorithm      :   Implemented binary search algorithm to delete the Item by Item Id method to reduce the time complexity from O(User Products) to O(log(user Products)
Spring Data JPA Integration :   Data storage and retrieval using JPA and a database.
WishlistItem Entity         :   Database schema for users and wishlist items.
Unit Tests                  :   Ensure robust functionality through comprehensive testing.

**********************About Spring boot******************
Spring boot is Java enterprise edition frame work.which is used to develop the microservice based applications
Microservice based applications allow developer to development easy.

Rapid Prototyping: Spring Boot eliminates boilerplate code, letting you jumpstart development and focus on core functionality.
Simplified Configuration: Opinionated defaults and auto-configuration make setting up projects and integrating features much faster.
Lightweight and Standalone: Build self-contained applications easily, deployable without complex web containers or configuration

******************Spring boot Initialization*******************

 Visit Spring Initializr

create the project by providing necessary details such as Java as Language, Maven as Dependency manager, Artifact Id,GroupId, project Name
import necessary dependency into you project like Spring web, Lombok, Spring Jpa, Mysql connecter, Spring Security
Unzip the downloaded file. Import the extracted project directory into your preferred IDE (e.g., IntelliJ IDEA, Eclipse).

 *******Dependencies Features*****************
Spring Web: Enables building web applications and REST APIs with Spring's powerful features and annotations.

Lombok: Reduces boilerplate code by automating getters, setters, toString, and other common methods.

Spring JPA: Provides an abstraction layer for interacting with relational databases using JPA and Hibernate.

MySQL Connector: Allows your application to connect and interact with MySQL databases.

Spring Security: Implements Spring-based security features like authentication, authorization, and access control

Junit Test : For testing
**************Sett up Project****************
Before Setting up the project first I thoroughly asses the requirements and I extract the key Requirements from the project details

Design and implement RESTful API endpoints for wishlist management:

/api/wishlists: GET endpoint to retrieve a user's wishlist.

/api/wishlists: POST endpoint to create a new wishlist item.

/api/wishlists/{id}: DELETE endpoint to remove a wishlist item by ID.

Authenticate  and Authorize the User
******************************Create project Structure***************************************
I developed a simple project with different packages such as Controller,Service Models,Repository,DTOS,Transformers,Security,Exceptions

**************Use of Packages************8
Packages help organize related classes and interfaces into groups, making it easier to manage and navigate through the codebase.
packages provide a way to encapsulate classes with related functionalities together. This helps in hiding the implementation details of certain functionalities
from other parts of the codebase, improving modularity and reducing dependencies.

/Controller : Handles incoming requests, maps them to appropriate methods, and orchestrates the flow of data in Spring MVC applications.

/Service: Implements business logic and performs operations on data retrieved from repositories, promoting separation of concerns and maintainability.

/Models: Represents domain objects or entities, encapsulating data and behavior, ensuring consistency and integrity in the application.

/Repository: Interacts with the database or external data sources, providing CRUD (Create, Read, Update, Delete) operations for domain objects, promoting data persistence and retrieval.

/DTOs (Data Transfer Objects): Transfers data between different layers of the application or between the application and external systems, facilitating loose coupling and preventing overexposure of domain objects.

/Transformers: Converts data between different formats or structures, such as transforming domain entities to DTOs or vice versa, ensuring compatibility between layers and systems.

/Security: Implements authentication, authorization, and other security mechanisms to protect resources and enforce access control, ensuring the confidentiality, integrity, and availability of the application.

/Exceptions: Handles and manages exceptional conditions or errors gracefully, providing meaningful feedback to users and helping in troubleshooting and debugging.

******************Importance Of Security of Web Applications************
1.Authentication and Authorization
  For Authentication and Authorization I have configure with  spring security filter chain
  Spring Security offers robustness through its extensive features and configurable options, ensuring comprehensive protection
  against various security threats in web applications
 | Spring Security provides a flexible and customizable framework for implementing authentication and authorization mechanisms tailored to specific application requirements.
 |Spring Security offers built-in protection against common security vulnerabilities such as cross-site request forgery (CSRF), cross-site scripting (XSS),

 2.Spring Security's filter chain architecture allows developers to configure a series of security filters to intercept and process
 incoming requests at various stages of the request processing lifecycle. These filters perform tasks such as authentication, authorization.
 *******************internal working of Spring security*****************
 1.Request Interception: When a request is made to the application, it first passes through the servlet container's filter
   chain, which may include filters provided by Spring Security.

 2.Security Filter Chain: Within Spring Security, the SecurityFilterChain is responsible for intercepting
   incoming requests and applying security-related processing.
   This chain consists of multiple security filters configured to handle different aspects of security.

 3.Authentication Processing: One of the critical filters in the chain is responsible for initiating the authentication process.
    This filter typically checks if the request requires authentication, and if so, it delegates the authentication process to
    the AuthenticationManager

 4.AuthenticationManager: The AuthenticationManager is the core component responsible for authenticating the user's credentials.
  It receives an Authentication object representing the user's credentials (such as username and password) and attempts to authenticate
   the user using configured AuthenticationProviders.


 ****************************Implementation of Spring security filterchain*************************************************
Security Filter Chain Initialization: The Spring Security framework initializes the SecurityFilterChain bean defined in the Configuration class,
 which is responsible for intercepting incoming requests and applying security measures.

HttpSecurity Configuration: Within the securityFilterChain method, the HttpSecurity object is configured to
customize security settings. This includes disabling CSRF protection, enabling
HTTP basic authentication, and defining authorization rules using method chaining.

Authorization Rules Definition: Authorization rules are specified using the authorizeHttpRequests method, where certain
 URL patterns are allowed or restricted based on the user's authorities. In this example, specific endpoints like /login and /register
 are permitted for all users, while endpoints under /user require the "user" authority.

UserDetailsService Configuration: The userDetailsService bean is injected into the Configuration class constructor,
 allowing access to user details during authentication. This custom UserDetailsService implementation
 retrieves user information from a repository and constructs a UserDetails object, including authorities and password.

UserDetails Loading: When a user attempts to authenticate, the loadUserByUsername method in the UserDetailsService implementation is invoked.
This method retrieves user details based on the provided username, constructs a UserDetails object, and returns it.

Authentication Process: During authentication, the UserDetailsService provides user details to
the AuthenticationManager, which authenticates the user based on the provided credentials.
******************************************************************************************************************************

////////////////////////////// End Points in Rest Controller/////////////////////////////////////////

User Registration Endpoint: The userRegistration method in the controller handles POST requests to the "/register" endpoint
for user registration. It accepts an AdduserDto object containing user registration details, attempts to add the user to the
database using the userService,every one can access to this end point.it permit all no authorization is require.

The method utilizes exception handling to manage different scenarios during user registration. It catches
 UniqueConstraintCustomExceptions specifically, which likely indicates a conflict due to an existing username.
 For this exception, it returns an HTTP status code of 409 (CONFLICT), indicating a conflict with the current state of
 the resource. Other exceptions are caught by
the general Exception block and result in an HTTP status code of 500 (INTERNAL_SERVER_ERROR).
__Note: Here we are storing the encoded password in the database by using ByCryptpassword encoder

2.For Login user needs to provide Login credentials Like username and password. here we authenticate the user with username
 username should be unique

*************************WishListItem End Points******************************************
|for accessing the item Rest api user should be authenticated by providing user credentials

1.addItemToWishListItems: Handles the addition of an item to a user's wish list, returning an appropriate response
based on the success or failure of the operation.

2.getALlWishListOfUserByUserId: Retrieves all wish list items of a user by their user ID, returning
the list of items if found, or an appropriate error response if the user is not found or if an unexpected error occurs.

3.deleteById: Deletes an item by its ID, handling different exceptions such as user not found or incorrect password,
and returning corresponding error responses.

******************************Item service business logic***********************************************
1.handled multiple corner  cases in  every business logic

addIteam(AddItemDto addItemDto):

This method adds an item to the user's wishlist.
It throws exceptions if the user is not found or if there's an issue with the input.
It saves the wishlist item to the repository and updates the user's wishlist.

getAllWishListOfUser(UserWishListDto userWishListDto):
This method retrieves all wishlist items for a given user.
It throws exceptions if the user is not found or if the wishlist is empty.
It constructs a response DTO containing item IDs and names for each item in the wishlist.

deleteItemById(DeleteByIdDto deleteByIdDto):
This method deletes an item from the user's wishlist by its ID.
It throws exceptions if the item is not found, if the wishlist is empty, or if there's an issue with the input.
It removes the item from the user's wishlist and updates the repository accordingly.

*************************precautions********************************************************
1.If you get any internal servers try to reload the dependencies once
    check for any updates or changes in the dependencies declared in the project configuration files
2.Rebuilding the Project: After updating dependencies, it's often necessary to rebuild the project to
   incorporate any changes or new features provided by the updated dependencies.
3.Whenever you trying to hits the protected end points you should provide the login  credentials(username,password) in the postman
  by selecting the  basic  auth
4.before run  the project add Mysql database data source details in application properties. make sure that you must provide valid details
********************************Testing********************************************
1.Implemented Junit testing by adding dependencies
2.reload the dependencies after addition of dependencies
--------------Controller Package Test---------------
1.user controller Test
  *it test the strength of the Password provided by the user
    1.Multiple TestCases performed on the password strength
    2.It describe whether password contains at least one Uppercase letter, Lowercase letter, one numeric
    3.password should contains the at least minimum length of 12
    4.password should contains the at least maximum of 32 length

  * User Registration Test
    1.evaluate wheather the user stored in db correctly or not
    2.evaluate success status code
 2.Product Controller Test
     AddItemToDatabaseThrowWishListController is a unit test designed to verify the functionality
     of adding an item to a wishlist through a controller in a Spring Boot application.
     It sends a POST request to a specific endpoint (/user/whishlistItem) with a JSON payload
     containing the item to be added. The method then checks whether the response indicates a
     successful addition of the item.
  3. The method getItemsListOfUser is a unit test designed to verify the functionality of retrieving a list of
      items belonging to a specific user from a wishlist through a controller in a Spring Boot application.
      It first retrieves the initial count of wishlist items present in the database using a repository query.
      Then, it sends a GET request to the endpoint
  4. The method Delete_By_ItemId is a unit test designed to verify the functionality of deleting an item from a
     wishlist based on its ID through a controller in a Spring Boot application.
     It constructs a request to the /user/deleteItemById endpoint, specifying the ID of the item to be deleted.

--------------Service Package Test------------------
 addItem_validInput_success():
 This test validates that adding an item with valid input parameters successfully returns the expected success message.
 It first creates a mock user and saves it to the database, then calls the addIteam method with valid input, and asserts that the result matches the expected success message.

 addItem_userNotFound_throwsException():
 This test ensures that attempting to add an item for a user that doesn't exist throws a NoSuchElementException.
 It constructs an AddItemDto with a non-existing user's email, calls the addIteam method, and asserts that the expected exception is thrown with the correct message.

 testGetAllWishListOfUser_validEmail_success():
 This test verifies that retrieving all wishlist items for a user with a valid email returns the expected list of items.
 It sets up a dummy user with wishlist items in the database, calls the getAllWishListOfUser method with the user's email, and asserts that the returned list matches the expected items.

 testGetAllWishListOfUser_userNotFound_throwsException():
 This test confirms that attempting to retrieve wishlist items for a non-existing user throws a UserNotFoundException.
 It constructs a UserWishListDto with a non-existing user's email, calls the getAllWishListOfUser method, and asserts that the expected exception is thrown.

 testGetAllWishListOfUser_emptyWishList_throwsException():
 This test ensures that attempting to retrieve wishlist items for a user with an empty wishlist throws an EmptyWishListCustomException.
 It sets up a dummy user without wishlist items in the database, calls the getAllWishListOfUser method, and asserts that the expected exception is thrown.

 !Attention
 when you performing  testing with services classes Make the authentication properly..
 User must be registered with our system
 User must exist in our database

-----------------------------------------------------------------------------------------------------------------
                               All test cases executed successfully
--------------------------------------------------------------------------------------------------------------------

![Screenshot (5)](https://github.com/shivasaiparsha/XindusECommerce/assets/112009608/1a6801fc-b264-473b-9e5c-d6b45c792780)



  ********************************Scope Of Improvement***********************************
##Area Of Improvement**
Spring security
    1.we can implement Jwt token to allows user no need to provide login credentials for every time Logged into our System
    2.JWT  are well-suited for implementing single sign-on solutions, where a user can authenticate once and access multiple
      applications or services without the need for repeated login prompts. This improves user experience and simplifies
     access control across distributed systems.
    3.JWTs can replace traditional session-based authentication mechanisms by storing user authentication state in the token itself
    4.JWTs are ideal for securing APIs, as they can be easily transmitted over HTTP headers or as URL parameters. By validating JWTs on each API request, developers
      can ensure that only authenticated and authorized users can access the API endpoints.
Caching Mechanism(Redis)
    1.In our system we have not implemented any cache memory.
    2.cache Memory  enables us to  improve  the performance and efficiency of  our system  by storing frequently accessed data
    3.Redis is often used as a caching layer to store frequently accessed data in memory, reducing the need to fetch the same data
      from slower storage systems such as databases.
    4.Redis is well-suited for storing session data in web applications. By storing session state in Redis, applications can scale horizontally
      since session data is not tied to a specific server.
