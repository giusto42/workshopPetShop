package petshop.workshop.com.di

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import dagger.Module
import dagger.Provides
import petshop.workshop.com.business.UserPrefs
import petshop.workshop.com.business.UserPrefsImpl
import petshop.workshop.com.persistence.AppDataBase
import petshop.workshop.com.persistence.PetDao
import petshop.workshop.com.util.ContextProvider
import petshop.workshop.com.util.SimpleContextProvider
import petshop.workshop.com.viewmodel.PetsViewModel
import javax.inject.Singleton

@Module
class AppModule(private val context: Context, private val application: Application) {

    companion object {
        private const val FILE_USER_PREFS = "com.petshop.user.prefs"
    }

    @Provides
    @Singleton
    fun provideAppContext(): Context = context

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideContextProvider(context: Context): ContextProvider =
        SimpleContextProvider(context)

    @Provides
    @Singleton
    fun provideUserPrefs(context: Context): UserPrefs = UserPrefsImpl(context, FILE_USER_PREFS)

    @Provides
    @Singleton
    fun providePetDao() : PetDao = Room.databaseBuilder(application, AppDataBase::class.java, "com.workshop.petshop").build().databasePetDao()

    @Provides
    @Singleton
    fun providePetsViewModel(contextProvider: ContextProvider): PetsViewModel = PetsViewModel(contextProvider = contextProvider)
}