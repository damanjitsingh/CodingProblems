# CodingProblems
Collections of coding problems and their solutions


# System Design Notes
Handy system design notes.

## Simple Web Server
- Below image shows some of the basic components of a simple web server.
![simple web server](resources/images/Webserver1.png)
- Static content like images, css etc are provided by content delivery networks (CDNs). Using CDNs are faster as these servers are closer to users.
- Load balancer is used to share the load of requests among various web servers. If one web server is down then load balancer can direct requests to
other server until a replacement is up. It helps in fail over.
- We use multiple web servers if to support large load as scaling up a single server vertically is limited by memory and CPU.
- Similarly we use multiple databases to avoid single point of failure by only using a single database. We call it master slave. Slaves can be used for Read
while master is used for DB write operations. If master goes down then one of the slave can be elevated to master database.
- We also use in memory cache so that requests can be served faster as DB access takes time. Since cache size is limited in memory we can use LRU (least recently used)
method to remove older data from the cache. Header time to live (TTL) can be used to tell how long the data needs to be kept in the cache. Low TTL will result in 
frequent movement of data from DB while high TTL can cause the data to stay longer in the cache and become stale.
