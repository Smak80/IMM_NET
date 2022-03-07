package ru.smak.net

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.concurrent.thread

class ConnectedClient(val socket: Socket) {

    fun startReceiving(){
        val br = BufferedReader(
            InputStreamReader(
                socket.getInputStream()
            )
        )
        thread {
            try {
                while (true) {
                    val data = br.readLine()
                    if (data.isNullOrEmpty())
                        throw IOException("Socket closed")
                    parseData(data)
                }
            } catch (_: Throwable){
                println("Соединение завершено")
                Server.clients.remove(this)
            }
        }
    }

    fun sendData(data: String){
        val pw = PrintWriter(socket.getOutputStream())
        pw.println(data)
        pw.flush()
    }

    private fun parseData(data: String?) {
        println("Получено сообщение: $data")
        sendData("Информация была получена.")
    }
}