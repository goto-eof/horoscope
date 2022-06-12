package org.andreidodu.horoscope;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = HoroscopeApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
class HoroscopeApplicationTests {

}
