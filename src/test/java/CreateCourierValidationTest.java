import dto.CourierRq;
import dto.CourierRs;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateCourierValidationTest extends Steps {

    @Test
    @DisplayName("Создание двух курьеров")
    @Description("возвращается сообщение, что логин уже используется")
    public void createTwoCouriers() {
        String login = RandomStringUtils.randomAlphabetic(5);
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(login, password, firstName);
        sendHttpPostPojoRq("/api/v1/courier", courier).then().statusCode(201).assertThat()
                .body("ok", equalTo(true));
        sendHttpPostPojoRq("/api/v1/courier", courier).then().statusCode(409).assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));

        Map<String, String> requestData = new HashMap<>();
        requestData.put("login", login);
        requestData.put("password", password);
        String id = sendHttpPostRq("/api/v1/courier/login", requestData).body().jsonPath().getString("id");
        sendHttpDeleteRq("/api/v1/courier/", id)
                .then().statusCode(200).assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без password")
    @Description("возвращается сообщение, что недостаточно данных для создания учетной записи")
    public void createCourierWithOutPassword() {
        String login = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(login, null, firstName);
        sendHttpPostPojoRq("/api/v1/courier", courier).then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без login")
    @Description("возвращается сообщение, что недостаточно данных для создания учетной записи")
    public void createCourierWithOutLogin() {
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(null, password, firstName);
        sendHttpPostPojoRq("/api/v1/courier", courier).then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка схемы POJO по документации")
    @Description("При создании курьера без параметра login В ответе возвращается только параметр message")
    public void checkDto() {
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(null, password, firstName);

        CourierRs responseBody = sendHttpPostPojoRq("/api/v1/courier", courier).body().as(CourierRs.class);

        MatcherAssert.assertThat(responseBody, notNullValue());
    }
}
