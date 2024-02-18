package ru.minsafin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.minsafin.entity.AppPhoto;

public interface AppPhotoDAO extends JpaRepository<AppPhoto, Long> {
}
