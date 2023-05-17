package com.gustavo.rocha.inmetrics.di

import com.gustavo.rocha.inmetrics.imageLoader.GlideImageLoader
import com.gustavo.rocha.inmetrics.imageLoader.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface AppModule {

    @Binds
    fun bindImageLoader(imageLoader: GlideImageLoader): ImageLoader
}