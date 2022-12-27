# Athletes-App
Athletes App is an app for presenting most of popular athletes in different sports. this application using the API from https://gist.githubusercontent.com/Bassem-Samy/f227855df4d197d3737c304e9377c4d4/raw/ece2a30b16a77ee58091886bf6d3445946e10a23/

[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=26)

## Code Installation

you can clone code and run it using :

``
  IDE : Android Studio
  kotlin_version = '1.7.10'
  Compile Sdk : 33
``

## App Screen Recording
 <table>
  <tr>
    <th>Splash Screen</th>
    <th>Home</th>
    <th>Product Details 1</th>
    <th>Ask for connection</th>
    <th>No Connection </th>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/72816466/209701170-631acf87-2ec8-4c7f-ace3-6d38c55f6e29.jpg" width="350"></td>
    <td><img src="https://user-images.githubusercontent.com/72816466/209701214-aecbf5fb-4260-4c3a-adb5-608fe6fcecb0.jpg" width="350"></td>
    <td><img src="https://user-images.githubusercontent.com/72816466/209701265-c21ac69e-00ba-482d-947c-6a570c9a958a.jpg" width="350"></td>
    <td><img src="https://user-images.githubusercontent.com/72816466/209701338-c8b8cf11-215f-403d-a8b2-2a9b2263b598.jpg" width="350"></td>
    <td><img src="https://user-images.githubusercontent.com/72816466/209701374-2e61afad-2015-4cf9-90da-a34d912e3557.jpg" width="350"></td>
  </tr>
</table> 

## Architecture
The following diagram shows all the modules and how each module interact with one another after. This architecture using a layered software architecture. 
![1 9fa8VrWQyNtpxvgGXghMPQ](https://user-images.githubusercontent.com/72816466/202196876-39bb8b5d-aa81-4693-8a5e-b1b588133975.jpeg)

## Tech stack & Open-source libraries
- Minimum SDK level 26
- Kotlin based.
- Android Gradle plugin requires Java 11 to run.
- StateFlow - emit state updates and emit values to consumers.
- ViewModel - UI related data holder, lifecycle aware.
- Coroutines for asynchronous.
- Architecture
    - MVVM Architecture ( DataBinding - ViewModel ).
    - Single Activity
       - multiple screens handled together using Navigation.
    - Repository Design Pattern
- Clean code, Use Cases
- Retrofit2 & Gson - construct the REST APIs.
- Room Databse - for cashing data local
- Dependency Injection (dagger-hilt).
