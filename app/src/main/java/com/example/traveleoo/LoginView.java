//______________TESTY LOGIN____________

package com.example.traveleoo;

public interface LoginView {

    //EMAIL TEST
    String getEmail();

    void showEmailError(int resId);

    String getPassword();


    public void onLoginClicked();

    //PASSWORD TEST

    void showPasswordError(int resId);


    // SPRAWDZANIE POPRAWNOSCI PO ZALOGOWANIU SIE
    void startMainActivity();

    void showLoginError(int resId);


}
