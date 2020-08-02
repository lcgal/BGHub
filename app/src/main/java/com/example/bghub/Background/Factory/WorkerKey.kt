package com.example.bghub.Background.Factory

import androidx.work.RxWorker
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

import dagger.MapKey
import kotlin.reflect.KClass

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@MapKey
annotation class WorkerKey(val value: KClass<out RxWorker>)


