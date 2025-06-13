package org.agroapp.alerthandler

class LastAlertSentHandler {

    private var lastAlertSent = 0L

    fun refreshLastAlertSent() {
        lastAlertSent = getCurrentTime()
    }

    private fun getCurrentTime(): Long = System.currentTimeMillis()

    fun getLastAlertSentElapsedTime(): Long = getCurrentTime() - lastAlertSent
}
