# TeamCode App
## Description
**TeamCode** transforms the way programmers find collaboration opportunities. With our intuitive platform, users can effortlessly initiate their own projects and seek out like-minded teammates, or discover and join existing projects that match their interests and skills. Whether you're looking to contribute to a project or seeking partners for your next programming endeavor, TeamCode makes the process seamless, connecting you with a community of programmers based on your personal preferences.



## Actor-goal list
| Actor           | Goal                                                        |
|-----------------|-------------------------------------------------------------|
| Project Manager | Create a new project                                        |
|                 | Procces project join request                                |
|                 | Set and change project's settings                           |
|                 | Delete project                                              |
|                 | Remove participant from a project                           |
|                 | Look up user's public info and projects history              |
| Participant     | Find projects based on skills and level                     |
|                 | Send request to join a project                              |
|                 | Leave a project                                             |
|                 | Look up user's public info and projects history              |

## Use Case Diagram
![UC Diagram](./TeamCodeUseCase.svg)

## Dictionary
| Term               | Definition                                                                                                 |
|--------------------|------------------------------------------------------------------------------------------------------------|
| TeamCode App      | The application that facilitates collaboration among programmers by allowing them to create and join projects.|
| Programmer         | An individual with coding skills who uses TeamCode to collaborate on projects.                             |
| Project            | A collaborative endeavor initiated by a user on TeamCode, aimed at achieving a specific goal.              |
| Project Manager   | A user who creates and manages a project on TeamCode.                           |
| Participant        | A user who joins a project created by a Project Manager to contribute.          |
| Collaboration     | The act of working together on a project with other programmers to achieve common objectives.              |
| Join Request      | A request sent by a Participant to join a project, subject to approval/disproval by the Project Manager.             |
| Project Settings  | Configuration options that allow the Project Manager to customize various aspects of a project.            |
| Public Info       | Information about a user or project that is accessible to all users on TeamCode.                           |
| Skills            | Proficiencies and abilities possessed by a programmer, which are used to match them with suitable projects. |
| Level             | The proficiency or experience level of a programmer, categorized as beginner, intermediate, or expert.      |
| History           | A record of past activities, such as projects joined or created, maintained by TeamCode for each user.      |
| Preferences       | User-defined criteria or settings that influence the matching process for finding projects.       |

## Business Rules
| Rule                              | Description                                                                                                    |
|-----------------------------------|----------------------------------------------------------------------------------------------------------------|
| Project Creation Limit            | Each user can create a limited number of projects (10) |
| Project Joining Restrictions      | Projects may have specific criteria or restrictions for joining, such as number of participants |
| User Conduct Policies             | Users must adhere to platform guidelines regarding appropriate conduct, including behavior and content standards. |
| Join Request Restriction                  | Each user can send only one join request to any project. |

## Use Cases

### User Registration
**Use Case Name**: User Registration <br/>
**Use Case Purpose**: To allow new users to create an account within the system. <br/>
**Prerequisite**: User must not already be logged in. <br/> 
**Typical Scenario**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User selects "Register" option | System displays the registration form |
  | User fills out the form with personal information and submits | System validates the information, creates a new account, and sends a confirmation email to the user |

**Alternative Scenarios**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User enters information that does not meet validation criteria | System displays an error message and requests corrections |
  
  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User attempts to register with an email that is already associated with an existing account | System displays an error message and suggests to login in an existing account |

**Result**: The user's new account is successfully created and saved in database, and the system ask them to log in using their new credentials.

### User Login 
**Use Case Name**: User Login <br>
**Use Case Purpose**: To authenticate existing users and grant access to their accounts. <br>
**Prerequisite**: User must have an existing account. <br>
**Typical Scenario**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User selects "Login" | System displays the login form |
  | User enters their credentials and submits | System validates the credentials and grants access to the user's account, redirecting them to main page |

**Alternative Scenarios**:
  
  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User attempts to login with an credentials that do not match | System displays an error message and offers the user the option to try again |

**Result**: User is successfully authenticated and gains access to authenticated areas of the system.

### Create Project
**Use Case Name**: Create Project <br>
**Use Case Purpose**: To enable users to initiate a new project for others to find and participate in. <br>
**Prerequisite**: User must be logged in. <br>
**Typical Scenario**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User selects "Create Project" option | System presents a project creation form |
  | User inputs project details and submits | System validates the information, creates the project, and notifies the user of successful creation |

**Alternative Scenarios**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User provides incomplete or invalid project details | System displays an error and requests the necessary corrections |

