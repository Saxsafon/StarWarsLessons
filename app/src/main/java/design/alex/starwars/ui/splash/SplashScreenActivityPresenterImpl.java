package design.alex.starwars.ui.splash;

public class SplashScreenActivityPresenterImpl implements SplashScreenActivityPresenter {

    /** view к которой прицеплен презентер */
    private SplashScreenActivityView mView;

    @Override
    public void setView(SplashScreenActivityView view) {
        mView = view;
    }

    @Override
    public void onCreate() {
        validate();
        mView.onStartAnimation();
    }

    @Override
    public void onStopAnimation() {
        validate();
        mView.onStartHomeActivity();
    }

    private void validate() {
        if (mView == null) {
            throw new IllegalStateException("Не указана View");
        }
    }
}
