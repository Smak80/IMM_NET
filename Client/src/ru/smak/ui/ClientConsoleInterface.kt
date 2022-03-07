package ru.smak.ui

import kotlin.concurrent.thread

class ClientConsoleInterface {
    private val dataListener = mutableListOf<(String)->Unit>()

    fun addDataListener(l: (String)->Unit){
        dataListener.add(l)
    }

    fun removeDataListener(l: (String)->Unit){
        dataListener.remove(l)
    }

    fun start(){
        thread {
            while (true) {
                println("Ожидание ввода сообщения...")
                val d = readln()
                dataListener.forEach { it(d) }
                if (d == "STOP") break
            }
        }
    }
}