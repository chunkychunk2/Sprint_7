import dto.CourierRq;
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

public class LoginCourierSuccessTest extends Steps {

    @Test
    @DisplayName("Авторизация курьера")
    @Description("успешный запрос возвращает ok: true")
    public void login() {
        String login = RandomStringUtils.randomAlphabetic(5);
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(login, password, firstName);
        sendHttpPostPojoRq("/api/v1/courier", courier).then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        Map<String, String> requestData = new HashMap<>();
        requestData.put("login", login);
        requestData.put("password", password);
        Response response = sendHttpPostRq("/api/v1/courier/login", requestData);
        response.then().assertThat().statusCode(200);
        String id = response.body().jsonPath().getString("id");
        MatcherAssert.assertThat(id, notNullValue());

        sendHttpDeleteRq("/api/v1/courier/", id)
                .then().statusCode(200).assertThat().body("ok", equalTo(true));
    }


}
