package com.ezraaudivano.kostku.UnitTestRegister;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class RegisterPresenterTest {
    @Mock
    private RegisterView view;
    @Mock
    private RegisterService service;
    private RegisterPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new RegisterPresenter(view, service);
    }

    @Test
    public void shouldShowErrorMessageWhenNameIsEmpty() throws Exception {
        System.out.println("Test Case - 1 : Name is Empty");
        when(view.getNama()).thenReturn("");
        System.out.println("Name : "+view.getNama());
        presenter.onRegisterClicked();
        verify(view).showNamaError("Nama Tidak Boleh Kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenEmailIsEmpty() throws Exception {
        System.out.println("Test Case - 2 : Email is Empty");
        when(view.getNama()).thenReturn("Ezra Audivano");
        System.out.println("Name : "+view.getNama());
        when(view.getEmail()).thenReturn("");
        System.out.println("Email : "+view.getEmail());
        presenter.onRegisterClicked();
        verify(view).showEmailError("Email Tidak Boleh Kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        System.out.println("Test Case - 3 : Password is Empty");
        when(view.getNama()).thenReturn("Ezra Audivano");
        System.out.println("Name : "+view.getNama());
        when(view.getEmail()).thenReturn("ezraaudivano@gmail.com");
        System.out.println("Email : "+ view.getEmail());
        when(view.getPassword()).thenReturn("");
        System.out.println("Password : "+view.getPassword());
        presenter.onRegisterClicked();
        verify(view).showPasswordError("Password Tidak Boleh Kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenRePasswordIsEmpty() throws Exception {
        System.out.println("Test Case - 4 : Re-Password is Empty");
        when(view.getNama()).thenReturn("Ezra Audivano");
        System.out.println("Name : "+view.getNama());

        when(view.getEmail()).thenReturn("ezraaudivano@gmail.com");
        System.out.println("Email : "+ view.getEmail());

        when(view.getPassword()).thenReturn("ezradio1");
        System.out.println("Password : "+view.getPassword());

        when(view.getRePassword()).thenReturn("");
        System.out.println("Password : "+view.getRePassword());

        presenter.onRegisterClicked();
        verify(view).showRePassError("Re-Password Tidak Boleh Kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordAndRePasswordDoesntMatch() throws Exception {
        System.out.println("Test Case - 5 : Re-Password is Empty");
        when(view.getNama()).thenReturn("Ezra Audivano");
        System.out.println("Name : "+view.getNama());

        when(view.getEmail()).thenReturn("ezraaudivano@gmail.com");
        System.out.println("Email : "+ view.getEmail());

        when(view.getPassword()).thenReturn("ezradio1");
        System.out.println("Password : "+view.getPassword());

        when(view.getRePassword()).thenReturn("ezradio2");
        System.out.println("Password : "+view.getRePassword());

        presenter.onRegisterClicked();
        verify(view).showRePassError("Password dan RePassword Tidak Sama");
    }

    @Test
    public void shouldStartMainActivityWhenEmailAndPasswordAreCorrect() throws
            Exception {
        System.out.println("Test Case - 6 : Valid Email, Name, Password, and Re-Password");
        when(view.getEmail()).thenReturn("ezraaudivano@gmail.com");
        System.out.println("Email : "+view.getEmail());
        when(view.getNama()).thenReturn("Ezra Audivano Dirfa");
        System.out.println("Nama : "+view.getNama());
        when(view.getPassword()).thenReturn("ezradio1");
        System.out.println("Password : "+view.getPassword());
        when(view.getRePassword()).thenReturn("ezradio1");
        System.out.println("Re - Password : "+view.getRePassword());

        when(service.getValid(view, view.getEmail(), view.getNama(),
                view.getPassword() ,view.getRePassword())).thenReturn(true);
        System.out.println("Hasil : "+service.getValid(view, view.getEmail(), view.getNama(),
                view.getPassword() ,view.getRePassword()));
        presenter.onRegisterClicked();
        //verify(view).startMainActivity();
    }




}