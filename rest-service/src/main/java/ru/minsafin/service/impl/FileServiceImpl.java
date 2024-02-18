package ru.minsafin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import ru.minsafin.dao.AppDocumentDAO;
import ru.minsafin.dao.AppPhotoDAO;
import ru.minsafin.entity.AppDocument;
import ru.minsafin.entity.AppPhoto;
import ru.minsafin.entity.BinaryContent;
import ru.minsafin.service.FileService;
import ru.minsafin.utils.CryptoTool;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Log4j
public class FileServiceImpl implements FileService {
    private final AppDocumentDAO appDocumentDAO;
    private final AppPhotoDAO appPhotoDAO;
    private final CryptoTool cryptoTool;

    @Override
    public AppDocument getDocument(String docId) {
        var id = cryptoTool.idOf(docId);
        if (id == null) {
            return null;
        }
        return appDocumentDAO.getById(id);
    }

    @Override
    public AppPhoto getPhoto(String photoId) {
        var id = cryptoTool.idOf(photoId);
        if (id == null) {
            return null;
        }
        return appPhotoDAO.getById(id);
    }

    @Override
    public FileSystemResource getFileSystemResource(BinaryContent binaryContent) {
        try {
            // TODO генерация имен файлов
            File tempFile = File.createTempFile("tempFile", "bin");
            tempFile.deleteOnExit();
            FileUtils.writeByteArrayToFile(tempFile, binaryContent.getFileAsArrayOfBytes());
            return new FileSystemResource(tempFile);
        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}
