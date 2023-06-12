import 'package:automacao_ar_condicionado/api/login.dart';
import 'package:flutter/material.dart';

class Auth with ChangeNotifier {
  String? token;

  isLoggedIn() => token == null;

  setToken(String value) {
    token = value;
    notifyListeners();
  }

  Future<void> login(String username, String password) async {
    final token = await LoginService().login(username, password);
    if (token != null) {
      setToken(token);
    }
  }
}
