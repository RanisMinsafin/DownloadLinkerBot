package ru.minsafin.service;

import ru.minsafin.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
