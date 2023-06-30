# Experience

I am working as a backend software engineer at Wolfram Research Inc since 2018. Our tech stack is java based Spring (5.3) application.
We use MySQL(5.6) to store persistent data and Redis(2.8) to store cache. We use Hibernate (5.3) library for object-relational mapping.

My team involves in the development and maintainance of back end code that contains RestAPIs to fetch information from the Wolfram cloud.
One simple use case is to open a computational notebook from the browser. Similarly we have APIs that we can call from Wolfram Language.
So I have a fair knowledge of RESTful APIs.


My current project is involved in containerizing our monolith cloud application. I have used Docker for creating containers for various section of cloud application.

Q How networking works in containers.
When you start a docker container running on a docker daemon, then it automatically uses default bridge network and can connect to all the containers running
on that bridge network. You can also use custom defined bridge networks, to connect specific containers.
We can use `--network` argument to run the docker on a user defined bridge network.

```
docker network create user-net
docker create --name my-nginx \
  --network my-net \
  --publish 8080:80 \
  nginx:latest
```

When I joined Wolfram Cloud team I worked in the backend code to fix some bugs. Involved in implementing new REST APIs.

Q: What is a RESTful API?
A RESTful API (Representational State Transfer API) is an architectural style for building web services that allows clients to access and manipulate data stored on a server.
It is based on the principles of the HTTP protocol and is designed to be simple, lightweight, and scalable.

The core idea behind a RESTful API is to expose a set of resources (such as users, products, or orders) as URLs that can be accessed using HTTP methods
(such as GET, POST, PUT, or DELETE). Each resource should have a unique URI (Uniform Resource Identifier), and its state should be represented as a JSON or XML document.


Our team uses Stash/GIT as a version control system.

Q: What are the common GIT commands?
git push, git checkout , git status, git pull, git fetch etc.

Q: What do you understand how git system works on a high level?
When you checkout a repository git creates a local repository under .git directory inside your repo path. Under this directory git stores information about the
current checkout branch, stashes you have created etc. So when you make changes to your code, it makes the changes locally in your system. When you do
git add adds the changes to the staging area and git commit will push it to the git directory. From there git push will push the changes to the remote repository.


Q What are the git commands to push an initial commit on a new git repository?

```
echo "hello sample API read me" >> ReadMe.md
git add ReadMe.md
git commit -m "first commit"
git branch -M main
git remote add origin git@github.com:damanjitsingh/SimpleSpringHello.git
git push -u origin main
```

Q Do you know basic MySQL commands?
Yes.

## Ansible

Examples of ad hoc ansible commands:
* `ansible localhost -m stat -a "path=/tmp/report.txt"`
* `ansible localhost -m file -a "path=/tmp/dir state=directory"`
* `ansible localhost -m ping`

Corresponding commands in a playbook:

```
---
- hosts: all
  tasks:
  - name: create directory
    file:
      path: /tmp/dir
      state: directory
  - name: ping
    ping:
  - name: get path info using stat
    stat:
      path: /tmp/report.txt

```

You can run the above playbook using:
` ansible-playbook commands.yml -i inventory`

In the above command inventory is the file that has information about the list of hosts on which the actions are run.

Ansible playbook to install a package by downloading it from a URL:

```
---
- name: Download and install a package
  hosts: all
  # become is used to escalate the privilege of the current user to become root
  become: true

  vars:
    package_url: "https://example.com/package.tar.gz"
    package_dir: "/opt/package"
  
  tasks:
    - name: Create directory for package
      file:
        path: "{{ package_dir }}"
        state: directory

    - name: Download package
      get_url:
        url: "{{ package_url }}"
        dest: "{{ package_dir }}/package.tar.gz"
      
    - name: Extract package
      unarchive:
        src: "{{ package_dir }}/package.tar.gz"
        dest: "{{ package_dir }}"
        remote_src: true
        creates: "{{ package_dir }}/package"
      
    - name: Install package
      command: "{{ package_dir }}/package/install.sh"

```


## GitHub actions
Sample action file:

```
name: GitHub Actions Demo
run-name: ${{ github.actor }} is testing out GitHub Actions 🚀
on: [push]
jobs:
  Explore-GitHub-Actions:
    runs-on: ubuntu-latest
    steps:
      - run: echo "🎉 The job was automatically triggered by a ${{ github.event_name }} event."
      - run: echo "🐧 This job is now running on a ${{ runner.os }} server hosted by GitHub!"
      - run: echo "🔎 The name of your branch is ${{ github.ref }} and your repository is ${{ github.repository }}."
      - name: Check out repository code
        uses: actions/checkout@v3
      - run: echo "💡 The ${{ github.repository }} repository has been cloned to the runner."
      - run: echo "🖥️ The workflow is now ready to test your code on the runner."
      - name: List files in the repository
        run: |
          ls ${{ github.workspace }}
      - run: echo "🍏 This job's status is ${{ job.status }}."
```

## Projects

### Cloud Containers

I am the lead developer handling the development of several containers. For example we have one container which act as a broker between the kernel and webapp communication called KernelBroker. Then another container is that handle packets communication between webapp and kernel. We use sockets for that communication.

My high level responsibilities in this project are:
* I have developed these two containers actively from scratch including implementing RESTful API end points.
* Collaborated with the Principal Architect of the project for design decisions.
* Presented the overall design and implementation to stake holders from other cloud sub teams so that everyone is on the same page.
* Currently I am handling stability and performance issues.

