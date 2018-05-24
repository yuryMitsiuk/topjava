package ru.javawebinar.topjava.web.user;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.EmailDuplicateException;

import javax.validation.Valid;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
    static final String REST_URL = "/rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(AuthorizedUser.id());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody UserTo userTo) {
        try {
            super.update(userTo, AuthorizedUser.id());
        } catch (DataIntegrityViolationException ex) {
            throw new EmailDuplicateException(messageSource.getMessage(EMAIL_DUPLICATE_MESSAGE, null, LocaleContextHolder.getLocale()));
        }
    }

    @GetMapping(value = "/text")
    public String testUTF() {
        return "Русский текст";
    }
}