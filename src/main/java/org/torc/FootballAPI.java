package org.torc;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Test
public class FootballAPI {

    @BeforeTest
    public void init() {
        baseURI = "http://api.football-data.org/v4";
    }

    @Test
    public void validateAmountOfCompetitionsWithoutFilters() {
        given()
                .get("/competitions/").
        then()
                .statusCode(200)
                .body("count", equalTo(167))
                .log()
                .all();
    }

    @Test
    public void validateBodySchemaForListOfAreasFiltered(){
        given()
                .get("/areas/2220").
        then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("area-schema.json"))
                .log()
                .all();
    }

    @Test
    public void validateScheduledMatchesForCampeonatoBrasileiroSerieA(){
        given()
                .get("/competitions/2013/matches")
        .then()
                .assertThat().statusCode(403)
                .log()
                .all();
    }

    @Test
    public void validateThatDoesntExistsListOfPersons(){
        given()
                .get("/persons")
                .then()
                .assertThat().statusCode(404)
                .log()
                .all();
    }
}