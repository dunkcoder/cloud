/**
 * Copyright (C) 2004-2015 http://oss.minlia.com/license/solution/usercenter/2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minlia.cloud.infrastructure.framework.usercenter.config;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ResponseBodyExtractionOptions;
import com.minlia.cloud.infrastructure.framework.usercenter.constants.ApiConstants;
import com.minlia.cloud.infrastructure.framework.usercenter.test.AbstractIntegrationTest;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@DatabaseSetup("classpath:testdata/auth.xml")
public class OAuth2ConfigurationTest extends AbstractIntegrationTest {

    @Before
    public void setUp() {
        RestAssured.port = getPort();
    }


    /**
     * Access to a protected url, without having been authenticated before, is forbidden.
     */
    @Test
    public void accessForbiddenToProtectedResource() {
        given()
                .queryParam("currentUser", true)
                .get(ApiConstants.API_PREFIX + "/user")
                .then()
                .statusCode(401);
    }


    /**
     * Requesting an access token without the client application identifying itself is forbidden.
     */
    @Test
    public void requestAccessTokenNoClientCredentialsAndFail() {
        given()
                .formParam("grant_type", "password")
                .formParam("username", "admin")
                .formParam("password", "admin")
                .post("/oauth/token")
                .then()
                .statusCode(401);
    }


    /**
     * Requesting an access token with the wrong user credentials is forbidden.
     */
    @Test
    public void requestAccessTokenWrongPasswordAndFail() {
        given()
                .header("Authorization", "Basic Ym9vdF93ZWJhcHA6NTlkMTRmMDEtMzhkYS00MDFjLTgwMTQtYjZjMDM1NjI3MWM4")
                .formParam("grant_type", "password")
                .formParam("username", "admin")
                .formParam("password", "wrongPassword")
                .post("/oauth/token")
                .then()
                .statusCode(400)
                .extract().body().asString();
    }


    /**
     * Requesting an access token with the right user credentials and client credentials
     * is successful.
     */
    @Test
    public void requestAccessTokenSuccessful() {
        ResponseBodyExtractionOptions body = given()
                .header("Authorization", "Basic Ym9vdF93ZWJhcHA6NTlkMTRmMDEtMzhkYS00MDFjLTgwMTQtYjZjMDM1NjI3MWM4")
                .formParam("grant_type", "password")
                .formParam("username", "admin")
                .formParam("password", "admin")
                .post("/oauth/token")
                .then()
                .statusCode(200)
                .extract().body();

        assertNotNull(body.jsonPath().get("access_token"));
        assertNotNull(body.jsonPath().get("refresh_token"));
        assertEquals("bearer", body.jsonPath().get("token_type"));
        assertEquals("read write", body.jsonPath().get("scope"));
    }


    /**
     * Using an access token to request a protected resource is successful.
     */
    @Test
    public void accessTokenCanAccessProtectedResource() {
        String accessToken = authenticate();

        given()
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("currentUser", true)
                .get(ApiConstants.API_PREFIX + "/profile")
                .then()
                .statusCode(200)
                .extract().body();
    }


    /**
     * After a change password operation, requesting a new access token with the new password
     * should be successful.
     */
    @Test
    public void requestAccessTokenAfterPwdChangeSuccessful() {
        String newPassword = "newPassword";
        String accessToken = authenticate();

        // Change password
        given()
                .formParam("oldPassword", "admin")
                .formParam("newPassword", newPassword)
                .header("Authorization", "Bearer " + accessToken)
                .post(ApiConstants.API_PREFIX + "/profile/password")
                .then()
                .log()
                .all()
                .statusCode(200);

        // Test that new password works
        given()
                .header("Authorization", "Basic Ym9vdF93ZWJhcHA6NTlkMTRmMDEtMzhkYS00MDFjLTgwMTQtYjZjMDM1NjI3MWM4")
                .formParam("grant_type", "password")
                .formParam("username", "admin")
                .formParam("password", newPassword)
                .post("/oauth/token")
                .then()
                .statusCode(200);
    }

}
