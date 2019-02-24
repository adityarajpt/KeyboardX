package com.itsadityaraj.keyboardx;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    public MyInputMethodService() {
        super();
    }
    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private Keyboard keyboard2;
    private Keyboard keyboard3;

    private boolean caps = false;
    private int mKeyboardState = R.integer.keyboard_normal;

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.key_layout);
        keyboard2 = new Keyboard(this, R.xml.key_layout_two);
        keyboard3 = new Keyboard(this, R.xml.key_layout_shift);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            switch(primaryCode) {
                case Keyboard.KEYCODE_MODE_CHANGE :
                    if(mKeyboardState == R.integer.keyboard_normal)
                    {
                        keyboardView.setKeyboard(keyboard2);
                        mKeyboardState = R.integer.keyboard_symbol;
                    }
                    else{
                        keyboardView.setKeyboard(keyboard);
                        mKeyboardState = R.integer.keyboard_normal;
                    }

                case Keyboard.KEYCODE_DELETE :
                    CharSequence selectedText = inputConnection.getSelectedText(0);

                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                    }
                    break;
                case Keyboard.KEYCODE_SHIFT:
                  if(mKeyboardState == R.integer.keyboard_normal)
                   {
                       caps = !caps;
                       keyboard.setShifted(caps);
                       keyboardView.invalidateAllKeys();
                       //keyboardView.setKeyboard(keyboard3);
                    }
                    else {
                            if(mKeyboardState == R.integer.keyboard_symbol)
                            {
                                keyboardView.setKeyboard(keyboard2);
                                mKeyboardState = R.integer.keyboard_symbol2;
                            }
                            else
                            {
                                keyboardView.setKeyboard(keyboard3);
                                mKeyboardState = R.integer.keyboard_symbol;
                            }
                    }
                    break;
                case Keyboard.KEYCODE_DONE:
                    inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

                    break;
                default :
                    char code = (char) primaryCode;
                    if(Character.isLetter(code) && caps){
                      //  code = Character.toUpperCase(code);
                    }
                    inputConnection.commitText(String.valueOf(code), 1);

            }
        }

    }


    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
