package ru.smak.net

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

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
        socket?.let {
            val br = BufferedReader(InputStreamReader(it.getInputStream()))
            val pw = PrintWriter(it.getOutputStream())
            val sendData = readLine()
            pw.println(sendData)
            pw.flush()
            val data = br.readLine()
            println("Получено сообщение: $data")
            it.close()
        }
    }
}