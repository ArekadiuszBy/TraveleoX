package com.example.traveleoo;

interface RegisterView {

    //EMAIL TEST
    String getEmail();

    void showEmailError(int resId);

    String getPassword();
    String getPassword2();

    public void onRegisterClicked();

    //PASSWORD TEST

    void showPasswordError(int resId);

    void showPassword2Error(int resId);


    // SPRAWDZANIE POPRAWNOSCI PO ZALOGOWANIU SIE
    void startMainActivity();

    void showRegisterError(int resId);

}
