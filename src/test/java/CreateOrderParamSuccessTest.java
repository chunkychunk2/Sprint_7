import dto.OrdersRq;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParamSuccessTest extends Steps {


    String firstName;

    String lastName;

    String address;

    String metroStation;

    String phone;

    Number rentTime;

    String deliveryDate;

    String comment;

    String[] color;

    public CreateOrderParamSuccessTest(String firstName, String lastName, String address, String metroStation, String phone, Number rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getData(){
       return new Object[][]{
               {"Rick", "Sanchez","Konoha, 141 apt.", "3","+7 915 355 35 35",5, "2020-06-06","Saske, come back to Konoha",new String[]{"GRAY"}},
               {"Morty", "Smith", "Konoha, 142 apt.", "4", "+7 916 355 35 35",3,"2020-06-05","Saske, come back again to Konoha",new String[]{"BLACK"}}
       };
    }

    @Test
    @DisplayName("Создание заказа c цветом BLACK и GRAY")
    @Description("В теле ответа возвращается параметр track")
    public void createOrderWithBothColors() {
        OrdersRq order = new OrdersRq(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        sendHttpPostPojoRq("/api/v1/orders", order).then().assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
