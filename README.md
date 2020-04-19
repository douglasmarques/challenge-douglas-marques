
# Doug code Challenge - ViewModel + LiveData + Coroutines + Navigation Component + Koin

## MVVM

The app was built using the MVVM (Model-View-View Model) design pattern.

The Model layer represents the data and state of the App, it is represented by classes using the Repository pattern. The repository is responsible for fetching the data from the API and convert it to domain models.

The View layer is represented by Fragments that observe and react to states and actions exposed by the ViewModel.

The ViewModel layer is responsible for interacting with the model and preparing observable data needed by the view. It also provides hooks for the view to pass events to the model. An important thing is that the ViewModel is not tied to the view.
This layer was implemented using Jetpack ViewModel and LiveData to expose the observers to the View layer.

## Single Activity App

The app is very simple in terms of navigation and has only two screens, it was built using the Navigation Architecture Component to simplified the implementation of the Single Activity concept.
Basically, the App has the MainActivity responsible for holding the NavHostFragment and two Fragments:

### Login Fragment

The login fragment basically has a password input and submit button. If the password is correct the app goes to the reward screen.
The password input was built using the lib [Android OTP View](https://github.com/mukeshsolanki/android-otpview-pinview)

### Reward Fragment

The reward fragment has a [lottie](https://github.com/airbnb/lottie-android) animation centered vertically. Also,  it is possible to navigate back to the login fragment through the action bar arrow.

## Courotines

Coroutines were introduced to handle asynchronous calls. They are mainly used to handle the Network calls and they work really well with the JetPack ViewModel and Retrofit.

## Network layer

Retrofit was used to build the network layer because it is a mature library and simple to implement, also is the most used network library across the Android community.
To de/serialize JSON objects was used Moshi. Moshi has adapters and converters that work well with Refrofit and Courotines and also has good performance.

## Unit Tests

Unit tests are covering the ViewModel and Repository
The unit tests are using two additional test rules to provide the proper executors and dispatchers to the ViewModel (InstantTaskExecutorRule) and Coroutines (CoroutinesTestRule).

## Next-Steps

- Create UI tests using espresso.
- Integrate lint and code style tools to maintain the code quality and style.
- Integrate some CI tools like Circle CI to automatically run Unit tests and code quality steps every time the code is pushed to the repository.
