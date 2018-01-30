# Blog: Mid

**Cillian Mc Neill**

##Note
Over the course of the project I will be working off a Trello board in place of an agile task tracker. This will allow me to compile each task and the work required for each. it can be found [here](https://trello.com/b/Lp2PAf1i/mid-identity-engine)

## 25th September 2017

I decided to get started on work and research regarding my project this week. This would involve initial drafting of my project proposal and researching the various technologies I want to use in my solution. Primarily the research will focus on Hyperledger and the implementations of it currently available. As It's open-source, there will be a lot of varied documentation on it but I'm confident in what's currently there from what I have previously found. My main source of information will come from [their own documentation pages](https://hyperledger-fabric.readthedocs.io/en/latest/)

## 26th September 2017

I took the opportunity to meet with two lecturers I thought would help me with my project. Brian Stone was my project supervisor for my third year project. He was extremely helpful last year so I wanted to get his input into my current idea. Geoff Hamilton was my other choice for the project supervisor. He has a background in the field I'm looking at so his experience would be very useful in avoiding some of the common pitfalls.
Over the past few days I have been putting together my project proposal and I intend to send on drafts to both of them and get some feedback.

## 2nd October 2017

After some discussion and emails with Geoff Hamilton he has agreed to be my project mentor. I think he'll be very helpful as he has a background in cryptography and security. As my project focuses on this, the help will be invaluable. I finalised everything I wanted in my [project proposal](https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/blob/master/docs/Proposal/Project%20Proposal.pdf). I'll email on the final draft to Geoff and get his thoughts before pushing it to git.

## 24th October 2017

Due to the weather my project proposal was pushed forward a week. Today the meeting went ahead and there were no issues. The meeting was with Charlie Daly and Donal Fitzpatrick. They liked my idea and didn't see anything wrong with the approach I'm taking. They reiterated the importance of security in the project and how I'm going to be handling the personal data being used. I've always kept this at the forefront of my planning so I don't see any issues stemming from this. My next steps will be to start on the preliminary version of the functional spec and see what Geoff thinks. If he gives the go ahead I will push it to the repo.

## 31st October 2017

I've finished writing the [functional specification](https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/blob/master/docs/functional-spec/Functional%20Specification.pdf) and have received back Geoff's thoughts on it. He didn't see any major issues and he thought it was very thorough. I'll run over it a few more times for semantic/grammar errors but I'm happy with the layout so far.

## 1st November 2017

After completing the functional specification i've started to work on the client mobile application. This has come in the form of creating screens in android studio. The next job will be to put logic behind the screens.

### Login Page

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_login.jpg" alt="Login page mockup"width="504" height="896">

### Card Select

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_card.jpg" alt="Card selection mockup"width="504" height="896">


### New Card Select

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_select.jpg" alt="New card selection"width="504" height="896">


## 7 November 2017
I had my first meeting with Geoff today. It was a group meeting where we discussed where we were in terms of progress and what our next moves were. Since last week I've stood up my web server and implemented functionality into the screens from the previous post. I'll need to mock up web screens and hopefully implement simple versions of them. After that I'll need to start on my backend and creating the links required for interacting with the blockchain.

## 14th November 2017
This weeks meeting was spent going over the web screens I mocked up over the week. It's a skeleton application that will need functionality written into it at a later point. This will serve as the example site for an Identifying party's portal and will be used in any demo when discussing the platform as a whole. For this week i'd like to work on building up object diagrams of the backend along with initial construction of these objects into a spring project. I will also need to go over the functional spec and ensure there are no inconsistencies with what's currently being developed. It will also being a chance to triple check my grammar.

### Login Page

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_web_login.png" alt="Web login page mockup"width="896" height="504">

### Submission Select

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_submission_select.png" alt="Submission selection mockup"width="896" height="504">

### Submission View
<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/mock_submission_view.png" alt="Submission view"width="896" height="504">

## 15th November 2017
Over today i've looked into the structure of the main classes that will make up my backend. There will be smaller helper classes i'll need to look into further but i think i have the general structure of what i'd like. I've divided up each section of the backend into controllers which can be interacted with externally. These controllers will go on to access repositories of the objects that are being requested/updated. With this approach, I can separate out functionality and make it cleaner overall. Along with this i've added initial firebase functionality into the mobile application. This is Google's cloud messaging service and will allow me to send messages to any of the users if needs be. Primarily i'd use this for submission notifications but it can be used for other things. I'll continue to develop more diagrams over the week and i should hopefully have the classes up on git by the end of the week.

### Initial Backend Class Diagram

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/backend_class_diagram.jpg" alt="Web login page mockup"width="1013" height="757">

## 21st November 2017
After the meeting with Geoff today I decided to give one look back over the functional specification. After talking with him, I felt that there could be more diagrams in it as well as a slightly different structure to some points. These have been addressed and i've pushed it to git.<br>
We also discussed the initial backend class diagram. He is happy with the progress with it. I've said that i'd like to further implement this diagram by next week. I have the initial structure coded but there is more to add to it before i want to show it to him.

## 28th November 2017
Last Friday (the 24th) was the deadline for the functional spec. I added some last minute updates to it, primarily some user stories and additional features.
The updated spec was run by Geoff and he was happy with the updates. Over the course of the week i've been putting together the classes that make up the backend diagram. <br>
I want the initial structure of things along with unit tests before I push anything up which should hopefully be over the next few days. There won't be a lot of functionality in the classes but I want to build up the overall structure and start expanding from there.

## 5th December 2017
Over the past 2 weeks I have been busy with assignments and due to this the updates to the backend have been delayed. I've added the initial backend code to gitlab and attached a screenshot of the current swagger definitions. By having swagger integrated into my code I can easily document the API endpoints. I've also added initial unit tests for each class. Whenever I add a new class to the project a new unit test is created. This will make the building/testing flow of the project much easier. Over the course of the holidays I hope to have the backend in a semi-finished state, allowing me to fine tune it before I go back to working on the two UIs.<br>
With the backend finished I can also start on the interface for the blockchain. There isn't as much of a rush to get this done as it can be emulated on the backend side but it is still a priority.


## Backend Swagger Definitions
<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/backend_swagger.png" alt="Web login page mockup"width="1013" height="757">

## 29th January 2018
I was extremely busy over the course of the christmas break which lead to a decrease in the amount of free time i had to work on the project. With the start of the new semester I will hopefully be able to use the first few weeks to get the bulk
of the work done. I primarily need to set the android app up to take in and send out requests to the backend. The UI isn't much of an issue right now and can be tweaked as time goes on. Right now I have simple communication with the backend and from here 
I can use it as a base to expand into what I want. The next few days will be spent getting the android app up to speed on the communication I want it to have and then its down to fine tuning it while I work on the functionality behind those calls in the backend.

## 30th January 2018
I was able to meet with Geoff today and discuss where I am with the project. Work is progressing steadily and there is currently nothing blocking me. I have taken massive steps in wrapping up the Android side of things over the last few days and by the end of the week i should hopefully
have everything I need to allow a user to user it as intended. The backend is slowly reflecting the updates to the app but the big changes will come later in the week (primarily the submission handling). With the app out of the way (functionality-wise) i'd like to get some
more opinions on the UI. I've approached students within my year for tips on it and so far there is nothing glaring. I feel once the functionality is there the flaws will be more prominant.
I have also added [swagger](https://www.sonarqube.org/) to keep track of development and any bugs that arise. It will also keep track of all tests created and document code coverage. Ideally I want to keep above 80% so that will be the goal going forward.

### Sonar Dashboard

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/sonar_dash.png" alt="Web login page mockup"width="896" height="504">