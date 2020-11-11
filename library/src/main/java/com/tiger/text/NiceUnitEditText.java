package com.tiger.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.EditText;



@SuppressLint("AppCompatCustomView")
public class NiceUnitEditText extends EditText {

    private Context context;
    private String unitText;//文本内容
    private int unitTextColor;//文字颜色
    private boolean ignoreCeText; // 是否忽略单位字符

    public NiceUnitEditText(Context context) {
        super(context);
        this.context = context;
        initView(null);
    }

    public NiceUnitEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(attrs);
    }

    public NiceUnitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs);
    }


    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NiceUnitEditText);
            unitText = array.getString(R.styleable.NiceUnitEditText_unit_text);
            unitTextColor = array.getColor(R.styleable.NiceUnitEditText_unit_text_color, 0);
            ignoreCeText = array.getBoolean(R.styleable.NiceUnitEditText_unit_ignore, false);
            array.recycle();
        }
        addTextChangedListener(textWatcher);
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (unitText.trim().isEmpty())
                return;
            if (s != null) {
                removeTextChangedListener(this);//移除输入监听
                if (s.toString().trim().equals(unitText)) {
                    setText("");
                } else {
                    String str = s.toString().replace(unitText, "") + unitText;//去重
                    //设置文字颜色
                    if (unitTextColor != 0) {
                        SpannableStringBuilder builder = new SpannableStringBuilder(str);
                        builder.setSpan(new ForegroundColorSpan(unitTextColor), str.length() - unitText.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        setText(builder);
                    } else
                        setText(str);
                }
                addTextChangedListener(this);
            }
        }
    };

    //设置光标位置
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (!getText().toString().isEmpty() && selEnd == getText().toString().length()) {
            setSelection(getText().toString().length() - unitText.length());
        } else {
            setSelection(selStart);
        }
    }

    public Editable getResultText() {

        CharSequence text = super.getText();
        CharSequence result = text;
        // This can only happen during construction.
        if (text == null) {
            return null;
        }
        if (ignoreCeText && text.length() > 0) {
            if (text.toString().endsWith(unitText)) {
                result = text.subSequence(0, text.length() - 1);
            }
        }
        return (Editable) result;
    }
}
