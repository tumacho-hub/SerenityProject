package b22.spartan.admin;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static net.serenitybdd.rest.SerenityRest.given;

@Disabled
@SerenityTest
public class SpartanAdminGetTest {

    @BeforeAll
    public static void init(){
        //save base url inside this variable so that we dont need to type each htttp method.

        baseURI = "http://100.26.48.87:8000";
    }

    @Test
    public void getAllSpartan(){
        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
        .when()
                .get("/api/spartans")
        .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);




    }
    @Test
    public void test2(){
//        given()
//                .accept(ContentType.JSON)
//                .and()
//                .auth().basic("admin","admin")
//                .pathParam("id",2)
//                .when()
//                .get("api/spartans{id}")
//                .then()
//                .statusCode(200)
//                .and()
//                .contentType(ContentType.JSON);
        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")
                .pathParam("id",2)
                .when()
                .get("api/spartans{id}");
        //if you send a request using SerenityRest, the response object
        //can be obtained from the method called lastResponse() without being saved separately
        //same with response object

        System.out.println("StatusCode = " + lastResponse().statusCode());

        //Print id
        //instead of response.path we use lastResponse.path() method

        System.out.println("lastResponse().path(\"id\") = " + lastResponse().path("id"));

        //use jsonpath with lastResponse and get the name
        String name = lastResponse().jsonPath().getString("name");
        System.out.println("name = " + name);


    }

    @DisplayName("GET request with Serenity Assertion way")
    @Test
    public void test3(){

        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")
                .pathParam("id", 15)
                .when()
                .get("api/spartans{id}");


        //serenity way of assertion, also a cool feature is that if one assertion  fails it will continue execution

        Ensure.that("Status code is 200",validatableResponse -> validatableResponse.statusCode(200));

        Ensure.that("Content-type is JSON", type -> type.contentType(ContentType.JSON));

        Ensure.that("Id is 15", id -> id.body("id",is(38)));


    }

}
