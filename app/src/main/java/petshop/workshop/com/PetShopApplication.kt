package petshop.workshop.com

import android.app.Application
import androidx.lifecycle.Lifecycle
import petshop.workshop.com.di.DaggerAppComponent
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.facebook.stetho.Stetho
import petshop.workshop.com.business.UserPrefs
import petshop.workshop.com.di.AppComponent
import petshop.workshop.com.di.AppModule
import timber.log.Timber
import javax.inject.Inject

class PetShopApplication: Application(), LifecycleObserver {

    @Inject
    lateinit var userPrefs: UserPrefs

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        initDagger(this)
        PetShopApplication.diInjector().inject(this)
        Stetho.initializeWithDefaults(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        userPrefs.setFirstOpen()

        Timber.d("Debug: ${userPrefs.getFirstOpen()}")
    }

    companion object {
        private var appComponent: AppComponent? = null

        private fun initDagger(application: Application) {
            appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(application, application))
                .build()
        }

        fun diInjector(): AppComponent {
            if (appComponent == null) throw RuntimeException("Dagger must be initialized in Application#onCreate()")
            else return appComponent!!
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAppForegrounded() {
        Timber.d("AppForeground()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onAppBackgrounded() {
       Timber.d("AppBackground()")
    }

}