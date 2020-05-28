//______________TESTY LOGIN____________

package com.example.traveleoo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RegisterPresenterTest {

    @Mock
    private RegisterView view;
    @Mock
    private Register service;
    private RegisterPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new RegisterPresenter(view, service);
    }
    // EMAIL TEST
    @Test
    public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception {
        //Kiedy nie wpiszemy maila => error:
        when(view.getEmail()).thenReturn("");

        // CTRL+SHIFT+F10 odpala test
        presenter.onRegisterClicked();

        //email_error zapisany w strings
        verify(view).showEmailError(R.string.email_error);
    }

    // PASSWORD TEST
    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        //when email is typed
        when(view.getEmail()).thenReturn("xxxxxxxx");
        //Kiedy nie wpiszemy hasła => error:
        when(view.getPassword()).thenReturn("");
        // CTRL+SHIFT+F10 odpala test
        presenter.onRegisterClicked();

        //email_error zapisany w strings
        verify(view).showPasswordError(R.string.password_error);
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordNotPassword2() throws Exception {
        when(view.getEmail()).thenReturn("xxxxxxxx");
        when(view.getPassword2()).thenReturn("");
        presenter.onRegisterClicked();

        verify(view).showPassword2Error(R.string.password_error);
    }

    //TEST SUCCESS START MAIN ACTIVITY
    @Test
    public void shouldStartMainActivityWhenUsernameAndPasswordAreCorrect() throws Exception {
        when(view.getEmail()).thenReturn("xxxxxxxx");
        when(view.getPassword()).thenReturn("aaaaa");

        // SPRAWDZANIE POPRAWNOSCI PO ZALOGOWANIU SIE
        when(service.fAuth.signInWithEmailAndPassword("xxxxxxxx", "aaaaa"));
        presenter.onRegisterClicked();

        verify(view).startMainActivity();
    }

    //TEST ERROR MAIN ACTIVITY
    @Test
    public void shouldStartMainActivityWhenUsernameAndPasswordAreIncorrect() throws Exception {
        when(view.getEmail()).thenReturn("xxxxxxxx");
        when(view.getPassword()).thenReturn("aaaaa");

        // SPRAWDZANIE POPRAWNOSCI PO ZALOGOWANIU SIE
//        when(service.fAuth.signInWithEmailAndPassword("a@a.pl", "aaaaaa"));
//        presenter.onLoginClicked();
//
//        verify(view).showLoginError(R.string.login_failed);

    }

}