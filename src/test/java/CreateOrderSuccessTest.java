import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.OrdersRq;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderSuccessTest extends Steps {

    String json = "{\n" +
            "  \"firstName\": \"Naruto\",\n" +
            "  \"lastName\": \"Uchiha\",\n" +
            "  \"address\": \"Konoha, 142 apt.\",\n" +
            "  \"metroStation\": 4,\n" +
            "  \"phone\": \"+7 800 355 35 35\",\n" +
            "  \"rentTime\": 5,\n" +
            "  \"deliveryDate\": \"2020-06-06\",\n" +
            "  \"comment\": \"Saske, come back to Konoha\",\n" +
            "  \"color\": [\n" +
            "    \"BLACK\"\n" +
            "  ]\n" +
            "}";

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    @DisplayName("Создание заказа")
    @Description("В теле ответа возвращается параметр track")
    public void createOrder() {

        OrdersRq order = gson.fromJson(json, OrdersRq.class);
        sendHttpPostPojoRq("/api/v1/orders", order).then().assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа c цветом GRAY")
    @Description("В теле ответа возвращается параметр track")
    public void createOrderWithGreyColor() {
        String firstName = RandomStringUtils.randomAlphabetic(5);
        String lastName = RandomStringUtils.randomAlphabetic(5);
        String address = RandomStringUtils.randomAlphabetic(5);
        String metroStation = String.valueOf(new Random().nextInt(5));
        String phone = "+7 800 " + RandomStringUtils.randomNumeric(3) + " 55 55";
        Number rentTime = new Random().nextInt(9);
        String deliveryDate = "2020-06-1" + new Random().nextInt(9);
        String comment = RandomStringUtils.randomAlphabetic(5);
        String[] color = {"GRAY"};
        OrdersRq order = new OrdersRq(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        sendHttpPostPojoRq("/api/v1/orders", order).then().assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа c цветом BLACK и GRAY")
    @Description("В теле ответа возвращается параметр track")
    public void createOrderWithBothColors() {
        String firstName = RandomStringUtils.randomAlphabetic(5);
        String lastName = RandomStringUtils.randomAlphabetic(5);
        String address = RandomStringUtils.randomAlphabetic(5);
        String metroStation = String.valueOf(new Random().nextInt(5));
        String phone = "+7 800 " + RandomStringUtils.randomNumeric(3) + " 55 55";
        Number rentTime = new Random().nextInt(9);
        String deliveryDate = "2020-06-1" + new Random().nextInt(9);
        String comment = RandomStringUtils.randomAlphabetic(5);
        String[] color = {"GRAY", "BLACK"};
        OrdersRq order = new OrdersRq(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        sendHttpPostPojoRq("/api/v1/orders", order).then().assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без выбора цвета")
    @Description("В теле ответа возвращается параметр track")
    public void createOrderWithOutColors() {
        String firstName = RandomStringUtils.randomAlphabetic(5);
        String lastName = RandomStringUtils.randomAlphabetic(5);
        String address = RandomStringUtils.randomAlphabetic(5);
        String metroStation = String.valueOf(new Random().nextInt(5));
        String phone = "+7 800 " + RandomStringUtils.randomNumeric(3) + " 55 55";
        Number rentTime = new Random().nextInt(9);
        String deliveryDate = "2020-06-1" + new Random().nextInt(9);
        String comment = RandomStringUtils.randomAlphabetic(5);
        OrdersRq order = new OrdersRq(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, null);
        sendHttpPostPojoRq("/api/v1/orders", order).then().assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
