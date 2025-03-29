# OCPP 1.6 features implementation


* Spring framework used for implementation 
* MySql databse 
* REST APIs and WebSocket for communication 

features implemented

* Implemented BootNotification service which sends the charger details and updates it's availability in database
* HeartBeat will check every minute for the activity of the charger, if it is not active for more than 5 minutes it's status will be updated as Faulted
* StatusNotification service to check for the status of the chargers in databse
* start / stop transaction to start the charging  and update the charger status to charging till it stops
* Used OAuth2 with Github for api authentication


Database tables used
* charger_status to store the status of charger
* charging_station to store the details of the charging station
* transactions to store the starttime and end time of the transaction
sample request responses were provided in sampleRequests table
