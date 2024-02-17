package ru.minsafin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.minsafin.entity.RawData;

public interface RawDataDAO extends JpaRepository<RawData, Long> {
}
