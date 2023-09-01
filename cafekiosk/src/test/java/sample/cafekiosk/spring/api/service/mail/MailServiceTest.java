package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * service 단위 테스트
 *
 * @ExtendWith(MockitoExtension.class)
 * 테스트 시작시점에 Mockito를 사용해서 @Mock 객체를 만들거라고 알려줌
 *
 * @InjectMocks
 * MailService의 생성자를 보고, @Mock으로 생성된 객체를 주입해준다.
 * 결국 new를 사용하여 수동으로 생성해줄 필요 없고, mock 객체 또한 자동으로 주입해줌
 *
 * @Spy
 * Mock과는 다른 테스트 대역이다.
 * 실제 객체를 기반으로 만들어진다. 따라서 해당 spy 객체로 when이나 given으로 행위를 stubbing하면 안된다.
 * 대신에 doReturn 문법을 사용해야한다.
 */
@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Spy
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given

//         Mock 객체를 사용할 경우
//         Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//            .thenReturn(true);

//         BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .willReturn(true);

        doReturn(true)
                .when(mailSendClient)
                .sendEmail(anyString(), anyString(), anyString(), anyString());

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }

}