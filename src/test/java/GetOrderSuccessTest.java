import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;

public class GetOrderSuccessTest extends Steps {

    @Test
    @DisplayName("Создание заказа")
    @Description("В теле ответа возвращается параметр track")
    public void createOrder() {
        Response response = sendHttpGetRq("/api/v1/orders");
        response.then().assertThat()
                .statusCode(200)
                .body("orders.size()", greaterThan(1));
    }
}
