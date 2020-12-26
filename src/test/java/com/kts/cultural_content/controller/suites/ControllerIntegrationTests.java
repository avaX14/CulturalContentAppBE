package com.kts.cultural_content.controller.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.kts.cultural_content.controller.*;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthencticationControllerIntegrationTest.class,
        UserControllerIntegrationTest.class
})
public class ControllerIntegrationTests {
}
