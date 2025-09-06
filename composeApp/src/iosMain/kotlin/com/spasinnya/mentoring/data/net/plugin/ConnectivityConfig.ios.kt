package com.spasinnya.mentoring.data.net.plugin

import platform.Network.nw_interface_type_cellular
import platform.Network.nw_interface_type_wifi
import platform.Network.nw_interface_type_wired
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_monitor_t
import platform.Network.nw_path_status_satisfied
import platform.Network.nw_path_uses_interface_type
import platform.darwin.dispatch_get_main_queue
import kotlin.concurrent.Volatile

actual object NetStatus {

    private var monitor: nw_path_monitor_t? = null
    @Volatile
    private var online: Boolean = false
    @Volatile private var started: Boolean = false

    private fun ensureStarted() {
        if (started) return
        started = true

        val m = nw_path_monitor_create()
        monitor = m

        nw_path_monitor_set_update_handler(m) { path ->
            val satisfied = nw_path_get_status(path) == nw_path_status_satisfied
            val hasWifi = nw_path_uses_interface_type(path, nw_interface_type_wifi)
            val hasCell = nw_path_uses_interface_type(path, nw_interface_type_cellular)
            val hasWired = nw_path_uses_interface_type(path, nw_interface_type_wired)
            online = satisfied && (hasWifi || hasCell || hasWired)
        }

        nw_path_monitor_set_queue(m, dispatch_get_main_queue())
        nw_path_monitor_start(m)
    }

    actual fun isOnline(): Boolean {
        ensureStarted()
        return online
    }
}