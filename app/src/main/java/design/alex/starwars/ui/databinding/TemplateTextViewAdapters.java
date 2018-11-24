package design.alex.starwars.ui.databinding;

import android.databinding.BindingAdapter;

import design.alex.starwars.ui.widgets.TemplateTextView;

public class TemplateTextViewAdapters {

    @BindingAdapter("templated_text")
    public static void setTemplatedText(TemplateTextView view, String text) {
        view.setTemplatedText(text);
    }

    @BindingAdapter("templated_text")
    public static void setTemplatedText(TemplateTextView view, Integer text) {
        view.setTemplatedText(text == null ? "-" : text.toString());
    }
}
