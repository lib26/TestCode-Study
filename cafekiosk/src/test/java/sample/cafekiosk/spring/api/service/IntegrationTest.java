package sample.cafekiosk.spring.api.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.client.mail.MailSendClient;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTest {

    // OrderStatisticsServiceTest에서만 사용된다.
    // 상위 클래스로 따로 빼서 환경을 동일하게 만든다.
    @MockBean
    protected MailSendClient mailSendClient;
}
