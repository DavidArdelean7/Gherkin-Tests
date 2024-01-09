package com.tests;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestfulBookerAPITests {

    private static final String BASE_URI = "https://restful-booker.herokuapp.com";
    private static int bookingId =0;
    private static String bookingToken;
    @Before
    public void setUp() {
        baseURI = BASE_URI;

    }


    /*Create auth token*/

    @Test
    public void t1_createTokenTest(){

        Response response = given()
                .header("Content-Type", "application/json")
                .body("{ \"username\": \"admin\", \"password\": \"password123\"}")
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .response();
        bookingToken = response.path("token");
    }
    /*Feature: Booking a Hotel Room

    Scenario: Create a hotel booking
    Given the base URI is "https://restful-booker.herokuapp.com"
    When a new booking is created with the following details:
      | firstname    | lastname  | totalprice | depositpaid | checkin      | checkout     | additionalneeds |
      | John          | Doe       | 150        | true        | 2023-01-01   | 2023-01-05   | Breakfast       |
    Then the booking should be created successfully with the following details:
      | firstname    | lastname  | totalprice | depositpaid | checkin      | checkout     | additionalneeds |
      | John          | Doe       | 150        | true        | 2023-01-01   | 2023-01-05   | Breakfast       |
    */
    @Test
    public void t2_createBookingTest() {

        // Create a booking
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{ \"firstname\": \"John\", \"lastname\": \"Doe\", \"totalprice\": 150, \"depositpaid\": true, \"bookingdates\": { \"checkin\": \"2023-01-01\", \"checkout\": \"2023-01-05\" }, \"additionalneeds\": \"Breakfast\" }")
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .body("booking.firstname", equalTo("John"))
                .body("booking.lastname", equalTo("Doe"))
                .body("booking.totalprice", equalTo(150))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2023-01-01"))
                .body("booking.bookingdates.checkout", equalTo("2023-01-05"))
                .body("booking.additionalneeds", equalTo("Breakfast"))
                .extract()
                .response();

        bookingId = response.path("bookingid");
    }

    @Test
    public void t3_getBookingDetailsTest() {

        given()
                .header("Accept", "application/json")
                .pathParam("bookingId", bookingId)
                .when()
                .get("/booking/{bookingId}")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("John"))
                .body("lastname", equalTo("Doe"))
                .body("totalprice", equalTo(150))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2023-01-01"))
                .body("bookingdates.checkout", equalTo("2023-01-05"))
                .body("additionalneeds", equalTo("Breakfast"));
    }

    @Test
    public void t4_updateBookingWithoutTokenTest() {
        given()
                .header("Content-Type", "application/json")
                .pathParam("bookingId", bookingId)
                .body("{ \"firstname\": \"Johnny\", \"lastname\": \"Upgrade\", \"totalprice\": 250, \"depositpaid\": true, \"bookingdates\": { \"checkin\": \"2023-01-01\", \"checkout\": \"2023-01-08\" }, \"additionalneeds\": \"Breakfast, Wifi\" }")
                .when()
                .put("/booking/{bookingId}")
                .then()
                .statusCode(403);
    }
    @Test
    public void t5_updateBookingWithEmptyDataTest(){


        given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token="+bookingToken)
                .pathParam("bookingId", bookingId)
                .body("{}")
                .when()
                .put("/booking/{bookingId}")
                .then()
                .statusCode(400);
    }

    @Test
    public void t6_updateBookingTest(){

        given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token="+bookingToken)
                .pathParam("bookingId", bookingId)
                .body("{ \"firstname\": \"Johnny\", \"lastname\": \"Upgrade\", \"totalprice\": 250, \"depositpaid\": true, \"bookingdates\": { \"checkin\": \"2023-01-01\", \"checkout\": \"2023-01-08\" }, \"additionalneeds\": \"Breakfast, Wifi\" }")
                .when()
                .put("/booking/{bookingId}")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Johnny"))
                .body("lastname", equalTo("Upgrade"))
                .body("totalprice", equalTo(250))
                .body("bookingdates.checkin", equalTo("2023-01-01"))
                .body("bookingdates.checkout", equalTo("2023-01-08"))
                .body("additionalneeds", equalTo("Breakfast, Wifi"));
    }

    @Test
    public void t7_getBookingWithInvalidIdTest() {
        given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/51235112399")
                .then()
                .statusCode(404);
    }
    @Test
    public void t8_getBookingIdsTest() {

        given()
                .header("Accept", "application/json")
                .when()
                .get("/booking?firstname=Johnny&lastname=Upgrade")
                .then()
                .statusCode(200);
    }

    @Test
    public void t9_deleteBookingWithoutTokenTest() {

        given()
                .header("Content-Type", "application/json")
                .pathParam("bookingId", bookingId)
                .when()
                .delete("booking/{bookingId}")
                .then()
                .statusCode(403);
    }

    @Test
    public void tt1_deleteBookingTest() {

        given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + bookingToken)
                .pathParam("bookingId", bookingId)
                .when()
                .delete("booking/{bookingId}")
                .then()
                .statusCode(201);
    }

    @Test
    public void tt2_deleteNonExistingBookingTest() {
        given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + bookingToken)
                .when()
                .delete("booking/23551998006")
                .then()
                .statusCode(405);
    }

    @Test
    public void tt3_updateNonExistingBookingTest() {

        given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + bookingToken)
                .pathParam("bookingId", bookingId)
                .when()
                .put("booking/{bookingId}")
                .then()
                .statusCode(400);
    }


}
