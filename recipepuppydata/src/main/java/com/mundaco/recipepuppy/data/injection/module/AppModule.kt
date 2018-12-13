package com.mundaco.recipepuppy.data.injection.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
@Suppress("unused")
class AppModule(val mApplication: Application) {


    @Provides
    @Singleton
    internal fun providesApplication(): Application {
        return mApplication
    }

}