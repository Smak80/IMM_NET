package ru.smak.net

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.concurrent.thread

class Communicator(val socket: Socket) {

    var dataParser: ((String)->Unit)? = null

    fun startReceiving(){
        val br = BufferedReader(
            InputStreamReader(
                socket.getInputStream()
            )
        )
        thread {
            while (true) {
                val data = br.readLine()
                if (data.isNullOrEmpty()) throw IOException("Socket closed")
                dataParser?.invoke(data)
            }
        }
    }

    fun sendData(data: String){
        when (data){
            "STOP" -> stop()
        }
        val pw = PrintWriter(socket.getOutputStream())
        pw.println(data)
        pw.flush()
    }

    fun stop() {
        try {
            socket.close()
        } catch (_: Throwable){
        }
    }
}