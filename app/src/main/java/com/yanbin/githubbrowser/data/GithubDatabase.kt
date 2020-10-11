package com.yanbin.githubbrowser.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [RepoEntity::class, IssueEntity::class], version = 3)
abstract class GithubDatabase: RoomDatabase() {

    abstract fun repoDao(): RepoDao
    abstract fun issueDao(): IssueDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getDatabase(context: Context): GithubDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java,
                    "word_database"
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Repo ADD COLUMN repoId TEXT NOT NULL DEFAULT '0'")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `issue` (`_id` INTEGER PRIMARY KEY AUTOINCREMENT, `repoId` TEXT NOT NULL, `title` TEXT NOT NULL, `openDate` TEXT NOT NULL, `issueStatus` TEXT NOT NULL)")
            }
        }
    }
}