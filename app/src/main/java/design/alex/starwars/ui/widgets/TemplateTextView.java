package design.alex.starwars.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import design.alex.starwars.R;

/**
 * Widget текстового поля с возможностью добавить шаблон текста
 */
public class TemplateTextView extends AppCompatTextView {

    /** ресурс шаблона */
    private int mTextTemplate;

    public TemplateTextView(Context context) {
        super(context);
    }

    public TemplateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public TemplateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    /**
     * Инициализация дополнительных аттрибутов
     * @param attrs - атрибуты
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.TemplateTextView);
        mTextTemplate = attributes.getResourceId(R.styleable.TemplateTextView_template, 0);
        attributes.recycle();
    }

    /**
     * Установка текста с применением шаблона
     * @param text - текст
     */
    public void setTemplatedText(String text) {
        if (mTextTemplate != 0) {
            setText(getContext().getResources().getString(mTextTemplate, text));
        }
    }
}
