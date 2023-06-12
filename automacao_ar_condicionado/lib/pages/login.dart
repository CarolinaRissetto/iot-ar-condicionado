import 'package:automacao_ar_condicionado/api/login.dart';
import 'package:automacao_ar_condicionado/context/auth.dart';
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

  String? _validator(String? value) {
    return value == null || value.isEmpty ? 'Campo obrigatório' : null;
  }

  void _submit() async {
    FocusScope.of(context).unfocus();
    if (_formKey.currentState == null || !_formKey.currentState!.validate())
      return;

    _formKey.currentState!.save();

    await Provider.of<Auth>(context, listen: false).login(
      _usernameController.text,
      _passwordController.text,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Padding(
      padding: const EdgeInsets.all(18.0),
      child: Center(
        child: Form(
            key: _formKey,
            child: Column(
              children: [
                TextFormField(
                  decoration:
                      const InputDecoration(label: Text('Nome de usuario')),
                  controller: _usernameController,
                  validator: _validator,
                ),
                TextFormField(
                  decoration:
                      const InputDecoration(label: Text('Nome de usuario')),
                  controller: _passwordController,
                  validator: _validator,
                ),
                ElevatedButton.icon(
                    onPressed: _submit,
                    icon: const Icon(Icons.login),
                    label: const Text('Login'))
              ],
            )),
      ),
    ));
  }

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    super.dispose();
  }
}
