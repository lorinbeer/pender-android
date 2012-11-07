# Pender Android v0.1a

2D Hardware accelerated Canvas context for mobile
this is the Android version

##Status

##Requirements
* Android SDK
* git

##Quick Started

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

* for a complete list of options

        ./pendertools -h


##Embedded Javascript Engine

Pender uses an embedded Javascript engine. This allows for fast dynamic parsing of javascript targeting the Pender Canvas. 
On Android and Blackberry, Rhino is used. A static library is provided along with the project.

the rest of the Pender project:

* [Pender Android](https://github.com/doggerelverse/Pender-android)

* [Pender iOS](https://github.com/doggerelverse/Pender-ios)

* [Pender Blackberry](https://github.com/doggerelverse/Pender-blackberry)

* [Pender Windows Phone 7](https://github.com/doggerelverse/Pender-wp7)

* [Pender Desktop](https://github.com/doggerelverse/Pender-desktop)

* [Pender demos](https://github.com/doggerelverse/Pender-demos)
