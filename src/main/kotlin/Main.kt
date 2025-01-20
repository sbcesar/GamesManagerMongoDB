package org.example


import org.example.DataBase.DataBaseManager
import org.example.utils.Menu


fun main() {

    val dataBaseManager = DataBaseManager()
    val menu = Menu(dataBaseManager)

    try {
        dataBaseManager.connect()

        menu.showMenu()
    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        dataBaseManager.disconnect()
    }
}