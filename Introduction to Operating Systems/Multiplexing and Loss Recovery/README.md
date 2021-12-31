# The Task
This task is inspired by a protocol that is rarely used today: the file service protocol FSP. It transfers large files (such as an operating system kernel) over UDP and is sometimes used to deliver large files to many blade computers in a compute cluster when the cluster is powered up. These blade computers do not have any permanent storage of their own; FSP is used to retrieve their operating systems during boot. FSP was developed because it is a very light-weight protocol on top of UDP, but its role is very limited because it cannot provide reliable data transfer. If packets get lost, FSP must restart the transfer.
In this task, you will create a new transport layer, RDP, the Reliable Datagram Protocol, which provides multiplexing and reliability, a NewFSP client that retrieves files from a server, and a NewFSP server that uses RDP to deliver large files to several NewFSP clients reliably.


## The Packets
<img width="594" alt="image" src="https://user-images.githubusercontent.com/86655546/147827419-ae4cf6b2-fc39-42e5-8bb8-30ab22f11f06.png">
<img width="600" alt="image" src="https://user-images.githubusercontent.com/86655546/147827435-2e4ceddc-85f0-47cf-8628-6790e6fc25eb.png">

