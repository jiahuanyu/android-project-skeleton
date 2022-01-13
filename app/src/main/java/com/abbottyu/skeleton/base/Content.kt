package com.abbottyu.skeleton.base

import kotlinx.coroutines.*
import java.util.concurrent.Executors

val IO_DISPATCHER = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
val MAIN_DISPATCHER = Dispatchers.Main

// 用户登入作用域，登出就要取消的域
private var loginJob = SupervisorJob()
var loginMainScope =
    CoroutineScope(MAIN_DISPATCHER + loginJob)
var loginIOScope = CoroutineScope(IO_DISPATCHER + loginJob)

// 全局作用域，生命周期跟随 App
private var applicationJob = SupervisorJob()
val applicationMainScope =
    CoroutineScope(MAIN_DISPATCHER + CoroutineName("全局作用域中的MAIN处理协程") + applicationJob)
val applicationIOScope =
    CoroutineScope(IO_DISPATCHER + CoroutineName("全局作用域中的IO处理协程") + applicationJob)

/**
 * 用户登录时调用
 */
fun login() {
    loginJob = SupervisorJob()
    loginMainScope =
        CoroutineScope(MAIN_DISPATCHER + loginJob)
    loginIOScope = CoroutineScope(IO_DISPATCHER + loginJob)
}

/**
 * 用户退出登入时调用
 */
suspend fun logout() {
    loginJob.cancelAndJoin()
}