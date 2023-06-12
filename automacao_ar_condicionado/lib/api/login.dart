import 'dart:convert';

import 'package:automacao_ar_condicionado/api/config.dart';
import 'package:http/http.dart';

class LoginService {
  bool isSuccessStatusCode(int number) => number >= 200 && number <= 299;

  Future<String?> login(String username, String password) async {
    final response = await post(Uri.parse(ApiConfig.server),
        body: jsonEncode({'username': username, 'password': password}));

    if (!isSuccessStatusCode(response.statusCode)) {
      // todo
      return null;
    }

    var json = jsonDecode(response.body);

    return json['token'];
  }
}
