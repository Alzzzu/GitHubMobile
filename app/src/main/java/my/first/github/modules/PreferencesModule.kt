package my.first.github.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import my.first.github.utils.PreferencesManager
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule{
    @Singleton
    @Provides
    fun providePreferencesManager(@ApplicationContext context: Context) : PreferencesManager{
        return PreferencesManager(context)
    }
}