Snakes and Ladders its a pretty popular game around the world, how would it be coded in Java in a basic way? This is one of the multiple answers to that. The first thing about snakes and ladders its the way how the movement works, its a zigzag kind of movement, so to be able to move the players through the board in this order i used Linked List with the class Square where every Square has a relation to the next Square (this can be the one above, left or right, but no matter where it is its always the next one) and possible relations with senakes and ladders (that are simply reference to the square where the ladder lead to). Working with the board like a simple Linked List had its own problems, the most painful was to show it properly. The way i achieve this was showing row by row, the way to get the row was with a method that was adding the squares in an specified row to a another linked list, that linked list was sorted and showed to the player, and the same with the other rows. 
