package com.example.logintask.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.logintask.di.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    class CallBack @Inject constructor(

        private val database: Provider<UserDatabase>,

        @AppModule.ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //THIS WILL BE EXECUTED AFTER THE DB IS CREATED AFTER THE DI
            val dao = database.get().userDao()

            //NEED THE APP COROUTINE SCOPE FOR DB OPERATIONS
            applicationScope.launch {
               // dao.inserUser(User)
                dao.inserUser(User(name = "Umair",email = "umair@gmail.com",number = 1234,image = "someuri"))
                dao.inserUser(User(name = "Khan",email = "khan@gmail.com",number = 1234,image = "someuri"))
                dao.inserUser(User(name = "Zubair",email = "zubair@gmail.com",number = 1234,image = "someuri"))
                dao.inserUser(User(name = "Hasan",email = "hasan@gmail.com",number = 1234,image = "someuri"))
                dao.inserUser(User(name = "Zafar",email = "zafar@gmail.com",number = 1234,image = "someuri"))
            }

        }
    }

}