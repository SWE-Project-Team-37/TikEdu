# TikEdu

## Client

Requirements:

    Windows OS, Android Studio, 15 GBs storage, 8 GBs Ram, Android Phone (Optional)
    
1. Install Android Studio
    1. Download Android Studio (https://developer.android.com/studio/)
    2. Make sure Android Virtual Device is selected, if not already
 
        ![client1](/readme_images/client1.png)

    3. Use all other default options and click finish when done

2. Clone TikEdu Client Repo
    1. Click “Get from VCS” when on Android Studio’s “Welcome to Android Studio” home page

        ![client2](/readme_images/client2.png)
        
    2. Paste in repo url (https://github.com/SWE-Project-Team-37/TikEdu.git)

        ![client3](/readme_images/client3.png)
        
    3. Click trust project

        ![client4](/readme_images/client4.png)
        
3. Create new device
    1. Click on **No Devices** and select **Device Manager**
    
        ![client5](/readme_images/client5.png)
        
    2. Click on **Create Device**, select **Pixel 5**, download and select **system image S**, i.e. **API Level 31**

        ![client6](/readme_images/client6.png)

        ![client7](/readme_images/client7.png)
 
        ![client8](/readme_images/client8.png)
        
    3. Accept all other defaults and click finish

4. Build and make project
    1. Select Build Variant (Debug or Release)

        ![client9](/readme_images/client9.png)

        ![client10](/readme_images/client10.png)
        
    2. If Build Variant = **Release**, fix default configuration: open **File->Project Structure**, add $signingConfigs.debug

        ![client11](/readme_images/client11.png)
        
        ![client12](/readme_images/client12.png)

5. Run app
    1. Click **Run "App”** or **Shift+F10**

        ![client13](/readme_images/client13.png)
        
## Server

Requirements:

    2x Windows OS, 2x Visual Studio Code, 2x Github Desktop, UF VPN Access, 

1. Download Github
    
    1. On both computers, open Github Desktop

    2. Select file, “clone repository”, search SWE-Project-Team37/TikEduBackend and select clone

    3. This will download all of the necessary files

2. Download Oracle
    1. On the computer intended to connect to the database, go to https://www.oracle.com/database/technologies/appdev/python/quickstartpythononprem.html and follow only step 1, the 2nd and 3rd bullet point

        ![server1](https://user-images.githubusercontent.com/73558998/164346458-6cf4fdc5-2a29-4803-9028-d5d52bb64f81.png)
        
    2. Make sure to put all of the files in the 2 zipped folder into 1 unzipped folder, then add that folder to the path as the instructions say (if 

    3. Now, go into vscode where your files are located and in the top bar of vscode select “Terminal” then “New Terminal”

    4. Run pip install oracle_cx

3. Starting the Database Connection and Server
    1. Using the computer that you would like to be on the VPN, go into Cisco AnyConnect VPN service and login

    2. Go to this page https://www.whatismybrowser.com/detect/what-is-my-local-ip-address and determine your private ip, usually starts with 192

    3. Open httpserver.py in VSCode then go to the bottom of the file and change the ip to the one you just determined 

        ![server2](https://user-images.githubusercontent.com/73558998/164347100-fd9c7033-f651-49c3-9863-914471f61156.png)

    4. Right click on the code and select run in terminal, you should then see the message on the above image and then the server to database is setup

4. Starting the pipeline from Client to Server

    1. Make sure that this part is run on a different machine than the one from the database but on the same wifi network

    2. Open the pipeline.py in VSCode

    3. Again, find your local ip (starting with 192 most likely) and insert it at the bottom of the file where it says ‘HOST =’  (around line 69), and insert the ip from Step 3 into the ‘HOST = ‘ section around line 4

    4. Follow these steps to setup port forwarding https://www.hellotech.com/guide/for/how-to-port-forward making sure to set the internal ip to the one inserted in line 69 in the step above and set the port (internal and external) to 8080

    5. If you want to do all machines on the same wifi you can change the ip in every file to be ‘localhost’ (including in the android code), but we intended it to be run remotely

    6. Right click on the code and select run in terminal, you should then see the message on the above image and then the server to database is setup
