Below are few assumptions and key points to consider:

1) Price is always in same currency i.e. £(Pound sterling).
2) Price will always be a positive value.
3) Assuming no Web frameworks and no DB is used, intermediate layers like Controller, Delegate, DAO are skipped.
4) Master list is not maintained separately assuming that data is provided as part of multiple separate JSON files.Ideally, it could have been a master list with subsets of that list being passed on to main checkout application.
5) Title of the book will always be present on the book list.
6) For a book(from book list):

   i) If price is missing then it will be considered as £0.

   ii) If year is missing, discount will not be applied and price will be calculated.

7) BookPriceService only contains logic to calculate discount for a book. This is done for the purpose of verifying test cases to mock objects correctly.
8) Service classes are created for separation of concern to keep the actual logic separate from the BookshopCheckout application.
