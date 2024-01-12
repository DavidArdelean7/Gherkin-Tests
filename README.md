
# Gherkin Scenarios
## Restful Booking API Tests

The Restful Booking API tests have to be run in the order they are written for all of them to be successful because some of them use previous test results (created/deleted bookings, tokens).

Feature: Token creation (**t1_createTokenTest**)

    Scenario: Successful token creation 
        Given the base URI is "https://restful-booker.herokuapp.com"
        When a user with username "admin" and password "password123" requests a token
        Then the response status code should be 200
        And a token is generated

Feature: Booking Creation (**t2_createBookingTest**)

    Scenario: Successful booking creation 
        Given the base URI is "https://restful-booker.herokuapp.com"
        When a new booking is created with the following details:
          | firstname    | lastname  | totalprice | depositpaid | checkin      | checkout     | additionalneeds |
          | John          | Doe       | 150        | true        | 2023-01-01   | 2023-01-05   | Breakfast       |
        Then the booking should be created successfully with the following details:
          | firstname    | lastname  | totalprice | depositpaid | checkin      | checkout     | additionalneeds |
          | John          | Doe       | 150        | true        | 2023-01-01   | 2023-01-05   | Breakfast       |

Feature: Get Booking (**t3_getBookingDetailsTest**)

    Scenario: Successful retrieval of booking details 
        Given the base URI is "https://restful-booker.herokuapp.com"
        And there is an existing booking with ID {bookingId}
        When the details of the booking with ID {bookingId} are requested
        Then the response status code should be 200
        And the booking details should match the following:
          | firstname    | lastname  | totalprice | depositpaid | checkin      | checkout     | additionalneeds |
          | John          | Doe       | 150        | true        | 2023-01-01   | 2023-01-05   | Breakfast       |

Feature: Update Booking (**t4_updateBookingWithoutTokenTest**)

    Scenario: Attempt to update booking without authentication token 
        Given the base URI is "https://restful-booker.herokuapp.com"
        And there is an existing booking with ID {bookingId}
        When an attempt is made to update the booking with ID {bookingId} without providing a valid authentication token
        Then the response status code should be 403
        And the booking details should remain unchanged

Feature: Update Booking (**t5_updateBookingWithEmptyDataTest**)

    Scenario: Attempt to update booking with empty data 
        Given the base URI is "https://restful-booker.herokuapp.com"
        And there is an existing booking with ID {bookingId}
        And a valid authentication token is available
        When an attempt is made to update the booking(e.g., 51235112399)
        Then the response status code should be 400


Feature: Update Booking (**t6_updateBookingTest**)

    Scenario: Successful update of booking 
        Given the base URI is "https://restful-booker.herokuapp.com"
        And there is an existing booking with ID {bookingId}
        And a valid authentication token is available
        When the booking with ID {bookingId} is updated with the following details:
          | firstname    | lastname  | totalprice | depositpaid | checkin      | checkout     | additionalneeds         |
          | Johnny        | Upgrade   | 250        | true        | 2023-01-01   | 2023-01-08   | Breakfast, Wifi         |
        Then the response status code should be 200
        And the updated booking details should match the provided values

Feature: Get Booking (**t7_getBookingWithInvalidIdTest**)

    Scenario: Attempt to get booking details with an invalid ID 
        Given the base URI is "https://restful-booker.herokuapp.com"
        And there is no booking with ID 51235112399
        When an attempt is made to get the details of the booking with ID 51235112399
        Then the response status code should be 404

Feature: Get Booking IDs (**t8_getBookingIdsTest**)

    Scenario: Retrieve booking IDs based on firstname and lastname 
        Given the base URI is "https://restful-booker.herokuapp.com"
        And there exist bookings with firstname "Johnny" and lastname "Upgrade"
        When an attempt is made to retrieve booking IDs for a booking with firstname "Johnny" and lastname "Upgrade"
        Then the response status code should be 200
        And the response should contain a list of booking IDs


