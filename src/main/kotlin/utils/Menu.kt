package org.example.utils

import org.bson.Document
import org.bson.types.ObjectId
import org.example.DataBase.DataBaseManager
import org.example.entity.Videojuego
import java.text.SimpleDateFormat

class Menu(private val dataBaseManager: DataBaseManager) {

    private val collectionName = "videojuegos"

    fun showMenu() {
        var bandera = true

        while (bandera) {
            println("\n--- Menú de Gestión de Videojuegos ---")
            println("1. Mostrar todos los juegos")
            println("2. Buscar juegos por género")
            println("3. Registrar nuevo juego")
            println("4. Eliminar juegos por género")
            println("5. Modificar datos de un juego")
            println("6. Salir")
            print("Seleccione una opción: ")

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> listAllGames()
                2 -> searchGamesByGenre()
                3 -> registerNewGame()
                4 -> deleteGamesByGenre()
                5 -> modifyGameData()
                6 -> {
                    println("Saliendo...")
                    bandera = false
                }
                else -> println("Opción no válida. Intente de nuevo.")
            }
        }
    }

    private fun listAllGames() {
        println("Mostrando todos los juegos...")
        val games = dataBaseManager.findAllGames(collectionName)
        if (games.isEmpty()) {
            println("No se encontraron juegos.")
        } else {
            games.forEach { println(it) }
        }
    }

    private fun searchGamesByGenre() {
        println("Ingrese el género para la búsqueda:")
        val genre = readlnOrNull()
        if (!genre.isNullOrEmpty()) {
            val games = dataBaseManager.findByGenre(collectionName, genre)
            if (games.isEmpty()) {
                println("No se encontraron juegos de ese género.")
            } else {
                games.forEach { println(it) }
            }
        } else {
            println("Por favor, ingrese un género válido.")
        }
    }

    private fun registerNewGame() {
        println("Ingrese el título del juego:")
        val title = readlnOrNull()

        if (title.isNullOrEmpty()) {
            println("El título es obligatorio.")
            return
        }

        println("Ingrese el género del juego:")
        val genre = readlnOrNull()

        println("Ingrese el precio del juego:")
        val price = readln().toDoubleOrNull()

        println("Ingrese la fecha de lanzamiento (DD/MM/YYYY):")
        val releaseDate = SimpleDateFormat("dd/MM/yyyy").parse(readlnOrNull())

        if (title != null && genre != null && price != null && releaseDate != null) {
            val newGame = Document()
                .append("titulo", title)
                .append("genero", genre)
                .append("precio", price)
                .append("fecha_lanzamiento", SimpleDateFormat("dd/MM/yyyy").format(releaseDate))


            val gameExistenceVerified = dataBaseManager.insertGame(collectionName, newGame)
            if (gameExistenceVerified) {
                println("Juego registrado exitosamente: $newGame")
            } else {
                println("Error: Ya existe un juego con ese título.")
            }
        } else {
            println("Datos inválidos. Por favor, verifique los campos.")
        }
    }


    private fun deleteGamesByGenre() {
        println("Ingrese el género de los juegos que desea eliminar:")
        val genre = readlnOrNull()
        if (!genre.isNullOrEmpty()) {
            val deleteResult = dataBaseManager.deleteByGenre(collectionName, genre)
            if (deleteResult) {
                println("Se eliminaron todos los juegos del género $genre.")
            } else {
                println("No se encontraron juegos para eliminar.")
            }
        }
    }

    private fun modifyGameData() {
        println("Ingrese el título del juego a modificar:")
        val titleToModify = readlnOrNull()

        if (titleToModify != null) {
            val game = dataBaseManager.findByTitle(collectionName, titleToModify)

            if (game != null) {
                println("Juego encontrado: $game")
                println("Ingrese el nuevo género del juego:")
                val genre = readlnOrNull()

                println("Ingrese el nuevo precio del juego:")
                val price = readln().toDoubleOrNull()

                println("Ingrese la nueva fecha de lanzamiento (DD/MM/YYYY):")
                val releaseDate = SimpleDateFormat("dd/MM/yyyy").parse(readlnOrNull())

                if (genre != null && price != null && releaseDate != null) {
                    val updatedGame = Document()
                        .append("titulo", titleToModify)
                        .append("genero", genre)
                        .append("precio", price)
                        .append("fecha_lanzamiento", SimpleDateFormat("dd/MM/yyyy").format(releaseDate))

                    val updateResult = dataBaseManager.updateGame(collectionName, updatedGame)

                    if (updateResult) {
                        println("Juego actualizado exitosamente: $updatedGame")
                    } else {
                        println("No se pudo actualizar el juego.")
                    }
                } else {
                    println("Datos inválidos. Por favor, verifique los campos.")
                }
            } else {
                println("No se encontró el juego con el título $titleToModify.")
            }
        } else {
            println("Título de juego inválido.")
        }
    }
}