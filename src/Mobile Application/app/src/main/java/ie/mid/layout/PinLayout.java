package ie.mid.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ie.mid.R;

public class PinLayout extends LinearLayout {
    List<View> pins = new ArrayList<>();
    private PinInterface pinInterface;
    private EditText invisiblePinEditText;
    private int pinLenght;

    public PinLayout(Context context) {
        super(context);
        init();
    }

    public PinLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PinLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setTypeYourPinInterface(PinInterface pinInterface) {
        this.pinInterface = pinInterface;
    }

    public void callFocus() {
        invisiblePinEditText.requestFocus();
    }

    public void clearPassword() {
        invisiblePinEditText.getText().clear();
        for (View pin : pins)
            removeView(pin);
        pins.clear();
        addPins();

    }

    public void addChar(String c){
        invisiblePinEditText.setText(invisiblePinEditText.getText() + c);
    }

    public void removeChar() {
        if(invisiblePinEditText.getText().length() > 0) {
            String current = invisiblePinEditText.getText().toString();
            current = current.substring(0, current.length() - 1);
            invisiblePinEditText.setText(current);
        }
    }

    private void addPins() {
        int marginSize = getResources().getDimensionPixelSize(R.dimen.pin_margins);
        int height = getResources().getDimensionPixelSize(R.dimen.pin_size);
        int width = getResources().getDimensionPixelSize(R.dimen.pin_size);
        for (int i = 1; i <= pinLenght; i++) {
            View pin = new View(getContext());
            setSize(pin, width, height);
            unfillPin(pin);
            setMarginLeft(pin, marginSize);

            if (isLastPin(i, pinLenght)) {
                setMarginRight(pin, marginSize);
            }

            pins.add(pin);
            addView(pin);
        }
    }

    private void init() {
        pinLenght = getResources().getInteger(R.integer.pin_lenght);
        int inputType = getResources().getInteger(R.integer.pin_input_type);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setSize(this, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setupOnClickListener();
        setFocusableInTouchMode(false);

        setInvisibleEditText(pinLenght, inputType);

        addPins();
    }

    private void setupOnClickListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPinEditTextFocus();
            }
        });
    }

    private void requestPinEditTextFocus() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && invisiblePinEditText != null) {
            imm.showSoftInput(invisiblePinEditText, 0);
            invisiblePinEditText.requestFocus();
        }
    }

    private void setInvisibleEditText(int pinLenght, int inputType) {
        invisiblePinEditText = new EditText(getContext());
        setSize(invisiblePinEditText, 0, 0);
        invisiblePinEditText.setInputType(inputType);
        invisiblePinEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(pinLenght)});
        addView(invisiblePinEditText);
        setupEditTextPinListener(invisiblePinEditText);
        requestPinEditTextFocus();
    }

    private void setSize(View v, int width, int height) {
        v.setLayoutParams(new LayoutParams(width, height));
    }

    public static void setMarginLeft(View v, int left) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            setMargins(v, left, p.topMargin, p.rightMargin, p.bottomMargin);
        }
    }

    public static void setMarginRight(View v, int right) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            setMargins(v, p.leftMargin, p.topMargin, right, p.bottomMargin);
        }
    }

    public static void setMargins(View v, int left, int top, int right, int bottom) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            v.requestLayout();
        }
    }

    private boolean isLastPin(int i, int pinSize) {
        return i == pinSize;
    }


    private void setupEditTextPinListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            int lastTextLenght = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lastTextLenght = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int textLenghtAfter = charSequence.length();
                if (hasNewPin(lastTextLenght, textLenghtAfter)) {
                    fillPin(pins.get(textLenghtAfter - 1));
                    if (pinInterface != null && hasFinishedTyping(textLenghtAfter)) {
                        pinInterface.onPinTyped(charSequence.toString());

                    }
                } else {
                    unfillPin(pins.get(textLenghtAfter));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private boolean hasFinishedTyping(int textLenghtAfter) {
        return textLenghtAfter == pinLenght;
    }

    private boolean hasNewPin(int textSizeBefore, int size) {
        return textSizeBefore < size;
    }

    private void fillPin(View pin) {
        pin.setBackground(getResources().getDrawable(R.drawable.filled_dot));
    }

    private void unfillPin(View pin) {
        pin.setBackground(getResources().getDrawable(R.drawable.empty_dot));
    }


}
