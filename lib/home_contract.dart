import 'dart:async';
import 'contact.dart';

abstract class View {

  void showErrorMessage();

  void showContactsWithPhoneNumber(List<Contact> contacts);

  void showLoadingContactsInProgress();

  void showPermissionRationale();
}



abstract class Model {
  Future<PermissionState> canGetContacts();

  Future<List<Contact>> getContactsWithMobilePhoneNumber();

}

enum PermissionState {
  GRANTED,
  DENIED,
  SHOW_RATIONALE //  Refer https://developer.android.com/training/permissions/requesting.html#explain
}

abstract class Presenter {

  Future viewDisplayed();

}