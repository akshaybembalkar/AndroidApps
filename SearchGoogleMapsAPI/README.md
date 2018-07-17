# Search Google Maps API

This app allows users to make searches for locations and present the results in a list, allowing them to be presented in a map view inside the app.


### Technical Details:

* **Topics covered:** Android, Java, Google Maps, ListView, Retrofit, Room Persistence Library (SQLite), Singleton, AsyncTask
* **Tests:** Espresso
* **Documentation:** [Google Maps API](https://developers.google.com/maps/documentation/geocoding/)
* **API call example:** http://maps.googleapis.com/maps/api/geocode/json?address=Springfield&sensor=false
* **Location name key used:** `formatted_address`
* **Location coordinates key path:** `geometry.location`


### Features:

1. This app support Android 4 and later.
1. Text field on the top of the screen.
1. Tapping search button makes the search through the call to the Google Maps API.
1. The results are listed in the same order as received by the API response. Each item in the list shows the name of the related location.
1. If there is only one result, display only one row in the list.
1. If there are more than one results, show button for "Display All on Map" in a separated section.
1. Selecting this button would display the map with all results centered.
1. Selecting one item in the list presents a map view with result item presented as marker. The selected marker would be centered.
1. Selecting a marker in the map view presents its location name and coordinates in the marker title.
1. User can save the selected result object to a SQLite database.
    * **This is not applicable to the "Display All on Map" option.**
1. In case the object has been previously saved, the "Save" button will be replaced by a "Delete" button. Tapping this button will delete the current object from the database.
    * **This is not applicable to the "Display All on Map" option.**
