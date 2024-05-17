package org.example.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.smallrye.mutiny.Uni;
import org.example.model.Superhero;
import org.example.services.SuperheroService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class SuperheroResourceTests {
    @InjectMock
    SuperheroService superheroService;

    @Test
    public void When_GetList_Should_ReturnDataFromService() {
        List<Superhero> mockSuperheroes = Arrays.asList(new Superhero("Batman", "Bruce Wayne"), new Superhero("Superman", "Clark Kent"));
        Mockito.when(superheroService.GetAllAsync()).thenReturn(Uni.createFrom().item(mockSuperheroes));

        RestAssured.given()
                .when().get("/superhero/list")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", is(2))
                .body("[0].name", is("Batman"))
                .body("[1].name", is("Superman"));
    }

    @Test
    public void When_GetSuperhero_WithExistentId_Should_ReturnCorrectData() {
        Superhero mockSuperhero = new Superhero("Superman", "Clark Kent");
        Mockito.when(superheroService.GetAsync(1)).thenReturn(Uni.createFrom().item(mockSuperhero));

        RestAssured.given()
                .when().get("/superhero/get/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", is("Superman"));
    }

    @Test
    public void When_GetSuperhero_WithNonExistentId_Should_ReturnNotFoundStatus() {
        Mockito.when(superheroService.GetAsync(1)).thenReturn(Uni.createFrom().nullItem());

        RestAssured.given()
                .when().get("/superhero/get/1")
                .then()
                .statusCode(404);
    }
}
