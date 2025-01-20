package org.example.entity

import org.bson.types.ObjectId
import java.util.Date

data class Videojuego(
    val id: ObjectId,
    val titulo: String,
    val genero: String,
    val precio: Double,
    val fechLanzamiento: Date
)