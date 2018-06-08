package com.learn.flutterruntimepermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

  private static final String CHANNEL_CONTACTS = "runtimepermissiontutorial/contacts";

  private static final int GET_CONTACTS_PERMISSION_REQUEST_ID = 2345;

  private PermissionCallback getContactsPermissionCallback;

  private boolean rationaleJustShown = false;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    new MethodChannel(getFlutterView(), CHANNEL_CONTACTS).setMethodCallHandler(
            new MethodChannel.MethodCallHandler() {
              @Override
              public void onMethodCall(MethodCall call, final MethodChannel.Result result) {
                getContactsPermissionCallback = new PermissionCallback() {
                  @Override
                  public void granted() {
                    rationaleJustShown = false;
                    result.success(0);
                  }

                  @Override
                  public void denied() {
                    rationaleJustShown = false;
                    result.success(1);
                  }

                  @Override
                  public void showRationale() {
                    rationaleJustShown = true;
                    result.success(2);
                  }
                };
                if (call.method.equals("hasPermission")) {
                  hasPermission();
                }
              }
            });
  }

  private void hasPermission() {
    if (rationaleJustShown) {
      ActivityCompat.requestPermissions(this,
              new String[]{Manifest.permission.READ_CONTACTS},
              GET_CONTACTS_PERMISSION_REQUEST_ID);
    } else {
      if (ContextCompat.checkSelfPermission(this,
              Manifest.permission.READ_CONTACTS)
              != PackageManager.PERMISSION_GRANTED) {

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {

          getContactsPermissionCallback.showRationale();

        } else {

          // No explanation needed, we can request the permission.

          ActivityCompat.requestPermissions(this,
                  new String[]{Manifest.permission.READ_CONTACTS},
                  GET_CONTACTS_PERMISSION_REQUEST_ID);
        }

      } else {
        getContactsPermissionCallback.granted();
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {
    switch (requestCode) {
      case GET_CONTACTS_PERMISSION_REQUEST_ID:
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          getContactsPermissionCallback.granted();
        } else {
          getContactsPermissionCallback.denied();
        }
        return;
    }
  }

  public interface PermissionCallback {

    void granted();

    void denied();

    void showRationale();
  }

}