import 'dart:convert';

import 'package:automacao_ar_condicionado/api/config.dart';
import 'package:http/http.dart';

class LoginService {
  bool isSuccessStatusCode(int number) => number >= 200 && number <= 299;

  Future<String?> login(String username, String password) async {
    final endpoint = Uri.parse('${ApiConfig.server}/auth');
    final payload = jsonEncode({'username': username, 'senha': password});
    final response = await post(endpoint,
        headers: {'Content-Type': 'application/json'}, body: payload);

    if (!isSuccessStatusCode(response.statusCode)) {
      // todo
      return null;
    }

    var json = jsonDecode(response.body);

    return json['token'];
  }

  Future<bool> register(String username, String password, String ip) async {
    var response = await post(Uri.parse('${ApiConfig.server}/usuario'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'nome': username, 'senha': password, 'ip': ip}));

    return isSuccessStatusCode(response.statusCode);
  }
}
