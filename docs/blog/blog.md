# Blog: Mid

**Cillian Mc Neill**

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
Over today i've looked into the structure of the main classes that will make up my backend. There will be smaller helper classes i'll need to look into further but i think i have the general structure of what i'd like. I've divided up each section of the backend into controllers which can be interacted with externally. These controllers will go on to access repositories of the objects that are being requested/updated. With this approach, I can seperate out functionality and make it cleaner overall. Along with this i've added initial firebase functionality into the mobile application. This is Google's clould messaging service and will allow me to send messages to any of the users if needs be. Primarily i'd use this for submission notifications but it can be used for other things. I'll continue to develop more diagrams over the week and i should hopefully have the classes up on git by the end of the week.

### Initial Backend Class Diagram

<img src="https://gitlab.computing.dcu.ie/mcneilc2/2018-ca400-mcneilc2/raw/master/docs/blog/images/backend_class_diagram.png" alt="Web login page mockup"width="1013" height="757">
