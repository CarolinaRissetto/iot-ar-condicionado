import 'package:automacao_ar_condicionado/context/agendamento.dart';
import 'package:automacao_ar_condicionado/context/auth.dart';
import 'package:automacao_ar_condicionado/router/routes.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (context) => Auth(),
        ),
        ChangeNotifierProxyProvider<Auth, Agendamento>(
          create: (ctx) => Agendamento(""),
          update: (ctx, auth, agendamento) => Agendamento(auth.token ?? ""),
        )
      ],
      child: Consumer<Auth>(
        builder: (ctx, auth, _) => MaterialApp(
          title: 'Flutter Demo',
          debugShowCheckedModeBanner: false,
          theme: ThemeData(
            scaffoldBackgroundColor: Colors.white,
            visualDensity: VisualDensity.adaptivePlatformDensity,
            useMaterial3: true,
          ),
          initialRoute: RouteConfig.initial,
          routes: RouteConfig.routes,
        ),
      ),
    );
  }
}
