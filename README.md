# BreakingBad

A simple app that allows a user to view a information about Breaking Bad Characters

  - App can be ran by starting the project up in Android Studio V4 and hitting run

# Technologies Used
  -Koin
  -WorkManager
  -Room
  -Retrofit+Okhttp
  -Coroutines
  -LiveData
  -DataBinding
  -Events [experimental/WIP]
  -viewModel
  -MockK

# Architecture

This app uses a MVVMPC (Events) architecture with the following components

  - Model: Is effectively the output from the domain layer
  - View: The android activity and it's layout responsible for UI and user/system events
  - ViewModel: A simple datastore that is observed by the view to populate it's UI
  - Presenter: The gateway mechanism that translates the domains output for UI consumption
  - Controller: The mechanism where the view can trigger and make requests against the domain

![Clean Architecture](https://jeremiahflaga.github.io/images/2017/CleanArchitectureDesignByUncleBobMartin.png)
This is Robert C. Martin's diagram of what clean architecture would typically look like in an application.
The diagram specifically represents the flow of a synchronous web application hosted by a server.
Mobile applications and asynchronous web applications would have another link between the view and the controller.
Mobile applications will not always have a significant domain in the source of the application, as this is more often than not sitting on a server.

# An example flow using MVVMPC:

  - A user opens up the breaking bad application and it's view is instantiated
  - The view will have it's controller and viewmodel injected
  - The domain and presenter will also be injected by Koin at application start
  - in the views onCreate a method is called to fetch users
  - The domain will handle the event and pass it's output (characters) to the presenter
  - The presenter will translate the output into a simple type (characterViewModels) ready for viewmodel's consumption
  - The viewmodel will update it's value and the view will observe and reflect the changes to the UI

# Advantage to MVVMPC to MVVM

  - Very clear separations of concerns which simplifies keeping logic out of the viewmodel
  - With an event driven approach it's easy to summarise the applications functionality
  - Smaller classes are faster to setup and easier to test
  - Completely decoupled as only the View has a reference to other components (viewmodel & controller)
  - Just by using the application and reading the Events file it's easy to predict what the code looks like

# TDD for MVVMPC

There are two main approaches that can be used when working with TDD in a MVVMPC architecture:

The first being to start the TDD cycle with the domain, in which you first decide what logic that is desired,
write a failing test and implement the minimum production code to pass it. This cycle is completed till the domain
is complete and then the controller, presenter and viewmodel can be created in a similar fashion in any order.
Starting with the domain is a powerful way to verify the application will meet acceptance criteria upfront.

The other approach is to define all the events the application will rely on upfront, start the TDD test cycle
with a single event in the controller and progress to the domain, presenter and viewmodel. Do this for every event.
Starting with the events definitions is also a powerful way to quickly map out the functionality of an application.

The choice of which approach may prove better can come down to a case of our confidence in what we are building,
vs how the user will interact with it. If we are more confident in what we are building then the domain approach is best,
otherwise the event approach may be simpler.

# Improvements to make on app

  - Use a non-null Event type that will propagate only non-null values to avoid uneccessary null checks
  - Take greater advantage of binding adapters to reduce the amount layout configuration done in Breaking Bad Activity
  - Use the viewModel to hold more state such as the character_search.query and the spinner's value by creating more UIEvents

# final notes

  - Please treat the library package as a Work in progress library and not considered part of the app
  - More documentation in app source
