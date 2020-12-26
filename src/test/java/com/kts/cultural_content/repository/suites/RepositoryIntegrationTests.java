package com.kts.cultural_content.repository.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.kts.cultural_content.repository.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthorityRepositoryIntegrationTest.class
})
public class RepositoryIntegrationTests {
}
