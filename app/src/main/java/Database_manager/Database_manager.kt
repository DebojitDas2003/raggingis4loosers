val connection: Connection? = DatabaseManager.getConnection()

if (connection != null) {
    // Use the connection for database operations
} else {
    // Handle the case where the connection couldn't be established
}

