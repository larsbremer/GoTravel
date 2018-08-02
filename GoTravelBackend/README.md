# GoTravel

To run with MongoDB you can use  the Mongo docker container and install as follows:

1. Install Docker
2. Call 'docker pull mongo'
3. Create container with: 'docker run -p 27017:27017 --name gotravel -d mongo'
4. Enter container with: 'docker exec -it gotravel mongo'
5. Create database with 'use gotravel'