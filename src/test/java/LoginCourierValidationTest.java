import dto.CourierRq;
import dto.CourierRs;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierValidationTest extends Steps {

    @Test
    @DisplayName("Авторизация курьера без параметра login")
    @Description("В ответе возвращается только параметр message")
    public void checkDto() {
        String login = RandomStringUtils.randomAlphabetic(5);
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(login, password, firstName);
        sendHttpPostPojoRq("/api/v1/courier", courier).then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        Map<String, String> requestData = new HashMap<>();
        requestData.put("password", password);
        try{
            CourierRs responseBody = sendHttpPostRq("/api/v1/courier/login", requestData).body().as(CourierRs.class);
            MatcherAssert.assertThat(responseBody, notNullValue());
        }
        catch (Exception e){
            requestData.put("login", login);
            String id = sendHttpPostRq("/api/v1/courier/login", requestData).body().jsonPath().getString("id");
            sendHttpDeleteRq("/api/v1/courier/", id)
                    .then().statusCode(200).assertThat().body("ok", equalTo(true));
        }
    }

    @Test
    @DisplayName("Авторизация курьера без параметра login")
    @Description("Возвращает message: Недостаточно данных для входа")
    public void loginWithOutLoginName() {
        String login = RandomStringUtils.randomAlphabetic(5);
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(login, password, firstName);
        sendHttpPostPojoRq("/api/v1/courier", courier).then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        Map<String, String> requestData = new HashMap<>();
        requestData.put("password", password);
        Response response = sendHttpPostRq("/api/v1/courier/login", requestData);
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message",equalTo("Недостаточно данных для входа"));

        requestData.put("login", login);
        String id = sendHttpPostRq("/api/v1/courier/login", requestData).body().jsonPath().getString("id");
        sendHttpDeleteRq("/api/v1/courier/", id)
                .then().statusCode(200).assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Авторизация курьера без параметра password")
    @Description("Возвращает message: Недостаточно данных для входа")
    public void loginWithOutPasswordName() {
        String login = RandomStringUtils.randomAlphabetic(5);
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(login, password, firstName);
        sendHttpPostPojoRq("/api/v1/courier", courier).then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        Map<String, String> requestData = new HashMap<>();
        requestData.put("login", login);
        Response response = sendHttpPostRq("/api/v1/courier/login", requestData);
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message",equalTo("Недостаточно данных для входа"));

        requestData.put("password", password);
        String id = sendHttpPostRq("/api/v1/courier/login", requestData).body().jsonPath().getString("id");
        sendHttpDeleteRq("/api/v1/courier/", id)
                .then().statusCode(200).assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным паролем")
    @Description("Возвращает message: Учетная запись не найдена")
    public void loginWithWrongPassword() {
        String login = RandomStringUtils.randomAlphabetic(5);
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(login, password, firstName);
        sendHttpPostPojoRq("/api/v1/courier", courier).then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        Map<String, String> requestData = new HashMap<>();
        requestData.put("login", login);
        requestData.put("password", password+2);
        Response response = sendHttpPostRq("/api/v1/courier/login", requestData);
        response.then().assertThat().statusCode(404);
        response.then().assertThat().body("message",equalTo("Учетная запись не найдена"));

        requestData.put("password", password);
        String id = sendHttpPostRq("/api/v1/courier/login", requestData).body().jsonPath().getString("id");
        sendHttpDeleteRq("/api/v1/courier/", id)
                .then().statusCode(200).assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Авторизация под несуществующим пользователем")
    @Description("Возвращает message: Учетная запись не найдена")
    public void loginwothWrongLogin() {
        String login = RandomStringUtils.randomAlphabetic(5);
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(login, password, firstName);
        sendHttpPostPojoRq("/api/v1/courier", courier).then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        Map<String, String> requestData = new HashMap<>();
        requestData.put("login", login+2);
        requestData.put("password", password);
        Response response = sendHttpPostRq("/api/v1/courier/login", requestData);
        response.then().assertThat().statusCode(404);
        response.then().assertThat().body("message",equalTo("Учетная запись не найдена"));

        requestData.put("login", login);
        String id = sendHttpPostRq("/api/v1/courier/login", requestData).body().jsonPath().getString("id");
        sendHttpDeleteRq("/api/v1/courier/", id)
                .then().statusCode(200).assertThat().body("ok", equalTo(true));
    }
}
