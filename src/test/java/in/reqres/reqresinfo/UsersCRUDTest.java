package in.reqres.reqresinfo;

import in.reqres.testbase.TestBase;
import in.reqres.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UsersCRUDTest extends TestBase {
    static String name = "morpheus" + TestUtils.getRandomValue();
    static String job = "leader" + TestUtils.getRandomValue();
    static String email = "eve.holt@reqres.in";
    static String password = "cityslicka";
    static String userID;
    static String token;
    @Steps
    UsersSteps usersSteps;

    @Title("This will create a user")
    @Test
    public void test001() {
        ValidatableResponse response = usersSteps.createUser(name, job);
        response.log().all().statusCode(201);
        userID = response.log().all().extract().path("id");
        System.out.println(userID);
    }


    @Title("Verify is User is added or not")
    @Test
    public void test002() {
        HashMap<String,?> userMap= usersSteps.getProductInfoByName(userID);
        Assert.assertThat(userMap,hasValue(name));
        System.out.println(userID);
    }

    @Title("This will login a user")
    @Test
    public void test003() {
        HashMap<String, ?> tokenMap = usersSteps.loginUser(email, password);
        Assert.assertThat(tokenMap, hasKey("token"));
        System.out.println(tokenMap);
    }

    @Title("This will update a user by PUT")
    @Test
    public void test004() {
        name = name + "_updatedbyPut";
        ValidatableResponse response = usersSteps.updateUserByPut(userID, name, job);
        response.log().all().statusCode(200);

    }

    @Title("This will update a user by Patch")
    @Test
    public void test005() {
        name = name + "_updatedbyPatch";
        ValidatableResponse response = usersSteps.updateUserByPatch(userID, name, job);
        response.log().all().statusCode(200);
    }

    @Title("This will update a user by Patch")
    @Test
    public void test006() {
        ValidatableResponse response = usersSteps.deleteProduct(userID);
        response.log().all().statusCode(204);
    }

}
