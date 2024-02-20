package ru.minsafin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.minsafin.dao.AppUserDAO;
import ru.minsafin.dto.MailParams;
import ru.minsafin.entity.AppUser;
import ru.minsafin.entity.enums.UserState;
import ru.minsafin.service.AppUserService;
import ru.minsafin.utils.CryptoTool;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
@RequiredArgsConstructor
@Log4j
public class AppUserServiceImpl implements AppUserService {
    private final AppUserDAO appUserDAO;
    private final CryptoTool cryptoTool;
    @Value("${service.mail.uri}")
    private String mailServiceUri;

    @Override
    public String registerUser(AppUser appUser) {
        if (appUser.getIsActive()) {
            return "Вы уже зарегистрированы";
        } else if (appUser.getEmail() != null) {
            return "Вам на почту уже было отправлено письмо." +
                    "Перейдите по ссылке в письме для потверждения регистрации.";
        }
        appUser.setState(UserState.WAIT_FOR_EMAIL_STATE);
        appUserDAO.save(appUser);
        return "Введите, пожалуйста, ваш email";
    }

    @Override
    public String setEmail(AppUser user, String email) {
        try {
            var emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            return "Введите, пожалуйста, корректный email. Для отмены команды введите /cancel";
        }

        var optional = appUserDAO.findByEmail(email);
        if (optional.isEmpty()) {
            user.setEmail(email);
            user.setState(UserState.BASIC_STATE);
            user = appUserDAO.save(user);

            var cryptoUserId = cryptoTool.hashOf(user.getId());
            var response = sendRequestToMailService(cryptoUserId, email);
            if (response.getStatusCode() != HttpStatus.OK) {
                var msg = String.format("Отправка эл. письма на почту %s не удалась.", email);
                log.error(msg);
                user.setEmail(null);
                appUserDAO.save(user);
                return msg;
            }
            return "Вам на почту было отправлено письмо." +
                    "Перейдите по ссылке в письме для потверждения регистрации.";
        } else {
            return "Этот email уже используется. Введите корректный email. " +
                    "Для отмены команды нажмите /cancel";
        }
    }

    private ResponseEntity<String> sendRequestToMailService(String cryptoUserId, String email) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var mailParams = MailParams.builder()
                .id(cryptoUserId)
                .emailTo(email)
                .build();
        var request = new HttpEntity<MailParams>(mailParams, headers);
        return restTemplate.exchange(mailServiceUri,
                HttpMethod.POST,
                request,
                String.class);
    }
}
