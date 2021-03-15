package com.languages.learningapi.services;

import com.languages.learningapi.models.Login;

public interface ILoginService {
    Login findWithRoleByUsername(String username);
}
