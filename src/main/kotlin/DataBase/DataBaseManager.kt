package org.example.DataBase

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import io.github.cdimascio.dotenv.dotenv
import org.bson.Document
import org.example.entity.Videojuego

class DataBaseManager {
    private val dbName = "cserben"
    private val dotenv = dotenv()
    private val connectionString = dotenv["URL_MONGODB"]
    private lateinit var mongoClient: MongoClient
    private lateinit var database: MongoDatabase

    fun connect() {
        try {
            mongoClient = MongoClients.create(connectionString)
            database = mongoClient.getDatabase(dbName)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun getCollection(collectionName: String): MongoCollection<Document> {
        return database.getCollection(collectionName)
    }

    fun findAllGames(collectionName: String): List<Document> {
        val collection = getCollection(collectionName)
        return collection.find().toList()
    }

    fun findByTitle(collName: String, title: String): Videojuego? {
        return try {
            val collection = database.getCollection(collName, Videojuego::class.java)
            collection.find(eq("titulo", title)).firstOrNull()
        } catch (e: Exception) {
            println("Error al buscar el juego: ${e.message}")
            null
        }
    }

    fun findByGenre(collectionName: String, genre: String): List<Document> {
        val collection = getCollection(collectionName)

        return collection.find(Document("genero", genre)).sort(Document("titulo", 1)).toList()
    }

    fun insertGame(collName: String, game: Document): Boolean {
        val collection = getCollection(collName)

        val existenceFilter = collection.find(Document("titulo", game.getString("titulo"))).first()

        if (existenceFilter != null) {
            return false
        }

        collection.insertOne(game)
        return true
    }

    fun deleteByGenre(collName: String, genre: String): Boolean {
        val collection = getCollection(collName)
        val deleteResult = collection.deleteMany(Document("genero", genre))
        return deleteResult.deletedCount > 0
    }

    fun updateGame(collName: String, updatedGame: Document): Boolean {
        val collection = getCollection(collName)
        val gameId = updatedGame.getObjectId("_id")
        val filter = Document("_id", gameId)
        val updateResult = collection.replaceOne(filter, updatedGame)
        return updateResult.modifiedCount > 0
    }


    fun disconnect() {
        try {
            mongoClient.close()
        }catch (e:Exception){
            println(e.message)
        }
    }

}