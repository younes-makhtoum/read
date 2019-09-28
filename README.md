# [Read] Android Application :

This application's goal is to enable users to build their own library of books, e-books, (audio books?) and to manage it from a central place.

## <b>Functional features :</b>

- [x] Fetching books from different remote API : starting with Google Books API
- [x] Filtering the remote queries by language and number of results
- [x] Saving books in the database (Room) : owned and wished
- [ ] Adding a book in the the database by :
  - scanning the barcode of a physical book
  - fetching its meta-data from remote APIs
- [ ] Manual editing a saved book's meta-data
- [ ] Adding info to a saved book : categories, number of read page...
- [ ] Consulting statistics about the personal library (database) : books per categories, number of read/unread books...

## <b>Technical features :</b>

### Implemented :

- Hide bottom nav bar in specific fragments :

https://stackoverflow.com/questions/47489616/hiding-bottomnavigationview-from-different-fragment?rq=1

- Properly using the the constraint layout with 0dp instead of match_parent (provokes issues with the display of remotely fetched books in the explore fragment) :

https://medium.com/@jorgecool/the-problem-is-that-you-are-using-constraintlayout-wrong-you-have-match-parent-in-some-places-and-822136c4bfb4

### Later :

- Display full book cover image in the detail view when scrolling up the collapsing toolbar :

https://stackoverflow.com/questions/41458682/issue-with-coordinatorlayout-and-imageview-that-adjusts-width-while-scrolling


------------------------------------
Basic writing and formatting syntax :
https://help.github.com/en/articles/basic-writing-and-formatting-syntax