Feature: Delete Booking (**t9_deleteBookingWithoutTokenTest**)

    Scenario: Attempt to delete booking without authentication token 
        Given the base URI is "https://restful-booker.herokuapp.com"
        And there is an existing booking with ID {bookingId}
        And there is no valid authentication token provided
        When an attempt is made to delete the booking with ID {bookingId}
        Then the response status code should be 403
        And the booking should not be deleted

Feature: Delete Booking (**tt1_deleteBookingTest**)

    Scenario: Successful deletion of a booking
        Given the base URI is "https://restful-booker.herokuapp.com"
        And there is an existing booking with ID {bookingId}
        And a valid authentication token is available
        When an attempt is made to delete the booking
        Then the response status code should be 201
        And the booking with ID {bookingId} should be successfully deleted

Feature: Delete Booking (**tt2_deleteNonExistingBookingTest**)

    Scenario: Attempt to delete a nonexistent booking
        Given the base URI is "https://restful-booker.herokuapp.com"
        And there is no booking with ID 23551998006
        And a valid authentication token is available
        When an attempt is made to delete the booking with ID 23551998006
        Then the response status code should be 405
        And an appropriate error message should be returned
  
Feature: Update Nonexistent Booking (**tt3_updateNonExistingBookingTest**)

    Scenario: Attempt to update a nonexistent booking
        Given the base URI is "https://restful-booker.herokuapp.com"
        And there is no booking with ID {nonexistentBookingId}
        And a valid authentication token is available
        When an attempt is made to update a nonexistent booking with ID {nonexistentBookingId}
        Then the response status code should be 400
        And an appropriate error message should be returned

## JSON Placeholder API Tests

Feature: Get Post (**t1_getAllPostsTest**)

    Scenario: Retrieve all posts
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When an attempt is made to retrieve all posts
        Then the response status code should be 200
        And the response should contain a list of all posts

Feature: Get Comment (**t2_getAllCommentsTest**)

    Scenario: Retrieve all comments
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When an attempt is made to retrieve all comments
        Then the response status code should be 200
        And the response should contain a list of all comments

Feature: Get Album (**t3_getAllAlbumsTest**)

    Scenario: Retrieve all albums
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When an attempt is made to retrieve all albums
        Then the response status code should be 200
        And the response should contain a list of all albums

Feature: Get Photo (**t4_getAllPhotosTest**)

    Scenario: Retrieve all photos
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When an attempt is made to retrieve all photos
        Then the response status code should be 200
        And the response should contain a list of all photos

Feature: Get Todo (**t5_getAllTodosTest**)

    Scenario: Retrieve all todos
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When an attempt is made to retrieve all todos
        Then the response status code should be 200
        And the response should contain a list of all todos

Feature: Get User (**t6_getAllUsersTest**)

    Scenario: Retrieve all users
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When an attempt is made to retrieve all users
        Then the response status code should be 200
        And the response should contain a list of all users

Feature: Get Post (**t7_getPostByIdTest**)

    Scenario: Retrieve a post by ID
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When an attempt is made to retrieve a post with ID 1
        Then the response status code should be 200
        And the response should contain the post details
        | userId | id | title                                                                      | body                                                                                                                                                       |
        | 1      | 1  | sunt aut facere repellat provident occaecati excepturi optio reprehenderit | quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto |

Feature: Get Post (**t8_getPostByInvalidIdTest**)

    Scenario: Attempt to retrieve a post with an invalid ID
        Given the base URI is "https://jsonplaceholder.typicode.com"
        And there is no post with ID 1241251231
        When an attempt is made to retrieve a post with ID 1241251231
        Then the response status code should be 404

Feature: Create Post (**t9_createPostTest**)

    Scenario: Create a new post
        Given the base URI is "https://jsonplaceholder.typicode.com"
        And the request header Content-Type is "application/json; charset=UTF-8"
        And the request body contains the following JSON:
        """
        {
        "title": "fooxxxx",
        "body": "barny",
        "userId": 1
        }
        """
        When an attempt is made to create a new post
        Then the response status code should be 201
        And the response should contain the created post details
        | title   | body  | userId |
        | fooxxxx | barny | 1      |

