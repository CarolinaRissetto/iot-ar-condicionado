import 'package:automacao_ar_condicionado/pages/login.dart';
import 'package:automacao_ar_condicionado/pages/main.dart';
import 'package:flutter/material.dart';

class RouteConfig {
  static const String initial = '/home';
  static final Map<String, Widget Function(BuildContext)> routes = {
    '/': (ctx) => const LoginPage(),
    '/home': (context) => const MyHomePage(title: 'Ar condicionado')
  };
}
