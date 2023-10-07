package com.adds.raggingis4loosers.activity


    import java.sql.Connection
            import java.sql.DriverManager
            import java.sql.SQLException

            object DatabaseManager {
                private val url = "jdbc:postgresql://localhost:5432/contact_details"
                private val user = "postgres"
                private val password = "anwpost"

                fun getConnection(): Connection? {
                    return try {
                        Class.forName("org.postgresql.Driver")
                        DriverManager.getConnection(url, user, password)
                    } catch (e: SQLException) {
                        e.printStackTrace()
                        null
                    } catch (e: ClassNotFoundException) {
                        e.printStackTrace()
                        null
                    }
                }
            }

