package ru.minsafin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.minsafin.entity.BinaryContent;

public interface BinaryContentDAO extends JpaRepository<BinaryContent, Long> {
}
