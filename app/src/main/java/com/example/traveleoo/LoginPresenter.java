//______________TESTY LOGIN____________

package com.example.traveleoo;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginPresenter extends Login{
    private LoginView view;
    private Login service;

    public LoginPresenter(LoginView view, Login service) {
        this.view = view;
        this.service = service;
    }

    public void onLoginClicked(){
        String email = view.getEmail();
        if (email.isEmpty()) {
            view.showEmailError(R.string.email_error);
            return;
        }
        String password = view.getPassword();
        if(password.isEmpty()) {
            view.showPasswordError(R.string.password_error);
            return;
        }
        // SPRAWDZANIE POPRAWNOSCI PO ZALOGOWANIU SIE
        Task<AuthResult> loginSucceed = service.fAuth.signInWithEmailAndPassword(email, password);
            view.startMainActivity();
            return;
        //Wyżej powinno być .....(email,password).getResult(true);
//        view.showLoginError(R.string.login_failed);
    }
}
