package design.alex.starwars.ui.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageViewAdapters {

    @BindingAdapter("image_url")
    public static void setImage(ImageView view, String url) {
        if (url != null) {
            Glide.with(view.getContext()).load(url).into(view);
        }
    }
}
