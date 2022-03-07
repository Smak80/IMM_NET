import ru.smak.net.Client
import ru.smak.ui.ClientConsoleInterface

fun main() {
    val c = Client()
    ClientConsoleInterface().apply {
        addDataListener { c.sendData(it) }
        start()
    }
    c.start()
}