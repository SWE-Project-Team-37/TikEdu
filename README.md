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
