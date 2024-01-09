package com.tests;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class JSONPlaceholderAPITests {


    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    @Before
    public void setUp() {
        baseURI = BASE_URI;
    }
    @Test
    public void t1_getAllPostsTest(){

        given()
                .header("Accept", "application/json")
                .when()
                .get("/posts")
                .then()
                .statusCode(200);
    }

    @Test
    public void t2_getAllCommentsTest(){

        given()
                .header("Accept", "application/json")
                .when()
                .get("/comments")
                .then()
                .statusCode(200);
    }

    @Test
    public void t3_getAllAlbumsTest(){

        given()
                .header("Accept", "application/json")
                .when()
                .get("/albums")
                .then()
                .statusCode(200);
    }

    @Test
    public void t4_getAllPhotosTest(){

        given()
                .header("Accept", "application/json")
                .when()
                .get("/photos")
                .then()
                .statusCode(200);
    }

    @Test
    public void t5_getAllTodosTest(){

        given()
                .header("Accept", "application/json")
                .when()
                .get("/todos")
                .then()
                .statusCode(200);
    }

    @Test
    public void t6_getAllUsersTest(){

        given()
                .header("Accept", "application/json")
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void t7_getPostByIdTest(){
        given()
                .header("Accept", "application/json")
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
                .body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
    }

    @Test
    public void t8_getPostByInvalidIdTest(){
        given()
                .header("Accept", "application/json")
                .when()
                .get("/posts/1241251231")
                .then()
                .statusCode(404);
    }

    @Test
    public void t9_createPostTest(){
        given()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body("{ \"title\" : \"fooxxxx\", \"body\" : \"barny\", \"userId\" : 1 }")
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("fooxxxx"))
                .body("body", equalTo("barny"))
                .body("userId", equalTo(1));
    }

    @Test
    public void tt1_createCommentTest(){
        given()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body("{ \"postId\" : 2, \"email\" : \"test@yahoo.com\", \"body\" : \"lorem ipsum sit dolor\" }")
                .when()
                .post("/comments")
                .then()
                .statusCode(201)
                .body("postId", equalTo(2))
                .body("email", equalTo("test@yahoo.com"))
                .body("body", equalTo("lorem ipsum sit dolor"));
    }

    @Test
    public void tt2_getCommentByIdTest(){
        given()
                .header("Accept", "application/json")
                .when()
                .get("/comments/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("id labore ex et quam laborum"))
                .body("email", equalTo("Eliseo@gardner.biz"))
                .body("body", equalTo("laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"));
    }

    @Test
    public void tt3_getCommentByInvalidIdTest() {
        given()
                .header("Accept", "application/json")
                .when()
                .get("/comments/21512512")
                .then()
                .statusCode(404);
    }

    @Test
    public void tt4_getUserByIdTest(){

        given()
                .header("Accept", "application/json")
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Leanne Graham"))
                .body("username", equalTo("Bret"))
                .body("email", equalTo("Sincere@april.biz"))
                .body("address.street", equalTo("Kulas Light"))
                .body("address.suite", equalTo("Apt. 556"))
                .body("address.city", equalTo("Gwenborough"))
                .body("address.zipcode", equalTo("92998-3874"))
                .body("address.geo.lat", equalTo("-37.3159"))
                .body("address.geo.lng", equalTo("81.1496"))
                .body("phone", equalTo("1-770-736-8031 x56442"))
                .body("website", equalTo("hildegard.org"))
                .body("company.name", equalTo("Romaguera-Crona"))
                .body("company.catchPhrase", equalTo("Multi-layered client-server neural-net"))
                .body("company.bs", equalTo("harness real-time e-markets"));
    }

    @Test
    public void tt5_getUserByInvalidIdTest() {

        given()
                .header("Accept", "application/json")
                .when()
                .get("/users/2151251231")
                .then()
                .statusCode(404);
    }

    @Test
    public void tt6_createUserTest(){

        given()
                .header("Content-Type", "application/json")
                .body(" { \"name\" : \"User Test\", \"username\" : \"usertest\", \"email\" : \"test@user.com\", \"address\" : { \"street\" : \"Test Street\", \"suite\" : \"Apt. Test223\"}} ")
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("User Test"))
                .body("username", equalTo("usertest"))
                .body("email", equalTo("test@user.com"))
                .body("address.street", equalTo("Test Street"))
                .body("address.suite", equalTo("Apt. Test223"));
    }

    @Test
    public void tt7_getAlbumByIdTest(){

        given()
                .header("Accept", "application/json")
                .when()
                .get("/albums/1")
                .then()
                .statusCode(200)
                .body("userId", equalTo(1))
                .body("title", equalTo("quidem molestiae enim"));
    }

    @Test
    public void tt8_getAlbumByInvalidIdTest(){

        given()
                .header("Accept", "application/json")
                .when()
                .get("/albums/21512411")
                .then()
                .statusCode(404);
    }

    @Test
    public void tt9_createAlbumTest(){

        given()
                .header("Content-Type", "application/json")
                .body("{ \"userId\" : 1, \"title\" : \"test album title\" }")
                .when()
                .post("/albums")
                .then()
                .statusCode(201)
                .body("id", equalTo(101))
                .body("title", equalTo("test album title"))
                .body("userId", equalTo(1));
    }

}


