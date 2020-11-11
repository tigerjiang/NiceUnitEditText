### 属性说明  
unit_text：文本  
unit_text_color：文本颜色   
unit_ignore : 获取输入框内容的时候是否忽略单元

### for example  
<com.tiger.text.NiceUnitEditText
        android:id="@+id/text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint="需支付1000元"
        android:text="1000"
        android:inputType="number"
        app:unit_ignore="false"
        app:unit_text="元"
        app:unit_text_color="@color/colorPrimary"/>

使用 

dependencies {
implementation 'com.github.tigerjiang:niceUnitEditText:1.0'
}
