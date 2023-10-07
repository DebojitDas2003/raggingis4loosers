import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DatabaseManager {
    private const val DB_URL = "jdbc:postgresql://your_server_address:5432/contact_details"
    private const val DB_USER = "postgres"
    private const val DB_PASSWORD = "anwpost"

    fun getConnection(): Connection? {
        try {
            Class.forName("org.postgresql.Driver")
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }
}
