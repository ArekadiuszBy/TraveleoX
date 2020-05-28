
package com.example.traveleoo;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegisterPresenter extends Register{
    private RegisterView view;
    private Register service;

    public RegisterPresenter(RegisterView view, Register service) {
        this.view = view;
        this.service = service;
    }


    public void onRegisterClicked(){
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
        String password2 = view.getPassword2();
        if(password2.compareTo(password) != password.compareTo(password2)) {
            view.showPassword2Error(R.string.password2_error);
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
