package com.example.upcyclick.database.dao

import androidx.room.*
import com.example.upcyclick.database.entity.Scroll
import com.example.upcyclick.database.entity.Upgrade

@Dao
public interface UpgradeDao {
    //Здесь находятся все запросы для этой сущности (можно добавлять свои сколько угодно)
    @Insert
    fun insert(vararg upgrade: Upgrade)

    @Update
    fun update(upgrade: Upgrade)

    @Delete
    fun delete(upgrade: Upgrade)

    @Query("SELECT * FROM upgrades")
    fun getAll(): List<Upgrade>

    @Query("DELETE FROM upgrades")
    fun deleteAll()

    @Query("SELECT * FROM upgrades WHERE purchased=1")
    fun getAcquiredUpdates(): List<Upgrade>

    @Query("SELECT * FROM upgrades WHERE purchased = 0")
    fun getAvailable(): MutableList<Upgrade>

    @Query("SELECT * FROM upgrades WHERE purchased = 1")
    fun getBought(): MutableList<Upgrade>

}