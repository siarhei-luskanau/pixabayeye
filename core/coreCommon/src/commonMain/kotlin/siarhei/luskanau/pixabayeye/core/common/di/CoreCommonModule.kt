package siarhei.luskanau.pixabayeye.core.common.di

import org.koin.core.module.Module
import org.koin.dsl.module

val coreCommonModule = module {}

expect val coreCommonPlatformModule: Module
