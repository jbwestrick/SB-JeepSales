package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.jeep.controller.support.FetchJeepTestSupport;
import com.promineotech.jeep.entity.JeepModel;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
    "classpath:flyway/migrations/V1.1__Jeep_Data.sql"}, config = @SqlConfig(encoding = "utf-8"))
class FetchJeepTest extends FetchJeepTestSupport {

  /**
  @Test
  void testThatJeepsAreReturnedWhenAValidModelAndTrimAreSupplied() {
    //Given: A valid model, trip and URI
    JeepModel model = JeepModel.WRANGLER;
    String trim = "Sport";
    String uri = String.format("%s?model=%s&trim=%s", getBaseUri(), model, trim);
    
    System.out.println(uri);
    
    //When: a connection is made to the URI
    ResponseEntity<List<Jeep>> response =
 getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}); 
 
    //Then: a success (OK - 200) status code is returned
 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); 

 //And: the actual list returned is the same as the expected list
 List<Jeep> actual = response.getBody();
 List<Jeep> expected = buildExpected();

 assertThat(actual).isEqualTo(expected);}
  */

  /**
   * 
   */
  @Test
  void testThatAnErrorMessageIsReturnedWhenAnUnknownTrimIsSupplied() {
    // Given: A valid model, trip and URI
    JeepModel model = JeepModel.WRANGLER;
    String trim = "Unknown Value";
    String uri = String.format("%s?model=%s&trim=%s", getBaseUriForJeeps(), model, trim);

    System.out.println(uri);

    // When: a connection is made to the URI
    ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, 
        HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

    // Then: a not found (404) status code is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    // And: an error message is returned
    Map<String, Object> error = response.getBody();
    
    assertErrorMessageValid(error, HttpStatus.NOT_FOUND);
  }
  /**
   * 
   */
    @ParameterizedTest 
    @MethodSource("com.promineotech.jeep.controller.FetchJeepTest#parametersForInvalidInput")
    void testThatAnErrorMessageIsReturnedWhenAnInvalidTrimIsSupplied(
        String model, String trim, String reason) {
      // Given: A valid model, trip and URI
         String uri = String.format("%s?model=%s&trim=%s", getBaseUriForJeeps(), model, trim);

      //System.out.println(uri);

      // When: a connection is made to the URI
      ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, 
          HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

      // Then: a not found (404) status code is returned
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

      // And: an error message is returned
      Map<String, Object> error = response.getBody();
      
      assertErrorMessageValid(error, HttpStatus.BAD_REQUEST);
    }
    
    static Stream<Arguments> parametersForInvalidInput(){
      return Stream.of(
          arguments("WRANGLER", "@#$%", "Trim contains non-alpha-numberic chars")
          );
    }
}
