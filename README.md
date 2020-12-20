## MyAlarm


MyAlarm is a simple alarm app. This app works because it uses foregroudnservice even if  I exit the app.

Also When you boot or reboot your phone, broadcastReceiver broadcasts and starts alarm service.

<br>

### Architecture

![Untitled Diagram (8)](https://user-images.githubusercontent.com/34837583/102713168-77496f00-4309-11eb-8cff-52625eec559a.png)

This app is implemented based on the MVP architecture.

The MVP architecture can solve the problem that the controller depends on the Android API. This makes unit tesing easier.

But like the MVC architecture, when you change the view, you have to go back to the controller or presenter and change it. Also over time, the code of the controller or presenter gets longer, which may cause problems.

<br>

### Component Design

![image](https://user-images.githubusercontent.com/34837583/102713399-5f72ea80-430b-11eb-8f2c-421e8789a54a.png)

**MainPresenter** : Mainpresenter applied singleton design pattern. Mainpresenter is responsible for executing the alarmservice.

**AlarmDatabae** : AlarmDatabase is database that have setting alarm information. This is used SharedPreference and applied singleton design pattern.

**BootReceiver** : This is BroadcastReceiver that is executed when application boot up or reboot.

<br>

### End

I'm still junior developer. I'm going to grow into an Android developer.  Thanks very much a drip if you would give the advice you gave me an efficient development. ( ex) pull request ..)

Thank you for reading my readme.
