C24 Integration Objects & Mule ESB Studio
=========================================

Overview
--------

This sample project is a simple demonstration of how C24 Integration Objects can be used within
a Mule ESB flow to parse, validate & transform inbound messages.

The flow shows receiving and processing of SWIFT MT541s, either read from the file system or
received via an HTTP post.


Importing the Project
---------------------

In the Mule Studio, import the project by going to File->Import... and selecting Mule->Mule Studio Project from External Location.
Set the Project Root to be the folder containing this file and import the project.


Setup the Project
-----------------

Open flows->iO Sample.mflow. We need to set the locations that files will be read and written from and the port that
the HTTP listener will use:

1. For the file, first double click on the 'Inbound SWIFT' file adapter at the start of the FileAcquisition flow. Change 
the Path attribute to somewhere that exists in your local filesystem (defaults to /tmp/mule/in).

2. Similarly, at the end of the ProcessingFlow set the Path of the 'Persist XML Message' (defaults to /tmp/mule/out).

3. Double click on the 'Inbound SWIFT' HTTP adapter at the start of the HTTPAcquistion flow. Set the Port attribute
to an ununsed port (defaults to 8081).


Running the Project
-------------------

Right click on the iO Sample.mflow in the Package Explorer and select 'Run As->Mule Application' 

You'll see some startup output on the console which will end with:

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
+ Started app 'io_sample'                                  +
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

The application is now waiting for input.


Processing a File
-----------------

Copy the swift file from data/swift into the directory you configured on the inbound file adapater
(default /tmp/mule/in). You will notice the file is deleted from the directory when Mule picks it up
and on the console you will see the message parsing progress being logged. 

Once complete there will be
a new file in the output directory (that you configured on the outbound file adapter) containing the
complete MT541 but converted to an XML format.


Processing an HTTP POST
-----------------------

The HTTPAcquisition flow takes the SWIFT message from the body of an HTTP POST.

Remember that SWIFT messages must use \r\n as their line terminator, so you need to make sure that
these are preserved when you create the HTTP request.

For example, to use curl to post the contents of the supplied MT541 sample message, from this directory
run:

curl --data-binary @data/swift/MT541.dat http://localhost:8081/SwiftMT541

ensuring the port matches the one you configured above.

You will notice in the flow that, in addition to writing the XML result to a file, we've configured
the XML document to be sent back as the HTTP response. When you run curl, you should see the XML
document written to the standard out of your console.
