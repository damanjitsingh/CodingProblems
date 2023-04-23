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

In the above command inventory is the file that has inforamation about the list of hosts on which the actions are run.


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
Q.



Q: Do you want to twll about yourself which I have not asked.

Q Tell me about a crisis situation in your life?

Q Who are your motivators? What motivates you?

Took initiative to write teamcity script to build deployt infrastructre for cloud deployment.