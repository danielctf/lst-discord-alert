package org.agroapp.main

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.agroapp.alerthandler.AlertHandler

class LoopHandler {

    private val alertHandler = AlertHandler()

    fun start() {
        runBlocking {
            println("Started")
            while (true) {
                try {
                    alertHandler.sendAlerts()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(5000)
            }
        }
    }
}
