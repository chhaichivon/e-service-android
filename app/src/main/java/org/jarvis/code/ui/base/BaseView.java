package org.jarvis.code.ui.base;

/**
 * Created by ki.kao on 10/4/2017.
 */

public interface BaseView {

    boolean isNetworkConnected();

    void hideKeyboard();

    void toastMessage(String message);

    void showSnackBar(String message);

    void showSweetAlert(int type, String title, String content);

}