package ru.minsafin.service;

import ru.minsafin.entity.AppUser;

public interface AppUserService {
    String registerUser(AppUser appUser);
    String setEmail(AppUser user, String email);
}
