import 'dart:async';
import 'home_contract.dart';
import 'contact.dart';
import 'package:flutter/services.dart';


class HomeModel implements Model {

  static const _methodChannel = const MethodChannel('runtimepermissiontutorial/contacts');

  @override
  Future<PermissionState> canGetContacts() async {
    // TODO
    try {
      final int result = await _methodChannel.invokeMethod('hasPermission');
      return new Future.value(PermissionState.values.elementAt(result));
    } on PlatformException catch (e) {
      print('Exception ' + e.toString());
    }

    return new Future.value(PermissionState.DENIED);
  }

  @override
  Future<List<Contact>> getContactsWithMobilePhoneNumber() async {
    // TODO
    return new Future.value(null);
  }
}