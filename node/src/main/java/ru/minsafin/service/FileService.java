package ru.minsafin.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.minsafin.entity.AppDocument;
import ru.minsafin.entity.AppPhoto;
import ru.minsafin.enums.LinkType;

public interface FileService {
    AppDocument processDoc(Message message);
    AppPhoto processPhoto(Message message);
    String generateLink(Long docId, LinkType linkType);
}
