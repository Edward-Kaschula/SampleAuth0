# SampleAuth0
Auth0 Project that doesn't show username and password controls

Steps to reproduce: 

Clone the project:

Add your own auth0 domain and client id in the Strings.xml file
Build and run

Expected result:
The Auth0 login andn sign up screen appears but has no controls for entering a username and password.

To fix the problem: 

If you change the module level build.gradle file entry for 

implementation 'com.google.android.material:material:1.1.0' 

to rather be 

implementation 'com.google.android.material:material:1.0.0'

Sync the gradle changes, build and run the project.

Expected result:
The Auth0 login and sign up screen appears with controls for entering a username and password.
