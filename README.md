# camera-fp05
A nifty custom android camera library for taking and cropping photos on the fly. Best suited for use on the fp05 mobile device.
Official release library for fgtit-fp05 device.

Usage:

Step 1. Add the JitPack repository to your build fileor
Add it in your root build.gradle at the end of repositories:

```gradle

    allprojects {
    		repositories {
    			...
    			maven { url 'https://jitpack.io' }
    		}
    	}

 ```

Step 2. Add the dependency
```gradle
    dependencies {
    	        compile 'com.github.interwap:camera-fp05:v1.1'
    	}
 ```

Get more information about versions and releases here: [`jitpack`](https://jitpack.io/#interwap/camera-fp05/v1.1)



Using the Camera Module:

Step 1. Set up camera call back request code

```gradle
    private static final int CAMERA_REQUEST_CODE = 1;
 ```

Step 2. Call Camera Intent

```gradle
   new Intent(this, CameraFP05.class);
 ```

 Step 3. Optional Parameters

 ```gradle

     STORAGE LOCATION (String):

     intent.putExtra ("storage", "storage location");


     //Please note the default storage location for CameraFP05 is the external storage.
     //Providing a storage location means that you have already created a folder or folders in your sd card
     else it will save to the root folder of the sd card as default
     //Storage location must not start with a file separator (/)
     //Usage Example: intent.putExtra("storage", "newimages") or intent.putExtra("storage", "newimages/today");

     IMAGE QUALITY (int):

     int.putExtra("quality", 100);

     //Please note that the default image quality is 70 and parameter expects integer
     //For the best image quality, set quality to 100.

     Caveat: The higher the quality, the higher the actual size of photo on device.
     Although image produced have been optimized.

  ```

 Step 4. Start Activity and pass request code
 ```gradle
    startActivityForResult(intent, CAMERA_REQUEST_CODE);

    //On capture, photo will be saved in specified storage location or in the root of your external storage if non was specified

  ```

 Step 5. (Optional)
  ```gradle

     After capture, CameraFP05 returns to the previous activity it was launched from. Optionally,
     absolute path of image captured is sent along (as a String: "imageURI")
      and can be retrieved by calling the override method:

     @Override
         protected void onActivityResult(int requestCode, int resultCode, Intent data) {
             super.onActivityResult(requestCode, resultCode, data);

             if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){

                 String photoName  = data.getExtras().getString("imageURI");
                 Toast.makeText(context, photoName , Toast.LENGTH_SHORT).show();
             }
         }

   ```

  Why Camera-FP05?
  - Suitable for use in data collection
  - Crops image to International Civil Aviation Organization (ICAO) Specifications
  - Low quality images for online identification and enrolment

  Coming Soon (v1.2)?
  - Specify crop size
  - Change crop box color
  - Show and hide cropper

License
=======

    Copyright 2017 Ikomi Moses

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
