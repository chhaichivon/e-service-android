package org.jarvis.code.dagger.module;

import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.ui.main.MainPresenter;
import org.jarvis.code.ui.main.MainPresenterImpl;
import org.jarvis.code.ui.main.MainView;
import org.jarvis.code.ui.product.ProductPresenter;
import org.jarvis.code.ui.product.ProductPresenterImpl;
import org.jarvis.code.ui.product.ProductView;
import org.jarvis.code.ui.product.promotion.PromotionPresenter;
import org.jarvis.code.ui.product.promotion.PromotionPresenterImpl;
import org.jarvis.code.ui.register.RegisterPresenter;
import org.jarvis.code.ui.register.RegisterPresenterImpl;
import org.jarvis.code.ui.register.RegisterView;
import org.jarvis.code.ui.splash.SplashPresenter;
import org.jarvis.code.ui.splash.SplashPresenterImpl;
import org.jarvis.code.ui.splash.SplashView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ki.kao on 10/4/2017.
 */
@Module
public class PresenterModule {

    @Provides
    @PerActivity
    MainPresenter<MainView> provideMainPresenter(MainPresenterImpl mainPresenter) {
        return mainPresenter;
    }

    @Provides
    ProductPresenter<ProductView> provideProductPresenter(ProductPresenterImpl productPresenter) {
        return productPresenter;
    }

    @Provides
    PromotionPresenter<ProductView> providePromotionPresenter(PromotionPresenterImpl promotionPresenter) {
        return promotionPresenter;
    }


    @Provides
    RegisterPresenter<RegisterView> provideRegisterPresenter(RegisterPresenterImpl registerPresenter) {
        return registerPresenter;
    }

    @Provides
    @PerActivity
    SplashPresenter<SplashView> provideSplashPresenter(SplashPresenterImpl splashPresenter) {
        return splashPresenter;
    }
}
