package com.example.android.hilt.data

import javax.sql.DataSource

/*
* Created by JJJoonngg
*/

interface LoggerDataSource {
    fun addLog(msg: String)
    fun getAllLogs(callback: (List<Log>) -> Unit)
    fun removeLogs()
}