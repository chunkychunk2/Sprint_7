import dto.POJO;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Steps extends Hooks{

    @Step("Отправить GET запрос")
    public Response sendHttpGetRq(String endPoint) {
        return
                given()
                        .when()
                        .get(endPoint);
    }

    @Step("Отправить POST запрос")
    public Response sendHttpPostRq(String endPoint, Map<String, String> requestData) {
        return
                given()
                        .body(requestData)
                        .when()
                        .post(endPoint);
    }

    @Step("Отправить POST запрос c dto")
    public Response sendHttpPostPojoRq(String endPoint, POJO pojo) {
        return
                given()
                        .body(pojo)
                        .when()
                        .post(endPoint);
    }

    @Step("Отправить DELETE запрос")
    public Response sendHttpDeleteRq(String endPoint, String id) {
        return
                given()
                        .when()
                        .delete(endPoint + id);
    }
}
