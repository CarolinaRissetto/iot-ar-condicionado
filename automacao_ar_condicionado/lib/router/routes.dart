import 'package:automacao_ar_condicionado/pages/login.dart';
import 'package:automacao_ar_condicionado/pages/main.dart';
import 'package:automacao_ar_condicionado/pages/register.dart';
import 'package:flutter/material.dart';

class RouteNames {
  static const loginPage = '/';
  static const homePage = '/home';
  static const registerPage = '/register';
}

class RouteConfig {
  static const String initial = RouteNames.loginPage;
  static final Map<String, Widget Function(BuildContext)> routes = {
    RouteNames.loginPage: (ctx) => const LoginPage(),
    RouteNames.homePage: (ctx) => const MyHomePage(title: 'Ar condicionado'),
    RouteNames.registerPage: (ctx) => const RegisterPage()
  };
}
