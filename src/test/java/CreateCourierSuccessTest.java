import dto.CourierRq;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierSuccessTest extends Steps {

    @Test
    @DisplayName("Создание курьера")
    @Description("успешный запрос возвращает ok: true")
    public void createCourier() {
        String login = RandomStringUtils.randomAlphabetic(5);
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(login, password, firstName);
        sendHttpPostPojoRq("/api/v1/courier", courier).then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без firstName")
    @Description("успешный запрос возвращает ok: true")
    public void createCourierWithOutName() {
        String login = RandomStringUtils.randomAlphabetic(5);
        String password = RandomStringUtils.randomAlphabetic(5);
        CourierRq courier = new CourierRq(login, password, null);
        sendHttpPostPojoRq("/api/v1/courier", courier).then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        Map<String, String> requestData = new HashMap<>();
        requestData.put("login", login);
        requestData.put("password", password);
        String id = sendHttpPostRq("/api/v1/courier/login", requestData).body().jsonPath().getString("id");
        sendHttpDeleteRq("/api/v1/courier/", id)
                .then().statusCode(200).assertThat().body("ok", equalTo(true));
    }
}
