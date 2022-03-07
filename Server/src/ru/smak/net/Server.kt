package ru.smak.net

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import kotlin.concurrent.thread

class Server(port: Int = 5903) {

    private var ss: ServerSocket? = null
    companion object {
        val clients = mutableListOf<ConnectedClient>()
    }

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
                    clients.add(
                        ConnectedClient(it.accept()).apply {
                            startReceiving()
                        }
                    )
                    println("Новый клиент подключился")
                }
                it.close()
            } catch (_: Exception){
                println("Ошибка передачи данных по сети")
            }
        }
    }
}




