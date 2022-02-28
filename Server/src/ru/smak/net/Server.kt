package ru.smak.net

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import kotlin.concurrent.thread

class Server(port: Int = 5903) {

    private var ss: ServerSocket? = null

    var port: Int = 0
        get() = field
        set(value) {
            try {
                ss = ServerSocket(value)
                field = value
            } catch (_: Exception){
                ss = ServerSocket(0).also{
                    field = it.localPort
                }
            }
        }

    init{
        this.port = port
        println(this.port)
    }

    fun start(){
        ss?.let{
            try {
                while(true) {
                    val s = it.accept()
                    println("Новый клиент подключился")
                    thread {
                        val br = BufferedReader(InputStreamReader(s.getInputStream()))
                        val data = br.readLine()
                        println("Получено сообщение: $data")
                        val sendData = "Привет, это сервер!"
                        val pw = PrintWriter(s.getOutputStream())
                        pw.println(sendData)
                        pw.flush()
                        s.close()
                    }
                }
                it.close()
            } catch (_: Exception){
                println("Ошибка передачи данных по сети")
            }
        }
    }
}




