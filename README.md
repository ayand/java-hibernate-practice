The main method specified in Driver.java can only be run in conjunction with a MySQL database on the localhost. This database can be populated with the .sql file that has the commands for putting in all of the data necessary.

Running the line:

java -cp target/hibernate-practice-1.0-SNAPSHOT.jar com.ayandas.hibernatepractice.Driver -p [pokemonID]

Gives you the record for the Pokémon with id number pokemonID

Running the line:

java -cp target/hibernate-practice-1.0-SNAPSHOT.jar com.ayandas.hibernatepractice.Driver -r [userID] [pokemonID]

Allows for user number "userID" to write a short review for Pokémon number "pokemonID" based on the text specified in the review.txt file.

Running the line:

java -cp target/hibernate-practice-1.0-SNAPSHOT.jar com.ayandas.hibernatepractice.Driver -t [type1] [type2 (if necessary)]

Allows you to get the weaknesses for a Pokémon which has the type type1 or the type combination type1/type2

Running the line:

java -cp target/hibernate-practice-1.0-SNAPSHOT.jar com.ayandas.hibernatepractice.Driver -u [pokemonID] [stat] [newStat]

Allows you to update the "stat" stat of the Pokémon with id number "pokemonID" with the new number "newStat"
