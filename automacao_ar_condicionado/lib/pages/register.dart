import 'package:automacao_ar_condicionado/api/login.dart';
import 'package:automacao_ar_condicionado/context/auth.dart';
import 'package:automacao_ar_condicionado/router/routes.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();
  final _ipController = TextEditingController();
  final _formKey = GlobalKey<FormState>();

  String? _validator(String? value) {
    return value == null || value.isEmpty ? 'Campo obrigatório' : null;
  }

  void _submit() async {
    FocusScope.of(context).unfocus();
    if (_formKey.currentState == null || !_formKey.currentState!.validate()) {
      return;
    }

    _formKey.currentState!.save();

    final success = await Provider.of<Auth>(context, listen: false).register(
        _usernameController.text, _passwordController.text, _ipController.text);

    Navigator.of(context).pushReplacementNamed(
        success ? RouteNames.loginPage : RouteNames.registerPage);
  }

  void _onClickLogin() {
    Navigator.of(context).pushNamed(RouteNames.loginPage);
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
                  maxLength: 40,
                  validator: _validator,
                ),
                TextFormField(
                  obscureText: true,
                  enableSuggestions: false,
                  autocorrect: false,
                  decoration: const InputDecoration(label: Text('Senha')),
                  controller: _passwordController,
                  maxLength: 10,
                  validator: _validator,
                ),
                TextFormField(
                  decoration: const InputDecoration(label: Text('IP Público')),
                  controller: _ipController,
                  maxLength: 40,
                  validator: _validator,
                ),
                ElevatedButton.icon(
                    onPressed: _submit,
                    icon: const Icon(Icons.how_to_reg),
                    label: const Text('Criar conta')),
                OutlinedButton.icon(
                    onPressed: _onClickLogin,
                    icon: const Icon(Icons.login),
                    label: const Text('Já tenho conta')),
              ],
            )),
      ),
    ));
  }

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    _ipController.dispose();
    super.dispose();
  }
}
