# Pender Android

Pender Android is an Android application library which allows Pender client applications to be built and run for the Android platform. Pender Client applications are implemented in javascript.


## What's a Pender?

Pender is a dom-less JS runtime with a high-performance graphics api implementing the HTML5 Canvas spec. Pender is open source and multiplatform..

The primary goal is to provide a hardware accelerated graphics api to mobile devices which lack high performance graphics in the native browser. The impetus behind that is mobile game development using web tech. On a given platform, we use an embedded javascript engine to provide faster js processing, and tighter coupling between native and js api's. 

Pender's use case philosophy mirrors phonegap/cordova's: write once, run everywhere. A Pender "client" application is a javascript renderer (e.g. a game engine) expecting a canvas api. On desktop browsers, or any browser with a hardware accelerated canvas implementation (IE9), a shim allows Pender Clients to run without modification. On Android/iOS, the Pender Client is dropped into a project container, and Pender supplies the necessary js hooks. 


## Platform Status

Android is currently Pender's flagship platform. We are in a demo-able state, with excellent performance from a custom render stress test. The Cordova Pender Plugin allows communication with Phonegap/Cordova allowing various use cases, including: 

* use Cordova's Webview as a UI layer

* use Pender to mimic an embedded canvas element

* leverage Cordova's plugins (Twitter, Facebook, etc)

## Requirements

* Java JDK 1.5 or greater

* Android SDK [http://developer.android.com](http://developer.android.com)

* git


##Quick Start

* open a unix terminal or terminal emulator (OSX Terminal, Windows Cygwin etc)

* Obtain Pender Source

         git clone git@github.com:lorinbeer/Pender-android.git

* navigate to Pender-android/tools

         cd Pender-android/tools

* Create a Pender Android project, omitting -p will create a pender android project in the current directory

         ./pendertools -p /path/to/put/pender/project


##Pender Build Tool

* run the pendertool from the tools directory

        cd Pender-android/tools

        ./pendertools

executing the script without arguments will create an Android library project in the current working directory.

* for a complete list of options

        ./pendertools -h


##Embedded Javascript Engine

Pender uses an embedded Javascript engine. This allows for fast dynamic parsing of javascript targeting the Pender Canvas. 
On Android and Blackberry, Rhino is used. Efforts are currently underway to upgrade Pender Android to V8. A static library is provided along with the project.

the rest of the Pender project:

* [Pender Docs](https://github.com/lorinbeer/pender-docs)

* [Pender Android](https://github.com/lorinbeer/pender-android)

* [Pender iOS](https://github.com/lorinbeer/pender-ios)

* [Pender Blackberry](https://github.com/lorinbeer/pender-blackberry)

* [Pender Windows Phone 7](https://github.com/lorinbeer/pender-wp7)

* [Pender Desktop](https://github.com/lorinbeer/pender-desktop)

* [Pender demos](https://github.com/lorinbeer/pender-demos)
