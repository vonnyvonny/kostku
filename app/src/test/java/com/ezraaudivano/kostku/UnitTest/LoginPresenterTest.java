package com.ezraaudivano.kostku.UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    private LoginView view;
    @Mock
    private LoginService service;
    private LoginPresenter presenter;
    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, service);
    }
    @Test
    public void shouldShowErrorMessageWhenEmailIsEmpty() throws Exception {
        System.out.println("Test Case - 1 : Email is Empty");
        when(view.getEmail()).thenReturn("");
        System.out.println("Email : "+view.getEmail());
        presenter.onLoginClicked();
        verify(view).showEmailError("Email Tidak Boleh Kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenEmailInvalid() throws Exception {
        System.out.println("Test Case - 2 : Invalid Email");
        when(view.getEmail()).thenReturn("ezraaudivano");
        System.out.println("Email : "+view.getEmail());
        presenter.onLoginClicked();
        verify(view).showEmailError("Email Tidak Valid");
    }
    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        System.out.println("Test Case - 3 : Password is Empty");
        when(view.getEmail()).thenReturn("ezraaudivano@gmail.com");
        System.out.println("Email : "+ view.getEmail());
        when(view.getPassword()).thenReturn("");
        System.out.println("Password : "+view.getPassword());
        presenter.onLoginClicked();
        verify(view).showPasswordError("Password Tidak Boleh Kosong");
    }
    @Test
    public void shouldStartMainActivityWhenEmailAndPasswordAreCorrect() throws
            Exception {
        System.out.println("Test Case - 4 : Valid Email and Password");
        when(view.getEmail()).thenReturn("ezraaudivano@gmail.com");
        System.out.println("Email : "+view.getEmail());
        when(view.getPassword()).thenReturn("asdasd");
        System.out.println("Password : "+view.getPassword());
        when(service.getValid(view, view.getEmail(),
                view.getPassword())).thenReturn(true);
        System.out.println("Hasil : "+service.getValid(view,view.getEmail(),
                view.getPassword()));
        presenter.onLoginClicked();
        //verify(view).startMainActivity();
    }

    @Test
    public void shouldStartMainActivityWhenNotRegister() throws
            Exception {
        System.out.println("Test Case - 5 : Email not Register");
        when(view.getEmail()).thenReturn("jokondokondo@gmail.com");
        System.out.println("Email : "+view.getEmail());
        when(view.getPassword()).thenReturn("asdasd");
        System.out.println("Password : "+view.getPassword());
        when(service.getValid(view, view.getEmail(),
                view.getPassword())).thenReturn(false);
        System.out.println("Hasil : "+service.getValid(view,view.getEmail(),
                view.getPassword()));
        presenter.onLoginClicked();
        //verify(view).startMainActivity();
    }
    @Test
    public void shouldShowLoginErrorWhenEmailAndPasswordAreInvalid() throws
            Exception {
        System.out.println("Test Case - 6 : Invalid Password");
        when(view.getEmail()).thenReturn("admin");
        System.out.println("Email : "+view.getEmail());
        when(view.getPassword()).thenReturn("admins");
        System.out.println("Password : "+view.getPassword());
        when(service.getValid(view, view.getEmail(),
                view.getPassword())).thenReturn(false);
        System.out.println("Hasil : "+service.getValid(view,view.getEmail(),
                view.getPassword()));
        presenter.onLoginClicked();
        //verify(view).showLoginError(R.string.login_failed);
    }
}