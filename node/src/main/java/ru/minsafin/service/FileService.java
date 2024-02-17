package ru.minsafin.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.minsafin.entity.AppDocument;

public interface FileService {
    AppDocument processDoc(Message externalMessage);
}
