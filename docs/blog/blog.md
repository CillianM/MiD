# MiD Blog: Cillian Mc Neill

## Contents
- [Intro](#intro)
- [25th September 2017 - Research Phase](#25th-september-2017-research-phase)
- [26th September 2017 - Finding a Project Supervisor](#26th-september-2017-finding-a-project-supervisor)
- [2nd October 2017 - Securing a Project Supervisor](#2nd-october-2017-securing-a-project-supervisor)
- [24th October 2017 - Project Proposal Meeting](#24th-october-2017-project-proposal-meeting)
- [31st October 2017 - Functional Spec](#31st-october-2017-functional-spec)
- [1st November 2017 - Initial Client Development](#1st-november-2017-initial-client-development)
    + [Login Page](#login-page)
    + [Card Select](#card-select)
    + [New Card Select](#new-card-select)
- [7th November 2017 - First Supervisor Meeting](#7th-november-2017-first-supervisor-meeting)
- [14th November 2017 - Web Application](#14th-november-2017-web-application)
    + [Login Page](#login-page-1)
    + [Submission Select](#submission-select)
    + [Submission View](#submission-view)
- [15th November 2017 - Backend Classes](#15th-november-2017-backend-classes)
    + [Initial Backend Class Diagram](#initial-backend-class-diagram)
    + [Firebase Console](#firebase-console)
- [21st November 2017 - Backend Development](#21st-november-2017-backend-development)
- [28th November 2017 - Functional Specification Submission](#28th-november-2017-functional-specification-submission)
- [5th December 2017 - Backend Updates](#5th-december-2017-backend-updates)
    + [Backend Swagger Definitions](#backend-swagger-definitions)
- [29th January 2018 - Post Christmas Update](#29th-january-2018-post-christmas-update)
- [30th January 2018 - Mobile Application Updates](#30th-january-2018-mobile-application-updates)
    + [Sonar Dashboard - Initial Setup](#sonar-dashboard-initial-setup)
- [15th February 2018 - Web Application Pivot & Mobile Functionality Update](#15th-february-2018-web-application-pivot-mobile-functionality-update)
  * [Web Screens](#web-screens)
  * [Mobile Screens](#mobile-screens)
- [20th February 2018 - User testing](#20th-february-2018-user-testing)
- [27th February 2018 - Blockchain Implementation](#27th-february-2018-blockchain-implementation)
- [5th March 2018 - Access Control](#5th-march-2018-access-control)
- [14th March 2018 - SSL Security](#14th-march-2018-ssl-security)
- [22nd March 2018 - Website Promo Page](#22nd-march-2018-website-promo-page)
- [16th April 2018 - Testing Wrap-Up & Documentation](#16th-april-2018-testing-wrap-up-documentation)


## Intro
This blog will cover the development of my project "MiD", a blockchain leveraging identity application with the goal to be the replacement for all forms of identity we currently posses. This idea came to me as a result of my time spent in Mastercard during my internship. I noticed a gap in the process of online verification. As we are (to a certain extent) anonymous online it's hard to prove who a bank of authroity is talking to. My application hopes to aid in this as well as allow users to regain control of their identity and who has access to it.
While my blog will keep track of my thoughts and progress during development I will be working off a [Trello board](https://trello.com/b/Lp2PAf1i/mid-identity-engine) in place of an agile task tracker. This will allow me to compile each task and the work required for each.

## 25th September 2017 - Research Phase

### What has been done
I wanted to get an early start on the technology stack that I will be using for my project. To this end, I decided to get started on research this week. This has involved researching the various technologies I want to use in my solution. Since I came up with the idea I have been thinking of ways in which I could implement it so I have a base to build off. What I will look into first is the viability of using Spring as the backend framework for users to work off of. These users will be admins on a web interface and users on a mobile device. The certification will have to be stored on a blockchain infrastructure. I have looked into this previously and Hyperledger seems like the most optimal solution due to its speciality in the identity infrastructure.

### What will be done
Primarily the research will focus on Hyperledger and the implementations of it currently available. As It's open-source, there will be a lot of varied documentation on it but I'm confident in what's currently there from what I have previously found. My main source of information will come from [their own documentation pages](https://hyperledger-fabric.readthedocs.io/en/latest/)

## 26th September 2017 - Finding a Project Supervisor

### What has been done
I took the opportunity to meet with two lecturers I thought would help me with my project. Brian Stone was my project supervisor for my third year project. He was extremely helpful last year so I wanted to get his input into my current idea. Geoff Hamilton was my other choice for the project supervisor. He has a background in the field I'm looking at so his experience would be very useful in avoiding some of the common pitfalls.

### What will be done
As well as my research into Hyperledger and the surrounding technologies I want to use, I want to put together my project proposal and I send on drafts to both Brian and Geoff and get some feedback.

## 2nd October 2017 - Securing a Project Supervisor

### What has been done
After some discussion and emails with Geoff Hamilton he has agreed to be my project mentor. I think he'll be very helpful as he has a background in cryptography and security. As my project focuses on this, the help will be invaluable. 
<br> The research into the technology is progressing smoothly. I think I will go forward with using Spring for the backend as it is a framework I have worked with in Mastercard and there are plenty of resources available if needed. I've come to the conclusion that Hyperledger is definitely the direction I want to go. The supporting documentation is extremely helpful and I should be able to set it up and link it to my application without too much difficulty. <br>There are two types of users interacting with the system, an everyday person and an employee of some identifying authority. They will interact with the system with a mobile device and web interface respectively. I will develop the mobile interface for Android as i'm more familiar with it over something like IOS. I'm still unsure about whether I'll need to develop something for the authorities. Ideally I would give them access to the endpoints and they can add them to their current infrastructure but the examiner will need some form of visual representation of their workflow so it may be necessary.
<br>I finalised everything I wanted in my [project proposal](https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/blob/master/docs/Proposal/Project%20Proposal.pdf). I'll email on the final draft to Geoff and get his thoughts before pushing it to git.

### What will be done
Assuming Geoff is happy with the proposal I can prepare to present it during the proposal meeting. In the meantime I can solidify how the surrounding components will work with each other. I want to make sure I have a good understanding going forward of how the mobile interface will interact with the interface and how any Identifying parties will.

## 24th October 2017 - Project Proposal Meeting

### What has been done
Due to the weather my project proposal was pushed forward a week. Today the meeting went ahead and there were no issues. The meeting was with Charlie Daly and Donal Fitzpatrick. They liked my idea and didn't see anything wrong with the approach I'm taking. They reiterated the importance of security in the project and how I'm going to be handling the personal data being used. I've always kept this at the forefront of my planning so I don't see any issues stemming from this. 

### What will be done
My next steps will be to start on the preliminary version of the functional spec and see what Geoff thinks. If he gives the go ahead I will push it to the repo.

## 31st October 2017 - Functional Spec

### What has been done

I've finished writing the [functional specification](https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/blob/master/docs/functional-spec/Functional%20Specification.pdf) and have received back Geoff's thoughts on it. He didn't see any major issues and he thought it was very thorough. I'll run over it a few more times for semantic/grammar errors but I'm happy with the layout so far.

### What will be done
Following the gantt chart, I want to start working on the UI screens for the mobile application and admin interface. I'll also start developing the prototype for the Mobile interface.

## 1st November 2017 - Initial Client Development

### What has been done
After completing the functional specification i've started to work on the client mobile application. This has come in the form of creating screens in android studio. The initial screen will be a login page followed by a tabbed interface to view cards, current requests or any additonal settings. I have only developed the ones you see below. The other pages aren't as involved as these so I created these ones first to show the general flow of the app.

#### Login Page

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_login.jpg" alt="Login page mockup"width="304" height="603"><br>
This is the intial page that will allow a user to select a profile or create a new one. After this the user will be prompted for a pin and then they will be shown their currently saved cards.

#### Card Select

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_card.jpg" alt="Card selection mockup"width="304" height="603"><br>
Making use of [MaterialViewPager](https://github.com/florent37/MaterialViewPager) by Florent Champigny I was able to mock up a nice looking card selection page. The view pager is easy to implement and i've added my own adapters to the layout to allow me to display custom information.
```
<com.github.florent37.materialviewpager.MaterialViewPager
    android:id="@+id/materialViewPager"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:viewpager_color="@color/colorPrimary"
    app:viewpager_headerHeight="200dp"
    app:viewpager_headerAlpha="1.0"
    />
    
//Then implemented through the FragmentStatePagerAdapter
MaterialViewPager mViewPager = getView().findViewById(R.id.materialViewPager);
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                ListFragment listFragment = ListFragment.newInstance();
                listFragment.setCardType(cardTypes.get(position));
                return listFragment;
            }
                            ........
```

The user can swipe through the currently saved cards and see what their status is (if they've been validated or not) and view the information linked to them.

#### New Card Select

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_select.jpg" alt="New card selection"width="304" height="603"><br>

When creating a new card the user will be show the available cards they can select. Each card has unique information they require so they will be brought to a dynamically created form when the card selected is pulled down.

### What will be done
With these screens created I will need to implement the logic behind them. As well as this I will neeed to work on the rest of the screens for the mobile application. After some planning I don't think i'll need to mockup pages for the admin interface. While i'll need a demo a UI for that portion of the application it can be simple lists and form entries so i'm not too concerned with the look.

## 7th November 2017 - First Supervisor Meeting

### What was done
I had my first meeting with Geoff today. It was a group meeting where we discussed where we were in terms of progress and what our next moves were. Since last week I've stood up my web server and implemented functionality into the screens from the previous post. 

### What will be done
I'll need to do up web screens and hopefully implement simple versions of them. After that I'll need to start on my backend and creating the links required for interacting with the blockchain.

## 14th November 2017 - Web Application

### What was done
This weeks meeting was spent going over the web screens I mocked up over the week. It's a skeleton application that will need functionality written into it at a later point. This will serve as the example site for an Identifying party's portal and will be used in any demo when discussing the platform as a whole.

#### Login Page

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_web_login.png" alt="Web login page mockup"width="502" height="295"><br>
This is a simple mock of a login page. There will be no access control placed on the web application as this isn't something within the scope of the project. The application i'm creating is a set of endpoints for an authority to use. It's not my job to create a web application for them.

#### Submission Select

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_submission_select.png" alt="Submission selection mockup"width="502" height="295"><br>
When someone selects their form of identity and submits it for verification it will show up here for the authority to view. Only pending submissions will be show through this for now.

#### Submission View
<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_submission_view.png" alt="Submission view"width="755" height="322"><br>
This is the information that is submitted when the user requests it to be verified. The fields of that authorities idnetity type will be filled out by the user and a current photo will be sent along with it. This will allow the authority to link this information & picture to what they have on their system and make the decision as to whether the information is valid or not.

### What will be done
For this week i'd like to work on building up object diagrams of the backend along with initial construction of these objects into a spring project. I will also need to go over the functional spec and ensure there are no inconsistencies with what's currently being developed. It will also being a chance to triple check my grammar.

## 15th November 2017 - Backend Classes

### What has been done
Over today i've looked into the structure of the main classes that will make up my backend. There will be smaller helper classes i'll need to look into further but I think I have the general structure of what i'd like. I've divided up each section of the backend into controllers which can be interacted with externally. These controllers will go on to access repositories of the objects that are being requested/updated. With this approach, I can separate out functionality and make it cleaner overall.<br>
Along with this i've added initial firebase functionality into the mobile application. This is Google's cloud messaging service and will allow me to send messages to any of the users. Primarily i'd use this for submission notifications but it can be used for other things such as application updates or other application status announcements.

#### Initial Backend Class Diagram

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/backend_class_diagram.jpg" alt="Backend class diagram"width="604" height="583">

#### Firebase Console

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/firebase_console.png" alt="Firebase console page"width="1144" height="327">

### What will be done
I'll continue to develop more diagrams over the week and I should hopefully have the classes up on git by the end of the week.

## 21st November 2017 - Backend Development

### What has been done
After the meeting with Geoff today I decided to give one look back over the functional specification. After talking with him, I felt that there could be more diagrams in it as well as a slightly different structure to some points. These have been addressed and i've pushed it to git.<br>
We also discussed the initial backend class diagram. He is happy with the progress with it.

### What will be done
I've said that I'd like to further implement this diagram by next week. I have the initial structure coded but there is more to add to it before I want to show it to him.

## 28th November 2017 - Functional Specification Submission

### What has been done
Last Friday (the 24th) was the deadline for the functional spec. I added some last minute updates to it, primarily some user stories and additional features.
The updated spec was run by Geoff and he was happy with the updates. Over the course of the week i've been putting together the classes that make up the backend diagram. <br>

### What will be done
I want the initial structure of things along with unit tests before I push anything up. This should hopefully be over the next few days. There won't be a lot of functionality in the classes but I want to build up the overall structure to make it easier to expand from there.

## 5th December 2017 - Backend Updates

### What has been done
Over the past 2 weeks I have been busy with assignments and due to this the updates to the backend have been delayed. I've added the initial backend code to gitlab and attached a screenshot of the current swagger definitions. By having swagger integrated into my code I can easily document the API endpoints. I've also added initial unit tests for each class. Whenever I add a new class to the project a new unit test is created. This will make the building/testing flow of the project much easier. Over the course of the holidays I hope to have the backend in a semi-finished state, allowing me to fine tune it before I go back to working on the two UIs.<br>
With the backend finished I can also start on the interface for the blockchain. There isn't as much of a rush to get this done as it can be emulated on the backend side but it is still a priority.

### What will be done
Further updates to the backend to then allow for interaction betweenthe mobile applciation and demo authority's interface

#### Backend Swagger Definitions
<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/backend_swagger.png" alt="Web login page mockup"width="864" height="374">

## 29th January 2018 - Post Christmas Update

### What has been done
I was extremely busy over the course of the christmas break which lead to a decrease in the amount of free time I had to work on the project. Simple communication with the backend has been implemented but with little testing and verification. With the start of the new semester I will hopefully be able to use the first few weeks to get the bulk of the work done. 

### What will be done
I primarily need to set the android app up to take in and send out requests to the backend. The UI isn't much of an issue right now and can be tweaked as time goes on. Right now I have simple communication with the backend and from here I can use it as a base to expand into what I want. The next few days will be spent getting the android app up to speed on the communication I want it to have and then its down to fine tuning it while I work on the functionality behind those calls in the backend.

## 30th January 2018 - Mobile Application Updates

### What has been done
I was able to meet with Geoff today and discuss where I am with the project. Work is progressing steadily and there is currently nothing blocking me. I have taken massive steps in wrapping up the Android side of things over the last few days and by the end of the week i should hopefully
have everything I need to allow a user to use it as intended. The backend is slowly reflecting the updates to the app but the big changes will come later in the week (primarily the submission handling).
I have also added [sonar](https://www.sonarqube.org/) to keep track of development and any bugs that arise. It will also keep track of all tests created and document code coverage. Ideally I want to keep above 80% so that will be the goal going forward.

#### Sonar Dashboard - Initial Setup

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/sonar_dash.png" alt="Web login page mockup"width="1148" height="569">

### What will be done
With the app out of the way (functionality-wise) i'd like to get some more opinions on the UI. I've approached students within my year for tips on it and so far there is nothing glaring. I feel once the functionality is there the flaws will be more prominant.

## 15th February 2018 - Web Application Pivot & Mobile Functionality Update

### What has been done
Since the last blog post I've had several meetings with Geoff and numerous updates to the application to catch up on what I wasn't able to do during the christmas break. I want to get as much as I can done this month as assigments will start to come in at the start of March.<br>
I've updated demo authority interface into an admin portal to streamline party/identity creation and submission handling. This has allowed functional testing to proceed a lot smoother as I can use it to fine tune variables where I see fit.<br>
The backend hasn't seen many updates beyond patches to smoothen out functionality on the mobile interface. <br>
The mobile side has been the area where a lot of the work has been done. A lot of the projects funtionality has been added and the UI screens to enable this have been added too. Connections to the backend have been implemented and have allowed for the basic workflows to be tested functionality-wise. There is still tweaks that need to be performed (eg. cleaning up the UI and how the async calls are made to the backend) but otherwise the core of it is there<br>
As It stands now the app has:
* Profile Creation
    - A profile is created locally along with a public/private keypair
    - The name of the profile along with the public key is submitted to the backend and saved
* Card selection and local saving
    - Initially a user selects from a list of available cards (these are created by an identifying party)
    - The fields of the identity card are requested from the user
    - Once the user finishes, the card is created on the device. 
        + Note that the card doesn't touch the backend here until the user wants to create a submission
* Submission of created cards
    - The user reviews the information they entered and sends it, along with a picture of themselves, to the backend
    - The card is reviewed and accepted/rejected
        + During this time a user can view the submission and see the data and its status
* Responses on reviewed verifications
    - Once a verdict has been reached it is updated on the backend and the user is notified
    - The user now has a validated identity through which they can user to answer and information request (certificates to be implemented as of yet)
* Request creation and response handling
    - Users and parties (assuming they know the id of the user they want to ask) can request information from an identity card
    - Users scan a QR code and select the fields they want from a form of identity (a parties implementation of this is up to them)
    - The requested user is notified and shown the fields they are being asked for
    - The requested user can accept or deny the request outright with the addtional option to send only some of the fields that are being requested
    - Once the requested user updates the request the sender (if they are a mobile user) is notified and can view the data that was sent back in the request
        + Note here that the requested user can still withdraw the request to stop further viewings of it

### Web Screens

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/web_screen.png" alt="Web login page mockup"width="1157" height="521"><br>
If you compare this to what I had [previously](#login-page-1) you can see that the interface is a lot more generic. A user can create a party and add as many identity types as they need. Submissions are viewable for each party and are responded to in the same way as the original UI.

### Mobile Screens

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mobile_screens.png" alt="Web login page mockup"width="1083" height="768"><br>
These are the core functions of the app I outlined in the functional spec so I'm on track to getting the core done. With this done I can focus on user testing the UI and fine tuning it where needs be.<br>

### What will be done
The next step will be to implement calls on the backend to the blockchain and have request/responses call back to the backend to view certificates from the blockchain. This is a key part of the identity verification process so the sooner this is up and running the more fine tuning I can perform.<br>
I will need to start implementing more unit and integration tests. As it stands now I'm maintaining 80% code coverage of the backend but this must be the same for the mobile application. Integration tests will be in the form of cucumber BDDs that will be runnable  along with the unit tests. This suite will provide sufficient error checking on top of the functional testing I've been performing.<br>
In our recent meeting Geoff reminded me to get user feedback on the mobile application so i'm going to put together a simple questionaire and have friends/family of different computer literacy levels to step through the application and identity any flaws.

## 20th February 2018 - User testing

### What was done
Over the past few days I have gotten people in and out of college to go through the workflows of my application and [provide feedback](https://goo.gl/forms/fek6WQsjfGs84SpG2) on what they thought was missing/confusing. Overall there wasn't anything glaring. The main issue that i found from the testing was that there was a lack of labeling so a user was unsure on how to progress or how they might go about accomplishing a certain task. It was an enlightening test and i'm going to work to implement the suggested changes over the next few weeks.

### What will be done
Along with updating the application with the suggested changes I need to start on adding blockchain functionality to the backend & mobile application. The mobile site won't need a lot of changes beyond the option to view the certificate tied to information in a submission or a received request.

## 27th February 2018 - Blockchain Implementation

### What has been done
Since my initial look into Hyperledger there have been updates to streamline the over implementation process. This made using it even easier than I first thought. I created a seperated VM with the Hyperledger toolset. The developers toolset has commands to start up basic docker containers to deploy business logic to. `startFabric.sh` will accomplish all of this and allow me to deploy the network with `composer runtime install -n mid-network -c Admin@mid-network`<br>
With the network deployed I can create a REST server to interact with it in the same way I would my own backend with `composer-rest-server` or deploy a UI to perform similar actions with `composer-playground`<br>
With the above network and REST server in place I created a class to call and consume data from the REST servers's endpoints. This has allowed me to integrate it into user/party creation as well as the all important submission acceptance phase. Now, when a submission is marked as accepted a certificate is created in the users name which can then be called to by the user on the mobile application or by anyone the user has passed information to from that form of identity.

#### Composer Playground
<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/blockchain.png" alt="Web login page mockup"width="896" height="665"><br>
Above is an example of the playground interface I can use in conjunction with the REST enpoints. I can view all of the participants in the network aswell as create test ones from the UI. I can also create assets and initiate transactions on these assets. Currently when a submission is accepted a certificate is created in their name. Users can view the certificates but a transaction will need to be create to edit one.

### What will be done
With the overal functionality of the project in place I need to go back and look at the security and access control of the backend. Currently anyone can make calls to anything and receive back data that may not belong to them. Access control will need to to be placed on all endpoints and some form of encryption will need to be put in place (either SSL for communication encryption or RSA for per packet encryption)

## 5th March 2018 - Access Control

### What has been done
With the core functionality in place I needed to ensure endpoints were only accessible by the correct user. For example, A party can update and delete identity types but a user can't while i user can update requests but a party can't. There are also some endpoints that are open to anyone. Examples of this are the creation of a user or party. By implementing basic authentication headers on these calls the user can send in their id and an encrypted token (for now this will be the id itself). This token is encrypted with the private key of that user so through this we are showing ownership of the key and therefore the profile. `Authorization: Basic id:token` is the basic form of these headers. With this I can ensure a user's role (are they a normal user,party,etc.) and then limit access to data they're asking for based on the user requesting it. I can also ensure that even if they're allowed to hit the specified endpoint they can't request data that doesn't belong to them. If I wasn't to implement this user A, authorized as user A, can request B's data from the endpoint. 

### What will be done
With the basic access control in place I need to look at encrypting the data for submissions/requests. This is to ensure that even if data is leaked the attacker cannot get access to private information. Aswell as this I will need to add a dummy backend for the admin interface. This is because key creation/encryption/decryption isn't something for a web front-end to do. I will take calls made from the front-end and send them to this new web back-end. I can then perform the steps necessary to implement encryption/decryption/key creation with the applications back-end.

## 14th March 2018 - SSL Security

### What has been done
Security in the form of access control and SSL encryption on the backend endpoints have been implemented. This was done by creating a new domain [mid-secure.ie](https://mid-secure.ie) and implementing an SSL certificate from [sslforfree.com](https://sslforfree.com). This certificate allows me to have valid communication with a user without the need of a self signed certificate and the issues that come with that. I validated the ownership of that domain and was given certificate files through which I generated a keystore with `openssl pkcs12 -export -in certificate.crt -inkey private.key -out mycert.p12`. This p12 keystore file is in use with my tomcat instance I have on my server. The IP of the domain points to a personal server of mine that I can turn on and off easily and perform any maintence necessary for the platform to run.<br>
As I couldn't decrypt files on a web UI or authenticate through token encryption I have also created the dummy admin backend that will allow me to properly secure party endpoints and allow for data to be transmitted securely for the intended party. All this backend really does is capture calls from the UI and wrap any necessary authentication. This is the private key that is required for token encryption. The dummy backend stores the private key so that it may easily wrap any parties request with the correct key.

#### SSL Test Certificate
<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/ssl_cert.png" alt="ssl test cert"width="849" height="374"><br>
Above is a test I have run against the SSL certificate. It ensures that it meets all current standards for SSL and is secure for use. The test was successful so i'm happy to deploy it to the server 

### What will be done
The last step in terms of security will be the encryption of submission forms made to a party. As they're stored on the backend they need to be encrypted so that even if someone got a hold of a file they will not be able to view it. Now that I have the dummy admin backend in place I can decrypt requests from users. I couldn't do this before as it was just an lightweight angular UI so files remained unencrypted. Files will now be encrypted with the users private key and parties public key to provide non-repudiation and security as it is transferred. 

## 22nd March 2018 - Website Promo Page

### What has been done
With the new domain created, I wanted to make a splash page to explain the application and provide download links. I felt this would come in handy when demo-ing the application to someone. A small homepage (at [mid-secure](https://mid-secure.ie)) has been created with a link to where to download it and where you can view the source. I have added the mobile application to [the google play store](https://play.google.com/store/apps/details?id=ie.mid) where users can download and update the app. This is useful to me as i can get user usage data and crash reports.<br> 
I have implemented Log4J2 into the backend application. This will allow for easy debugging of runtime errors as they occur. The logging is levelled (INFO,DEBUG,ERROR) so it may be easily filtered.

#### MiD Web Page
<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mid_site.png" alt="mid site page"width="1152" height="641"><br>
Above is a screenshot of the homepage. It has 3 links to download the application, view source code and the supporting documentation within gitlab.

### What will be done
I now need to look into creating BDDs (Behavior-driven development). This will be in the for of cucumber integration tests for the backend. It will allow for human readable tests and further testing of the applications backend.

## 16th April 2018 - Testing Wrap-Up & Documentation

### What has been done
Over the last month I have been busy compiling all of the tests that will go into the backend portion of MiD. Everything is now put together and just requires some polishing. I have implemented more unit tests and create full BDDs for end to end testing of the application. There have been some updates to both the backend and mobile application as a result of these tests. The primary update is the further securing of submission data and request verfication from these submissions. This means that when someone makes a submission I will store a hash of each field of the data, each time a request is answered by a user the fields they're answering with are compared against these hashes so that i can ensure no updated data is being sent. This means that we ensure users are sending what was verified and not what has been made up by them. This oversight was brought up during the BDD portion when i tried to update it with illegal data and it passed.<br> 
I have compiled both this testing and all other forms done throughout MiD into [testing documentation](https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/blob/master/docs/documentation/Testing%20Documentation.pdf) that will add to the technical manual that I am still working on. Along with this I have created the initial draft of the [user manual](https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/blob/master/docs/documentation/User%20Manual.pdf) and I hope to get feedback from Geoff in our next meeting.

### What will be done
I will work on any feedback Geoff gives me on the current documentation I have. He is happy with the level of testing documentation I have and would like me to ensure that there is evidence of action taken on these tests. I will work on this, feedback on the user manual and hopefully start my technical manual.<br>
I will also go back over both the mobile and backend applications and refactor what I can. I'd like to ensure the code is as clean and readable as possible. I will also try to keep code coverage above 80%.