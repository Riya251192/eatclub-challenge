# eatclub-challenge
1.List of all the available restaurant deals that are active at a specified time of day.
   # Curl : curl --location 'http://localhost:8080/v1/eatclub/deals?timeOfDay=3%3A00pm'
     Assumptions : If deal.start() and deal.end() is not present , restaurant.open() and restaurant.close() time are 
                 considered for deal timings .
                    Strategic Design Pattern has been used to keep the code extendable for future filter usecases.

2.calculates the ‘peak’ time window, during which most deals are available.
   # Curl : curl --location 'http://localhost:8080/v1/eatclub/deals/peak-time'
     Assumptions : Peak time window of 1hour is selected to get the maximum deal in that window of 1hour.

3. Add-on Pagination while returning restaurant deals that are active at a specified time of day.
   # Curl : curl --location 'http://localhost:8080/v1/eatclub/deals/paginated?timeOfDay=12%3A00pm&page=0&size=2' , 
            curl --location 'http://localhost:8080/v1/eatclub/deals/paginated?timeOfDay=12%3A00pm&page=1&size=2'
        Assumptions : If deal.start() and deal.end() is not present , restaurant.open() and restaurant.close() time are
                 considered for deal timings. 
                      This endpoint is to handle huge payload , with pagination.
           