Feature: Create Comment (**tt1_createCommentTest**)

    Scenario: Create a new comment
        Given the base URI is "https://jsonplaceholder.typicode.com"
        And the request header Content-Type is "application/json; charset=UTF-8"
        And the request body contains the following JSON:
        """
        {
        "postId": 2,
        "email": "test@yahoo.com",
        "body": "lorem ipsum sit dolor"
        }
        """
        When an attempt is made to create a new comment
        Then the response status code should be 201
        And the response should contain the created comment details
        | postId | email            | body                   |
        | 2      | test@yahoo.com   | lorem ipsum sit dolor  |

Feature: Get Comment (**tt2_getCommentByIdTest**)

    Scenario: Retrieve a comment by ID
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When an attempt is made to retrieve a comment with ID 1
        Then the response status code should be 200
        And the response should contain the comment details
        | name                       | email               | body                                                                                                                                         |
        | id labore ex et quam laborum | Eliseo@gardner.biz | laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium |

Feature: Get Comment (**tt3_getCommentByInvalidIdTest**)

    Scenario: Attempt to retrieve a comment with an invalid ID
    Given the base URI is "https://jsonplaceholder.typicode.com"
    And there is no comment with ID 21512512
    When an attempt is made to retrieve a comment with ID 21512512
    Then the response status code should be 404

Feature: Get User (**tt4_getUserByIdTest**)

    Scenario: Retrieve user details by ID
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When an attempt is made to retrieve user details with ID 1
        Then the response status code should be 200
        And the response should contain the user details
        | name           | username | email                 | street       | suite    | city          | zipcode     | lat       | lng        | phone                  | website           | company_name        | company_catchPhrase                 | company_bs                          |
        | Leanne Graham  | Bret     | Sincere@april.biz     | Kulas Light  | Apt. 556 | Gwenborough   | 92998-3874  | -37.3159  | 81.1496    | 1-770-736-8031 x56442 | hildegard.org     | Romaguera-Crona    | Multi-layered client-server neural-net | harness real-time e-markets          |

Feature: Get User (**tt5_getUserByInvalidIdTest**)

    Scenario: Attempt to retrieve a user with an invalid ID
        Given the base URI is "https://jsonplaceholder.typicode.com"
        And there is no user with ID 2151251231
        When an attempt is made to retrieve a user with ID 2151251231
        Then the response status code should be 404

Feature: Create User (**tt6_createUserTest**)

    Scenario: Create a new user
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When a new user is created with the following details:
        | name       | username | email           | street        | suite       |
        | User Test  | usertest | test@user.com   | Test Street   | Apt. Test223 |
        Then the response status code should be  201 
        And the user should be created successfully with the following details:
        | name       | username | email           | street        | suite       |
        | User Test  | usertest | test@user.com   | Test Street   | Apt. Test223 |

Feature: Get Album (**tt7_getAlbumByIdTest**)

    Scenario: Retrieve album details by ID
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When the album with ID 1 is requested
        Then the response status code should be 200
        And the response body should contain the album details:
        | userId | title                   |
        | 1      | quidem molestiae enim   |

Feature: Get Album (**tt8_getAlbumByInvalidIdTest**)

    Scenario: Attempt to retrieve album with invalid ID
        Given the base URI is "https://jsonplaceholder.typicode.com"
        And there is no album with ID 21512411
        When an attempt is made to retrieve the album with ID 21512411
        Then the response should indicate that the album is not found with a status code of 404

Feature: Create Album (**tt9_createAlbumTest**)

    Scenario: Create a new album
        Given the base URI is "https://jsonplaceholder.typicode.com"
        When a new album is created with the following details:
        | userId | title            |
        | 1      | test album title |
        Then the album should be created successfully with response status code 201
        And the response body should contain following album details:
        | id  | title            | userId |
        | 101 | test album title | 1      |
