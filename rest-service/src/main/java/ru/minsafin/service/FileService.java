package ru.minsafin.service;

import org.springframework.core.io.FileSystemResource;
import ru.minsafin.entity.AppDocument;
import ru.minsafin.entity.AppPhoto;
import ru.minsafin.entity.BinaryContent;

public interface FileService {
    AppDocument getDocument(String id);
    AppPhoto getPhoto(String id);
    FileSystemResource getFileSystemResource(BinaryContent binaryContent);
}
