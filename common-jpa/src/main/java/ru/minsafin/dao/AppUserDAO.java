package ru.minsafin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.minsafin.entity.AppUser;

public interface AppUserDAO extends JpaRepository<AppUser, Long> {
    AppUser findAppUsersById(Long id);
}
