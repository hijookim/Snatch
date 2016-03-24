# Snatch
Fashion Hackathon project 2016 - Android App

This project was developed by Karen Kim (developer for the Android App),
Christy Yang (designer), and Allison Lee (product) for the fashion week
hackathon in February 2016. This 24 hour project consists leveraging a
beacon technology in retail stores to connect the potential buyers to
the items of interest.

1) User can log in using their Pinterest account, which the app then
authenticates the user and fetches their boards. User can select the
boards to upload.

2) Once the boards are selected, all of the pins (related to clothing
and fashion) are uploaded and properly matched to the retailers based on
the origin of the pin

3) When the user walks into a physical retail store, beacons placed
around the store can be detected by the app running on the phone. When
the app detects a nearby beacon, it transmits the beacon id with the
position identifier to the server, which then can use this positioning
data inside of a retail store to determine whether the store has user's
previously pinned (marked as interested) item in stock. If the item that
the user has pinned is in store and near the user (as is determined by
the beacon nearby), then a push notification is sent to the user
informing him/her that a clothing item that they've marked as interested
is nearby and available.

This is a great way to connect online to offline retail experience,
since offline stores have virtually no way to connect user's interest
data to their offerings in the physical stores. The name of the app
reflects giving users the opportunity to "snatch" a product they've previously
marked as favorite in person.
