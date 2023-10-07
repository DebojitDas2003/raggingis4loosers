import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class YourActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connection: Connection? = DatabaseManager.getConnection()

        if (connection != null) {
            try {
                val statement: Statement = connection.createStatement()
                val resultSet: ResultSet = statement.executeQuery("SELECT * FROM activity")

                while (resultSet.next()) {
                    val columnName = resultSet.getString("column_name")
                    // Process data here
                }

                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                connection.close()
            }
        }
    }
}
