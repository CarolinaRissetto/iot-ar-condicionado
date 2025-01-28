import 'package:automacao_ar_condicionado/context/auth.dart';
import 'package:automacao_ar_condicionado/router/routes.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();
  final _formKey = GlobalKey<FormState>();
  var loading = false;

  @override
  void initState() {
    loading = false;
    super.initState();
  }

  String? _validator(String? value) {
    return value == null || value.isEmpty ? 'Campo obrigatório' : null;
  }

  void _submit() async {
    FocusScope.of(context).unfocus();
    if (_formKey.currentState == null || !_formKey.currentState!.validate()) {
      return;
    }

    setState(() {
      loading = true;
    });

    _formKey.currentState!.save();

    final success = await Provider.of<Auth>(context, listen: false).login(
      _usernameController.text,
      _passwordController.text,
    );

    Navigator.of(context).pushReplacementNamed(
        success ? RouteNames.homePage : RouteNames.loginPage);
  }

  void _onClickRegister() {
    Navigator.of(context).pushNamed(RouteNames.registerPage);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Form(
            key: _formKey,
            child: Padding(
              padding:
                  const EdgeInsets.symmetric(horizontal: 20.0, vertical: 50.0),
              child: Column(
                children: [
                  TextFormField(
                    decoration:
                        const InputDecoration(label: Text('Nome de usuario')),
                    maxLength: 40,
                    controller: _usernameController,
                    validator: _validator,
                  ),
                  TextFormField(
                    obscureText: true,
                    enableSuggestions: false,
                    autocorrect: false,
                    decoration: const InputDecoration(label: Text('senha')),
                    maxLength: 10,
                    controller: _passwordController,
                    validator: _validator,
                  ),
                  Center(
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        ElevatedButton.icon(
                            onPressed: loading ? null : _submit,
                            icon: const Icon(Icons.login),
                            label: const Text('Entrar')),
                        const SizedBox(
                          width: 10,
                        ),
                        OutlinedButton.icon(
                            onPressed: loading ? null : _onClickRegister,
                            icon: const Icon(Icons.how_to_reg),
                            label: const Text('Criar conta'))
                      ],
                    ),
                  ),
                ],
              ),
            )),
      ),
    );
  }

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    super.dispose();
  }
}
