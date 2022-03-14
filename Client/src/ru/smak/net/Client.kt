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

    private val comm: Communicator

    init {
        try {
            comm = Communicator(Socket(host, port))
            comm.dataParser = ::parseData
        } catch (_: Exception){
            throw ConnectionException()
        }
    }

    fun start(){
        try {
            comm.startReceiving()
        } catch (_: Throwable){}
    }

    private fun parseData(data: String) {
        println("Сервер: $data")
    }
}