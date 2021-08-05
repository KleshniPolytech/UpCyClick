package com.example.upcyclick.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.upcyclick.database.entity.Scroll

@Dao
public interface ScrollDao {
    //Здесь находятся все запросы для этой сущности (можно добавлять свои сколько угодно)
    @Insert
    fun insert(vararg scroll: Scroll)

    @Update
    fun update(scroll: Scroll)

    @Delete
    fun delete(scroll: Scroll)

    @Query("SELECT * FROM scrolls")
    fun getAll(): List<Scroll>

    @Query("DELETE FROM scrolls")
    fun deleteAll()

    @Query("SELECT * FROM scrolls WHERE purchased=1")
    fun getAllPurchasedScrolls(): List<Scroll>

    @Query("SELECT * FROM scrolls WHERE purchased = 0 and typeId = :rarity")
    fun getAvailable(rarity: Int): MutableList<Scroll>

    @Query("SELECT * FROM scrolls WHERE purchased = 1 and typeId = :rarity")
    fun getBought(rarity: Int): MutableList<Scroll>

    @Query("UPDATE scrolls SET purchased = 1 WHERE name = :name")
    fun buyScroll(name: String): Unit


}