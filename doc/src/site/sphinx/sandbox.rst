Stratio Sparkta sandbox
*****************************
Sandbox is a test environment where you can easily try the examples. It's the fastest way to do it, because you don't need to install
all the applications needed such as zookeeper,mongoDB and many others by yourself.

Everything you need to run sparkta it's already in the sandbox, it allows you in a few minutes to have a fully Sparkta application installed with all their dependencies.

Also in our sandbox you can find some demonstrations of our technology, they are explained step by step `here <examples.html>`__ .

Step 1: Vagrant Setup
===========

To get an operating virtual machine with Stratio Sparkta distribution up
and running, we use `Vagrant <https://www.vagrantup.com/>`__.

-  Download and install the lastest version (1.7.2)
   `Vagrant <https://www.vagrantup.com/downloads.html>`__.
-  Download and install
   `VirtualBox <https://www.virtualbox.org/wiki/Downloads>`__.
-  If you are in a windows machine, we will install
   `Cygwin <https://cygwin.com/install.html>`__.

Step 2: Running the sandbox
===========

 * Initialize the current directory from the command line::

     vagrant init stratio/sparkta


 * Start the sandbox from command line::

     vagrant up

 * In case you need to provision the sandbox run::

     vagrant provision


 * If the sandbox ask you for the credentials::


     user -> vagrant

     pass -> vagrant


Step 3: Accessing the sandbox
===========

 Located in /install-folder

 * Run the command::

    vagrant ssh



Step 4: Run Sparkta
===========


 To start Sparkta

 * Start zookeeper::

    sudo service zookeeper start

 * Start Sparkta::

    sudo service sparkta start

 * You can check the logs in::

    /var/log/sds/sparkta/sparkta.out

 * You can configure Sparkta in::

    /etc/sds/sparkta/


Useful commands
===========

 * Start the sandbox::

    vagrant up

 * Shut down the sandbox::

    vagrant halt

 * Exit the sandbox::

    exit


Now you are ready to `test <examples.html>`__ the sandbox

What you will find in the sandbox
=================================

-  OS: CentOS 6.5
-  3GB RAM - 2 CPU
-  Two ethernet interfaces.

+------------------+---------+-------------------------------+
|    Name          | Version |         Command               |
+==================+=========+===============================+
| Spark            | 1.3.0   | service spark start           |
+------------------+---------+-------------------------------+
| Cassandra        | 2.1.2   | service cassandra start       |
+------------------+---------+-------------------------------+
| MongoDB          | 2.6.9   | service mongod start          |
+------------------+---------+-------------------------------+
| Elasticsearch    | 1.5.2   | service elasticearch start    |
+------------------+---------+-------------------------------+
| zookeeper        | 3.4.6   | service zookeeper start       |
+------------------+---------+-------------------------------+
| Kafka            | 0.8.1   |                               |
+------------------+---------+-------------------------------+
| scala            | 2.10.4  |                               |
+------------------+---------+-------------------------------+
| RabbitMQ         | 3.5.1   | service rabbitmq-server start |
+------------------+---------+-------------------------------+




F.A.Q about the sandbox
=======================

I am in the same directory that I copy the Vagrant file but I have this error:

.. code:: bash

        A Vagrant environment or target machine is required to run this
        command. Run vagrant init to create a new Vagrant environment. Or,
        get an ID of a target machine from vagrant global-status to run
        this command on. A final option is to change to a directory with a
        Vagrantfile and to try again.

Make sure your file name is Vagrantfile instead of Vagrantfile.txt or
VagrantFile.

--------------

When I execute vagrant ssh I have this error:

.. code:: bash

        ssh executable not found in any directories in the %PATH% variable. Is an
        SSH client installed? Try installing Cygwin, MinGW or Git, all of which
        contain an SSH client. Or use your favorite SSH client with the following
        authentication information shown below:

We need to install `Cygwin <https://cygwin.com/install.html>`__ or `Git
for Windows <http://git-scm.com/download/win>`__.


