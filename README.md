# Gift_Card
Java based gift card system.
To switch from text to SQL connectivity, replace all instances of ConnectTxt with ConnectSQL(using find and replace) and from SQL to text based connectivity,use ConnectTxt class.
For SQL connectivity, check the ConnectSQL.java file's getConnection() function for sql username and password.Use a database named "giftcard".
The following tables should be created for SQL:
i)Products
ii)Transactions
iii)GiftCards
iv)Users

To run the application with text file storage mode, go to bin folder and run "java com.GiftCard.main.Application" command.
To run the pplication with SQL storage mode, go to bin folder and run "java -cp . .:mysql-connector-java-8.0.29.jar com.GiftCard.main.Application" command.