List of Questions that can be asked here are:
* Why do you need to use Docker? What purpose it is serving?
* What is the role of Kubernetes here?
* What is your biggest achievement in this project? (Learning new technologies that no one in our team was familiar of, independently developing containers from scratch, researched and added distributed tracing etc).
* What is/was the main hurdle you had faced in this project.
One hurdle is the lack of resources or developers and websys people, because many have left in the past. This has impacted the momentum of the project.
Initially the state machine of KernelWorker container was not stable, so we used to see lots of bugs. Then there was extensive discussions between the Architect and me
to set this thing straight. Once we had the stable state machine everything just fell into the right place.
Another hurdle which we are facing is hiring the right set of people who knows about containerization.

### CloudObject and EPC automation
When I joined Cloud team here at Wolfram Research, I was assigned the responsibilities of fixing issues and maintaining the code that handles communicating with our cloud webpp through Mathematica. We use CloudObjects client (which is written in WL) to communicate with it programmatically.

* There was not a one big project perse but I fixed couple of critical issues in the infrastructure related to performance and add new features (like using compressed serialization to transfer data).
* I was also involved in the project where I wrote the automation of installing an EPC (Enterprise private cloud) on a linux VM. I used Ansbile to automate downloading
the dependencies and installing EPC on the VM.
* I was also involved in automating our CI/CD pipeline on Github using Github actions.


List of Questions that can be asked here are:
* Questions related to Ansible like what are roles, playbooks?
* Questions related to CI/CD like Jenkins.

### Sofware QA engineer
I was responsible for testing the critical components of Mathematica (like all the External services connections).
I used MUnit, C and Ansible for automation.
In my free time I voluntarily involved myself into development tasks (like fixing bugs and implementing new features)by approaching to the project managers of features that I used to test.
I developed my own services which were published in the Wolfram documentation.

I think above are nice examples of what you did other than your assigned tasks.



### Questions that I should ask
* What is the revenue model of FHLBI?
* Is the recent collapse of several banks has any impact on FHLBI?
* How can I expect my career to progress at FHLBI?
* From the requirements in the job description it seems like the candidate should have experience in software development, CI CD pipeline automation, Server
side setup and application automation. Does it mean that everything will depend on project and based on that the candidate will be assigned a role?
* What are the programming languages your team uses commonly?


### Behavioural questions
* Why do you want to join the bank?
Professional reason:
Opportunity to work on cloud migration like gov cloud
Lot of appliations to learn from
Want to work in finance sector
I am interested in cloud migration. How it pans out in the future since a lot of companies has started doing it recently.
I am interested to learn and work on the security aspect of bank applications.
My personal reason is to move to a big city and I saw an opportunity at your back which matches my skill set.

* Why are you interested in this position?
I have a chance to apply my skills while simultaneously learning more about automation and its applications.
I worked on some automation in the past and want to try my hands on this on a bigger level.
Technology around building infrastructure for applications deployment and configuration has grown tremendously. For example use of Ansible and Terraform has seen significant
growth. I want to try my hands in the emerging stuff while not too much deviating away from my core skills.


* What motivates you at work?
I am motivated by new challenges. Whether it is learning to implement new technologies or discussing a complex design.
I am also motivated by the opportunities to learn in software development field. The learning never stops.
Mentoring teammates.
Interacting with peers and learning from their keystone habits.

* What do you do to upskill yourself?
Take new courses from educative.io.
Read news about latest technical advancements like ChatGPT.
Participate in StackExchange discussions with like minded community members.

* Why did you left your previous company and rejoined Wolfram.
Although it was a lot of learning there as well, but the company management decided to cut some roles in lieu of cost cutting efforts considering the market
conditions which I think was impacting their revenue as well. It lost couple of projects from some big vendors towards the second quarter of last year.
When we were told about the company is planning its cost cutting efforts I reached out to my previous manager and becuase my relationship with him ended on good terms
he happily offered me the position. He was also looking to fill my vacany during that time so luckily it was a right opportunity for me at the right time.


What is the recent thing you learn outside of your work.

* Quick resume
I have a passion to automate manual tasks. Projects on CI CD pipeline.

Sometimes it is not worth to move to cloud. Depends on usage.

Use inspire, motivating words
Continmuous improvement is always my goal.

My goal is to understand the bank's business model. Technology is always is easy but critical part is to understand the business.

Documentaiton is useless withoug search.

## Notes from discussion with Gogu
Q Why you want to move?
A: Want to move to a big city. THen cloud migration is one technical reason.

Q Did you work on Ansible? How to write yaml playbook on Ansible. To teest the ansible code yoiui can use Assert, Check?
Read about https://www.simplilearn.com/tutorials/ansible-tutorial/ansible-interview-questions
Why containers are important in going to Cloud.
Know about MySQL queries
https://www.wsj.com/articles/banks-leaned-on-a-little-known-lender-in-march-as-customers-fled-c09ef6ab
https://www.fhlbi.com/who-we-are/mission-vision-guiding-principles/


Behavior questions:
Q: How to handle pressure situation?


Q: Do you want to tell about yourself which I have not asked.

Q Tell me about a crisis situation in your life?

Q Who are your motivators? What motivates you?

Took initiative to write teamcity script to build deployt infrastructre for cloud deployment.