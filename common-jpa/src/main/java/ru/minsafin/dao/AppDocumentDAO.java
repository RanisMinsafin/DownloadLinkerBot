package ru.minsafin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.minsafin.entity.AppDocument;

public interface AppDocumentDAO extends JpaRepository<AppDocument, Long> {
}
