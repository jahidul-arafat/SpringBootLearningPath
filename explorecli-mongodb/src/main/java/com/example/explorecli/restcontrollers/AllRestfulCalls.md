# All RESTful Calls
```bash
# Get all tour ratings for tour 1 sorted by the customer identifier number, lowest to highest?
> http://localhost:8080/tours/1/ratings/pages?sort=pk.customerId,desc

# get all tour ratings for tour 1 sorted by the comment in alphabetical order?
> http://localhost:8080/tours/1/ratings/pages?sort=comment,asc
```


