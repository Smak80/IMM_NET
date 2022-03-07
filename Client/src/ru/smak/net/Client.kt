package ru.smak.net

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.concurrent.thread

class ConnectionException : Throwable()

class Client(host: String = "localhost", port: Int = 5903) {

    var port: Int = port
        get() = field
        set(value) {
            field = value.coerceIn(1, 65535)
        }

    private var socket: Socket? = null

    init {
        try {
            socket = Socket(host, port)
        } catch (_: Exception){
            throw ConnectionException()
        }
    }

    fun start(){
        startReceiving()
    }

    fun sendData(data: String){
        when (data){
            "STOP" -> stop()
        }
        socket?.run {
            val pw = PrintWriter(getOutputStream())
            pw.println(data)
            pw.flush()
        }
    }

    private fun stop() {
        try {
            socket?.close()
        } catch (_: Throwable){
        } finally {
            socket = null
        }
    }

    private fun startReceiving(){
        socket?.run {
            val br = BufferedReader(
                InputStreamReader(
                    getInputStream()
                )
            )
            thread {
                try {
                    while (true) {
                        val data = br.readLine()
                        if (data.isNullOrEmpty()) throw IOException("Socket closed")
                        parseData(data)
                    }
                } catch (_: Throwable){
                }
            }
        }
    }

    private fun parseData(data: String?) {
        println("Сервер: $data")
    }
}