**Result**: A new project is created within a system, user becomes owner (manager) of this project.

### Process Join Requests
**Use Case Name**: Process Join Requests <br>
**Use Case Purpose**: To allow project managers to review, approve, or reject requests from users wishing to join the project. <br>
**Prerequisite**: User must be logged in and own a project. <br>
**Typical Scenario**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User navigates to project's join request section | System displays a list of pending join requests |
  | User reviews and decides on each join request | System updates the project membership status based on user's decision and notifies affected users |

**Alternative Scenarios**: None. <br>
**Result**: Project membership is updated to reflect approved or rejected join requests, with notifications sent to applicants.

### Delete People from Project
**Use Case Name**: Delete People from Project <br>
**Use Case Purpose**: To enable project managers to remove members from a project. <br>
**Prerequisite**: User must be logged in and own the project. <br>
**Typical Scenario**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User opens a members section on the project page | System makes and displays a list of all members "
  | User clicks "remove" button associated with member user wants to remove | System prompts for confirmation of removal |
  | User confirms removal | System removes the selected member from the project, updates the membership list and notifies removed member |

**Alternative Scenarios**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User cancels the removal action | No changes are made to the project membership |

**Result**: Selected members are removed from the project, and the project's membership list is updated accordingly.

### Find Projects
**Use Case Name**: Find Projects <br>
**Use Case Purpose**: To enable users to search for projects to potentially join. <br>
**Prerequisite**: None. <br>
**Typical Scenario**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User opens the project search page and selects level, skills, enters a name | System displays a list of projects matching the criteria |

**Alternative Scenarios**: None. <br>

**Result**: Users are presented with a list of projects that align with their search criteria.

### Send Join Request
**Use Case Name**: Send Join Request <br>
**Use Case Purpose**: To allow users to request to join a specific project. <br>
**Prerequisite**: User must be logged in. <br>
**Typical Scenario**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User finds a project they wish to join and selects "Request to Join" | System submits the join request to the project's manager for review |

**Alternative Scenarios**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User trying to send request to project that currently doesn't accept requests | System informs the user that project currently doesn't accept join requests |

**Result**: The user's join request is recorded and pending review by the project's manager.

### Leave Project
**Use Case Name**: Leave Project <br>
**Use Case Purpose**: To allow users to voluntarily leave a project. <br>
**Prerequisite**: User must be logged in and be a member of the project. <br>
**Typical Scenario**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User navigates to page with list of projects user participates in | System makes and displays a list of project user participates in |
  | User clicks "leave project" button associated with project user wants to leave | System prompts for confirmation to leave |
  | User confirms their intention to leave | System removes the user from the project's membership list and notifies project manager |

**Alternative Scenarios**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User do not to confirm decision to leave | System cancel the action, and no changes are made to their membership status |

**Result**: The user is removed from the project, and the project's membership list is updated to reflect this change.

### Change Project Settings
**Use Case Name**: Changing Project Settings <br>
**Use Case Purpose**: To enable project owners to modify the settings of their project. <br>
**Prerequisite**: User must be logged in and must be an owner of the project. <br>
**Typical Scenario**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User navigates to their project's settings page | System displays current project settings |
  | User click "edit" button | System displays an edit form, prefilled with current settings |
  | User makes changes to the settings and submits | System validates changes and updates the project settings accordingly |

**Alternative Scenarios**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User attempts to save settings with invalid data | System displays an error message and asks for corrections |

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User clicks "cancel" button | System doesn't make any changes |

**Result**: The project's settings are updated to reflect the changes made by the user.

### Change Public Info
**Use Case Name**: Changing Public Info <br>
**Use Case Purpose**: To allow users to update their public profile information. <br>
**Prerequisite**: User must be logged in. <br>
**Typical Scenario**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User navigates to their profile settings | System displays current public information |
  | User click "edit" button | System displays an edit form, prefilled with current settings |
  | User updates information and submits changes | System validates and saves the new public information |

**Alternative Scenarios**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User submits incomplete or invalid information | System displays an error message and requests corrections |
  
  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User clicks "cancel" button | System doesn't make any changes |

**Result**: User's public profile information is updated in the system.

### Look at Other Users' Public Info
**Use Case Name**: Looking at Other Users' Public Info <br>
**Use Case Purpose**: To enable users to view the public profile information of other users. <br>
**Prerequisite**: None. <br>
**Typical Scenario**:

  | User's Action | System's Reaction |
  |---------------|-------------------|
  | User clicks on a user's profile | System displays the public profile information of the selected user |

**Alternative Scenarios**: None.

**Result**: None.



