package com.frameworkium.services;

import com.frameworkium.tests.internal.BaseTest;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by civie21 on 27/01/2016.
 */
public class ExampleServiceTests extends BaseTest {

    @Test()
    @Issue("1")
    public void test(){

        ExampleService service  = new ExampleService();

        String result = service.getName();

        assertThat(result).contains("Civ");


    }
}
